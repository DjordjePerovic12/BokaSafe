package llc.bokadev.bokabayseatrafficapp.presentation.more

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import llc.bokadev.bokabayseatrafficapp.ui.theme.BokaBaySeaTrafficAppTheme

@Composable
fun ChangeSpeedUnitDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        title = {
            Text(
                text = "CHANGE SPEED UNIT", color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold14
            )
        },
        text = {
            Text(
                text = "Are you sure that you want to change your preferred speed unit?",
                color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(
                    text = "Confirm",
                    color = BokaBaySeaTrafficAppTheme.colors.darkGreen,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold14
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text(
                    text = "Cancel", color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold14
                )
            }
        }
    )
}
