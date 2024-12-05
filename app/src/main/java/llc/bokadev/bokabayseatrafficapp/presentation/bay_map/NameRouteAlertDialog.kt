package llc.bokadev.bokabayseatrafficapp.presentation.bay_map


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import llc.bokadev.bokabayseatrafficapp.ui.theme.BokaBaySeaTrafficAppTheme

@Composable
fun NameRouteAlertDialog(
    routeName: String,
    shouldShowNameError: Boolean,
    onNameChange: (String) -> Unit,
    onEmptyNameSaveClick: () -> Unit,
    onConfirm: () -> Unit, // Pass the input text back to the caller
    onCancel: () -> Unit
) {


    AlertDialog(
        containerColor = BokaBaySeaTrafficAppTheme.colors.defaultGray,
        onDismissRequest = { onCancel() },
        title = {
            Text(
                text = "Save your route",
                color = BokaBaySeaTrafficAppTheme.colors.white,
                style = BokaBaySeaTrafficAppTheme.typography.ralewayBold16
            )
        },
        text = {
            Column {
                OutlinedTextField(

                    value = routeName,
                    onValueChange = { onNameChange(it) },
                    placeholder = {
                        Text(
                            text = "Your route name",
                            color = BokaBaySeaTrafficAppTheme.colors.darkBlue.copy(.3f),
                            style = BokaBaySeaTrafficAppTheme.typography.ralewayRegular14
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = BokaBaySeaTrafficAppTheme.colors.darkBlue.copy(.5f),
                        disabledPlaceholderColor = BokaBaySeaTrafficAppTheme.colors.white.copy(
                            .3f
                        ),
                        unfocusedTextColor = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                        focusedPlaceholderColor = BokaBaySeaTrafficAppTheme.colors.darkBlue.copy(.3f),
                        cursorColor = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                        focusedIndicatorColor = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                        disabledContainerColor = BokaBaySeaTrafficAppTheme.colors.white,
                        focusedContainerColor = BokaBaySeaTrafficAppTheme.colors.white,
                        unfocusedContainerColor = BokaBaySeaTrafficAppTheme.colors.white
                    )
                )

                if (shouldShowNameError)
                    Text(
                        text = "Can't save the route before naming it",
                        color = BokaBaySeaTrafficAppTheme.colors.textRed,
                        style = BokaBaySeaTrafficAppTheme.typography.ralewayRegular14
                    )

            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (routeName.isEmpty() || routeName.isBlank()) {
                        onEmptyNameSaveClick()
                    } else {
                        onConfirm()
                    }
                }
            ) {
                Text(
                    text = "Save",
                    color = BokaBaySeaTrafficAppTheme.colors.confirmGreen,
                    style = BokaBaySeaTrafficAppTheme.typography.nunitoRegular14
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text(
                    text = "Cancel",
                    color = BokaBaySeaTrafficAppTheme.colors.white,
                    style = BokaBaySeaTrafficAppTheme.typography.nunitoLight14
                )
            }
        }
    )
}
