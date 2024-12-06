package llc.bokadev.bokabayseatrafficapp.presentation.bay_map


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import llc.bokadev.bokabayseatrafficapp.core.utils.toKilometersPerHour
import llc.bokadev.bokabayseatrafficapp.core.utils.toKnots
import llc.bokadev.bokabayseatrafficapp.ui.theme.BokaBaySeaTrafficAppTheme
import timber.log.Timber

@Composable
fun SpeedometerItem(
    modifier: Modifier = Modifier,
    preferredSpeedUnit: String,
    isUserStatic: Boolean,
    speedList: ArrayList<Float>,
    userMovementSpeed: Float
) {
    val greenThreshold = 10 * 0.514444f // 10 knots in m/s
    val yellowThreshold = 13 * 0.514444f // 13 knots in m/s
    val maxSpeedInMetersPerSecond = 40f // Maximum speed for the arrow

    // Background color based on thresholds
    val backgroundColor by animateColorAsState(
        targetValue = when {
            userMovementSpeed.isNaN() -> BokaBaySeaTrafficAppTheme.colors.confirmGreen
            userMovementSpeed <= greenThreshold -> BokaBaySeaTrafficAppTheme.colors.confirmGreen
            userMovementSpeed <= yellowThreshold -> BokaBaySeaTrafficAppTheme.colors.yellow
            else -> BokaBaySeaTrafficAppTheme.colors.red
        }
    )

    // Arrow angle animation
    val angle = remember { Animatable(0f) }

    LaunchedEffect(userMovementSpeed) {
        // Map speed to the range [0°, 180°] for the arrow
        val targetAngle = if (isUserStatic || userMovementSpeed.isNaN() || userMovementSpeed <= 0) {
            0f
        } else {
            (userMovementSpeed / maxSpeedInMetersPerSecond) * 180f
        }

        angle.animateTo(
            targetValue = targetAngle.coerceIn(0f, 180f), // Clamp within 0° to 180°
            animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
        )
    }

    // Format speed for display
    val formattedSpeed = when (preferredSpeedUnit) {
        "km/h" -> userMovementSpeed.toKilometersPerHour()
        "knots" -> userMovementSpeed.toKnots()
        else -> userMovementSpeed
    }

    Box(
        modifier = Modifier
            .size(80.dp)
            .background(color = backgroundColor, shape = CircleShape)
    ) {
        val defaultGray = BokaBaySeaTrafficAppTheme.colors.defaultGray
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Draw the speedometer arc (half-circle)
            drawArc(
                color = defaultGray,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 20f)
            )

//            // Draw the arrow
//            rotate(degrees = angle.value - 90f, pivot = center) {
//                drawLine(
//                    color = defaultGray,
//                    start = center,
//                    end = Offset(center.x, center.y - size.minDimension / 3),
//                    strokeWidth = 8f,
//                    cap = StrokeCap.Round
//                )
//            }
        }

        // Display the speed value at the center
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = if (isUserStatic || speedList.isEmpty()) "0.0 \n $preferredSpeedUnit"
                else "$formattedSpeed \n $preferredSpeedUnit",
                style = BokaBaySeaTrafficAppTheme.typography.nunitoRegular18,
                color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                textAlign = TextAlign.Center
            )
        }
    }
}




//TEST COMPOSABLE CODE
//KEEP FOR COMPARISON
//SO IF REAL TESTING IS OFF
//WE CAN FIX BASED ON TEST EXAMPLE


//var speedInMetersPerSecond by remember { mutableStateOf(0f) }
//val speedListHelper = remember { arrayListOf<Float>() }
//
//val greenThreshold = 10 * 0.514444f // 10 knots in m/s
//val yellowThreshold = 13 * 0.514444f // 13 knots in m/s
//
//
//// Define the maximum speed for the speedometer (in m/s)
//val maxSpeedInMetersPerSecond = 40f // Example: Max speed corresponds to 180° (half-circle)
//
//// Background color based on thresholds
//val backgroundColor by animateColorAsState(
//    targetValue = when {
//        speedInMetersPerSecond.isNaN() -> BokaBaySeaTrafficAppTheme.colors.green
//        speedInMetersPerSecond <= greenThreshold -> BokaBaySeaTrafficAppTheme.colors.green
//        speedInMetersPerSecond <= yellowThreshold -> BokaBaySeaTrafficAppTheme.colors.yellow
//        else -> BokaBaySeaTrafficAppTheme.colors.primaryRed.copy(.7f)
//    }
//)
//
//// Arrow angle animation
//val angle = remember { Animatable(0f) }
//
//LaunchedEffect(speedInMetersPerSecond) {
//    // Map speed to the range [0°, 180°]
//    val targetAngle = if (speedInMetersPerSecond.isNaN() || speedInMetersPerSecond <= 0) {
//        0f
//    } else {
//        (speedInMetersPerSecond / maxSpeedInMetersPerSecond) * 180f
//    }
//
//    angle.animateTo(
//        targetValue = targetAngle.coerceIn(0f, 180f), // Clamp within 0° to 180°
//        animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing)
//    )
//}
//
//// Simulate speed updates for testing
//LaunchedEffect(Unit) {
//    val testSpeedsInMetersPerSecond = listOf(
//        3.51f, 17.2f, 5.16f, 24.64f, 3.03f,
//        4.3f, 19.09f, 1.78f, 18.9f, 5.81f,
//        5.94f, 6.05f, 28.87f, 6.23f, 0.75f
//    )
//    for (currentSpeed in testSpeedsInMetersPerSecond) {
//        speedInMetersPerSecond = currentSpeed
//        speedListHelper.add(currentSpeed)
//        delay(3000L) // Update every 3 seconds
//    }
//}
//
//// Convert speed for display
//val formattedSpeed = when (preferredSpeedUnit) {
//    "km/h" -> speedInMetersPerSecond.toKilometersPerHour()
//    "knots" -> speedInMetersPerSecond.toKnots()
//    else -> speedInMetersPerSecond
//}
//
//Box(
//modifier = Modifier
//.size(80.dp)
//.background(color = backgroundColor, shape = CircleShape)
//) {
//    val primaryRed = BokaBaySeaTrafficAppTheme.colors.primaryRed
//    val defaultGray = BokaBaySeaTrafficAppTheme.colors.defaultGray.copy(.8f)
//    Canvas(modifier = Modifier.fillMaxSize()) {
//        // Draw the speedometer arc (half-circle)
//        drawArc(
//            color = defaultGray,
//            startAngle = 180f,
//            sweepAngle = 180f,
//            useCenter = false,
//            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 20f)
//        )
//
//        // Draw the arrow
//        rotate(degrees = angle.value - 90f, pivot = center) {
//            drawLine(
//                color = defaultGray,
//                start = center,
//                end = Offset(center.x, center.y - size.minDimension / 3),
//                strokeWidth = 8f,
//                cap = StrokeCap.Round
//            )
//        }
//    }
//
//    // Display the speed value at the center
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Text(
//            text = if (speedListHelper.isEmpty()) "0.0 \n $preferredSpeedUnit"
//            else "$formattedSpeed \n $preferredSpeedUnit",
//            style = BokaBaySeaTrafficAppTheme.typography.nunitoRegular18,
//            color = BokaBaySeaTrafficAppTheme.colors.white.copy(.8f),
//            textAlign = TextAlign.Center
//        )
//    }
//}