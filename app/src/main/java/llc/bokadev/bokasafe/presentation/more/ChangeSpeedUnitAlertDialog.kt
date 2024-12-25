package llc.bokadev.bokasafe.presentation.more

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import llc.bokadev.bokasafe.ui.theme.BokaBaySeaTrafficAppTheme

@Composable
fun ChangeSpeedUnitDialog(
    preferredSpeedUnit: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    val unitAfterChange = if (preferredSpeedUnit == "knots") "km/h" else "knots"
    AlertDialog(
        containerColor = BokaBaySeaTrafficAppTheme.colors.defaultGray,
        onDismissRequest = { onCancel() },
        title = {
            Text(
                text = "CHANGE SPEED UNIT", color = BokaBaySeaTrafficAppTheme.colors.white,
                style = BokaBaySeaTrafficAppTheme.typography.ralewayBold16
            )
        },
        text = {
            Text(
                text = "Are you sure that you want to change your preferred speed unit from $preferredSpeedUnit to $unitAfterChange?",
                color = BokaBaySeaTrafficAppTheme.colors.white,
                style = BokaBaySeaTrafficAppTheme.typography.ralewayRegular14
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(
                    text = "Confirm",
                    color = BokaBaySeaTrafficAppTheme.colors.confirmGreen,
                    style = BokaBaySeaTrafficAppTheme.typography.nunitoRegular14
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text(
                    text = "Cancel", color = BokaBaySeaTrafficAppTheme.colors.white,
                    style = BokaBaySeaTrafficAppTheme.typography.nunitoLight14
                )
            }
        }
    )
}
