package llc.bokadev.bokasafe

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.hardware.GeomagneticField
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import llc.bokadev.bokasafe.core.navigation.Navigator
import llc.bokadev.bokasafe.core.navigation.graphs.BokaBaySeaTrafficAppNavigation
import llc.bokadev.bokasafe.core.utils.CustomModifiers
import llc.bokadev.bokasafe.core.utils.rememberAppState
import llc.bokadev.bokasafe.presentation.bay_map.BayMapViewModel
import llc.bokadev.bokasafe.presentation.bay_map.MapEvent
import llc.bokadev.bokasafe.presentation.PermissionDenied
import llc.bokadev.bokasafe.ui.theme.BokaBaySeaTrafficAppTheme
import timber.log.Timber
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
    private lateinit var magnetometer: Sensor

    private var gravity: FloatArray = FloatArray(9)
    private var geomagentic: FloatArray = FloatArray(9)
    private var currentDegree = 0f


    @RequiresApi(Build.VERSION_CODES.Q)
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseMessaging.getInstance().subscribeToTopic("lighthouse_updates")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("Successfully subscribed to topic: lighthouse_updates")
                } else {
                    Timber.e("Failed to subscribe to topic: lighthouse_updates")
                }
            }


        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)!!
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.isNavigationBarContrastEnforced = false
        enableEdgeToEdge()


        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.lighthouses.isEmpty()
            }
        }


        setContent {
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->

                }
            )

            val multiplePermissionsState =
                rememberMultiplePermissionsState(
                    listOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )

            SideEffect {
                if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    Timber.e("Petar Gajevic 1")
                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                        // show rationale and then launch launcher to request permission
                        Timber.e("Petar Gajevic 2")
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    } else {
                        Timber.e("Petar Gajevic 3")
                        // first request or forever denied case
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                }

                val hasLocationPermission = multiplePermissionsState.permissions.all {
                    it.status == PermissionStatus.Granted
                }


                if (hasLocationPermission) {
                    checkLocationSettings()
                } else {
                    multiplePermissionsState.launchMultiplePermissionRequest()
                }

            }
            BokaBaySeaTrafficAppTheme {
                val appState = rememberAppState()

                Scaffold(
                    snackbarHost = {
                        CustomModifiers.snackBarHost(appState.scaffoldState)
                    }
                ) { innerPadding ->


                            // If permissions are granted, proceed with the app's navigation and flow
                            BokaBaySeaTrafficAppNavigation(
                                navController = appState.navController,
                                navigator = navigator,
                                showSnackBar = { message ->
                                    appState.showSnackBar(message)
                                },
                                launchIntent = { intent ->
                                    startActivity(intent)
                                },
                                viewModel = viewModel,
                                activateCustomRoute = {
                                    viewModel.onEvent(MapEvent.OnAddNewRoute)
                                },

                                )


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


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            1001
        )
    }


    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this, accelerometer)
        sensorManager.unregisterListener(this, magnetometer)

    }

    var lastUpdateTime: Long = 0
    val updateInterval: Long = 5000 // 750 milliseconds
    var sensorUpdateJob: Job? = null
    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            gravity = p0.values
        } else if (p0?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagentic = p0.values
        }




        if (gravity != null && geomagentic != null) {
            val R = FloatArray(9)
            val I = FloatArray(9)

            if (SensorManager.getRotationMatrix(R, I, gravity, geomagentic)) {
                val orientation = FloatArray(3)
                SensorManager.getOrientation(R, orientation)
                val azimuthRadians = orientation[0]
                var azimuthDegrees = Math.toDegrees(azimuthRadians.toDouble())
                azimuthDegrees = (azimuthDegrees + 360) % 360

                val degree = Math.round(azimuthDegrees)


                val userLocation = Location("").apply {
                    latitude = viewModel.state.userLocation?.latitude ?: 0.0
                    longitude = viewModel.state.userLocation?.longitude ?: 0.0
                }
                val geomagneticField = GeomagneticField(
                    userLocation.latitude.toFloat(),
                    userLocation.longitude.toFloat(),
                    userLocation.altitude.toFloat(), // Make sure altitude is accurate if available, or use 0.0 if not
                    System.currentTimeMillis()
                )
                val declination = geomagneticField.declination


                val trueNorth = (azimuthDegrees + declination + 360) % 360

                currentDegree = -degree.toFloat()
                val userCourseOfMovement = getDirection(trueNorth.toFloat())
                if (System.currentTimeMillis() - lastUpdateTime > updateInterval) {
                    sensorUpdateJob?.cancel() // Cancel any previous job
                    sensorUpdateJob = CoroutineScope(Dispatchers.Main).launch {
                        lastUpdateTime = System.currentTimeMillis()
                        viewModel.onEvent(
                            MapEvent.OnCourseChange(
                                direction = userCourseOfMovement,
                                angle = azimuthDegrees
                            )
                        )
                        Timber.e("SENSOR CHANGE: True North $trueNorth, Declination $declination, azimuth $azimuthDegrees")
                    }
                }
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    private fun getDirection(degree: Float): String {
        return when (degree) {
            in 337.5..360.0,
            in 0.0..22.5 -> "North"

            in 22.5..67.5 -> "North-East"
            in 67.5..112.5 -> "East"
            in 112.5..157.5 -> "South-East"
            in 157.5..202.5 -> "South"
            in 202.5..247.5 -> "South-West"
            in 247.5..292.5 -> "West"
            in 292.5..337.5 -> "North-West"
            else -> "Invalid degree"
        }
    }
}







