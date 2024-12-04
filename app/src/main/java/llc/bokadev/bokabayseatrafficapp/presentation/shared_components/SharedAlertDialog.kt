package llc.bokadev.bokabayseatrafficapp.presentation.shared_components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import llc.bokadev.bokabayseatrafficapp.ui.theme.BokaBaySeaTrafficAppTheme

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
        onDismissRequest = { onCancel() },
        title = {
            Text(
                text = title,
                color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold14
            )
        },
        text = {
            Text(
                text = text,
                color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(
                    text = confirmButtonText,
                    color = BokaBaySeaTrafficAppTheme.colors.darkGreen,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold14
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text(
                    text = cancelButtonText,
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold14
                )
            }
        }
    )
}
