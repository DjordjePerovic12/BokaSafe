package llc.bokadev.bokabayseatrafficapp.core.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.net.URLEncoder

@Composable
inline fun <reified T> Flow<T>.observeWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.lifecycleScope.launch {
            flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect(action)
        }
    }
}

fun Context.hasLocationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this, Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
        this, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

fun String?.toNonNull() = if (this.isNullOrEmpty()) "" else this

//infix fun Request.signWithToken(accessToken: String?): Request {
//    val builder = newBuilder().header("Accept", "application/json")
//    if (this.url.toString().contains(BASE_URL) && !accessToken.isNullOrEmpty()) {
//        builder.header("Authorization", "Bearer $accessToken")
//    }
//    return builder.build()
//}

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}
fun String.isToken() = this != ""

fun String.encodeArgument(): String = URLEncoder.encode(
    this, Charsets.UTF_8.name()
).replace('+', ' ')

@Composable
fun Modifier.debounceButton(
    scope: CoroutineScope,
    onClick: () -> Unit
): Modifier = composed {
    var buttonDebounceJob by remember { mutableStateOf<Job?>(null) }

    // Apply the modifier logic to the button
    Modifier.pointerInput(Unit) {
        detectTapGestures { offset ->
            // Handle button click
            buttonDebounceJob?.cancel()
            buttonDebounceJob = scope.launch {
                delay(500)
                onClick() // Invoke the provided function
            }
        }
    }
}

fun Modifier.hideKeyboard(focusManager: FocusManager): Modifier = composed {
    pointerInput(Unit) {
        detectTapGestures(onTap = {
            focusManager.clearFocus()
        })
    }
}

fun Int.toThreeDigitString(): String {
    return String.format("%03d", this)
}

fun Float.toKnots() : Float {
    return String.format("%.1f", (this / 1852f) * 3600f).toFloat()
}

fun Float.toKilometersPerHour(): Float {
    return String.format("%.1f", this * 3.6f).toFloat()
}

/**
 * Extension function to format a latitude value (DD° MM').
 * Latitude degrees range from -90 to 90.
 * @return A string in the format "DD° MM'"
 */
fun Double.toLatitude(): String {
    val degrees = this.toInt() // Extract the integer part for degrees
    val minutes = (Math.abs(this) - Math.abs(degrees)) * 60 // Calculate fractional part in minutes
    return String.format("%02d° %.3f'", degrees, minutes) // Use %02d for two digits
}

/**
 * Extension function to format a longitude value (DDD° MM').
 * Longitude degrees range from -180 to 180.
 * @return A string in the format "DDD° MM'"
 */
fun Double.toLongitude(): String {
    val degrees = this.toInt() // Extract the integer part for degrees
    val minutes = (Math.abs(this) - Math.abs(degrees)) * 60 // Calculate fractional part in minutes
    return String.format("%03d° %.3f'", degrees, minutes) // Use %03d for three digits
}
