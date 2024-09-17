package llc.bokadev.bokabayseatrafficapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import llc.bokadev.bokabayseatrafficapp.core.navigation.Navigator
import llc.bokadev.bokabayseatrafficapp.core.navigation.graphs.BokaBaySeaTrafficAppNavigation
import llc.bokadev.bokabayseatrafficapp.core.utils.CustomModifiers
import llc.bokadev.bokabayseatrafficapp.core.utils.rememberAppState
import llc.bokadev.bokabayseatrafficapp.presentation.BayMapViewModel
import llc.bokadev.bokabayseatrafficapp.presentation.PermissionDenied
import llc.bokadev.bokabayseatrafficapp.ui.theme.BokaBaySeaTrafficAppTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity(), SensorEventListener {


    private val viewModel: BayMapViewModel by viewModels()

    @Inject
    internal lateinit var navigator: Navigator

    private lateinit var locationRequest: LocationRequest
    private val REQUEST_CHECK_SETTINGS = 1001

    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor

    private var lastAccelerometerValues: FloatArray = FloatArray(3)


    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)

        setContent {
            BokaBaySeaTrafficAppTheme {
                val appState = rememberAppState()
                val multiplePermissionsState = rememberMultiplePermissionsState(
                    listOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )

                val hasLocationPermission = multiplePermissionsState.permissions.all {
                    it.status == PermissionStatus.Granted
                }

                LaunchedEffect(hasLocationPermission) {
                    if (hasLocationPermission) {
                        checkLocationSettings()
                    } else {
                        multiplePermissionsState.launchMultiplePermissionRequest()
                    }
                }



                Scaffold(
                    snackbarHost = {
                        CustomModifiers.snackBarHost(appState.scaffoldState)
                    }
                ) { innerPadding ->
                    AnimatedContent(
                        targetState = hasLocationPermission, label = "",
                    ) { hasLocationPermission ->
                        if (hasLocationPermission) {
                            // If permissions are granted, proceed with the app's navigation and flow
                            BokaBaySeaTrafficAppNavigation(
                                navController = appState.navController,
                                navigator = navigator,
                                showSnackBar = { message ->
                                    appState.showSnackBar(message)
                                },
                                launchIntent = { intent ->
                                    startActivity(intent)
                                }
                            )
                        } else {
                            // If permissions are denied, show a custom UI
                            PermissionDenied()
                        }
                    }
                }
            }
        }
    }

    private fun checkLocationSettings() {
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000L)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(1000)
            .setMaxUpdateDelayMillis(3000)
            .build()

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed by showing the user a dialog
                try {
                    exception.startResolutionForResult(this@MainActivity, REQUEST_CHECK_SETTINGS)
                } catch (_: IntentSender.SendIntentException) {

                }
            }
        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            lastAccelerometerValues = p0.values.clone()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}







