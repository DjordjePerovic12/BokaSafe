package llc.bokadev.bokabayseatrafficapp.presentation.bay_map

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import llc.bokadev.bokabayseatrafficapp.core.components.ColumnLayout
import llc.bokadev.bokabayseatrafficapp.core.utils.Gps
import llc.bokadev.bokabayseatrafficapp.core.utils.enableGps
import llc.bokadev.bokabayseatrafficapp.core.utils.isGpsOn
import llc.bokadev.bokabayseatrafficapp.core.utils.observeWithLifecycle
import llc.bokadev.bokabayseatrafficapp.core.utils.toLatitude
import llc.bokadev.bokabayseatrafficapp.core.utils.toLongitude
import llc.bokadev.bokabayseatrafficapp.core.utils.toThreeDigitString
import llc.bokadev.bokabayseatrafficapp.ui.theme.BokaBaySeaTrafficAppTheme

import timber.log.Timber


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BayMapScreen(
    viewModel: BayMapViewModel,
    showSnackBar: (String) -> Unit,
    launchPhoneIntent: (intent: Intent) -> Unit
) {

    viewModel.launchIntentChannel.observeWithLifecycle { intent ->
        launchPhoneIntent(intent)
    }

    viewModel.snackBarChannel.observeWithLifecycle { message ->
        showSnackBar(message)
    }




    val state = viewModel.state




    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    val context = LocalContext.current


    LaunchedEffect(key1 = true) {
        IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION).also { intentFilter ->
            context.registerReceiver(
                viewModel.gpsReceiver, intentFilter,
                Context.RECEIVER_NOT_EXPORTED
            )
        }
        if (context.findActivity()?.let { isGpsOn(it) } == true)
            viewModel.onEvent(MapEvent.UpdateGpsState(Gps.ON))
        else viewModel.onEvent(MapEvent.UpdateGpsState(Gps.OFF))
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getPreferredUnit()
    }


    LaunchedEffect(key1 = state.userCourseOfMovementAzimuth) {
        Timber.e("COURSE main comp ${state.userCourseOfMovementAzimuth}")
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    OnLifecycleEvent { _, event ->
        try {
            when (event) {
                Lifecycle.Event.ON_DESTROY -> {
                    context.unregisterReceiver(viewModel.gpsReceiver)
                }

                else -> Unit
            }
        } catch (e: Exception) {
            Timber.e(e.message)
        }
    }






    ColumnLayout {
        BokaBayMapScreenContent(viewModel = viewModel,
            onCheckPointClick = { checkpoint ->
                viewModel.onEvent(
                    MapEvent.CheckpointSelected(
                        checkpoint
                    )
                )
            }, onShipwreckClick = {
                viewModel.onEvent(MapEvent.ShipWreckSelected(it))
            },
            onProhibitedAnchoringZoneClick = {
                viewModel.onEvent(MapEvent.ProhibitedAnchoringZoneSelected(it))
            },
            onAnchorageClick = {
                viewModel.onEvent(MapEvent.AnchorageSelected(it))
            },
            onAnchorageZoneClick = {
                viewModel.onEvent(MapEvent.AnchorageZoneSelected(it))
            },
            onBuoyClick = {
                viewModel.onEvent(MapEvent.BuoySelected(it))
            },
            onMarkerCreation = {
                viewModel.onEvent(MapEvent.CreateUserMarker(it))
            }, onShipwreckMarkersCreation = {
                viewModel.onEvent(MapEvent.AddShipwreckCheckpointMarker(it))
            },
            onProhibitedAnchoringZoneMarkersCreation = {
                MapEvent.AddProhibitedAnchoringZoneMarker(it)
            },
            onLighthouseMarkersCreation = {
                viewModel.onEvent(MapEvent.AddLighthouseCheckpointMarker(it))
            },
            onAnchorageMarkerCreation = {
                viewModel.onEvent(MapEvent.AddAnchorageMarker(it))
            },
            onAnchorageZoneMarkerCreation = {
                viewModel.onEvent(MapEvent.AddAnchorageZoneMarker(it))
            },
            onBuoyMarkerCreation = {
                viewModel.onEvent(MapEvent.AddBuoyMarker(it))
            },
            onMarkerUpdate = {
                viewModel.onEvent(MapEvent.UpdateUserMarker)
            },
            resetZoom = {
                viewModel.onEvent(MapEvent.ResetZoom)
            },
            onUserLocationClick = {
                if (isGpsOn(context))
                    viewModel.onEvent(MapEvent.ZoomUserLocation)
                else context.findActivity()?.let { enableGps(it) }
            },
            state = state,
            onItemHide = {
                viewModel.onEvent(MapEvent.OnItemHide(it))
            }
        )
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
            .padding(horizontal = 25.dp)
    ) {
        AnimatedVisibility(visible = state.isLighthouseMarkerSelected) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(5.dp))
                    .background(BokaBaySeaTrafficAppTheme.colors.lightBlue.copy(.9f))
            ) {
                Text(
                    text = viewModel.state.selectedCheckpoint?.name ?: "",
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold18,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Characteristics : ${viewModel.state.selectedCheckpoint?.characteristics}",
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular18
                )


                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "D: ${viewModel.state.distanceToCheckpoint?.toNauticalMiles()} NM",
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular18
                )

                Spacer(modifier = Modifier.height(15.dp))


                Text(
                    text = "W: ${viewModel.state.azimuth?.toInt()?.toThreeDigitString()}°",
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular18
                )


            }
        }

        AnimatedVisibility(visible = state.isShipwreckMarkerSelected) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(5.dp))
                    .background(BokaBaySeaTrafficAppTheme.colors.lightBlue.copy(.9f))
            ) {
                viewModel.state.selectedShipwreck?.name?.let {
                    Text(
                        text = it,
                        color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                        style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold18,
                        modifier = Modifier.padding(horizontal = 25.dp),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "D: ${viewModel.state.selectedShipwreck?.name} : ${state.distanceToCheckpoint?.toNauticalMiles()} NM",
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "W: ${viewModel.state.selectedShipwreck?.name} : ${state.azimuth?.toInt()?.toThreeDigitString()}°",
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                viewModel.state.selectedShipwreck?.description?.let {
                    Text(
                        text = it,
                        color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                        style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14,
                        modifier = Modifier.padding(horizontal = 25.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                viewModel.state.selectedShipwreck?.latitude?.let {
                    Text(
                        text = "Latitude : ${it.toLatitude()} N",
                        color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                        style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14,
                        modifier = Modifier.padding(horizontal = 25.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                viewModel.state.selectedShipwreck?.longitude?.let {
                    Text(
                        text = "Longitude : ${it.toLongitude()} E",
                        color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                        style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14,
                        modifier = Modifier.padding(horizontal = 25.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                viewModel.state.selectedShipwreck?.depth?.let {
                    Text(
                        text = "Depth : $it",
                        color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                        style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14,
                        modifier = Modifier.padding(horizontal = 25.dp)
                    )
                }

            }
        }

        AnimatedVisibility(visible = state.isProhibitedAnchoringZoneMarkerSelected) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(BokaBaySeaTrafficAppTheme.colors.lightBlue.copy(.9f))
            ) {
                Text(
                    text =  viewModel.state.selectedProhibitedProhibitedAnchoringZone?.name?: "",
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold18,
                    modifier = Modifier.padding(horizontal = 25.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Anchoring prohibited area",
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular18,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )
            }
        }

        AnimatedVisibility(visible = state.isAnchorageMarkerSelected) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(BokaBaySeaTrafficAppTheme.colors.lightBlue.copy(.9f))
            ) {
                Text(
                    text = "${viewModel.state.selectedAnchorage?.name}",
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold18,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )


                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "D: ${viewModel.state.selectedAnchorage?.name} : ${state.distanceToCheckpoint?.toNauticalMiles()} NM",
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "W: ${viewModel.state.selectedAnchorage?.name} : ${state.azimuth?.toInt()?.toThreeDigitString()}°",
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )

            }
        }
        AnimatedVisibility(visible = state.isAnchorageZoneMarkerSelected) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(BokaBaySeaTrafficAppTheme.colors.lightBlue.copy(.9f))
            ) {
                Text(
                    text = "${viewModel.state.selectedAnchorageZone?.name}",
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold18,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )


                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "D: ${viewModel.state.selectedAnchorageZone?.name} : ${state.distanceToCheckpoint?.toNauticalMiles()} NM",
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "W: ${viewModel.state.selectedAnchorageZone?.name} : ${state.azimuth?.toInt()?.toThreeDigitString()}°",
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )

            }
        }
        AnimatedVisibility(visible = state.isBuoyVisible) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(BokaBaySeaTrafficAppTheme.colors.lightBlue.copy(.9f))
            ) {
                Text(
                    text = "${viewModel.state.selectedBuoy?.name}",
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold18,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )


                Spacer(modifier = Modifier.height(10.dp))


                Text(
                    text = "Characteristics: ${viewModel.state.selectedBuoy?.characteristic}",
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular18,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )


                Spacer(modifier = Modifier.height(10.dp))



                Text(
                    text = "D: ${state.distanceToCheckpoint?.toNauticalMiles()} NM",
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "W: ${state.azimuth?.toInt()?.toThreeDigitString()}°",
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )

            }
        }
    }
}


@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}


fun Float.toNauticalMiles(): String {
    val nauticalMiles = this / 1852
    return "%.2f".format(nauticalMiles)
}