package llc.bokadev.bokabayseatrafficapp.presentation.bay_map


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import llc.bokadev.bokabayseatrafficapp.ui.theme.BokaBaySeaTrafficAppTheme

@Composable
fun NameRouteAlertDialog(
    routeName: String,
    onNameChange: (String) -> Unit,
    onConfirm: () -> Unit, // Pass the input text back to the caller
    onCancel: () -> Unit
) {


    AlertDialog(
        onDismissRequest = { onCancel() },
        title = {
            Text(
                text = "Save your route",
                color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold14
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
                            style = BokaBaySeaTrafficAppTheme.typography.neueMontrealRegular14
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                        disabledPlaceholderColor = BokaBaySeaTrafficAppTheme.colors.darkBlue.copy(
                            .3f
                        ),
                        unfocusedTextColor = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                        focusedPlaceholderColor = BokaBaySeaTrafficAppTheme.colors.darkBlue.copy(.3f),
                        cursorColor = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                        focusedIndicatorColor = BokaBaySeaTrafficAppTheme.colors.darkBlue

                    )
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(
                    text = "Save",
                    color = BokaBaySeaTrafficAppTheme.colors.darkGreen,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold14
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text(
                    text = "Cancel",
                    color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
                    style = BokaBaySeaTrafficAppTheme.typography.neueMontrealBold14
                )
            }
        }
    )
}
