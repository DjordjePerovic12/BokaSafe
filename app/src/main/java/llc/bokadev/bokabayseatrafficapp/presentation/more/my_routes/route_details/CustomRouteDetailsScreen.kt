package llc.bokadev.bokabayseatrafficapp.presentation.more.my_routes.route_details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
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
import llc.bokadev.bokabayseatrafficapp.R
import llc.bokadev.bokabayseatrafficapp.core.utils.bitmapDescriptorFromVectorWithNumber
import llc.bokadev.bokabayseatrafficapp.core.utils.calculateBoundsCustomRoute
import llc.bokadev.bokabayseatrafficapp.ui.theme.BokaBaySeaTrafficAppTheme

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun CustomRouteDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: CustomRouteDetailsViewModel,
    showSnackBar: (String) -> Unit,
) {

    val state = viewModel.viewStateFlow.collectAsState().value

    val uiSettingsState = remember {
        MapUiSettings(
            zoomControlsEnabled = false,
            mapToolbarEnabled = false,
            compassEnabled = false,
            rotationGesturesEnabled = false,
            scrollGesturesEnabled = true,
            tiltGesturesEnabled = false,
            zoomGesturesEnabled = true,
            scrollGesturesEnabledDuringRotateOrZoom = true,

            )
    }

    var hasZoomed by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val width = context.resources.displayMetrics.widthPixels
    val height = context.resources.displayMetrics.heightPixels
    val padding = (width * 0.10).toInt()


    val cameraPositionState = rememberCameraPositionState()
    val mapProperties = remember {
        MapProperties(
            mapType = MapType.SATELLITE,
            isBuildingEnabled = false
        )
    }
    val customRoutePointsMarkers = remember { mutableListOf<Marker>() }
    val customRoutePointsPollyline = remember { mutableListOf<Polyline?>(null) }


    BackHandler {
        viewModel.onEvent(RouteDetailsEvent.OnBackClick)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            uiSettings = uiSettingsState,
            cameraPositionState = cameraPositionState,
            properties = mapProperties
        ) {

            MapEffect(key1 = state.customRoute?.pointS) { map ->
                val points = state.customRoute?.pointS!!
                if (state.customRoute.pointS.isNotEmpty() == true && !hasZoomed) {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newLatLngBounds(
                            calculateBoundsCustomRoute(state.customRoute, context),
                            width,
                            height.toInt(),
                            padding
                        )
                    )
                    hasZoomed = true
                }

                customRoutePointsMarkers.forEach { it.remove() }
                customRoutePointsPollyline.forEach { it?.remove() }
                customRoutePointsMarkers.clear()
                customRoutePointsPollyline.clear()

                // Add markers and polylines for the route
                state.customRoute.pointS.forEachIndexed { index, latLng ->
                    // Add marker
                    val marker = map.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .icon(bitmapDescriptorFromVectorWithNumber(index + 1))
                    )
                    marker?.let { customRoutePointsMarkers.add(it) }
                }

                // Add polylines
                for (i in 0 until (state.customRoute.pointS.size.minus(1) ?: -1)) {
                    val polylineOptions = PolylineOptions()
                        .add(points[i], points[i + 1])
                        .color(0xFFDC6601.toInt())
                        .width(5f)
                    val polyline = map.addPolyline(polylineOptions)
                    customRoutePointsPollyline.add(polyline)
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp)
                .padding(top = 30.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow),
                contentDescription = null,
                modifier = Modifier.size(20.dp).
                clickable {
                    viewModel.onEvent(RouteDetailsEvent.OnBackClick)
                },
                tint = BokaBaySeaTrafficAppTheme.colors.white
            )

            Text(
                text = state.customRoute?.name ?: "",
                color = BokaBaySeaTrafficAppTheme.colors.white,
                style = BokaBaySeaTrafficAppTheme.typography.nunitoBold20
            )
        }
    }
}
