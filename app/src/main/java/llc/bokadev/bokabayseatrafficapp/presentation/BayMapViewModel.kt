package llc.bokadev.bokabayseatrafficapp.presentation

import android.app.Application
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import llc.amplitudo.flourish_V2.core.utils.Constants
import llc.bokadev.bokabayseatrafficapp.core.navigation.Navigator
import llc.bokadev.bokabayseatrafficapp.core.utils.Gps
import llc.bokadev.bokabayseatrafficapp.core.utils.MapItems
import llc.bokadev.bokabayseatrafficapp.core.utils.calculateCentroid
import llc.bokadev.bokabayseatrafficapp.core.utils.createGpsReceiver
import llc.bokadev.bokabayseatrafficapp.domain.model.Anchorage
import llc.bokadev.bokabayseatrafficapp.domain.model.AnchorageZone
import llc.bokadev.bokabayseatrafficapp.domain.model.Buoy
import llc.bokadev.bokabayseatrafficapp.domain.model.Checkpoint
import llc.bokadev.bokabayseatrafficapp.domain.model.MapItemFilters
import llc.bokadev.bokabayseatrafficapp.domain.model.Pipeline
import llc.bokadev.bokabayseatrafficapp.domain.model.ProhibitedAnchoringZone
import llc.bokadev.bokabayseatrafficapp.domain.model.ShipWreck
import llc.bokadev.bokabayseatrafficapp.domain.model.UnderwaterCable
import llc.bokadev.bokabayseatrafficapp.domain.repository.LocationRepository
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class BayMapViewModel @Inject constructor(
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val locationRepository: LocationRepository,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(GuideState())
    private var previouslySelectedLighthouseMarkerId: Int? = null
    private var previouslySelectedShipwreckMarkerId: Int? = null
    private var previouslySelectedProhibitedZoneMarkerId: Int? = null
    private var previouslySelectedAnchorageMarkerId: Int? = null
    private var previouslySelectedAnchorageZoneMarkerId: Int? = null
    private var previouslySelectedBuoyMarkerId: Int? = null


    private val _smoothedSpeed = MutableStateFlow(0f)
    val smoothedSpeed: StateFlow<Float> = _smoothedSpeed

    private val alpha = .5f // Smoothing factor for EMA (between 0 and 1, higher is more responsive)
    private var previousSpeed: Float = 0f
    private val stationaryThreshold = 0.5f // Threshold in m/s to consider the device as stationary
    private var isStationary: Boolean = true


    private val _launchIntentChannel = Channel<Intent>()
    val launchIntentChannel = _launchIntentChannel.receiveAsFlow()

    val gpsReceiver = createGpsReceiver(onGpsON = {
        state = state.copy(gpsState = Gps.ON)
        observeUserLocation()
    }, onGpsOFF = {
        state = state.copy(gpsState = Gps.OFF)
    })

    init {
        observeUserLocation()
    }

    private var buttonDebounceJob: Job? = null

    private fun buttonDebounce(onClick: () -> Unit) {
        buttonDebounceJob?.cancel()
        buttonDebounceJob = viewModelScope.launch {
            delay(100)
            onClick()
        }
    }

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.CheckpointSelected -> handleCheckpointSelected(event)

            is MapEvent.ShipWreckSelected -> {
                handleShipWreckSelected(event)
            }

            is MapEvent.ProhibitedAnchoringZoneSelected -> {
                handleProhibitedAnchoringZoneSelected(event)
            }

            is MapEvent.AnchorageSelected -> {
                handleAnchorageSelected(event)
            }

            is MapEvent.AnchorageZoneSelected -> {
                handleAnchorageZoneSelected(event)
            }

            is MapEvent.BuoySelected -> {
                handleBuoySelected(event)
            }

            is MapEvent.CreateUserMarker -> {
                state = state.copy(userMarker = event.marker)
            }

            is MapEvent.UpdateUserMarker -> {
                Timber.e("User marker updated Location")
                state.userLocation?.let {
                    state.userMarker?.position = it
                }

            }

            is MapEvent.ZoomUserLocation -> {
                state = state.copy(shouldZoomUserLocation = true)
            }

            is MapEvent.ResetSelection -> {
                resetSelection()
            }

            is MapEvent.ResetZoom -> {
                state = state.copy(shouldZoomUserLocation = false)
            }

            is MapEvent.AddLighthouseCheckpointMarker -> {
                state.lighthouseMarkers.add(event.marker)
            }


            is MapEvent.AddShipwreckCheckpointMarker -> {
                state.shipwreckMarkers.add(event.marker)
            }

            is MapEvent.AddProhibitedAnchoringZoneMarker -> {
                state.prohibitedAnchoringZoneMarkers.add(event.marker)
            }

            is MapEvent.AddAnchorageMarker -> {
                state.anchorageMarkers.add(event.marker)
            }

            is MapEvent.AddAnchorageZoneMarker -> {
                state.anchorageZoneMarkers.add(event.marker)
            }

            is MapEvent.AddBuoyMarker -> {
                state.buoyMarkers.add(event.marker)
            }

            is MapEvent.OnItemHide -> {
                when (event.mapItemTypeId) {
                    MapItems.LIGHTHOUSE.mapItemTypeId -> {
                        state.lighthouseMarkers.clear()
                    }

                    MapItems.SHIPWRECK.mapItemTypeId -> {
                        state.shipwreckMarkers.clear()
                    }

                    MapItems.PROHIBITED_ANCHORING_ZONE.mapItemTypeId -> {
                        state.prohibitedAnchoringZoneMarkers.clear()
                    }

                    MapItems.ANCHORAGE.mapItemTypeId -> {
                        state.anchorageMarkers.clear()
                    }

                    MapItems.LIGHTHOUSE.mapItemTypeId -> {
//                        state.lighthouses.clear()
                    }
                }
//                state.lighthouses.clear()
            }

            is MapEvent.UpdateGpsState -> {
                state = state.copy(gpsState = event.gpsState)
            }

            is MapEvent.OnPhoneClick -> {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:069069069")
                }
                viewModelScope.launch {
                    _launchIntentChannel.send(intent)
                }

            }

            is MapEvent.OnPreferencesSwitchClick -> {
                buttonDebounce {
                    state = when (event.mapItemTypeId) {
                        MapItems.LIGHTHOUSE.mapItemTypeId -> {
                            buttonDebounce {

                            }
                            state.copy(
                                mapItemFilters = state.mapItemFilters?.copy(
                                    lighthouses = event.checked
                                )
                            )

                        }

                        MapItems.SHIPWRECK.mapItemTypeId -> {
                            state.copy(
                                mapItemFilters = state.mapItemFilters?.copy(
                                    shipwrecks = event.checked
                                )
                            )
                        }

                        MapItems.PROHIBITED_ANCHORING_ZONE.mapItemTypeId -> {
                            state.copy(
                                mapItemFilters = state.mapItemFilters?.copy(
                                    prohibitedAnchoringZone = event.checked
                                )
                            )
                        }

                        MapItems.ANCHORAGE.mapItemTypeId -> {
                            state.copy(
                                mapItemFilters = state.mapItemFilters?.copy(
                                    anchorages = event.checked
                                )
                            )
                        }

                        MapItems.UNDERWATER_CABLE.mapItemTypeId -> {
                            state.copy(
                                mapItemFilters = state.mapItemFilters?.copy(
                                    underwaterCables = event.checked
                                )
                            )
                        }

                        MapItems.BUOY.mapItemTypeId -> {
                            state.copy(
                                mapItemFilters = state.mapItemFilters?.copy(
                                    buoys = event.checked
                                )
                            )
                        }

                        else -> {
                            state
                        }
                    }

                }


            }

            is MapEvent.OnCancelPreferencesClick -> {
                state = state.copy(
                    mapItemFilters = MapItemFilters(
                        lighthouses = true,
                        shipwrecks = true,
                        prohibitedAnchoringZone = true,
                        anchorages = true,
                        underwaterCables = true,
                        buoys = true
                    )
                )
            }

            is MapEvent.OnCompassIconClick -> {
                state =
                    state.copy(shouldEnableCustomPointToPoint = !state.shouldEnableCustomPointToPoint)
                if (!state.shouldEnableCustomPointToPoint) {
                    state = state.copy(
                        customPoints = mutableListOf(),
                        distanceTextOffset = Offset.Zero,
                        customPointsDistance = null
                    )
                }
                Timber.e("compas ${state.shouldEnableCustomPointToPoint}")
            }

            is MapEvent.ClearDistanceBetweenCustomPoints -> {
                state = state.copy(customPointsDistance = null)
            }

            is MapEvent.OnMapTwoPointsClick -> {
                val updatedCustomPoints = state.customPoints.toMutableList()
                if (updatedCustomPoints.size == 2) {
                    updatedCustomPoints[1] = event.position
                } else {
                    updatedCustomPoints.add(event.position)
                }

                state = state.copy(customPoints = updatedCustomPoints)

                if (updatedCustomPoints.size == 2) {
                    calculateDistanceBetweenPoints(updatedCustomPoints[0], updatedCustomPoints[1])
                } else {
                    state = state.copy(customPointsDistance = null)
                }
            }

            is MapEvent.OnMarkerRemovedTwoPoints -> {
                val updatedCustomPoints = state.customPoints.toMutableList()
                if (event.index >= 0 && event.index < updatedCustomPoints.size) {
                    updatedCustomPoints.removeAt(event.index)

                    state = state.copy(customPoints = updatedCustomPoints)

                    if (updatedCustomPoints.size < 2) {
                        state = state.copy(customPointsDistance = null)
                    } else if (updatedCustomPoints.size == 2) {
                        calculateDistanceBetweenPoints(
                            updatedCustomPoints[0],
                            updatedCustomPoints[1]
                        )
                    }
                }
            }

            is MapEvent.ClearCustomPoints -> {
                state = state.copy(
                    customPoints = mutableListOf(),
                    customPointsDistance = null,
                    distanceTextOffset = Offset.Zero,
                    shouldEnableCustomPointToPoint = false
                )
            }


            is MapEvent.OnDistanceTextOffsetChange -> {
                state = state.copy(distanceTextOffset = event.offset)
            }

            else -> {}
        }
    }


    private fun calculateDistance(checkpointLocation: LatLng) {
        val loc1 = Location(LocationManager.GPS_PROVIDER)
        val loc2 = Location(LocationManager.GPS_PROVIDER)
        loc1.latitude = checkpointLocation.latitude
        loc1.longitude = checkpointLocation.longitude
        loc2.latitude = state.userLocation?.latitude ?: 0.0
        loc2.longitude = state.userLocation?.longitude ?: 0.0
        state = state.copy(distanceToCheckpoint = loc1.distanceTo(loc2))

    }

    private fun calculateDistanceBetweenPoints(point1: LatLng, point2: LatLng) {
        val loc1 = Location(LocationManager.GPS_PROVIDER).apply {
            latitude = point1.latitude
            longitude = point1.longitude
        }

        val loc2 = Location(LocationManager.GPS_PROVIDER).apply {
            latitude = point2.latitude
            longitude = point2.longitude
        }

        val distance = loc1.distanceTo(loc2)
        state = state.copy(customPointsDistance = distance)
    }

    private fun observeUserLocation() {
        locationRepository.getLocationUpdates().catch { e -> e.printStackTrace() }
            .onEach { location ->
                updateDirection(location)
                Timber.e("Location vm ${location.latitude}, ${location.latitude}")
                val latitudeCurrent = location.latitude
                val longitudeCurrent = location.longitude
                state = state.copy(userLocation = LatLng(latitudeCurrent, longitudeCurrent))
                Timber.e("Location vm state ${state.userLocation?.latitude}, ${state.userLocation?.latitude}")
            }.launchIn(viewModelScope)
    }


    private fun calculateAzimuth(pointLocation: LatLng) {
        // Ensure current location is valid before calculating
        val userLocation = state.userLocation ?: return

        // Target location
        val targetLocation = Location("").apply {
            latitude = pointLocation.latitude
            longitude = pointLocation.longitude
        }

        // Current location
        val currentLocation = Location("").apply {
            latitude = userLocation.latitude
            longitude = userLocation.longitude
        }

        // Get the bearing from current to target
        val bearing: Float = currentLocation.bearingTo(targetLocation)

        // Correct the bearing to ensure it is within 0-360 degrees
        val correctedBearing = (bearing + 360) % 360

        // Update state with the calculated azimuth
        state = state.copy(azimuth = correctedBearing)

    }

    private fun handleCheckpointSelected(event: MapEvent.CheckpointSelected) {
        val marker = state.lighthouseMarkers.find { it.tag == event.checkpoint.id }
        val checkpoint = state.lighthouses.firstOrNull { it.id == event.checkpoint.id }

        if (checkpoint != null) {
            if (checkpoint.id == previouslySelectedLighthouseMarkerId) {
                resetSelection()
                // Deselect lighthouse
                checkpoint.isSelected = false
                state = state.copy(
                    isLighthouseMarkerSelected = false,
                    isLighthouseMarkerVisible = false,
                    selectedCheckpoint = null
                )
                marker?.hideInfoWindow()
                previouslySelectedLighthouseMarkerId = null
            } else {
                // Select new lighthouse
                checkpoint.isSelected = true
                state = state.copy(
                    selectedCheckpoint = checkpoint,
                    isLighthouseMarkerSelected = true,
                    isLighthouseMarkerVisible = true
                )
                calculateDistance(LatLng(checkpoint.latitude, checkpoint.longitude))
                calculateAzimuth(LatLng(checkpoint.latitude, checkpoint.longitude))
                marker?.showInfoWindow()
                previouslySelectedLighthouseMarkerId = checkpoint.id
            }
        }
    }


    private fun handleShipWreckSelected(event: MapEvent.ShipWreckSelected) {
        val marker = state.shipwreckMarkers.find { it.tag == event.shipwreck.id }
        val shipwreck = state.shipwrecks.firstOrNull { it.id == event.shipwreck.id }

        if (shipwreck != null) {
            if (shipwreck.id == previouslySelectedShipwreckMarkerId) {
                // Deselect shipwreck
                shipwreck.isSelected = false
                state = state.copy(
                    isShipwreckMarkerSelected = false,
                    isShipwreckMarkerVisible = false,
                    selectedShipwreck = null
                )
                marker?.hideInfoWindow()
                previouslySelectedShipwreckMarkerId = null
            } else {
                // Select new shipwreck
                shipwreck.isSelected = true
                state = state.copy(
                    selectedShipwreck = shipwreck,
                    isShipwreckMarkerSelected = true,
                    isShipwreckMarkerVisible = true
                )
                calculateDistance(LatLng(shipwreck.latitude, shipwreck.longitude))
                calculateAzimuth(LatLng(shipwreck.latitude, shipwreck.longitude))
                marker?.showInfoWindow()
                previouslySelectedShipwreckMarkerId = shipwreck.id
            }
        }
    }

    private fun handleProhibitedAnchoringZoneSelected(event: MapEvent.ProhibitedAnchoringZoneSelected) {
        val marker =
            state.prohibitedAnchoringZoneMarkers.find { it.tag == event.prohibitedAnchoringZone.id }
        val prohibitedAnchoringZone =
            state.prohibitedProhibitedAnchoringZones.firstOrNull { it.id == event.prohibitedAnchoringZone.id }

        if (prohibitedAnchoringZone != null) {
            if (prohibitedAnchoringZone.id == previouslySelectedProhibitedZoneMarkerId) {
                // Deselect prohibited anchoring zone
                prohibitedAnchoringZone.isSelected = false
                state = state.copy(
                    isProhibitedAnchoringZoneMarkerSelected = false,
                    isProhibitedAnchoringZoneVisible = false,
                    selectedProhibitedProhibitedAnchoringZone = null
                )
                marker?.hideInfoWindow()
                previouslySelectedProhibitedZoneMarkerId = null
            } else {
                // Select new prohibited anchoring zone
                prohibitedAnchoringZone.isSelected = true
                state = state.copy(
                    selectedProhibitedProhibitedAnchoringZone = prohibitedAnchoringZone,
                    isProhibitedAnchoringZoneMarkerSelected = true,
                    isProhibitedAnchoringZoneVisible = true
                )
                marker?.showInfoWindow()
                previouslySelectedProhibitedZoneMarkerId = prohibitedAnchoringZone.id
            }
        }
    }

    private fun handleAnchorageSelected(event: MapEvent.AnchorageSelected) {
        val marker = state.anchorageMarkers.find { it.tag == event.anchorage.id }
        val anchorage = state.anchorages.firstOrNull { it.id == event.anchorage.id }

        if (anchorage != null) {
            if (anchorage.id == previouslySelectedAnchorageMarkerId) {
                // Deselect anchorage
                anchorage.isSelected = false
                state = state.copy(
                    isAnchorageMarkerSelected = false,
                    isAnchorageVisible = false,
                    selectedAnchorage = null
                )
                marker?.hideInfoWindow()
                previouslySelectedAnchorageMarkerId = null
            } else {
                // Select new anchorage
                anchorage.isSelected = true
                state = state.copy(
                    selectedAnchorage = anchorage,
                    isAnchorageMarkerSelected = true,
                    isAnchorageVisible = true
                )
                calculateDistance(LatLng(anchorage.latitude, anchorage.longitude))
                calculateAzimuth(LatLng(anchorage.latitude, anchorage.longitude))
                marker?.showInfoWindow()
                previouslySelectedAnchorageMarkerId = anchorage.id
            }
        }
    }

    private fun handleAnchorageZoneSelected(event: MapEvent.AnchorageZoneSelected) {
        val marker = state.anchorageZoneMarkers.find { it.tag == event.anchorageZone.id }
        val anchorageZone = state.anchorageZones.firstOrNull { it.id == event.anchorageZone.id }

        if (anchorageZone != null) {
            if (anchorageZone.id == previouslySelectedAnchorageZoneMarkerId) {
                // Deselect anchorageZone
                anchorageZone.isSelected = false
                state = state.copy(
                    isAnchorageZoneMarkerSelected = false,
                    isAnchorageZoneVisible = false,
                    selectedAnchorageZone = null
                )
                marker?.hideInfoWindow()
                previouslySelectedAnchorageZoneMarkerId = null
            } else {
                // Select new anchorageZone
                anchorageZone.isSelected = true
                state = state.copy(
                    selectedAnchorageZone = anchorageZone,
                    isAnchorageZoneMarkerSelected = true,
                    isAnchorageZoneVisible = true
                )
                val center = calculateCentroid(anchorageZone.points)
                calculateDistance(LatLng(center.latitude, center.longitude))
                calculateAzimuth(LatLng(center.latitude, center.longitude))
                marker?.showInfoWindow()
                previouslySelectedAnchorageZoneMarkerId = anchorageZone.id
            }
        }
    }

    private fun handleBuoySelected(event: MapEvent.BuoySelected) {
        val marker = state.buoyMarkers.find { it.tag == event.buoy.id }
        val buoy = state.buoys.firstOrNull { it.id == event.buoy.id }

        if (buoy != null) {
            if (buoy.id == previouslySelectedAnchorageZoneMarkerId) {
                // Deselect buoy
                buoy.isSelected = false
                state = state.copy(
                    isBuoyMarkerSelected = false,
                    isBuoyVisible = false,
                    selectedBuoy = null
                )
                marker?.hideInfoWindow()
                previouslySelectedAnchorageZoneMarkerId = null
            } else {
                // Select new buoy
                buoy.isSelected = true
                state = state.copy(
                    selectedBuoy = buoy,
                    isBuoyMarkerSelected = true,
                    isBuoyVisible = true
                )
                calculateDistance(LatLng(buoy.coordinates.latitude, buoy.coordinates.longitude))
                calculateAzimuth(LatLng(buoy.coordinates.latitude, buoy.coordinates.longitude))
                marker?.showInfoWindow()
                previouslySelectedAnchorageZoneMarkerId = buoy.id
            }
        }
    }

    private fun resetSelection() {
        state = state.copy(
            isAnchorageMarkerSelected = false,
            selectedCheckpoint = null,
            selectedShipwreck = null,
            selectedProhibitedProhibitedAnchoringZone = null,
            selectedAnchorage = null,
            isShipwreckMarkerSelected = false,
            isLighthouseMarkerSelected = false,
            isProhibitedAnchoringZoneMarkerSelected = false,
            isProhibitedAnchoringZoneVisible = false,
            isLighthouseMarkerVisible = false,
            isShipwreckMarkerVisible = false,
            isAnchorageVisible = false,
            selectedAnchorageZone = null,
            isAnchorageZoneMarkerSelected = false,
            isAnchorageZoneVisible = false,
            isBuoyMarkerSelected = false,
            isBuoyVisible = false,
            selectedBuoy = null

        )
    }

    private fun updateDirection(location: Location) {
        val bearing = location.bearing // Bearing in degrees

        // Convert bearing to a readable direction
        val direction = when {
            bearing >= 337.5 || bearing < 22.5 -> "N"
            bearing in 22.5..67.5 -> "NE"
            bearing in 67.5..112.5 -> "E"
            bearing in 112.5..157.5 -> "SE"
            bearing in 157.5..202.5 -> "S"
            bearing in 202.5..247.5 -> "SW"
            bearing in 247.5..292.5 -> "W"
            bearing in 292.5..337.5 -> "NW"
            else -> "Unknown"
        }
        Timber.e("DIRECTION $direction")
        state = state.copy(userCourseOfMovement = direction)
    }

    fun updateSpeed(rawSpeed: Float, acceleration: FloatArray) {
        viewModelScope.launch {
            // Check if the device is stationary using accelerometer data
            isStationary = isDeviceStationary(acceleration)

            // Apply threshold and smoothing only if not stationary
            val speedToUse = if (isStationary) 0f else rawSpeed

            // Apply EMA smoothing
            val smoothedSpeedValue = alpha * speedToUse + (1 - alpha) * previousSpeed

            // If the smoothed speed is below the threshold, consider the device stationary
            val finalSpeed =
                if (smoothedSpeedValue < stationaryThreshold) 0f else smoothedSpeedValue

            // Update the state
            previousSpeed = finalSpeed
            _smoothedSpeed.emit(finalSpeed)
        }
    }

    private fun isDeviceStationary(acceleration: FloatArray): Boolean {
        val accelerationMagnitude = Math.sqrt(
            (acceleration[0] * acceleration[0] + acceleration[1] * acceleration[1] + acceleration[2] * acceleration[2]).toDouble()
        ).toFloat()

        // Consider the device stationary if the magnitude is close to 9.8 (gravity) for some time
        return accelerationMagnitude < 10.5f && accelerationMagnitude > 9.0f
    }


}


