package llc.bokadev.bokasafe.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import llc.bokadev.bokasafe.ui.theme.BokaBaySeaTrafficAppTheme


@Composable
fun PermissionDenied() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BokaBaySeaTrafficAppTheme.colors.lightBlue)
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "We need access to locations for this app to work. \n Please go to your phone settings and turn on the location",
            style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular18,
            color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
            textAlign = TextAlign.Center
        )
    }
}