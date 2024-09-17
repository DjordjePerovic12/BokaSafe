package llc.bokadev.bokabayseatrafficapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import llc.bokadev.bokabayseatrafficapp.R
import llc.bokadev.bokabayseatrafficapp.core.components.GoogleMaps
import llc.bokadev.bokabayseatrafficapp.core.utils.getMidpoint
import llc.bokadev.bokabayseatrafficapp.core.utils.noRippleClickable
import llc.bokadev.bokabayseatrafficapp.domain.model.Anchorage
import llc.bokadev.bokabayseatrafficapp.domain.model.AnchorageZone
import llc.bokadev.bokabayseatrafficapp.domain.model.Buoy
import llc.bokadev.bokabayseatrafficapp.domain.model.ProhibitedAnchoringZone
import llc.bokadev.bokabayseatrafficapp.domain.model.Checkpoint
import llc.bokadev.bokabayseatrafficapp.domain.model.ShipWreck
import llc.bokadev.bokabayseatrafficapp.ui.theme.BokaBaySeaTrafficAppTheme

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

    val speed = viewModel.smoothedSpeed.collectAsState()
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

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val context = LocalContext.current
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
            onCheckpointClick = { checkpoint ->
                viewModel.onEvent(MapEvent.ResetSelection)
                onCheckPointClick(checkpoint)
            },
            userLocation = state.userLocation,
            userIcon = R.drawable.ic_user_location,
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
            onMapClick = { viewModel.onEvent(MapEvent.OnMapClick(it)) }


        )

        if (state.customPointsDistance != null && state.customPointsDistance.toNauticalMiles() != "0.00") {
            Text(
                text = "D = ${state.customPointsDistance.toNauticalMiles()} NM",
                modifier = Modifier.offset {
                    IntOffset(
                        textPosition.x.toInt(),
                        textPosition.y.toInt()
                    )
                },// Ensure the text is displayed above the map
                color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold20
            )

        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 25.dp, start = 25.dp)
                    .size(50.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(BokaBaySeaTrafficAppTheme.colors.darkBlue)
                    .noRippleClickable {
                        viewModel.onEvent(MapEvent.OnPhoneClick)
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_phone), contentDescription = null,
                    modifier = Modifier.padding(15.dp),
                    tint = BokaBaySeaTrafficAppTheme.colors.lightBlue
                )
            }
            Box(
                modifier = Modifier
                    .padding(top = 25.dp, start = 25.dp)
                    .size(50.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(BokaBaySeaTrafficAppTheme.colors.darkBlue)
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
                    tint = BokaBaySeaTrafficAppTheme.colors.lightBlue
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 25.dp, start = 25.dp)
                    .size(50.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(BokaBaySeaTrafficAppTheme.colors.darkBlue)
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
                    tint = BokaBaySeaTrafficAppTheme.colors.lightBlue
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 65.dp)
        ) {
            Text(
                text = "Course: ${state.userCourseOfMovement}",
                style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14,
                color = BokaBaySeaTrafficAppTheme.colors.darkBlue
            )
        }

    }

}


