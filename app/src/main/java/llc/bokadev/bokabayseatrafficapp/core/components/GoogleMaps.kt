package llc.bokadev.bokabayseatrafficapp.core.components

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import llc.bokadev.bokabayseatrafficapp.R
import llc.bokadev.bokabayseatrafficapp.core.utils.bitmapDescriptorFromVector
import llc.bokadev.bokabayseatrafficapp.core.utils.calculateBoundingBoxCentroid
import llc.bokadev.bokabayseatrafficapp.core.utils.calculateBounds
import llc.bokadev.bokabayseatrafficapp.core.utils.calculateCentroid
import llc.bokadev.bokabayseatrafficapp.core.utils.clearAllPolylines
import llc.bokadev.bokabayseatrafficapp.core.utils.clearPipelineLinesAndCircles
import llc.bokadev.bokabayseatrafficapp.core.utils.createWavyPolyline
import llc.bokadev.bokabayseatrafficapp.core.utils.drawAnchoringZoneLines
import llc.bokadev.bokabayseatrafficapp.core.utils.drawAnchoringZoneLinesWith8Points
import llc.bokadev.bokabayseatrafficapp.core.utils.drawAnchoringZoneLinesWithSixPoints
import llc.bokadev.bokabayseatrafficapp.core.utils.drawCustomDashedPolylineWithCircles
import llc.bokadev.bokabayseatrafficapp.core.utils.drawLinesBetweenPoints
import llc.bokadev.bokabayseatrafficapp.core.utils.getMidpoint
import llc.bokadev.bokabayseatrafficapp.domain.model.Anchorage
import llc.bokadev.bokabayseatrafficapp.domain.model.AnchorageZone
import llc.bokadev.bokabayseatrafficapp.domain.model.Buoy
import llc.bokadev.bokabayseatrafficapp.domain.model.ProhibitedAnchoringZone
import llc.bokadev.bokabayseatrafficapp.domain.model.Checkpoint
import llc.bokadev.bokabayseatrafficapp.domain.model.Pipeline
import llc.bokadev.bokabayseatrafficapp.domain.model.ShipWreck
import llc.bokadev.bokabayseatrafficapp.domain.model.UnderwaterCable
import llc.bokadev.bokabayseatrafficapp.presentation.BayMapViewModel
import llc.bokadev.bokabayseatrafficapp.presentation.MapEvent
import llc.bokadev.bokabayseatrafficapp.presentation.NotificationPreferencesBottomSheet
import llc.bokadev.bokabayseatrafficapp.ui.theme.BokaBaySeaTrafficAppTheme
import timber.log.Timber


