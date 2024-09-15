package llc.bokadev.bokabayseatrafficapp.core.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import llc.bokadev.bokabayseatrafficapp.ui.theme.BokaBaySeaTrafficAppTheme
import timber.log.Timber

object CustomModifiers {
    val snackBarHost: @Composable SnackbarHostState.() -> Unit = {
        SnackbarHost(hostState = this) { snackBarData ->
            Snackbar(
                snackbarData = snackBarData,
                containerColor = BokaBaySeaTrafficAppTheme.colors.lightBlue,
                contentColor = BokaBaySeaTrafficAppTheme.colors.white,
                actionColor = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                shape = RoundedCornerShape(4.dp)
            )
        }
    }

}