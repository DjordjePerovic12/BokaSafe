package llc.bokadev.bokasafe.presentation.more.external_resources

import android.content.Intent
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import llc.bokadev.bokasafe.R
import llc.bokadev.bokasafe.core.navigation.Screen
import llc.bokadev.bokasafe.core.utils.observeWithLifecycle
import llc.bokadev.bokasafe.ui.theme.BokaBaySeaTrafficAppTheme

@Composable
fun ExternalResourcesScreen(
    modifier: Modifier = Modifier,
    viewModel: ExternalResourcesViewModel,
    launchIntent: (intent: Intent) -> Unit,
    showSnackBar: (String) -> Unit
) {

    viewModel.launchIntentChannel.observeWithLifecycle { intent ->
        launchIntent(intent)
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
//                        viewModel.onEvent(SafetyHubEvent.OnBackClick)
                    }
                    .size(18.dp)
            )
            Text(
                text = "External Resources",
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
            items(state.documents) { document ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(ExtenralResourcesEvent.OnDocumentClick(document.url))
                        }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.pdf_file),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .size(30.dp),
                        tint = BokaBaySeaTrafficAppTheme.colors.white
                    )
                    Text(
                        text = document.name,
                        color = BokaBaySeaTrafficAppTheme.colors.white,
                        style = BokaBaySeaTrafficAppTheme.typography.nunitoRegular14,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 5.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(
                        painter = painterResource(R.drawable.tabler_icon_chevron_right),
                        contentDescription = null,
                        modifier = Modifier,
                        tint = BokaBaySeaTrafficAppTheme.colors.white

                    )

                }
                Spacer(
                    modifier = Modifier
                        .padding(vertical = 18.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(BokaBaySeaTrafficAppTheme.colors.white)
                )
            }

        }
    }
}