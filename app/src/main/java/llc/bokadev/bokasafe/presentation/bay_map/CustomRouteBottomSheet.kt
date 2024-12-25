package llc.bokadev.bokasafe.presentation.bay_map

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import llc.bokadev.bokasafe.R
import llc.bokadev.bokasafe.core.utils.toLatitude
import llc.bokadev.bokasafe.core.utils.toLongitude
import llc.bokadev.bokasafe.ui.theme.BokaBaySeaTrafficAppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomRouteBottomSheet(
    state: GuideState,
    onTogglePointsClick: (Int) -> Unit,
    onSaveRouteClick: () -> Unit
) {
    val points = state.customRoutePoints

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxHeight(0.4f)
            .navigationBarsPadding()
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
            .fillMaxWidth()
            .background(BokaBaySeaTrafficAppTheme.colors.defaultGray)
            .padding(horizontal = 20.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(15.dp))
        }

        stickyHeader {
            Text(
                text = "Create a custom route and calculate the distance between custom points on the sea.",
                color = BokaBaySeaTrafficAppTheme.colors.white,
                style = BokaBaySeaTrafficAppTheme.typography.ralewayBold14,
                modifier = Modifier
                    .background(BokaBaySeaTrafficAppTheme.colors.defaultGray)
                    .padding(vertical = 10.dp),


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
                                .background(BokaBaySeaTrafficAppTheme.colors.secondaryRed) // Light blue background
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = "Point ${index + 1}",
                                color = BokaBaySeaTrafficAppTheme.colors.white,
                                style = BokaBaySeaTrafficAppTheme.typography.nunitoLight14,
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.width(15.dp))

                        Text(
                            text = "Latitude: ${point.latitude.toLatitude()} N\nLongitude: ${point.longitude.toLongitude()} E",
                            color = BokaBaySeaTrafficAppTheme.colors.white,
                            style = BokaBaySeaTrafficAppTheme.typography.nunitoLight14
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp)
                            .padding(top = 15.dp)
                    ) {
                        if (index < points.size - 1) {
                            Icon(
                                painter = painterResource(R.drawable.up_and_down_arrow),
                                contentDescription = null,
                                tint = BokaBaySeaTrafficAppTheme.colors.secondaryRed,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        onTogglePointsClick(index)
                                    })
                        }

                        if (index < state.customRouteConsecutivePointsDistance.size) {
                            Text(
                                text = "D: ${state.customRouteConsecutivePointsDistance[index].toNauticalMiles()} NM, " +
                                        "C: ${state.customRouteConsecutivePointsAzimuth[index].toInt()}°",
                                color = BokaBaySeaTrafficAppTheme.colors.white,
                                style = BokaBaySeaTrafficAppTheme.typography.nunitoLight14,
                                modifier = Modifier.padding(start = 40.dp)
                            )
                        }
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
            item { Text(text = "Total distance: ${distance.toNauticalMiles()} NM",
                color = BokaBaySeaTrafficAppTheme.colors.white,
                style = BokaBaySeaTrafficAppTheme.typography.nunitoBold14)}
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }

        if (state.customRoutePoints.size > 1)
            item {
                Button(
                    onClick = { onSaveRouteClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = ButtonColors(
                        containerColor = BokaBaySeaTrafficAppTheme.colors.secondaryRed,
                        disabledContainerColor = BokaBaySeaTrafficAppTheme.colors.secondaryRed,
                        contentColor = BokaBaySeaTrafficAppTheme.colors.white,
                        disabledContentColor = BokaBaySeaTrafficAppTheme.colors.white
                    )
                ) {
                    Text(
                        text = "SAVE ROUTE",
                        style = BokaBaySeaTrafficAppTheme.typography.ralewayBold20,
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
