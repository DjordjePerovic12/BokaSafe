package llc.bokadev.bokasafe.presentation.bay_map

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.launch
import llc.bokadev.bokasafe.R
import llc.bokadev.bokasafe.core.components.GoogleMaps
import llc.bokadev.bokasafe.core.utils.noRippleClickable
import llc.bokadev.bokasafe.core.utils.toLatitude
import llc.bokadev.bokasafe.core.utils.toLongitude
import llc.bokadev.bokasafe.core.utils.toThreeDigitString
import llc.bokadev.bokasafe.domain.model.Anchorage
import llc.bokadev.bokasafe.domain.model.AnchorageZone
import llc.bokadev.bokasafe.domain.model.Buoy
import llc.bokadev.bokasafe.domain.model.ProhibitedAnchoringZone
import llc.bokadev.bokasafe.domain.model.Checkpoint
import llc.bokadev.bokasafe.domain.model.ShipWreck
import llc.bokadev.bokasafe.ui.theme.BokaBaySeaTrafficAppTheme
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BokaBayMapScreenContent(
    viewModel: BayMapViewModel,
    onMarkerCreation: (Marker) -> Unit,
    onLighthouseMarkersCreation: (Marker) -> Unit,
    onShipwreckMarkersCreation: (Marker) -> Unit,
    onProhibitedAnchoringZoneMarkersCreation: (Marker) -> Unit,
    onAnchorageMarkerCreation: (Marker) -> Unit,
    onAnchorageZoneMarkerCreation: (Marker) -> Unit,
    onBuoyMarkerCreation: (Marker) -> Unit,
    onMarkerUpdate: () -> Unit,
    resetZoom: () -> Unit,
    onCheckPointClick: (Checkpoint) -> Unit,
    onShipwreckClick: (ShipWreck) -> Unit,
    onAnchorageClick: (Anchorage) -> Unit,
    onAnchorageZoneClick: (AnchorageZone) -> Unit,
    onProhibitedAnchoringZoneClick: (ProhibitedAnchoringZone) -> Unit,
    onBuoyClick: (Buoy) -> Unit,
    onUserLocationClick: () -> Unit,
    onItemHide: (Int) -> Unit,
    state: GuideState
) {

    val bottomSheetState =
        rememberModalBottomSheetState(
            confirmValueChange = {
                it == SheetValue.Hidden
            }, skipPartiallyExpanded = true
        )


    val scope = rememberCoroutineScope()


    var textPosition by remember { mutableStateOf(state.distanceTextOffset) }

    var shouldShowPopup by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = state.distanceTextOffset) {
        textPosition = state.distanceTextOffset
    }


    if (state.showCursorInstruction) {
        CursorInstructionsAlertDialog(onConfirm = {
            viewModel.onEvent(MapEvent.DismissCursorDialog)
        },
            onNeverShowThisAgain = {
                viewModel.onEvent(MapEvent.OnDontShowAgainClick)
            })
    }

    if (state.shouldShowNameRouteAlertDialog) {
        NameRouteAlertDialog(
            routeName = state.routeName,
            shouldShowNameError = state.shouldShowNameError,
            onNameChange = {
                viewModel.onEvent(MapEvent.OnRouteNameChange(it))
            },
            onConfirm = {
                viewModel.onEvent(MapEvent.OnConfirmSaveRouteClick)
            },
            onCancel = {
                viewModel.onEvent(MapEvent.ToggleSaveRouteAlertDialog)
            },
            onEmptyNameSaveClick = {
                viewModel.onEvent(MapEvent.OnEmptyNameSaveClick)
            }
        )
    }

    if (state.lighthouses.isEmpty()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(BokaBaySeaTrafficAppTheme.colors.primaryRed.copy(.7f))
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(70.dp),
                color = BokaBaySeaTrafficAppTheme.colors.defaultGray

            )
        }
    } else
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val context = LocalContext.current
        Timber.e("MAP ${state.lighthouses}")
        GoogleMaps(
            scope = scope, sheetState = bottomSheetState,
            modifier = Modifier
                .fillMaxSize(),
            checkpoints = state.lighthouses,
            shipwrecks = state.shipwrecks,
            prohibitedProhibitedAnchoringZones = state.prohibitedProhibitedAnchoringZones,
            anchorages = state.anchorages,
            anchorageZones = state.anchorageZones,
            underwaterCables = state.underWaterCables,
            pipelines = state.pipelines,
            buoys = state.buoys,
            depths = state.depths,
            onCheckpointClick = { checkpoint ->
                viewModel.onEvent(MapEvent.ResetSelection)
                onCheckPointClick(checkpoint)
            },
            userLocation = state.userLocation,
            userIcon = R.drawable.ic_boat,
            onMarkerCreation = {
                onMarkerCreation(it)
            },
            onShipwreckMarkerCreation = {
                onShipwreckMarkersCreation(it)
            },
            onProhibitedAnchoringZoneMarkerCreation = {
                onProhibitedAnchoringZoneMarkersCreation(it)
            },
            onAnchorageMarkerCreation = {
                onAnchorageMarkerCreation(it)
            },
            onAnchorageZoneMarkerCreation = {
                onAnchorageZoneMarkerCreation(it)
            },
            onBuoyMarkerCreation = {
                onBuoyMarkerCreation(it)
            },
            onMarkerUpdate = { onMarkerUpdate() },
            shouldZoomUserLocation = state.shouldZoomUserLocation,
            resetZoom = { resetZoom() },
            onLighthouseMarkersCreation = { onLighthouseMarkersCreation(it) },
            onShipwreckClick = {
                viewModel.onEvent(MapEvent.ResetSelection)
                onShipwreckClick(it)
            },
            onProhibitedAnchoringZoneClick = {
                viewModel.onEvent(MapEvent.ResetSelection)
                onProhibitedAnchoringZoneClick(it)
            },
            onAnchorageClick = {
                viewModel.onEvent(MapEvent.ResetSelection)
                onAnchorageClick(it)
            },
            onAnchorageZoneClick = {
                viewModel.onEvent(MapEvent.ResetSelection)
                onAnchorageZoneClick(it)
            },
            onBuoyClick = {
                viewModel.onEvent(MapEvent.ResetSelection)
                onBuoyClick(it)
            },
            viewModel = viewModel,
            onItemHide = { onItemHide(it) },
            onMapClick = { poistion, index ->
                viewModel.onEvent(MapEvent.OnMapTwoPointsClick(poistion, index))
            }


        )

        if (state.customPointsDistance != null && state.customPointsDistance.toNauticalMiles() != "0.00") {
            Text(
                text = "D: ${state.customPointsDistance.toNauticalMiles()} NM \n" +
                        if (state.customPointsAzimuth != null) "W: ${
                            state.customPointsAzimuth?.toInt()?.toThreeDigitString()
                        }°" else "",
                modifier = Modifier.offset {
                    IntOffset(
                        textPosition.x.toInt(),
                        textPosition.y.toInt()
                    )
                },// Ensure the text is displayed above the map
                color = BokaBaySeaTrafficAppTheme.colors.white,
                style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold20
            )
        }

        if (state.distanceFromCursor != null && state.azimuthFromCursor != null) {
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            state.distanceTextOffset.x.toInt(),
                            state.distanceTextOffset.y.toInt()
                        )
                    }
            ) {
                // Draw cursor (a small '+' with a hole in the center)
                Canvas(modifier = Modifier.size(30.dp)) { // Increased size for better visibility
                    drawLine(
                        color = Color.Red,
                        start = Offset(15f, 0f), // Centered vertically
                        end = Offset(15f, 30f), // Longer vertical line
                        strokeWidth = 3f // Increased stroke width for visibility
                    )
                    drawLine(
                        color = Color.Red,
                        start = Offset(0f, 15f), // Centered horizontally
                        end = Offset(30f, 15f), // Longer horizontal line
                        strokeWidth = 3f // Increased stroke width for visibility
                    )
                    drawCircle(
                        color = Color.Transparent,
                        radius = 3f, // Slightly larger hole in the center
                        center = Offset(15f, 15f) // Center of the canvas
                    )
                }

                // Calculate dynamic offset for the text
                val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
                val density = LocalContext.current.resources.displayMetrics.density
                val dynamicOffsetX =
                    if (state.distanceTextOffset.x > screenWidth - (200 * density)) {
                        (-200).dp // Offset to the left if near the right edge
                    } else {
                        20.dp // Default offset to the right
                    }


                // Draw text below the cursor
                Text(
                    text = "D: ${state.distanceFromCursor?.toNauticalMiles()} NM\n" +
                            "W: ${state.azimuthFromCursor?.toInt()?.toThreeDigitString()}°\n" +
                            "Latitude : ${state.cursorLatLng?.latitude?.toLatitude()} N\",\n" +
                            "Longitude : ${state.cursorLatLng?.longitude?.toLongitude()} E\"",
                    color = Color.White,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold20,
                    modifier = Modifier.offset(x = dynamicOffsetX) // Position text below cursor
                )
            }
        }






        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 25.dp, start = 25.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(BokaBaySeaTrafficAppTheme.colors.primaryRed)
                    .noRippleClickable {
                        viewModel.onEvent(MapEvent.OnPhoneClick)
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_phone), contentDescription = null,
                    modifier = Modifier.padding(15.dp),
                    tint = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f)
                )
            }
            Box(
                modifier = Modifier
                    .padding(top = 25.dp, start = 25.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(BokaBaySeaTrafficAppTheme.colors.primaryRed)
                    .noRippleClickable {
                        viewModel.onEvent(MapEvent.ResetSelection)
                        scope.launch {
                            bottomSheetState.show()
                        }
                    }
                    .zIndex(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filters),
                    contentDescription = null,
                    modifier = Modifier.padding(15.dp),
                    tint = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f)
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 25.dp, start = 25.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(if (state.shouldEnableCustomPointToPoint) BokaBaySeaTrafficAppTheme.colors.confirmGreen else BokaBaySeaTrafficAppTheme.colors.primaryRed)
                    .noRippleClickable {
                        viewModel.onEvent(MapEvent.ResetSelection)
                        viewModel.onEvent(MapEvent.OnCompassIconClick)

                    }
                    .zIndex(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.compass),
                    contentDescription = null,
                    modifier = Modifier.padding(15.dp),
                    tint = if (state.shouldEnableCustomPointToPoint) BokaBaySeaTrafficAppTheme.colors.primaryRed else BokaBaySeaTrafficAppTheme.colors.white.copy(
                        .5f
                    )
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 25.dp, start = 25.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(BokaBaySeaTrafficAppTheme.colors.primaryRed)
                    .noRippleClickable {
                        viewModel.onEvent(MapEvent.ResetSelection)
                        viewModel.onEvent(MapEvent.OnRouteIconClick)

                    }
                    .zIndex(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_route),
                    contentDescription = null,
                    modifier = Modifier.padding(15.dp),
                    tint = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f)
                )
            }

        }

        Column(
            verticalArrangement = Arrangement.spacedBy(40.dp),
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 110.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 25.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(BokaBaySeaTrafficAppTheme.colors.primaryRed)
                    .align(Alignment.End)
                    .clickable {
                        viewModel.onEvent(MapEvent.OnMoreClick)
                    }
                    .zIndex(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.more),
                    contentDescription = null,
                    modifier = Modifier.padding(15.dp),
                    tint = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f)
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(top = 120.dp, start = 20.dp)
        ) {
            if (state.preferredSpeedUnit != "")
                SpeedometerItem(
                    preferredSpeedUnit = state.preferredSpeedUnit,
                    isUserStatic = state.isUserStatic,
                    speedList = viewModel.speedList,
                    userMovementSpeed = state.userMovementSpeed ?: -1f
                )


            if (state.userCourseOfMovement != String() && state.userCourseOfMovementAzimuth != null) {
                CompassItem(userCourse = state.userCourseOfMovementAzimuth)
//                Box(
//                    modifier = Modifier
//                        .padding(start = 25.dp)
//                        .size(50.dp)
//                        .clip(CircleShape)
//                        .background(BokaBaySeaTrafficAppTheme.colors.lightGreen)
//                        .zIndex(1f),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = state.userCourseOfMovementAzimuth.toInt().toThreeDigitString(),
//                        style = BokaBaySeaTrafficAppTheme.typography.nunitoRegular18,
//                        color = BokaBaySeaTrafficAppTheme.colors.darkBlue
//                    )
//                }
            }
        }






        if (state.shouldEnableCustomRoute)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(top = 65.dp)
            ) {
                CustomRouteBottomSheet(state = viewModel.state, onSaveRouteClick = {
                    viewModel.onEvent(MapEvent.ToggleSaveRouteAlertDialog)
                },
                    onTogglePointsClick = {
                        viewModel.onEvent(MapEvent.OnRoutePointsToggle(it))
                    })
            }


        if (!state.shouldEnableCustomRoute)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 55.dp, end = 25.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(BokaBaySeaTrafficAppTheme.colors.primaryRed)
                    .noRippleClickable {
                        onUserLocationClick()

                    }
                    .zIndex(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.target),
                    contentDescription = null,
                    modifier = Modifier.padding(15.dp),
                    tint = BokaBaySeaTrafficAppTheme.colors.white.copy(.5f)
                )
            }


    }
}


