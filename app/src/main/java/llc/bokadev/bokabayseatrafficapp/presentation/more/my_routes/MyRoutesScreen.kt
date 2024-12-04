package llc.bokadev.bokabayseatrafficapp.presentation.more.my_routes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import llc.bokadev.bokabayseatrafficapp.R
import llc.bokadev.bokabayseatrafficapp.presentation.bay_map.toNauticalMiles
import llc.bokadev.bokabayseatrafficapp.presentation.shared_components.SharedAlertDialog
import llc.bokadev.bokabayseatrafficapp.ui.theme.BokaBaySeaTrafficAppTheme

@Composable
fun MyRoutesScreen(
    modifier: Modifier = Modifier,
    viewModel: MyRoutesViewModel,
    showSnackBar: (String) -> Unit
) {
    val state = viewModel.viewStateFlow.collectAsState().value

    if (state.shouldShowDeleteAlertDialog)
        SharedAlertDialog(
            title = "DELETE ROUTE",
            text = "Are you sure you want to delete \"${state.selectedRoute?.name}\" from your routes?",
            onConfirm = {
                viewModel.onEvent(MyRoutesEvent.OnConfirmDeleteClick)
            },
            onCancel = {
                viewModel.onEvent(MyRoutesEvent.ToggleDeleteRouteAlertDialog(null))
            }
        )

    BackHandler { viewModel.onEvent(MyRoutesEvent.OnBackClick) }
    Scaffold(topBar = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(BokaBaySeaTrafficAppTheme.colors.darkBlue)
                .padding(25.dp)

        ) {
            Icon(painter = painterResource(R.drawable.arrow),
                tint = BokaBaySeaTrafficAppTheme.colors.white,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        viewModel.onEvent(MyRoutesEvent.OnBackClick)
                    }
                    .size(18.dp)
            )
            Text(
                text = "My routes",
                color = BokaBaySeaTrafficAppTheme.colors.white,
                style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold20
            )
        }
    }) { innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(innerPadding)
                .padding(25.dp)
        ) {
            items(state.myRoutes) { route ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(8.dp),
                            color = BokaBaySeaTrafficAppTheme.colors.darkBlue.copy(.4f)
                        )
                        .clickable {
                            viewModel.onEvent(MyRoutesEvent.OnRouteClick(route.id))
                        }
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = route.name,
                                color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                                style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold18
                            )

                            Icon(
                                painter = painterResource(R.drawable.delete),
                                contentDescription = null,
                                tint = BokaBaySeaTrafficAppTheme.colors.red,
                                modifier = Modifier.size(25.dp).clickable {
                                    viewModel.onEvent(
                                        MyRoutesEvent.ToggleDeleteRouteAlertDialog(
                                            route
                                        )
                                    )
                                }
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    append("Total distance: ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("${route.totalDistance.toNauticalMiles()} NM")
                                    }
                                },
                                color = BokaBaySeaTrafficAppTheme.colors.darkBlue, // Replace with your desired color
                                textAlign = TextAlign.Start,
                                style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14
                            )

                            Text(
                                text = "${route.pointS.size} points",
                                color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                                style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14
                            )
                        }

                        route.distances.take(3).forEachIndexed { index, distance ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Point ${index + 1} to point ${index + 2}",
                                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14
                                )

                                Text(
                                    text = "D: ${route.distances[index].toNauticalMiles()} NM, C: ${route.azimuths[index].toInt()}°",
                                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14
                                )

                            }
                        }

                        if (route.pointS.size > 3) {
                            Text(
                                text = "See more",
                                color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                                style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14,
                                textDecoration = TextDecoration.Underline,
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}