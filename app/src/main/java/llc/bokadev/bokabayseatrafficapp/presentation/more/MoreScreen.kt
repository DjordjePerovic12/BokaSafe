package llc.bokadev.bokabayseatrafficapp.presentation.more

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import llc.bokadev.bokabayseatrafficapp.R
import llc.bokadev.bokabayseatrafficapp.core.utils.observeWithLifecycle
import llc.bokadev.bokabayseatrafficapp.domain.model.MoreOptions
import llc.bokadev.bokabayseatrafficapp.ui.theme.BokaBaySeaTrafficAppTheme

@Composable
fun MoreScreen(
    modifier: Modifier = Modifier,
    viewModel: MoreViewModel,
    showSnackBar: (String) -> Unit,
    launchWebBrowserIntent: (intent: Intent) -> Unit
) {

    viewModel.launchIntentChannel.observeWithLifecycle { intent ->
        launchWebBrowserIntent(intent)
    }

    val state = viewModel.viewStateFlow.collectAsState().value


    val options = listOf(
        MoreOptions(1, "Preferred speed unit"),
        MoreOptions(2, "My routes"),
        MoreOptions(3, "External resources"),
        MoreOptions(4, "Safety hub"),
        MoreOptions(5, "Notices for seafarers"),
    )

    if (state.shouldShowAlertDialog)
        ChangeSpeedUnitDialog(preferredSpeedUnit = state.preferredSpeedUnit, onConfirm = {
            viewModel.onEvent(MoreEvent.OnConfirmAlertDialogClick)
        }) {
            viewModel.onEvent(MoreEvent.OnCancelAlertDialogClick)
        }

    BackHandler { viewModel.onEvent(MoreEvent.OnBackClick) }

    Scaffold(
        topBar = {
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
                            viewModel.onEvent(MoreEvent.OnBackClick)
                        }
                        .size(18.dp)
                )
                Text(
                    text = "More Options",
                    color = BokaBaySeaTrafficAppTheme.colors.white,
                    style = BokaBaySeaTrafficAppTheme.typography.ralewayBold20
                )
            }
        }
    ) { innerPadidng ->
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .background(BokaBaySeaTrafficAppTheme.colors.defaultGray.copy(.9f))
                .padding(innerPadidng)
        ) {
            items(options) {
                if (it.id == 1) {
                    Spacer(modifier = Modifier.height(20.dp))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp)
                        .clickable {
                            viewModel.onEvent(MoreEvent.OnOptionClick(it.id))
                        }
                ) {
                    Text(
                        text = it.name,
                        color = BokaBaySeaTrafficAppTheme.colors.white,
                        style = BokaBaySeaTrafficAppTheme.typography.nunitoRegular16
                    )

                    if (it.id != 1)
                        Icon(painter = painterResource(R.drawable.tabler_icon_chevron_right),
                            tint = BokaBaySeaTrafficAppTheme.colors.white,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                viewModel.onEvent(MoreEvent.OnOptionClick(it.id))
                            }) else Text(
                        text = if (state.preferredSpeedUnit == "knots") "Change preferred unit to km/h" else "Change preferred unit to knots",
                        color = BokaBaySeaTrafficAppTheme.colors.white,
                        style = BokaBaySeaTrafficAppTheme.typography.nunitoRegular12,
                        textDecoration = TextDecoration.Underline,
                    )
                }

                Spacer(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(BokaBaySeaTrafficAppTheme.colors.white)
                )
            }
        }
    }

}