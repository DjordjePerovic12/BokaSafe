package llc.bokadev.bokabayseatrafficapp.presentation.bay_map

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import llc.bokadev.bokabayseatrafficapp.core.utils.toLatitude
import llc.bokadev.bokabayseatrafficapp.core.utils.toLongitude
import llc.bokadev.bokabayseatrafficapp.ui.theme.BokaBaySeaTrafficAppTheme

@Composable
fun CustomRouteBottomSheet(state: GuideState,
                           onSaveRouteClick: () -> Unit) {
    val points = state.customRoutePoints

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxHeight(0.6f)
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
                text = "Create a custom route and calculate\\n the distance between custom points on the sea",
                color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold14,
                modifier = Modifier
                    .padding(vertical = 10.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
        }

        if (points.isNotEmpty()) {
            itemsIndexed(points) { index, point ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Display point details
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFE0F7FA)) // Light blue background
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = "Point ${index + 1}",
                                color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                                style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold14,
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.width(15.dp))

                        Text(
                            text = "Latitude: ${point.latitude.toLatitude()}° N, Longitude: ${point.longitude.toLongitude()}° W",
                            color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                            style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14
                        )
                    }

                    if (index < state.customRouteConsecutivePointsDistance.size) {
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "D: ${state.customRouteConsecutivePointsDistance[index].toNauticalMiles()} NM, " +
                                    "C: ${state.customRouteConsecutivePointsAzimuth[index].toInt()}°",
                            color = Color.Gray,
                            style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14,
                            modifier = Modifier.padding(start = 50.dp)
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }

        if (state.customRouteConsecutivePointsDistance.isNotEmpty()) {
            var distance = 0f
            state.customRouteConsecutivePointsDistance.forEach {
                distance += it
            }
            item { Text(text = "Total distance: ${distance.toNauticalMiles()} NM") }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }

        if (state.customRoutePoints.size > 1)
            item {
                Button(
                    onClick = {onSaveRouteClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = ButtonColors(
                        containerColor = BokaBaySeaTrafficAppTheme.colors.lightBlue,
                        disabledContainerColor = BokaBaySeaTrafficAppTheme.colors.lightBlue,
                        contentColor = BokaBaySeaTrafficAppTheme.colors.white,
                        disabledContentColor = BokaBaySeaTrafficAppTheme.colors.white
                    )
                ) {
                    Text(
                        text = "SAVE ROUTE",
                        style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold18,
                        color = BokaBaySeaTrafficAppTheme.colors.white
                    )
                }
            }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun DashedLine() {
    Canvas(
        modifier = Modifier
            .width(1.dp)
            .height(25.dp)
    ) {
        val dashHeight = 10.dp.toPx()
        val gapHeight = 5.dp.toPx()
        val lineHeight = size.height

        var startY = 0f
        while (startY < lineHeight) {
            drawLine(
                color = Color.Gray,
                start = Offset(x = 0f, y = startY),
                end = Offset(x = 0f, y = startY + dashHeight),
                strokeWidth = 1f
            )
            startY += dashHeight + gapHeight
        }
    }
}
