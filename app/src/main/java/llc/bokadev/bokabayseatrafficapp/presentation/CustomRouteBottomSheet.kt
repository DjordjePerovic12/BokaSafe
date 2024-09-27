package llc.bokadev.bokabayseatrafficapp.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import llc.bokadev.bokabayseatrafficapp.ui.theme.BokaBaySeaTrafficAppTheme

@Composable
fun CustomRouteBottomSheet(
    state: GuideState // Use GuideState directly instead of separate 'points'
) {

    // The points are now derived directly from the state
    val points = state.customRoutePoints

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
            .fillMaxWidth()
            .background(BokaBaySeaTrafficAppTheme.colors.white)
            .padding(horizontal = 20.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(15.dp))
        }

        item {
            Text(
                text = "Create a custom route and calculate\n the distance between custom points on the sea",
                color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold14
            )
        }

        item {
            Spacer(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .height(3.dp)
                    .background(BokaBaySeaTrafficAppTheme.colors.gray)
            )
        }

        if (points.isNotEmpty()) {
            // Make sure to use the re-indexed list and proper keys
            itemsIndexed(
                items = points,
                key = { index, point -> "${point.latitude}_${point.longitude}" }) { index, point ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(BokaBaySeaTrafficAppTheme.colors.lightBlue)
                        ) {
                            Text(
                                text = "${index + 1}", // The index will always start from 1
                                color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                                style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold14,
                                modifier = Modifier.padding(5.dp),
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        Text(text = "${point.latitude} N ${point.longitude} E")


                    }

                    if (index < state.customRouteConsecutivePointsDistance.size) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            DashedLine()
                            Spacer(modifier = Modifier.width(20.dp))

                            if (state.customRouteConsecutivePointsDistance.isNotEmpty()) {
                                // Display the distance for consecutive points between index 0 and 1, 1 and 2, and so on
                                Text(
                                    text = "D = ${state.customRouteConsecutivePointsDistance[index].toNauticalMiles()} nm \n" +
                                            "W = ${state.customRouteConsecutivePointsAzimuth[index].toInt()}°"
                                )
                            }
                        }
                    }
                }
            }
        }

        if (state.customRouteConsecutivePointsDistance.isNotEmpty()) {
            var distance = 0f
            state.customRouteConsecutivePointsDistance.forEach {
                distance += it
            }
            item { Text(text = "Total distance: ${distance.toNauticalMiles()} NM") }
        }

    }
}


@Composable
fun DashedLine() {
    Canvas(
        modifier = Modifier
            .width(1.dp) // Set the width for the line
            .height(25.dp) // Make it fill the height of the container
    ) {
        val dashHeight = 10.dp.toPx()  // Height of each dash
        val gapHeight = 5.dp.toPx()    // Gap between dashes
        val lineHeight = size.height   // Total height of the line

        var startY = 0f
        while (startY < lineHeight) {
            drawLine(
                color = Color.Gray,
                start = Offset(x = 0f, y = startY), // Start point of the line (fixed x)
                end = Offset(x = 0f, y = startY + dashHeight), // End point of the line (fixed x)
                strokeWidth = 1f
            )
            startY += dashHeight + gapHeight
        }
    }
}