package llc.bokadev.bokasafe.presentation.bay_map

import android.graphics.Point
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.ktx.utils.sphericalDistance
import llc.bokadev.bokasafe.ui.theme.BokaBaySeaTrafficAppTheme

@Composable
fun CustomNauticalMilesMapScale(
    modifier: Modifier = Modifier,
    width: Dp = 65.dp,
    height: Dp = 40.dp,
    cameraPositionState: CameraPositionState,
    textColor: Color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
    lineColor: Color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
    shadowColor: Color = Color.White,
) {
    Box(
        modifier = modifier
            .size(width = width, height = height)
    ) {
        var horizontalLineWidthMeters by remember {
            mutableIntStateOf(0)
        }

        Canvas(
            modifier = Modifier.fillMaxSize(),
            onDraw = {
                val upperLeftLatLng =
                    cameraPositionState.projection?.fromScreenLocation(Point(0, 0))
                        ?: LatLng(0.0, 0.0)
                val upperRightLatLng =
                    cameraPositionState.projection?.fromScreenLocation(Point(0, size.width.toInt()))
                        ?: LatLng(0.0, 0.0)
                val canvasWidthMeters = upperLeftLatLng.sphericalDistance(upperRightLatLng)
                val eightNinthsCanvasMeters = (canvasWidthMeters * 8 / 9).toInt()

                horizontalLineWidthMeters = eightNinthsCanvasMeters

                val oneNinthWidth = size.width / 9
                val midHeight = size.height / 2
                val oneThirdHeight = size.height / 3
                val twoThirdsHeight = size.height * 2 / 3
                val strokeWidth = 4f
                val shadowStrokeWidth = strokeWidth + 3

                drawLine(
                    color = shadowColor,
                    start = Offset(oneNinthWidth, midHeight),
                    end = Offset(size.width, midHeight),
                    strokeWidth = shadowStrokeWidth,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = shadowColor,
                    start = Offset(oneNinthWidth, oneThirdHeight),
                    end = Offset(oneNinthWidth, midHeight),
                    strokeWidth = shadowStrokeWidth,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = shadowColor,
                    start = Offset(oneNinthWidth, midHeight),
                    end = Offset(oneNinthWidth, twoThirdsHeight),
                    strokeWidth = shadowStrokeWidth,
                    cap = StrokeCap.Round
                )

                drawLine(
                    color = lineColor,
                    start = Offset(oneNinthWidth, midHeight),
                    end = Offset(size.width, midHeight),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = lineColor,
                    start = Offset(oneNinthWidth, oneThirdHeight),
                    end = Offset(oneNinthWidth, midHeight),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = lineColor,
                    start = Offset(oneNinthWidth, midHeight),
                    end = Offset(oneNinthWidth, twoThirdsHeight),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )
            }
        )
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(bottom = 10.dp),
            horizontalAlignment = Alignment.End
        ) {
            val nauticalMiles =
                (horizontalLineWidthMeters.toDouble() / 1852) // Convert meters to nautical miles



            Text(
                style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14,
                color = shadowColor,
                text = "${nauticalMiles.format(2)} NM" // Format to 2 decimal places
            )
        }
    }
}

// Extension function to format numbers
fun Double.format(digits: Int) = "%.${digits}f".format(this)
