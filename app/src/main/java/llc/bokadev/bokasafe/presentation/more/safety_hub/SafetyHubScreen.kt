package llc.bokadev.bokasafe.presentation.more.safety_hub

import android.content.Intent
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import llc.bokadev.bokasafe.R
import llc.bokadev.bokasafe.core.utils.noRippleClickable
import llc.bokadev.bokasafe.core.utils.observeWithLifecycle
import llc.bokadev.bokasafe.core.utils.toLatitude
import llc.bokadev.bokasafe.core.utils.toLongitude
import llc.bokadev.bokasafe.ui.theme.BokaBaySeaTrafficAppTheme

@Composable
fun SafetyHubScreen(
    modifier: Modifier = Modifier,
    viewModel: SafetyHubViewModel,
    showSnackBar: (String) -> Unit,
    launchWebBrowserIntent: (intent: Intent) -> Unit
) {

    viewModel.launchIntentChannel.observeWithLifecycle { intent ->
        launchWebBrowserIntent(intent)
    }

    val state = viewModel.viewStateFlow.collectAsState().value

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
                        viewModel.onEvent(SafetyHubEvent.OnBackClick)
                    }
                    .size(18.dp)
            )
            Text(
                text = "Safety Hub",
                color = BokaBaySeaTrafficAppTheme.colors.white,
                style = BokaBaySeaTrafficAppTheme.typography.ralewayBold20
            )
        }
    }) { innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .background(BokaBaySeaTrafficAppTheme.colors.defaultGray)
                .padding(innerPadding)
                .padding(
                    horizontal = 20.dp,
                    vertical = 20.dp
                )
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(6.dp))
                        .background(BokaBaySeaTrafficAppTheme.colors.primaryRed.copy(.8f))
                        .border(
                            width = 1.dp,
                            color = BokaBaySeaTrafficAppTheme.colors.white.copy(.4f),
                            shape = RoundedCornerShape(6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 25.dp, vertical = 20.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.location),
                            contentDescription = null,
                            tint = BokaBaySeaTrafficAppTheme.colors.white.copy(.8f),
                            modifier = Modifier.size(40.dp),
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Current location",
                            color = BokaBaySeaTrafficAppTheme.colors.white.copy(.8f),
                            style = BokaBaySeaTrafficAppTheme.typography.nunitoBold20
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(30.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Latitude",
                                    color = BokaBaySeaTrafficAppTheme.colors.white.copy(.8f),
                                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14
                                )

                                if (state.userLocation == null) CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = BokaBaySeaTrafficAppTheme.colors.white
                                ) else
                                    Text(
                                        text = "${state.userLocation.latitude.toLatitude()} N",
                                        color = BokaBaySeaTrafficAppTheme.colors.white.copy(.8f),
                                        style = BokaBaySeaTrafficAppTheme.typography.nunitoBold18
                                    )
                            }

                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Longitude",
                                    color = BokaBaySeaTrafficAppTheme.colors.white.copy(.8f),
                                    style = BokaBaySeaTrafficAppTheme.typography.nunitoLight14
                                )

                                if (state.userLocation == null) CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = BokaBaySeaTrafficAppTheme.colors.white
                                ) else
                                Text(
                                    text = "${state.userLocation.longitude.toLongitude()} E",
                                    color = BokaBaySeaTrafficAppTheme.colors.white.copy(.8f),
                                    style = BokaBaySeaTrafficAppTheme.typography.nunitoBold18
                                )
                            }
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(20.dp)) }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(6.dp))
                        .border(
                            width = 1.dp,
                            color = BokaBaySeaTrafficAppTheme.colors.white.copy(.4f),
                            shape = RoundedCornerShape(6.dp)
                        )
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(BokaBaySeaTrafficAppTheme.colors.primaryRed.copy(.8f))
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "Nearest SAR contact",
                            color = BokaBaySeaTrafficAppTheme.colors.white.copy(.8f),
                            style = BokaBaySeaTrafficAppTheme.typography.nunitoBold14
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Text(
                                text = state.nearestSARContact,
                                color = BokaBaySeaTrafficAppTheme.colors.white.copy(.8f),
                                style = BokaBaySeaTrafficAppTheme.typography.nunitoBold18
                            )

                            Box(
                                modifier = Modifier
                                    .padding(top = 25.dp, start = 25.dp)
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(BokaBaySeaTrafficAppTheme.colors.white)
                                    .border(width = 1.dp, shape = CircleShape, color = BokaBaySeaTrafficAppTheme.colors.black)
                                    .noRippleClickable {
                                        viewModel.onEvent(SafetyHubEvent.OnCallNowClick)
                                    }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_phone),
                                    contentDescription = null,
                                    modifier = Modifier.padding(15.dp),
                                    tint = BokaBaySeaTrafficAppTheme.colors.black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}