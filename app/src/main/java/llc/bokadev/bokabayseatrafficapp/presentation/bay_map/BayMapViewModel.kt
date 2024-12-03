package llc.bokadev.bokabayseatrafficapp.presentation.bay_map

import android.app.Application
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import llc.amplitudo.flourish_V2.core.utils.Constants
import llc.bokadev.bokabayseatrafficapp.core.navigation.Navigator
import llc.bokadev.bokabayseatrafficapp.core.navigation.Screen
import llc.bokadev.bokabayseatrafficapp.core.utils.Gps
import llc.bokadev.bokabayseatrafficapp.core.utils.MapItems
import llc.bokadev.bokabayseatrafficapp.core.utils.calculateCentroid
import llc.bokadev.bokabayseatrafficapp.core.utils.createGpsReceiver
import llc.bokadev.bokabayseatrafficapp.core.utils.toKnots
import llc.bokadev.bokabayseatrafficapp.data.remote.dto.ElevationResponse
import llc.bokadev.bokabayseatrafficapp.domain.model.Anchorage
import llc.bokadev.bokabayseatrafficapp.domain.model.AnchorageZone
import llc.bokadev.bokabayseatrafficapp.domain.model.Buoy
import llc.bokadev.bokabayseatrafficapp.domain.model.Checkpoint
import llc.bokadev.bokabayseatrafficapp.domain.model.Depth
import llc.bokadev.bokabayseatrafficapp.domain.model.MapItemFilters
import llc.bokadev.bokabayseatrafficapp.domain.model.Pipeline
import llc.bokadev.bokabayseatrafficapp.domain.model.ProhibitedAnchoringZone
import llc.bokadev.bokabayseatrafficapp.domain.model.ShipWreck
import llc.bokadev.bokabayseatrafficapp.domain.model.UnderwaterCable
import llc.bokadev.bokabayseatrafficapp.domain.repository.AppRepository
import llc.bokadev.bokabayseatrafficapp.domain.repository.DataStoreRepository
import llc.bokadev.bokabayseatrafficapp.domain.repository.LocationRepository
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class BayMapViewModel @Inject constructor(
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val locationRepository: LocationRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val repository: AppRepository,
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

    private var totalDistance = 0.0f
    private var previousLocation: Location? = null
    val speedList: ArrayList<Float> = arrayListOf()
    private var staticCounter = 0
    private val staticSpeedThreshold = 0.5f // Speed threshold to consider user static
    private val staticDistanceThreshold = 1.5f // Distance threshold to consider user static
    private val staticCheckIterations = 3 // Number of checks to confirm user is static


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
        viewModelScope.launch {
            state = state.copy(
                preferredSpeedUnit = dataStoreRepository.getPreferredSpeedUnit().first(),
                showCursorInstruction = dataStoreRepository.getShouldShowCursorInstruction().first()
            )
        }

    }

    fun getPreferredUnit() {
        viewModelScope.launch {
            state = state.copy(
                preferredSpeedUnit = dataStoreRepository.getPreferredSpeedUnit().first()
            )
        }
    }

    private var buttonDebounceJob: Job? = null

    private fun buttonDebounce(onClick: () -> Unit) {
        buttonDebounceJob?.cancel()
        buttonDebounceJob = viewModelScope.launch {
            delay(100)
            onClick()
        }
    }


    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
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
                    data = Uri.parse("tel:129")
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
                    state.copy(
                        shouldEnableCustomPointToPoint = !state.shouldEnableCustomPointToPoint,
                        shouldEnableCustomRoute = false,
                        customRouteDistance = null,
                        customRouteAzimuth = null,
                        customRoutePoints = mutableListOf(),
                        customRouteConsecutivePointsAzimuth = mutableListOf(),
                        customRouteConsecutivePointsDistance = mutableListOf()
                    )
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
                state = state.copy(customPointsDistance = null, customPointsAzimuth = null)
            }

            is MapEvent.OnMapTwoPointsClick -> {
                val updatedCustomPoints = state.customPoints.toMutableList()

                // Add the new point to the list
                updatedCustomPoints.add(event.position)

                // Initialize the total distance
                var totalDistance = 0f

                // Lists to store consecutive distances and azimuths
                val updatedConsecutiveDistances = mutableListOf<Float>()
                val updatedConsecutiveAzimuths = mutableListOf<Float>()

                // Calculate distances and azimuths between consecutive points
                if (updatedCustomPoints.size > 1) {
                    for (i in 0 until updatedCustomPoints.size - 1) {
                        val point1 = updatedCustomPoints[i]
                        val point2 = updatedCustomPoints[i + 1]

                        // Calculate distance and azimuth
                        val distance = calculateDistanceBetweenCustomRoutePoints(point1, point2)
                        val azimuth = calculateAzimuthBetweenCustomRoutePoints(point1, point2)

                        // Accumulate total distance
                        totalDistance += distance

                        // Store consecutive values
                        updatedConsecutiveDistances.add(distance)
                        updatedConsecutiveAzimuths.add(azimuth)
                    }
                }

                // Update state with calculated values
                state = state.copy(
                    customPoints = updatedCustomPoints,
                    customPointsDistance = if (updatedCustomPoints.size > 1) totalDistance else null,
                    customConsecutivePointsDistance = updatedConsecutiveDistances, // Consecutive distances
                    customConsecutivePointsAzimuth = updatedConsecutiveAzimuths,  // Consecutive azimuths
                    customPointsAzimuth = if (updatedCustomPoints.size == 2) {
                        calculateAzimuthBetweenCustomRoutePoints(
                            updatedCustomPoints[0],
                            updatedCustomPoints[1]
                        )
                    } else null // Only set azimuth when there are exactly 2 points
                )
            }


            is MapEvent.OnMarkerRemovedTwoPoints -> {
                val updatedCustomPoints = state.customPoints.toMutableList()
                val updatedConsecutiveDistances =
                    state.customConsecutivePointsDistance.toMutableList()
                val updatedConsecutiveAzimuths =
                    state.customConsecutivePointsAzimuth.toMutableList()

                if (event.index in updatedCustomPoints.indices) {
                    // Remove the point
                    updatedCustomPoints.removeAt(event.index)

                    // Clear and recalculate distances and azimuths
                    updatedConsecutiveDistances.clear()
                    updatedConsecutiveAzimuths.clear()

                    var totalDistance = 0f

                    if (updatedCustomPoints.size > 1) {
                        for (i in 0 until updatedCustomPoints.size - 1) {
                            val point1 = updatedCustomPoints[i]
                            val point2 = updatedCustomPoints[i + 1]

                            // Recalculate distance and azimuth
                            val distance = calculateDistanceBetweenCustomRoutePoints(point1, point2)
                            val azimuth = calculateAzimuthBetweenCustomRoutePoints(point1, point2)

                            updatedConsecutiveDistances.add(distance)
                            updatedConsecutiveAzimuths.add(azimuth)

                            // Accumulate total distance
                            totalDistance += distance
                        }

                        // Update state with recalculated values
                        state = state.copy(
                            customPoints = updatedCustomPoints,
                            customPointsDistance = totalDistance,
                            customConsecutivePointsDistance = updatedConsecutiveDistances,
                            customConsecutivePointsAzimuth = updatedConsecutiveAzimuths,
                            customPointsAzimuth = if (updatedCustomPoints.size == 2) {
                                calculateAzimuthBetweenCustomRoutePoints(
                                    updatedCustomPoints[0],
                                    updatedCustomPoints[1]
                                )
                            } else null // Only set azimuth when there are exactly 2 points
                        )
                    } else {
                        // If fewer than two points remain, reset distances and azimuths
                        state = state.copy(
                            customPoints = updatedCustomPoints,
                            customPointsDistance = null,
                            customConsecutivePointsDistance = emptyList(),
                            customConsecutivePointsAzimuth = emptyList(),
                            customPointsAzimuth = null // Reset azimuth
                        )
                    }
                } else {
                    Timber.e("Invalid index ${event.index} for custom points size ${updatedCustomPoints.size}")
                }
            }


            is MapEvent.ClearCustomPoints -> {
                // Reset everything when clearing custom points
                state = state.copy(
                    customPoints = mutableListOf(),
                    customPointsDistance = null,
                    customPointsAzimuth = null,
                    distanceTextOffset = Offset.Zero,
                    shouldEnableCustomPointToPoint = false
                )
            }


            is MapEvent.OnDistanceTextOffsetChange -> {
                state = state.copy(distanceTextOffset = event.offset)
            }

            is MapEvent.OnRouteIconClick -> {
                state =
                    state.copy(
                        shouldEnableCustomRoute = !state.shouldEnableCustomRoute,
                        customPoints = mutableListOf(),
                        customPointsDistance = null,
                        customPointsAzimuth = null,
                        distanceTextOffset = Offset.Zero,
                        shouldEnableCustomPointToPoint = false
                    )
                if (!state.shouldEnableCustomRoute) {
                    state = state.copy(
                        customRoutePoints = mutableListOf(),
                        distanceTextOffset = Offset.Zero,
                        customPointsDistance = null
                    )
                }
            }

            is MapEvent.ClearCustomRouteDistance -> {
                state = state.copy(customRouteAzimuth = null, customRouteDistance = null)
            }

            is MapEvent.OnMapCustomRouteClick -> {
                val updatedRoutePoints = state.customRoutePoints.toMutableList()

                // Add the new point to the list of custom route points
                updatedRoutePoints.add(event.position)

                // Initialize the total distance to 0
                var totalDistance = 0f

                // List to store consecutive distances between points
                val updatedConsecutiveDistances = mutableListOf<Float>()
                val updatedConsecutiveAzimuths = mutableListOf<Float>()

                // If there are at least two points, calculate distances between each pair of consecutive points
                if (updatedRoutePoints.size > 1) {
                    for (i in 0 until updatedRoutePoints.size - 1) {
                        val point1 = updatedRoutePoints[i]
                        val point2 = updatedRoutePoints[i + 1]

                        // Calculate the distance between the consecutive points
                        val distance = calculateDistanceBetweenCustomRoutePoints(point1, point2)
                        val azimuth = calculateAzimuthBetweenCustomRoutePoints(point1, point2)

                        // Add to the total distance
                        totalDistance += distance

                        // Store the consecutive distance
                        updatedConsecutiveDistances.add(distance)
                        updatedConsecutiveAzimuths.add(azimuth)
                    }

                    // Update the state with the new distances and updated points
                    state = state.copy(
                        customRoutePoints = updatedRoutePoints,
                        customRouteDistance = totalDistance,  // Updated total distance
                        customRouteConsecutivePointsDistance = updatedConsecutiveDistances, // List of consecutive distances
                        customRouteConsecutivePointsAzimuth = updatedConsecutiveAzimuths
                    )
                } else {
                    // If there's only one point, reset the distances
                    state = state.copy(
                        customRoutePoints = updatedRoutePoints,
                        customRouteDistance = null,  // No total distance yet
                        customRouteConsecutivePointsDistance = mutableListOf(), // No consecutive distances yet
                        customRouteConsecutivePointsAzimuth = mutableListOf()
                    )
                }
            }


            is MapEvent.OnMarkerRemovedCustomRoute -> {
                // Create NEW mutable lists of points and distances from the immutable state
                val updatedCustomRoutePoints = state.customRoutePoints.toMutableList()
                val updatedConsecutiveDistances =
                    state.customRouteConsecutivePointsDistance.toMutableList()
                val updatedConsecutiveAzimuths =
                    state.customRouteConsecutivePointsAzimuth.toMutableList()

                if (event.index >= 0 && event.index < updatedCustomRoutePoints.size) {
                    // Remove the marker (point) at the specified index
                    updatedCustomRoutePoints.removeAt(event.index)

                    // Check if there are still enough points to calculate distances
                    if (updatedCustomRoutePoints.size > 1) {
                        when {
                            // If the removed point is the first one, remove the first consecutive distance
                            event.index == 0 -> {
                                updatedConsecutiveDistances.removeAt(0)
                                updatedConsecutiveAzimuths.removeAt(0)
                            }
                            // If the removed point is the last one, remove the last consecutive distance
                            event.index == updatedCustomRoutePoints.size -> {
                                updatedConsecutiveDistances.removeAt(updatedConsecutiveDistances.lastIndex)
                                updatedConsecutiveAzimuths.removeAt(updatedConsecutiveAzimuths.lastIndex)
                            }

                            // If the removed point is in the middle, recalculate the distance
                            else -> {
                                // Remove the previous distance (between the point before the removed one and the removed point)
                                updatedConsecutiveDistances.removeAt(event.index - 1)
                                updatedConsecutiveAzimuths.removeAt(event.index - 1)

                                // Now calculate the new distance between the previous point and the next point
                                if (event.index < updatedCustomRoutePoints.size) {
                                    val prevPoint = updatedCustomRoutePoints[event.index - 1]
                                    val nextPoint = updatedCustomRoutePoints[event.index]
                                    val newDistance = calculateDistanceBetweenCustomRoutePoints(
                                        prevPoint,
                                        nextPoint
                                    )
                                    val newAzimuth = calculateAzimuthBetweenCustomRoutePoints(
                                        prevPoint,
                                        nextPoint
                                    )

                                    // Replace the next distance with the recalculated distance
                                    updatedConsecutiveDistances[event.index - 1] = newDistance
                                    updatedConsecutiveAzimuths[event.index - 1] = newAzimuth
                                }
                            }
                        }

                        // **Reindex the entire points list after removing an item**
                        val reindexedCustomRoutePoints =
                            updatedCustomRoutePoints.mapIndexed { index, point ->
                                point // Rebuild the list starting from 0
                            }


                        // Recalculate the total distance after the marker is removed
                        val newTotalDistance = updatedConsecutiveDistances.sum()

                        // Create new immutable lists to trigger recomposition
                        val newCustomRoutePoints =
                            reindexedCustomRoutePoints.toList() // Immutable copy
                        val newCustomRouteConsecutiveDistances =
                            updatedConsecutiveDistances.toList() // Immutable copy
                        val newCustomRouteConsecutiveAzimuths = updatedConsecutiveAzimuths.toList()

                        // Update the state with new lists and distances
                        state = state.copy(
                            customRoutePoints = newCustomRoutePoints, // Updated points
                            customRouteConsecutivePointsDistance = newCustomRouteConsecutiveDistances, // Updated distances
                            customRouteDistance = newTotalDistance, // Recalculate total distance
                            customRouteConsecutivePointsAzimuth = newCustomRouteConsecutiveAzimuths,
                        )
                    } else {
                        // If there are fewer than two points left, reset distances
                        state = state.copy(
                            customRoutePoints = updatedCustomRoutePoints.toList(), // Immutable copy of points
                            customRouteConsecutivePointsDistance = emptyList(), // Reset distances to an empty list
                            customRouteDistance = null, // No total distance left
                            customRouteConsecutivePointsAzimuth = emptyList()
                        )
                    }
                }
            }


            is MapEvent.ClearCustomRoutePoints -> {
                state = state.copy(
                    customRoutePoints = mutableListOf(),
                    customRouteDistance = null,
                    distanceTextOffset = Offset.Zero,
                    shouldEnableCustomRoute = false,
                    customRouteConsecutivePointsDistance = mutableListOf(),
                    customRouteConsecutivePointsAzimuth = mutableListOf()
                )
            }

            is MapEvent.OnCourseChange -> {
                Timber.e("DA LI ${state.userCourseOfMovement}, ${state.userCourseOfMovementAzimuth}")
                state = state.copy(
                    userCourseOfMovement = event.direction,
                    userCourseOfMovementAzimuth = event.angle.toFloat()
                )
            }

            is MapEvent.OnMoreClick -> {
                viewModelScope.launch {
                    navigator.navigateTo(Screen.MoreScreen.route)
                }
            }

            is MapEvent.OnCursorActive -> {
                state =
                    state.copy(cursorLatLng = event.cursorLocation)
                calculateDistanceFromCursor(event.cursorLocation)
                calculateAzimuthFromCursor(event.cursorLocation)
                Timber.e("cursor called ${state.distanceFromCursor}, azimuth ${state.azimuthFromCursor}")
            }

            is MapEvent.OnCursorClear -> {
                state = state.copy(
                    distanceFromCursor = null,
                    azimuthFromCursor = null,
                    distanceTextOffset = Offset.Zero,
                    cursorLatLng = null
                )
            }

            is MapEvent.DismissCursorDialog -> {
                state = state.copy(showCursorInstruction = false)
            }

            is MapEvent.OnDontShowAgainClick -> {
                viewModelScope.launch {
                    dataStoreRepository.saveShouldShowCursorInstruction(false)
                }.invokeOnCompletion {
                    viewModelScope.launch {
                        state = state.copy(
                            showCursorInstruction = dataStoreRepository.getShouldShowCursorInstruction()
                                .first()
                        )
                    }

                }
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


    private fun calculateDistanceFromCursor(checkpointLocation: LatLng) {
        val loc1 = Location(LocationManager.GPS_PROVIDER)
        val loc2 = Location(LocationManager.GPS_PROVIDER)
        loc1.latitude = checkpointLocation.latitude
        loc1.longitude = checkpointLocation.longitude
        loc2.latitude = state.userLocation?.latitude ?: 0.0
        loc2.longitude = state.userLocation?.longitude ?: 0.0
        state = state.copy(distanceFromCursor = loc1.distanceTo(loc2))

    }

    private fun calculateAzimuthBetweenPoints(point1: LatLng, point2: LatLng) {


        val loc1 = Location(LocationManager.GPS_PROVIDER).apply {
            latitude = point1.latitude
            longitude = point1.longitude
        }

        val loc2 = Location(LocationManager.GPS_PROVIDER).apply {
            latitude = point2.latitude
            longitude = point2.longitude
        }

        val bearing = loc1.bearingTo(loc2)
        val correctedBearing = (bearing + 360) % 360
        state = state.copy(customPointsAzimuth = correctedBearing)
    }

    private fun calculateDistanceBetweenCustomRoutePoints(point1: LatLng, point2: LatLng): Float {
        val loc1 = Location(LocationManager.GPS_PROVIDER).apply {
            latitude = point1.latitude
            longitude = point1.longitude
        }

        val loc2 = Location(LocationManager.GPS_PROVIDER).apply {
            latitude = point2.latitude
            longitude = point2.longitude
        }

        // Return the calculated distance
        return loc1.distanceTo(loc2)
    }


    private fun calculateAzimuthBetweenCustomRoutePoints(point1: LatLng, point2: LatLng): Float {
        val loc1 = Location(LocationManager.GPS_PROVIDER).apply {
            latitude = point1.latitude
            longitude = point1.longitude
        }

        val loc2 = Location(LocationManager.GPS_PROVIDER).apply {
            latitude = point2.latitude
            longitude = point2.longitude
        }


        val bearing: Float = loc1.bearingTo(loc2)

        // Correct the bearing to ensure it is within 0-360 degrees
        val correctedBearing = (bearing + 360) % 360

        // Update state with the calculated azimuth
        return correctedBearing
        // Return the calculated distance

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
//                updateDirection(location)
                Timber.e("Location vm ${location.latitude}, ${location.latitude}")
                val latitudeCurrent = location.latitude
                val longitudeCurrent = location.longitude
                state = state.copy(userLocation = LatLng(latitudeCurrent, longitudeCurrent))
                Timber.e("Location vm state ${state.userLocation?.latitude}, ${state.userLocation?.latitude}")

                val speed = location.speed
                val accuracy = location.accuracy
                Timber.e("LOCATION ACCURACY $accuracy")
                Timber.e(
                    "LOCATION SPEED ${
                        speed.toKnots()
                    }"
                )
                if (!state.isUserStatic) {
                    if (accuracy > 3.0f && accuracy < 9f) {
                        speedList.add(speed)
                    }
                }
                Timber.e("SPEED LIST $speedList")


                state = state.copy(locationAccuracy = accuracy)
                val averageSpeed = calculateAverageSpeed()

                if (previousLocation != null) {
                    val distance = previousLocation?.distanceTo(location)
                    if (distance != null) {
                        totalDistance += distance
                    }

                    if (distance != null) {
                        if (speed < staticSpeedThreshold && distance < staticDistanceThreshold) {

                            staticCounter++
                            if (staticCounter >= staticCheckIterations) {
                                state = state.copy(isUserStatic = true)
                                if (speedList.isNotEmpty()) speedList.clear()
                            }
                        } else {
                            staticCounter = 0
                            state = state.copy(isUserStatic = false)
                        }
                    }
                }


                previousLocation = location

                state = state.copy(userMovementSpeed = averageSpeed)


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

    private fun calculateAzimuthFromCursor(pointLocation: LatLng) {
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
        state = state.copy(azimuthFromCursor = correctedBearing)

    }

    private fun calculateAverageSpeed(): Float {
        var sum = 0.0f
        for (speed in speedList) {
            sum += speed
        }
        return sum / speedList.size
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
            if (buoy.id == previouslySelectedBuoyMarkerId) {
                // Deselect buoy
                buoy.isSelected = false
                state = state.copy(
                    isBuoyMarkerSelected = false,
                    isBuoyVisible = false,
                    selectedBuoy = null
                )
                marker?.hideInfoWindow()
                previouslySelectedBuoyMarkerId = null
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
                previouslySelectedBuoyMarkerId = buoy.id
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

//    private fun getElevations() {
//        viewModelScope.launch {
//            when (val result = repository.getElevation(
//                locations = "42.315645, 18.368939",
//                apiKey = "AIzaSyAIhn_eoViubKHORi_rlliqVnHGgve2At0"
//            )) {
//                is Resource.Success -> {
//                    result.data?.let { elevationResponse ->
//                        state = state.copy(depth = elevationResponse)
//                    }
//                }
//
//                is Resource.Error -> {
//
//                }
//
//                is Resource.Loading -> {
//
//                }
//            }
//
//        }
//    }

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
    data class OnMapTwoPointsClick(val position: LatLng, val index: Int) : MapEvent()
    data class OnMarkerRemovedTwoPoints(val index: Int) : MapEvent()
    object ClearCustomPoints : MapEvent()
    data class OnDistanceTextOffsetChange(val offset: Offset) : MapEvent()
    object OnRouteIconClick : MapEvent()
    object ClearCustomRouteDistance : MapEvent()
    data class OnMapCustomRouteClick(val position: LatLng) : MapEvent()
    data class OnMarkerRemovedCustomRoute(val index: Int) : MapEvent()
    object ClearCustomRoutePoints : MapEvent()
    data class OnCourseChange(val direction: String, val angle: Double) : MapEvent()
    object OnMoreClick : MapEvent()
    data class OnCursorActive(val cursorLocation: LatLng) : MapEvent()
    object OnCursorClear : MapEvent()
    object DismissCursorDialog: MapEvent()
    object OnDontShowAgainClick : MapEvent()

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
    val shouldEnableCustomPointToPoint: Boolean = false,
    val customPoints: MutableList<LatLng> = mutableStateListOf(),
    val customPointsDistance: Float? = null,
    val customConsecutivePointsDistance: List<Float> = emptyList(),
    val customConsecutivePointsAzimuth: List<Float> = emptyList(),
    val customPointsAzimuth: Float? = null,
    val distanceTextOffset: Offset = Offset.Zero,
    val shouldEnableCustomRoute: Boolean = false,
    val customRoutePoints: List<LatLng> = emptyList(),
    val customRouteDistance: Float? = null,
    val customRouteConsecutivePointsDistance: List<Float> = emptyList(),
    val customRouteConsecutivePointsAzimuth: List<Float> = emptyList(),
    val customRouteAzimuth: Float? = null,
    val userCourseOfMovement: String? = String(),
    val userCourseOfMovementAzimuth: Float? = null,
    val userMovementSpeed: Float? = null,
    val locationAccuracy: Float? = null,
    val isUserStatic: Boolean = true,
    val depth: ElevationResponse? = null,
    val depths: MutableList<Depth> = Constants.depths,
    val preferredSpeedUnit: String = String(),
    val distanceFromCursor: Float? = null,
    val azimuthFromCursor: Float? = null,
    val cursorLatLng: LatLng? = null,
    val showCursorInstruction: Boolean = false,

)