sealed class MapEvent() {
    data class CheckpointSelected(
        val checkpoint: Checkpoint
    ) : MapEvent()

    data class ShipWreckSelected(
        val shipwreck: ShipWreck
    ) : MapEvent()

    data class ProhibitedAnchoringZoneSelected(
        val prohibitedAnchoringZone: ProhibitedAnchoringZone
    ) : MapEvent()

    data class AnchorageSelected(
        val anchorage: Anchorage
    ) : MapEvent()

    data class AnchorageZoneSelected(
        val anchorageZone: AnchorageZone
    ) : MapEvent()

    data class BuoySelected(
        val buoy: Buoy
    ) : MapEvent()


    object ResetZoom : MapEvent()
    data class LocationsEnabled(val isEnabled: Boolean) : MapEvent()
    data class CreateUserMarker(val marker: Marker) : MapEvent()
    data class AddLighthouseCheckpointMarker(val marker: Marker) : MapEvent()
    data class AddShipwreckCheckpointMarker(val marker: Marker) : MapEvent()
    data class AddProhibitedAnchoringZoneMarker(val marker: Marker) : MapEvent()
    data class AddAnchorageMarker(val marker: Marker) : MapEvent()
    data class AddAnchorageZoneMarker(val marker: Marker) : MapEvent()
    data class AddBuoyMarker(val marker: Marker) : MapEvent()
    object UpdateUserMarker : MapEvent()
    data class UpdateGpsState(val gpsState: Gps) : MapEvent()
    object ZoomUserLocation : MapEvent()
    object ResetSelection : MapEvent()
    data object OnPhoneClick : MapEvent()
    data class OnPreferencesSwitchClick(val checked: Boolean, val mapItemTypeId: Int?) :
        MapEvent()

