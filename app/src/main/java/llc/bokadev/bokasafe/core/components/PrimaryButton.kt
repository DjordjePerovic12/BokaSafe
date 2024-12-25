package llc.bokadev.bokasafe.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import llc.bokadev.bokasafe.ui.theme.BokaBaySeaTrafficAppTheme


@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
    borderWidth: Dp = 1.dp,
    text: String,
    shape: Shape = RectangleShape,
    color: Color,
    enabled: Boolean = true,
    borderColor: Color = BokaBaySeaTrafficAppTheme.colors.darkBlue,
    textColor: Color = BokaBaySeaTrafficAppTheme.colors.white,
    textStyle: TextStyle,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
        ),
        enabled = enabled,
        contentPadding = PaddingValues(),
        shape = shape,
        onClick = onClick,
        border = BorderStroke(borderWidth, borderColor)
    ) {
        Box(
            modifier = Modifier
                .background(color)
                .padding(padding)
                .fillMaxSize(),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center), text = text, style = textStyle,
                color = textColor
            )
        }
    }
}
