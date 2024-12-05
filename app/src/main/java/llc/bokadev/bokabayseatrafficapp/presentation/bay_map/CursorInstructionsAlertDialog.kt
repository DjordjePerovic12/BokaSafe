package llc.bokadev.bokabayseatrafficapp.presentation.bay_map

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import llc.bokadev.bokabayseatrafficapp.ui.theme.BokaBaySeaTrafficAppTheme

@Composable
fun CursorInstructionsAlertDialog(
    onConfirm: () -> Unit,
    onNeverShowThisAgain: () -> Unit
) {
    AlertDialog(
        containerColor = BokaBaySeaTrafficAppTheme.colors.defaultGray,
        onDismissRequest = { onConfirm() },
        title = {
            Text(
                text = "Cursor",
                color = BokaBaySeaTrafficAppTheme.colors.white,
                style = BokaBaySeaTrafficAppTheme.typography.ralewayBold16
            )
        },
        text = {
            Text(
                text = "To use the cursor to check the data of any point on the map you need to long click on the map, and the tap the map before moving the cursor around.",
                color = BokaBaySeaTrafficAppTheme.colors.white,
                style = BokaBaySeaTrafficAppTheme.typography.ralewayRegular14
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(
                    text = "OK",
                    color = BokaBaySeaTrafficAppTheme.colors.confirmGreen,
                    style = BokaBaySeaTrafficAppTheme.typography.nunitoRegular14
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onNeverShowThisAgain() }) {
                Text(
                    text = "Never show this again",
                    color = BokaBaySeaTrafficAppTheme.colors.white,
                    style = BokaBaySeaTrafficAppTheme.typography.nunitoLight14
                )
            }
        }
    )
}
