package llc.bokadev.bokasafe.presentation.bay_map

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import llc.bokadev.bokasafe.ui.theme.BokaBaySeaTrafficAppTheme

@Composable
fun CompassItem(
    modifier: Modifier = Modifier,
    userCourse: Float // The user's course in degrees (0° to 360°)
) {
    // Arrow rotation animation
    val courseAngle = remember { Animatable(0f) }

    LaunchedEffect(userCourse) {
        // Animate the arrow to the current course angle
        courseAngle.animateTo(
            targetValue = userCourse % 360, // Ensure the course is within 0°-360°
            animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
        )
    }

    Box(
        modifier = modifier
            .size(110.dp)
            .background(
                color = BokaBaySeaTrafficAppTheme.colors.defaultGray.copy(alpha = 0.2f),
                shape = CircleShape
            )
    ) {
        val white = BokaBaySeaTrafficAppTheme.colors.white
        val defaultGray = BokaBaySeaTrafficAppTheme.colors.defaultGray
        val primaryRed = BokaBaySeaTrafficAppTheme.colors.primaryRed
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Draw the compass outer circle
            drawCircle(
                color = defaultGray,
                radius = size.minDimension / 2,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 10f)
            )

            val radius = size.minDimension / 2
            val innerRadius = radius * 0.85f // Inner radius for the tick marks

            // Draw cardinal points (N, E, S, W) and ticks
            val cardinalPoints = listOf("N", "E", "S", "W")
            for (i in 0 until 360 step 10) {
                val angleRad = Math.toRadians(i.toDouble() - 90)
                val startX = center.x + (if (i % 90 == 0) innerRadius else radius * 0.9f) * kotlin.math.cos(angleRad).toFloat()
                val startY = center.y + (if (i % 90 == 0) innerRadius else radius * 0.9f) * kotlin.math.sin(angleRad).toFloat()
                val endX = center.x + radius * kotlin.math.cos(angleRad).toFloat()
                val endY = center.y + radius * kotlin.math.sin(angleRad).toFloat()

                drawLine(
                    color = if (i % 90 == 0) white else defaultGray,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = if (i % 90 == 0) 8f else 4f
                )
            }

            // Draw cardinal points (N, E, S, W) as text
            cardinalPoints.forEachIndexed { index, point ->
                val angle = index * 90f - 90f
                val textOffset = Offset(
                    center.x + (radius * 0.7f) * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat(),
                    center.y + (radius * 0.7f) * kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat()
                )
                drawContext.canvas.nativeCanvas.drawText(
                    point,
                    textOffset.x,
                    textOffset.y + 15, // Adjust for vertical centering
                    android.graphics.Paint().apply {
                        color = white.toArgb()
                        textSize = 40f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }

            // Draw the directional arrow
            rotate(degrees = courseAngle.value, pivot = center) {
                val offsetFactor = radius * 0.1f
                val startOffset = Offset(center.x, center.y + offsetFactor)

                // Create a gradient brush with transparency at the start
                val gradientBrush = Brush.linearGradient(
                    colors = listOf(
                        defaultGray .copy(alpha = 0f), // Transparent at the start
                        white // Opaque at the end
                    ),
                    start = startOffset,
                    end = Offset(center.x, center.y - radius * 0.6f)
                )

                drawLine(
                    brush = gradientBrush, // Use gradient brush
                    start = startOffset,
                    end = Offset(center.x, center.y - radius * 0.6f),
                    strokeWidth = 4f,
                    cap = StrokeCap.Round
                )
            }
        }

        // Display the course angle at the center
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "${userCourse.toInt()}°",
                style = BokaBaySeaTrafficAppTheme.typography.nunitoRegular18,
                color = BokaBaySeaTrafficAppTheme.colors.white,
                textAlign = TextAlign.Center,
            )
        }
    }
}
