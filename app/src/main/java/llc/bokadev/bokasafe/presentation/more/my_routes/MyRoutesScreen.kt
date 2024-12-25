package llc.bokadev.bokasafe.presentation.more.my_routes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import llc.bokadev.bokasafe.R
import llc.bokadev.bokasafe.core.utils.observeWithLifecycle
import llc.bokadev.bokasafe.presentation.bay_map.toNauticalMiles
import llc.bokadev.bokasafe.presentation.shared_components.SharedAlertDialog
import llc.bokadev.bokasafe.ui.theme.BokaBaySeaTrafficAppTheme

@Composable
fun MyRoutesScreen(
    modifier: Modifier = Modifier,
    viewModel: MyRoutesViewModel,
    activateCustomRoute: () -> Unit,
    showSnackBar: (String) -> Unit
) {
    val state = viewModel.viewStateFlow.collectAsState().value

    viewModel.activateCustomRouteChannel.observeWithLifecycle { shouldActivate ->
        if(shouldActivate) activateCustomRoute()
    }



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
                .background(BokaBaySeaTrafficAppTheme.colors.primaryRed)
                .padding(25.dp)
                .padding(top = 30.dp)

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
                style = BokaBaySeaTrafficAppTheme.typography.ralewayBold20
            )
        }
    }) { innerPadding ->
        LazyColumn(
            verticalArrangement = if(state.myRoutes.isEmpty()) Arrangement.Center else Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .background(BokaBaySeaTrafficAppTheme.colors.defaultGray.copy(.9f))
                .padding(innerPadding)
                .padding(25.dp)
        ) {
            if (state.myRoutes.isNotEmpty()) {
                items(state.myRoutes) { route ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(8.dp))
                            .background(BokaBaySeaTrafficAppTheme.colors.primaryRed.copy(.8f))
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(8.dp),
                                color = BokaBaySeaTrafficAppTheme.colors.white.copy(.4f)
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
                                    color = BokaBaySeaTrafficAppTheme.colors.white,
                                    style = BokaBaySeaTrafficAppTheme.typography.nunitoBold18
                                )

                                Icon(
                                    painter = painterResource(R.drawable.delete),
                                    contentDescription = null,
                                    tint = BokaBaySeaTrafficAppTheme.colors.secondaryRed,
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clickable {
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
                                    color = BokaBaySeaTrafficAppTheme.colors.white, // Replace with your desired color
                                    textAlign = TextAlign.Start,
                                    style = BokaBaySeaTrafficAppTheme.typography.nunitoRegular14
                                )

                                Text(
                                    text = "${route.pointS.size} points",
                                    color = BokaBaySeaTrafficAppTheme.colors.white,
                                    style = BokaBaySeaTrafficAppTheme.typography.nunitoBold14
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
                                        color = BokaBaySeaTrafficAppTheme.colors.white,
                                        style = BokaBaySeaTrafficAppTheme.typography.nunitoRegular14
                                    )

                                    Text(
                                        text = "D: ${route.distances[index].toNauticalMiles()} NM, C: ${route.azimuths[index].toInt()}°",
                                        color = BokaBaySeaTrafficAppTheme.colors.white,
                                        style = BokaBaySeaTrafficAppTheme.typography.nunitoRegular14
                                    )

                                }
                            }

                            if (route.pointS.size > 3) {
                                Text(
                                    text = "See more",
                                    color = BokaBaySeaTrafficAppTheme.colors.white,
                                    style = BokaBaySeaTrafficAppTheme.typography.nunitoRegular14,
                                    textDecoration = TextDecoration.Underline,
                                    textAlign = TextAlign.Center
                                )
                            }

                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            } else {
                item {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(BokaBaySeaTrafficAppTheme.colors.white.copy(.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_route),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = BokaBaySeaTrafficAppTheme.colors.primaryRed
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "No saved routes",
                            color = BokaBaySeaTrafficAppTheme.colors.primaryRed,
                            style = BokaBaySeaTrafficAppTheme.typography.ralewayBold20,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "You haven't saved any routes yet.",
                            color = BokaBaySeaTrafficAppTheme.colors.primaryRed,
                            style = BokaBaySeaTrafficAppTheme.typography.nunitoRegular18,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                viewModel.onEvent(MyRoutesEvent.OnAddNewRouteClick)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            colors = ButtonColors(
                                containerColor = BokaBaySeaTrafficAppTheme.colors.primaryRed,
                                disabledContainerColor = BokaBaySeaTrafficAppTheme.colors.primaryRed,
                                contentColor = BokaBaySeaTrafficAppTheme.colors.white,
                                disabledContentColor = BokaBaySeaTrafficAppTheme.colors.white
                            )
                        ) {
                            Text(
                                text = "+ CREATE NEW ROUTE",
                                style = BokaBaySeaTrafficAppTheme.typography.ralewayBold20,
                                color = BokaBaySeaTrafficAppTheme.colors.white
                            )
                        }
                    }
                }
            }
        }
    }
}