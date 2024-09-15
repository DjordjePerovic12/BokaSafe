package llc.bokadev.bokabayseatrafficapp.core.utils

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.location.LocationManager
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority


fun isGpsOn(context: Context): Boolean {
    val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}


fun createGpsReceiver(
    onGpsON: () -> Unit,
    onGpsOFF: () -> Unit
): BroadcastReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val hasProviderChanged = intent?.action == LocationManager.PROVIDERS_CHANGED_ACTION
        if (hasProviderChanged) {
            if (context?.let { isGpsOn(context = it) } == true) onGpsON()
            else onGpsOFF()
        }
    }
}

fun enableGps(
    activity: Activity
) {
    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
        .setWaitForAccurateLocation(false)
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        .build()

    val settingsBuilder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)
    settingsBuilder.setAlwaysShow(true)

    val result =
        LocationServices.getSettingsClient(activity).checkLocationSettings(settingsBuilder.build())
    result.addOnCompleteListener { task ->
        try {
            task.getResult(ApiException::class.java)
        } catch (ex: ApiException) {
            when (ex.statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                    val resolvableApiException = ex as ResolvableApiException
                    resolvableApiException.startResolutionForResult(
                        activity, 101
                    )
                } catch (e: IntentSender.SendIntentException) {
                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                }
            }
        }
    }
}
