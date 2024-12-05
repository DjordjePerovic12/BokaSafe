package llc.bokadev.bokabayseatrafficapp.core.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import llc.bokadev.bokabayseatrafficapp.ui.theme.BokaBaySeaTrafficAppTheme

object CustomModifiers {
    val snackBarHost: @Composable SnackbarHostState.() -> Unit = {
        SnackbarHost(hostState = this) { snackBarData ->
            Snackbar(
                snackbarData = snackBarData,
                containerColor = BokaBaySeaTrafficAppTheme.colors.defaultGray,
                contentColor = BokaBaySeaTrafficAppTheme.colors.primaryRed,
                actionColor = BokaBaySeaTrafficAppTheme.colors.primaryRed,
                shape = RoundedCornerShape(4.dp)
            )
        }
    }

}