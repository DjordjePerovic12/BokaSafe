package llc.bokadev.bokasafe.presentation.shared_components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import llc.bokadev.bokasafe.ui.theme.BokaBaySeaTrafficAppTheme

@Composable
fun SharedAlertDialog(
    title: String,
    text: String,
    confirmButtonText: String = "Confirm",
    cancelButtonText: String = "Cancel",
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        containerColor = BokaBaySeaTrafficAppTheme.colors.defaultGray,
        onDismissRequest = { onCancel() },
        title = {
            Text(
                text = title,
                color = BokaBaySeaTrafficAppTheme.colors.white,
                style = BokaBaySeaTrafficAppTheme.typography.ralewayBold16
            )
        },
        text = {
            Text(
                text = text,
                color = BokaBaySeaTrafficAppTheme.colors.white,
                style = BokaBaySeaTrafficAppTheme.typography.ralewayRegular14
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(
                    text = confirmButtonText,
                    color = BokaBaySeaTrafficAppTheme.colors.confirmGreen,
                    style = BokaBaySeaTrafficAppTheme.typography.nunitoRegular14
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text(
                    text = cancelButtonText,
                    color = BokaBaySeaTrafficAppTheme.colors.white,
                    style = BokaBaySeaTrafficAppTheme.typography.nunitoLight14
                )
            }
        }
    )
}