@OptIn(MapsComposeExperimentalApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GoogleMaps(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    sheetState: SheetState,
    viewModel: BayMapViewModel,
    checkpoints: MutableList<Checkpoint>,
    shipwrecks: MutableList<ShipWreck>,
    anchorages: MutableList<Anchorage>,
    anchorageZones: MutableList<AnchorageZone>,
    underwaterCables: MutableList<UnderwaterCable>,
    pipelines: MutableList<Pipeline>,
    buoys: MutableList<Buoy>,
    prohibitedProhibitedAnchoringZones: MutableList<ProhibitedAnchoringZone>,
    onCheckpointClick: (Checkpoint) -> Unit,
    onShipwreckClick: (ShipWreck) -> Unit,
    onProhibitedAnchoringZoneClick: (ProhibitedAnchoringZone) -> Unit,
    onAnchorageClick: (Anchorage) -> Unit,
    onAnchorageZoneClick: (AnchorageZone) -> Unit,
    onBuoyClick: (Buoy) -> Unit,
    userLocation: LatLng? = null,
    @DrawableRes userIcon: Int,
    shouldZoomUserLocation: Boolean,
    onMarkerCreation: (Marker) -> Unit,
    resetZoom: () -> Unit,
    onLighthouseMarkersCreation: (Marker) -> Unit,
    onShipwreckMarkerCreation: (Marker) -> Unit,
    onProhibitedAnchoringZoneMarkerCreation: (Marker) -> Unit,
    onAnchorageMarkerCreation: (Marker) -> Unit,
    onAnchorageZoneMarkerCreation: (Marker) -> Unit,
    onBuoyMarkerCreation: (Marker) -> Unit,
    onItemHide: (Int) -> Unit,
    onMarkerUpdate: () -> Unit,
    onMapClick: (LatLng, Int) -> Unit,
) {

    val state = viewModel.state
    val bottomSheetState = rememberModalBottomSheetState(
        confirmValueChange = {
            it == SheetValue.Hidden
        }, skipPartiallyExpanded = true
    )

    if (sheetState.isVisible) ModalBottomSheet(
        dragHandle = {},
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
            }
            viewModel.onEvent(MapEvent.OnCancelPreferencesClick)
        },
        content = {
            viewModel.state.mapItemFilters?.let {
                NotificationPreferencesBottomSheet(mapItemFilters = it,
                    onSwitchClick = { choice, id ->
                        viewModel.onEvent(
                            MapEvent.OnPreferencesSwitchClick(
                                choice, id
                            )
                        )
                    },
                    onConfirmClick = {
                        scope.launch {
                            sheetState.hide()
                        }
                        viewModel.onEvent(MapEvent.OnConfirmPreferencesClick)
                    },
                    onCancelClick = {
                        scope.launch {
                            sheetState.hide()
                        }
                        viewModel.onEvent(MapEvent.OnCancelPreferencesClick)
                    })
            }
        },
        sheetState = bottomSheetState,
        scrimColor = BokaBaySeaTrafficAppTheme.colors.darkBlue.copy(alpha = .5f),
        shape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),

        )
    val context = LocalContext.current
    val uiSettingsState = remember {
        MapUiSettings(
            zoomControlsEnabled = false,
            mapToolbarEnabled = false,
            compassEnabled = false,
            rotationGesturesEnabled = false
        )
    }
    var markerCreated by remember {
        mutableStateOf(false)
    }

    var hasZoomed by remember {
        mutableStateOf(false)
    }


    val mapStyle =
        LocalContext.current.assets.open("map_style.json").bufferedReader().use { it.readText() }
    val mapProperties = remember {
        MapProperties(
            mapType = MapType.SATELLITE,
            mapStyleOptions = MapStyleOptions(mapStyle),
            isBuildingEnabled = false,


            )
    }
    val cameraPositionState = rememberCameraPositionState()
    val width = context.resources.displayMetrics.widthPixels
    val height = context.resources.displayMetrics.heightPixels
    val padding = (width * 0.10).toInt()

    val localCheckpoints: ArrayList<Checkpoint> = arrayListOf()
    val localShipwrecks: ArrayList<ShipWreck> = arrayListOf()
    val localProhibitedProhibitedAnchoringZones: ArrayList<ProhibitedAnchoringZone> = arrayListOf()
    val localAnchorages: ArrayList<Anchorage> = arrayListOf()
    val localAnchorageZones: ArrayList<AnchorageZone> = arrayListOf()
    val localBuoys: ArrayList<Buoy> = arrayListOf()


    //Filtering markers
    val lightHouseMarkers = remember { mutableListOf<Marker>() }
    val shipwreckMarkers = remember { mutableListOf<Marker>() }
    val prohibitedAnchoringZoneMarkers = remember { mutableListOf<Marker>() }
    val anchorageMarkers = remember { mutableListOf<Marker>() }
    val anchorageZoneMarkers = remember { mutableListOf<Marker>() }
    val buoyMarkers = remember { mutableListOf<Marker>() }
    val dashedPolylines: MutableList<Polyline> = remember { mutableListOf() }
    val circles: MutableList<Circle> = remember { mutableListOf() }
    val anchorageZonesPolylines: MutableList<Polyline> = remember { mutableListOf() }


    //Distance between two points handlers
    val customPointsMarkers = remember { mutableListOf<Marker>() }
    val customPointsPollyline = remember { mutableStateOf<Polyline?>(null) }


    //Custom route
    val customRoutePointsMarkers = remember { mutableListOf<Marker>() }
    val customRoutePointsPollyline = remember { mutableListOf<Polyline?>(null) }


    var selectedMarker by remember { mutableStateOf<Marker?>(null) }






    GoogleMap(
        modifier = modifier,
        uiSettings = uiSettingsState,
        cameraPositionState = cameraPositionState,
        properties = mapProperties
    ) {
//        MapEffect(key1 = checkpoints) {
//            if (checkpoints.isNotEmpty() && !hasZoomed) {
//                cameraPositionState.animate(
//                    update = CameraUpdateFactory.newLatLngBounds(
//                        calculateBounds(checkpoints, context), width, height.toInt(), padding
//                    )
//                )
//                hasZoomed = true
//            }
//        }

        val bokaBayBounds = LatLngBounds(
            LatLng(42.3869, 18.5083),  // Southwest corner of Boka Bay
            LatLng(42.4860, 18.7557)   // Northeast corner of Boka Bay
        )

        MapEffect(key1 = Unit) { map ->
            // Restrict the camera movement to stay within the bounds of Boka Bay
            map.setLatLngBoundsForCameraTarget(bokaBayBounds)

            // Move and zoom the camera to fit Boka Bay and center it on the screen
            map.moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bokaBayBounds,
                    100
                )
            )  // 100 is padding
        }

        val userIconBitmap = bitmapDescriptorFromVector(
            width = 70, height = 50, context = context, vectorResId = userIcon
        )

        MapEffect(key1 = userLocation) { map ->
            Timber.e("User location $userLocation")
            if (!markerCreated && userLocation != null) {
                val userMarkerOptions = MarkerOptions().icon(userIconBitmap).position(userLocation)
                val marker = map.addMarker(userMarkerOptions)
                marker?.let(onMarkerCreation)
                markerCreated = true
            } else onMarkerUpdate()
        }
        MapEffect(key1 = shouldZoomUserLocation) { map ->
            if (shouldZoomUserLocation && userLocation != null) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 19f))
            }
            map.setOnCameraIdleListener(resetZoom)
        }


        // MapEffect code

        // MapEffect code

        MapEffect(key1 = state.shouldEnableCustomPointToPoint) { map ->
            if (state.shouldEnableCustomPointToPoint) {
                // Function to update distance and azimuth UI offset
                val updateDistanceTextOffset: () -> Unit = {
                    if (customPointsMarkers.size == 2) {
                        val midpoint = getMidpoint(
                            customPointsMarkers[0].position, customPointsMarkers[1].position
                        )
                        val projection = map.projection
                        val screenPosition = projection.toScreenLocation(midpoint)
                        viewModel.onEvent(
                            MapEvent.OnDistanceTextOffsetChange(
                                Offset(screenPosition.x.toFloat(), screenPosition.y.toFloat())
                            )
                        )
                    } else {
                        viewModel.onEvent(MapEvent.ClearDistanceBetweenCustomPoints)
                    }
                }

                // Map click listener to add new points
                map.setOnMapClickListener { latLng ->
                    if (customPointsMarkers.size < 2) {
                        // Calculate the new checkpointId
                        val newCheckpointId =
                            checkpoints.size + anchorages.size + prohibitedProhibitedAnchoringZones.size + buoys.size + shipwrecks.size + anchorageZones.size + customPointsMarkers.size + 1

                        val newMarker = map.addMarker(
                            MarkerOptions().position(latLng).icon(
                                bitmapDescriptorFromVector(
                                    vectorResId = R.drawable.checkpoint_to, context = context
                                )
                            )
                        )

                        newMarker?.tag = "${newMarker?.id}"

                        newMarker?.let {
                            customPointsMarkers.add(it)
                            viewModel.onEvent(
                                MapEvent.OnMapTwoPointsClick(latLng, customPointsMarkers.size - 1)
                            )
                        }


                        // If exactly 2 points, draw a polyline and calculate distance/azimuth
                        if (customPointsMarkers.size == 2) {
                            customPointsPollyline.value?.remove() // Clear previous polyline
                            customPointsPollyline.value = map.addPolyline(
                                PolylineOptions().add(
                                    customPointsMarkers[0].position, customPointsMarkers[1].position
                                ).color(0xFF001E31.toInt()).width(5f)
                            )
                            updateDistanceTextOffset()
                        }
                    }
                }

                // Marker click listener to remove a point
                map.setOnMarkerClickListener { clickedMarker ->
                    val tag = clickedMarker.tag as? String ?: return@setOnMarkerClickListener false
                    if (tag.startsWith("m")) {
                        val checkpointId = tag.removePrefix("m").toInt()

                        // Find the marker's index in customPointsMarkers
                        val customPointIndex = customPointsMarkers.indexOf(clickedMarker)
                        if (customPointIndex == -1) {
                            // Marker not found in list
                            return@setOnMarkerClickListener false
                        }

                        // Remove marker from map and list
                        clickedMarker.remove()
                        customPointsMarkers.removeAt(customPointIndex)

                        // Notify ViewModel
                        viewModel.onEvent(MapEvent.OnMarkerRemovedTwoPoints(customPointIndex))

                        // Reindex markers
                        customPointsMarkers.forEachIndexed { index, marker ->
                            val newCheckpointId =
                                checkpoints.size + anchorages.size + prohibitedProhibitedAnchoringZones.size + buoys.size + shipwrecks.size + anchorageZones.size + index + 1
                            marker.tag = "m$newCheckpointId"
                        }

                        // Update polyline and calculations
                        if (customPointsMarkers.size == 2) {
                            customPointsPollyline.value?.remove()
                            customPointsPollyline.value = map.addPolyline(
                                PolylineOptions().add(
                                    customPointsMarkers[0].position, customPointsMarkers[1].position
                                ).color(0xFF001E31.toInt()).width(5f)
                            )
                            updateDistanceTextOffset()
                        } else if (customPointsMarkers.size == 1) {
                            // Clear polyline and calculations
                            customPointsPollyline.value?.remove()
                            customPointsPollyline.value = null
                            viewModel.onEvent(MapEvent.ClearDistanceBetweenCustomPoints)
                        } else {
                            // No markers remain, clear everything
                            customPointsPollyline.value?.remove()
                            customPointsPollyline.value = null
                            viewModel.onEvent(MapEvent.ClearCustomPoints)
                        }

                        true // Indicate event was handled
                    } else {
                        false // Pass through for other markers
                    }
                }

                // Ensure camera movements update the distance/azimuth offset when polyline is present
                map.setOnCameraMoveListener {
                    updateDistanceTextOffset()
                }
            } else {
                // When the feature is disabled, remove all markers and clear the state
                customPointsMarkers.forEach { it.remove() }
                customPointsMarkers.clear()
                customPointsPollyline.value?.remove()
                customPointsPollyline.value = null
                map.setOnMapClickListener(null) // Disable further map clicks
                viewModel.onEvent(MapEvent.ClearCustomPoints)
            }
        }







        MapEffect(key1 = state.shouldEnableCustomRoute) { map ->
            if (state.shouldEnableCustomRoute) {
                val updateDistanceTextOffset: () -> Unit = {
                    if (customRoutePointsMarkers.size == 2) {
                        val midpoint = getMidpoint(
                            customRoutePointsMarkers.first().position,
                            customRoutePointsMarkers.last().position
                        )
                        val projection = map.projection
                        val screenPosition = projection.toScreenLocation(midpoint)
                        viewModel.onEvent(
                            MapEvent.OnDistanceTextOffsetChange(
                                Offset(screenPosition.x.toFloat(), screenPosition.y.toFloat())
                            )
                        )
                    } else {
                        viewModel.onEvent(MapEvent.ClearCustomRouteDistance)
                    }
                }

                // Enable map click listener for adding custom points
                map.setOnMapClickListener { latLng ->
                    // Add a new marker with the correct custom index
                    val newMarker = map.addMarker(
                        MarkerOptions().position(latLng).icon(
                            bitmapDescriptorFromVector(
                                vectorResId = R.drawable.checkpoint_to, context = context
                            )
                        )
                    )

                    // Assign the custom index to the marker based on the current list size
                    newMarker?.tag = "routem${customRoutePointsMarkers.size}"

                    // Add the new marker to the list
                    newMarker?.let { customRoutePointsMarkers.add(it) }

                    // Update ViewModel's list of points
                    viewModel.onEvent(MapEvent.OnMapCustomRouteClick(latLng))

                    // Rebuild the polyline if there are at least two markers
                    if (customRoutePointsMarkers.size >= 2) {
                        // Clear the previous polyline (if necessary)
                        customRoutePointsPollyline.forEach { it?.remove() }
                        customRoutePointsPollyline.clear()

                        // Create a new PolylineOptions object
                        val polylineOptions = PolylineOptions().color(0xFF001E31.toInt()).width(5f)

                        // Add positions of consecutive markers to the polyline
                        for (i in 0 until customRoutePointsMarkers.size - 1) {
                            val startPosition = customRoutePointsMarkers[i].position
                            val endPosition = customRoutePointsMarkers[i + 1].position
                            polylineOptions.add(startPosition, endPosition)
                        }

                        // Add the polyline to the map
                        customRoutePointsPollyline.add(map.addPolyline(polylineOptions))
                    }
                }





                map.setOnCameraMoveListener {
                    updateDistanceTextOffset()
                }
            } else {
                // Clear all customPointsMarkers from the map
                customRoutePointsMarkers.forEach { it.remove() }
                customRoutePointsMarkers.clear()

                // Remove the polyline if it exists

                customRoutePointsPollyline.forEach { it?.remove() }
                customRoutePointsPollyline.clear()

                // Disable the click listeners to prevent further drawing
                map.setOnMapClickListener(null)

                // Clear points in ViewModel
                viewModel.onEvent(MapEvent.ClearCustomRoutePoints)

            }
        }





        MapEffect(key1 = state.mapItemFilters?.lighthouses) { map ->
            if (state.mapItemFilters?.lighthouses == true) {
                for (checkpoint in checkpoints) {
                    val checkpointBitmap = bitmapDescriptorFromVector(
                        context = context,
                        vectorResId = checkpoint.iconResId,
                        height = 60,
                        width = 60,
                    )
                    val checkpointMarkerOptions = MarkerOptions().icon(checkpointBitmap)
                        .position(LatLng(checkpoint.latitude, checkpoint.longitude))
                        .title(checkpoint.name).infoWindowAnchor(.5f, 1.9f)
                    localCheckpoints.add(checkpoint)
                    val checkpointMarker: Marker? = map.addMarker(checkpointMarkerOptions)
                    checkpointMarker?.tag = "checkpoint_${checkpoint.id}"
                    checkpointMarker?.let(onLighthouseMarkersCreation)
                    if (checkpointMarker != null) {
                        lightHouseMarkers.add(checkpointMarker)
                    }
                    Timber.e("Local checkpoints $localCheckpoints")

                }

            } else {
                for (marker in lightHouseMarkers) {
                    marker.remove() // Remove the marker from the map
                }
                lightHouseMarkers.clear()
            }
        }

        MapEffect(key1 = Unit, key2 = state.shouldEnableCustomPointToPoint) { map ->
            map.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
                override fun getInfoContents(p0: Marker): Nothing? = null
                override fun getInfoWindow(marker: Marker): View? {
                    val view =
                        LayoutInflater.from(context).inflate(R.layout.custom_window_info, null)
                    val sectorName = view.findViewById<TextView>(R.id.checkpoint_name)
                    sectorName.text = marker.title
                    return view
                }
            })
        }

        MapEffect(key1 = state.mapItemFilters?.prohibitedAnchoringZone) { map ->
            if (state.mapItemFilters?.prohibitedAnchoringZone == true) {
                for (prohibitedAnchoringZone in prohibitedProhibitedAnchoringZones) {
                    val centorid = calculateBoundingBoxCentroid(prohibitedAnchoringZone.points)

                    val prohibitedAnchoringZoneBitmap = bitmapDescriptorFromVector(
                        context = context,
                        vectorResId = R.drawable.ic_prohibited_anchoring,
                        height = 50,
                        width = 50
                    )
                    val prohibitedAnchoringZoneMarkerOptions =
                        MarkerOptions().icon(prohibitedAnchoringZoneBitmap)
                            .title(prohibitedAnchoringZone.name).infoWindowAnchor(.5f, 1.9f)
                            .position(
                                if (prohibitedAnchoringZone.id == 10) LatLng(
                                    42.420606, 18.701578
                                ) else centorid
                            )
                    var prohibitedAnchoringZoneMarker: Marker?
                    localProhibitedProhibitedAnchoringZones.add(prohibitedAnchoringZone)
                    prohibitedAnchoringZoneMarker =
                        map.addMarker(prohibitedAnchoringZoneMarkerOptions)
                    prohibitedAnchoringZoneMarker?.tag =
                        "prohibitedAnchoringZone_${prohibitedAnchoringZone.id}"
                    prohibitedAnchoringZoneMarker?.let(onProhibitedAnchoringZoneMarkerCreation)

                    prohibitedAnchoringZoneMarker?.let { prohibitedAnchoringZoneMarkers.add(it) } // Store the marker in the list


                    when (prohibitedAnchoringZone.id) {
                        2 -> {
                            drawAnchoringZoneLinesWithSixPoints(map, prohibitedAnchoringZone)
                        }

                        9 -> {
                            drawAnchoringZoneLinesWith8Points(map, prohibitedAnchoringZone)

                        }

                        else -> {
                            drawAnchoringZoneLines(
                                map, prohibitedAnchoringZone
                            )
                        }
                    }


                }
            } else {
                clearAllPolylines()
                // If the filter state is false, remove the markers
                for (marker in prohibitedAnchoringZoneMarkers) {
                    marker.remove() // Remove the marker from the map
                }
                prohibitedAnchoringZoneMarkers.clear() // Clear the list of stored markers
            }
        }

        MapEffect(key1 = state.mapItemFilters?.anchorages) { map ->
            if (state.mapItemFilters?.anchorages == true) {
                // If the filter state is true, add the markers
                for (anchorage in anchorages) {
                    val anchorageBitmap = bitmapDescriptorFromVector(
                        context = context,
                        vectorResId = R.drawable.ic_anchorage,
                        height = 50,
                        width = 50
                    )
                    val anchorageMarkerOptions = MarkerOptions().icon(anchorageBitmap)
                        .position(LatLng(anchorage.latitude, anchorage.longitude))
                        .title(anchorage.name).infoWindowAnchor(.5f, 1.9f)

                    var anchorageMarker: Marker?
                    localAnchorages.add(anchorage)
                    anchorageMarker = map.addMarker(anchorageMarkerOptions)
                    anchorageMarker?.tag = "anchorage_${anchorage.id}"
                    anchorageMarker?.let(onAnchorageMarkerCreation)
                    anchorageMarker?.let { anchorageMarkers.add(it) } // Store the marker in the list


                }
            } else {
                // If the filter state is false, remove the markers
                for (marker in anchorageMarkers) {
                    marker.remove() // Remove the marker from the map
                }
                anchorageMarkers.clear() // Clear the list of stored markers
            }
        }

        MapEffect(key1 = state.mapItemFilters?.anchorages) { map ->
            if (state.mapItemFilters?.anchorages == true) {
                // If the filter state is true, add the markers
                for (anchorageZone in anchorageZones) {
                    val anchorageBitmap = bitmapDescriptorFromVector(
                        context = context,
                        vectorResId = R.drawable.ic_anchorage,
                        height = 50,
                        width = 50
                    )
                    val markerPosition = calculateCentroid(anchorageZone.points)
                    val anchorageZoneMarkerOptions = MarkerOptions().icon(anchorageBitmap)
                        .position(LatLng(markerPosition.latitude, markerPosition.longitude))
                        .title(anchorageZone.name).infoWindowAnchor(.5f, 1.9f)

                    var anchorageZoneMarker: Marker?
                    localAnchorageZones.add(anchorageZone)
                    anchorageZoneMarker = map.addMarker(anchorageZoneMarkerOptions)
                    anchorageZoneMarker?.tag = "anchorageZone_${anchorageZone.id}"
                    anchorageZoneMarker?.let(onAnchorageZoneMarkerCreation)
                    anchorageZoneMarker?.let { anchorageZoneMarkers.add(it) } // Store the marker in the list

                    drawLinesBetweenPoints(map, anchorageZone.points)


                }
            } else {
                clearAllPolylines()
                // If the filter state is false, remove the markers
                for (marker in anchorageZoneMarkers) {
                    marker.remove() // Remove the marker from the map
                }
                anchorageZoneMarkers.clear() // Clear the list of stored markers
            }
        }


        MapEffect(key1 = state.mapItemFilters?.underwaterCables) { map ->
            Timber.e("Under water cables $dashedPolylines")
            if (state.mapItemFilters?.underwaterCables == true) {
                for (underwaterCable in underwaterCables) {
                    val start = underwaterCable.coordinates.first
                    val end = underwaterCable.coordinates.second
                    val underwaterCableLine = createWavyPolyline(
                        start = start,
                        end = end,
                        amplitude = 0.005, // Adjust amplitude as needed
                        wavelength = 0.05, // Adjust wavelength as needed
                        segments = 200, // Number of segments to smooth the wave,
                    )

                    val dashedPolyline = underwaterCableLine.apply {
                        pattern(listOf(Dash(30f), Gap(20F)))
                    }


                    val line = map.addPolyline(dashedPolyline)
                    dashedPolylines.add(line)


                }
            } else {
                for (pollyline in dashedPolylines) {
                    pollyline.remove()
                }
                dashedPolylines.clear()
            }

        }
        MapEffect(key1 = state.mapItemFilters?.underwaterCables) { map ->
            if (state.mapItemFilters?.underwaterCables == true) {
                for (pipeline in pipelines) {

                    drawCustomDashedPolylineWithCircles(map, pipeline.points)

                }
            } else {
                clearPipelineLinesAndCircles()
                for (pollyline in dashedPolylines) {
                    pollyline.remove()
                }
                for (circle in circles) {
                    circle.remove()
                }
                dashedPolylines.clear()
                circles.clear()
            }

        }

        MapEffect(key1 = state.mapItemFilters?.buoys) { map ->
            if (state.mapItemFilters?.buoys == true) {
                for (buoy in buoys) {

                    val buoyBitmap = bitmapDescriptorFromVector(
                        context = context, vectorResId = R.drawable.ic_buoy, height = 40, width = 40
                    )
                    val buoyMarkerOptions = MarkerOptions().icon(buoyBitmap)
                        .position(LatLng(buoy.coordinates.latitude, buoy.coordinates.longitude))
                        .title(buoy.name).infoWindowAnchor(.5f, 1.9f)
                    var buoyMarker: Marker?
                    localBuoys.add(buoy)

                    buoyMarker = map.addMarker(buoyMarkerOptions)
                    buoyMarker?.tag = "buoy_${buoy.id}"
                    buoyMarker?.let(onBuoyMarkerCreation)
                    if (buoyMarker != null) {
                        buoyMarkers.add(buoyMarker)
                    }

                }
            } else {
                for (marker in buoyMarkers) {
                    marker.remove() // Remove the marker from the map
                }
                buoyMarkers.clear() //
            }

        }
        MapEffect(key1 = state.mapItemFilters?.shipwrecks) { map ->
            if (state.mapItemFilters?.shipwrecks == true) {
                for (shipwreck in shipwrecks) {
                    val shipwreckBitmap = bitmapDescriptorFromVector(
                        context = context,
                        vectorResId = shipwreck.iconResId,
                        height = 50,
                        width = 50
                    )
                    val shipwreckMarkerOptions = MarkerOptions().icon(shipwreckBitmap)
                        .position(LatLng(shipwreck.latitude, shipwreck.longitude))
                        .title(shipwreck.name).infoWindowAnchor(.5f, 1.9f)
                    var shipwreckMarker: Marker?
                    localShipwrecks.add(shipwreck)

                    shipwreckMarker = map.addMarker(shipwreckMarkerOptions)
                    shipwreckMarker?.tag = "shipwreck_${shipwreck.id}"
                    shipwreckMarker?.let(onShipwreckMarkerCreation)
                    if (shipwreckMarker != null) {
                        shipwreckMarkers.add(shipwreckMarker)
                    }




                    if (state.shouldEnableCustomPointToPoint) {
                        val updateDistanceTextOffset: () -> Unit = {
                            if (customPointsMarkers.size == 2) {
                                val midpoint = getMidpoint(
                                    customPointsMarkers[0].position, customPointsMarkers[1].position
                                )
                                val projection = map.projection
                                val screenPosition = projection.toScreenLocation(midpoint)
                                viewModel.onEvent(
                                    MapEvent.OnDistanceTextOffsetChange(
                                        Offset(
                                            screenPosition.x.toFloat(), screenPosition.y.toFloat()
                                        )
                                    )
                                )
                            } else {
                                viewModel.onEvent(MapEvent.ClearDistanceBetweenCustomPoints)
                            }
                        }

                        // Handle marker clicks for deselecting
                        map.setOnMarkerClickListener { clickedMarker ->
                            if (customPointsMarkers.contains(clickedMarker)) {
                                customPointsPollyline.value?.remove()
                                if (customPointsMarkers.size == 2) {
                                    customPointsPollyline.value = map.addPolyline(
                                        PolylineOptions().add(
                                            customPointsMarkers[0].position,
                                            customPointsMarkers[1].position
                                        ).color(0xFF001E31.toInt()).width(5f)
                                    )
                                    updateDistanceTextOffset()
                                } else {
                                    viewModel.onEvent(MapEvent.ClearDistanceBetweenCustomPoints)
                                }

                                true // Mark the event as handled
                            } else {
                                false // Let other marker clicks pass through (icon markers)
                            }
                        }
                    } else if (state.shouldEnableCustomRoute) {
                        val updateDistanceTextOffset: () -> Unit = {
                            if (customRoutePointsMarkers.size == 2) {
                                val midpoint = getMidpoint(
                                    customPointsMarkers.first().position,
                                    customPointsMarkers.last().position
                                )
                                val projection = map.projection
                                val screenPosition = projection.toScreenLocation(midpoint)
                                viewModel.onEvent(
                                    MapEvent.OnDistanceTextOffsetChange(
                                        Offset(
                                            screenPosition.x.toFloat(), screenPosition.y.toFloat()
                                        )
                                    )
                                )
                            } else {
                                viewModel.onEvent(MapEvent.ClearCustomRouteDistance)
                            }
                        }

                        // Handle marker clicks for deselecting
                        map.setOnMarkerClickListener { clickedMarker ->
                            if (customRoutePointsMarkers.contains(clickedMarker)) {
//                                customRoutePointsPollyline.remove(clickedMarker)
                                if (customRoutePointsMarkers.size >= 2) {
//                                    customRoutePointsPollyline.value?.remove()

                                    // Create a new PolylineOptions object
                                    val polylineOptions =
                                        PolylineOptions().color(0xFF001E31.toInt()).width(5f)

                                    // Add positions of consecutive markers to the polyline
                                    for (i in 0 until customRoutePointsMarkers.size - 1) {
                                        val startPosition = customRoutePointsMarkers[i].position
                                        val endPosition = customRoutePointsMarkers[i + 1].position
                                        polylineOptions.add(startPosition, endPosition)
                                    }

                                    // Add the polyline to the map
                                    customRoutePointsPollyline.add(map.addPolyline(polylineOptions))

                                    // Update the distance text offset
                                    updateDistanceTextOffset()
                                } else {
                                    viewModel.onEvent(MapEvent.ClearCustomRouteDistance)
                                }

                                true // Mark the event as handled
                            } else {
                                false // Let other marker clicks pass through (icon markers)
                            }
                        }
                    } else {
                        map.setOnMarkerClickListener { marker ->
                            Timber.e("Marker ${marker.title} clicked ${marker.id}, tag ${marker.tag}")
                            Timber.e("CustomCheckpointMarkers ${(checkpoints.size + anchorages.size + prohibitedProhibitedAnchoringZones.size + buoys.size + shipwrecks.size + anchorageZones.size)}")
                            val tag = marker.tag as? String ?: return@setOnMarkerClickListener true

                            when {
                                tag.startsWith("m") -> {
                                    val checkpointId = tag.removePrefix("m").toInt()
                                    Timber.e("Checkpoint ID: $checkpointId")

                                    // Find the marker's index in customPointsMarkers
                                    val customPointIndex = customPointsMarkers.indexOf(marker)
                                    if (customPointIndex == -1) {
                                        // Marker not found in list
                                        return@setOnMarkerClickListener false
                                    }

                                    // Remove the corresponding point from the ViewModel
                                    viewModel.onEvent(
                                        MapEvent.OnMarkerRemovedTwoPoints(
                                            customPointIndex
                                        )
                                    )

                                    // Remove the marker from the map and the marker list
                                    marker.remove()
                                    customPointsMarkers.removeAt(customPointIndex)

                                    // Reindex markers
                                    customPointsMarkers.forEachIndexed { index, marker ->
                                        val newCheckpointId =
                                            index + checkpoints.size + anchorages.size + prohibitedProhibitedAnchoringZones.size + buoys.size + shipwrecks.size + anchorageZones.size + 1
                                        marker.tag = "m$newCheckpointId"
                                    }

                                    // Reset polyline and recalculate everything
                                    customPointsPollyline.value?.remove()
                                    customPointsPollyline.value = null

                                    // Update logic for points after removal
                                    if (customPointsMarkers.size == 2) {
                                        // If 2 markers remain, recalculate and rebuild polyline
                                        customPointsPollyline.value = map.addPolyline(
                                            PolylineOptions().add(
                                                customPointsMarkers[0].position,
                                                customPointsMarkers[1].position
                                            ).color(0xFF001E31.toInt()).width(5f)
                                        )

                                        // Update ViewModel's distance and azimuth for the remaining points
                                        viewModel.onEvent(
                                            MapEvent.OnMapTwoPointsClick(
                                                customPointsMarkers[0].position, 0
                                            )
                                        )
                                        viewModel.onEvent(
                                            MapEvent.OnMapTwoPointsClick(
                                                customPointsMarkers[1].position, 1
                                            )
                                        )
                                    } else if (customPointsMarkers.size == 1) {
                                        // Clear polyline and calculations
                                        viewModel.onEvent(MapEvent.ClearDistanceBetweenCustomPoints)
                                    } else {
                                        // If no markers remain, reset everything
                                        viewModel.onEvent(MapEvent.ClearCustomPoints)
                                    }
                                }


                                // Marker tag handling code


                                tag.startsWith("routem") -> {
                                    val checkpointId = tag.removePrefix("routem").toInt()

                                    // The 'checkpointId' is the custom index we assigned earlier, so we can directly use it
                                    val markerIndex = checkpointId

                                    // Ensure the markerIndex is valid
                                    if (markerIndex >= 0 && markerIndex < customRoutePointsMarkers.size) {
                                        // Remove the marker at 'markerIndex'
                                        customRoutePointsMarkers[markerIndex].remove() // Remove the marker from the map
                                        customRoutePointsMarkers.removeAt(markerIndex) // Remove marker from the list


                                        viewModel.onEvent(
                                            MapEvent.OnMarkerRemovedCustomRoute(
                                                markerIndex
                                            )
                                        )

                                        // Reassign custom indices (routem0, routem1, etc.) to the remaining markers
                                        customRoutePointsMarkers.forEachIndexed { index, marker ->
                                            marker.tag =
                                                "routem${index}" // Update the marker tag with the new index
                                        }

                                        // Clear the existing polylines from the map
                                        customRoutePointsPollyline.forEach { it?.remove() }
                                        customRoutePointsPollyline.clear()

                                        // Rebuild the polyline if at least two markers remain
                                        if (customRoutePointsMarkers.size >= 2) {
                                            // Create a new PolylineOptions object
                                            val polylineOptions =
                                                PolylineOptions().color(0xFF001E31.toInt())
                                                    .width(5f)

                                            // Rebuild the polyline by connecting consecutive markers
                                            for (i in 0 until customRoutePointsMarkers.size - 1) {
                                                val startPosition =
                                                    customRoutePointsMarkers[i].position
                                                val endPosition =
                                                    customRoutePointsMarkers[i + 1].position
                                                polylineOptions.add(startPosition, endPosition)
                                            }

                                            // Add the new polyline to the map
                                            customRoutePointsPollyline.add(
                                                map.addPolyline(
                                                    polylineOptions
                                                )
                                            )

                                            // Optionally update the distance text offset or other UI components
                                            // updateDistanceTextOffset() // Uncomment if needed
                                        } else {
                                            // If fewer than two markers remain, clear the route distance and UI components
                                            viewModel.onEvent(MapEvent.ClearCustomRouteDistance)
                                        }

                                        // Remove the marker from the map
                                        marker.remove()
                                    }
                                }


                                tag.startsWith("checkpoint_") -> {
                                    val checkpointId = tag.removePrefix("checkpoint_").toInt()
                                    val clickedCheckpoint =
                                        localCheckpoints.firstOrNull { it.id == checkpointId }
                                    clickedCheckpoint?.let { onCheckpointClick(it) }
                                }

                                tag.startsWith("shipwreck_") -> {
                                    val shipwreckId = tag.removePrefix("shipwreck_").toInt()
                                    val clickedShipwreck =
                                        localShipwrecks.firstOrNull { it.id == shipwreckId }
                                    clickedShipwreck?.let { onShipwreckClick(it) }
                                }

                                tag.startsWith("prohibitedAnchoringZone_") -> {
                                    val prohibitedAnchoringZoneId =
                                        tag.removePrefix("prohibitedAnchoringZone_").toInt()
                                    val clickedProhibitedAnchoringZone =
                                        localProhibitedProhibitedAnchoringZones.firstOrNull { it.id == prohibitedAnchoringZoneId }
                                    clickedProhibitedAnchoringZone?.let {
                                        onProhibitedAnchoringZoneClick(
                                            it
                                        )
                                    }
                                }

                                tag.startsWith("anchorage_") -> {
                                    val anchorageId = tag.removePrefix("anchorage_").toInt()
                                    val clickedAnchorage =
                                        localAnchorages.firstOrNull { it.id == anchorageId }
                                    clickedAnchorage?.let {
                                        onAnchorageClick(it)
                                    }
                                }

                                tag.startsWith("anchorageZone_") -> {
                                    val anchorageZoneId = tag.removePrefix("anchorageZone_").toInt()
                                    val clickedAnchorageZone =
                                        localAnchorageZones.firstOrNull { it.id == anchorageZoneId }
                                    clickedAnchorageZone?.let {
                                        onAnchorageZoneClick(it)
                                    }
                                }

                                tag.startsWith("buoy_") -> {
                                    val buoyId = tag.removePrefix("buoy_").toInt()
                                    val clickedBuoy = localBuoys.firstOrNull { it.id == buoyId }
                                    clickedBuoy?.let {
                                        onBuoyClick(it)
                                    }
                                }
                            }
                            true // Mark the event as handled
                        }
                    }

                }
            } else {
                for (marker in shipwreckMarkers) {
                    marker.remove() // Remove the marker from the map
                }
                shipwreckMarkers.clear()
            }
        }
    }
}