    object OnConfirmPreferencesClick : MapEvent()
    object OnCancelPreferencesClick : MapEvent()
    data class OnItemHide(val mapItemTypeId: Int) : MapEvent()
    object OnCompassIconClick : MapEvent()
    object ClearDistanceBetweenCustomPoints : MapEvent()
    data class OnMapTwoPointsClick(val position: LatLng) : MapEvent()
    data class OnMarkerRemovedTwoPoints(val index: Int) : MapEvent()
    object ClearCustomPoints : MapEvent()
    data class OnDistanceTextOffsetChange(val offset: Offset) : MapEvent()
}

data class GuideState(
    val azimuth: Float? = null,
    val lighthouses: MutableList<Checkpoint> = Constants.lighthouses,
    val shipwrecks: MutableList<ShipWreck> = Constants.shipwrecks,
    val prohibitedProhibitedAnchoringZones: MutableList<ProhibitedAnchoringZone> = Constants.prohibitedProhibitedAnchoringZones,
    val anchorages: MutableList<Anchorage> = Constants.anchorages,
    val underWaterCables: MutableList<UnderwaterCable> = Constants.underwaterCables,
    val anchorageZones: MutableList<AnchorageZone> = Constants.anchorageZones,
    val pipelines: MutableList<Pipeline> = Constants.pipelines,
    val buoys: MutableList<Buoy> = Constants.buoys,
    val userLocation: LatLng? = null,
    val distanceToCheckpoint: Float? = null,
    val userMarker: Marker? = null,
    val shouldZoomUserLocation: Boolean = false,
    val isGpsOn: Boolean = false,
    val lighthouseMarkers: MutableList<Marker> = mutableStateListOf(),
    val shipwreckMarkers: MutableList<Marker> = mutableStateListOf(),
    val prohibitedAnchoringZoneMarkers: MutableList<Marker> = mutableStateListOf(),
    val anchorageMarkers: MutableList<Marker> = mutableStateListOf(),
    val anchorageZoneMarkers: MutableList<Marker> = mutableStateListOf(),
    val buoyMarkers: MutableList<Marker> = mutableStateListOf(),
    val gpsState: Gps = Gps.OFF,
    val selectedCheckpoint: Checkpoint? = null,
    val selectedShipwreck: ShipWreck? = null,
    val selectedProhibitedProhibitedAnchoringZone: ProhibitedAnchoringZone? = null,
    val selectedAnchorage: Anchorage? = null,
    val selectedAnchorageZone: AnchorageZone? = null,
    val selectedBuoy: Buoy? = null,
    val isLighthouseMarkerSelected: Boolean = false,
    val isShipwreckMarkerSelected: Boolean = false,
    val isProhibitedAnchoringZoneMarkerSelected: Boolean = false,
    val isAnchorageMarkerSelected: Boolean = false,
    val isAnchorageZoneMarkerSelected: Boolean = false,
    val isBuoyMarkerSelected: Boolean = false,
    val isLighthouseMarkerVisible: Boolean = false,
    val isShipwreckMarkerVisible: Boolean = false,
    val isBuoyVisible: Boolean = false,
    val isProhibitedAnchoringZoneVisible: Boolean = false,
    val isAnchorageVisible: Boolean = false,
    val isAnchorageZoneVisible: Boolean = false,
    val mapItemFilters: MapItemFilters? = MapItemFilters(true, true, true, true, true, true),
    val userSpeed: Double? = null,
    val userCourseOfMovement: String? = String(),
    val shouldEnableCustomPointToPoint: Boolean = false,
    val customPoints: MutableList<LatLng> = mutableStateListOf(),
    val customPointsDistance: Float? = null,
    val distanceTextOffset: Offset = Offset.Zero,

    )