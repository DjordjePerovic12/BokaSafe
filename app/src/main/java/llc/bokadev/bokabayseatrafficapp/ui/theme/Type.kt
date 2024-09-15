package llc.bokadev.bokabayseatrafficapp.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp


@Immutable
data class BokaBaySeaTrafficTypography(
    val neueMontrealRegular14: TextStyle,
    val neueMontrealRegular18: TextStyle,
    val neueMontrealRegular20: TextStyle,
    val neueMontrealBold14: TextStyle,
    val neueMontrealBold18: TextStyle,
    val neueMontrealBold20: TextStyle,
    val neueMontrealBold26: TextStyle,

    )

val LocalBokaBaySeaTrafficTypography = staticCompositionLocalOf {
    BokaBaySeaTrafficTypography(
        neueMontrealRegular14 = TextStyle.Default,
        neueMontrealRegular18 = TextStyle.Default,
        neueMontrealRegular20 = TextStyle.Default,
        neueMontrealBold14 = TextStyle.Default,
        neueMontrealBold18 = TextStyle.Default,
        neueMontrealBold20 = TextStyle.Default,
        neueMontrealBold26 = TextStyle.Default,

        )
}

val bokaBaySeaTafficTypography = BokaBaySeaTrafficTypography(
    neueMontrealRegular14 = TextStyle(
        fontFamily = neueMontrealRegular, fontSize = 14.sp
    ),
    neueMontrealRegular18 = TextStyle(
        fontFamily = neueMontrealRegular, fontSize = 18.sp
    ),
    neueMontrealRegular20 = TextStyle(
        fontFamily = neueMontrealRegular, fontSize = 20.sp
    ),
    neueMontrealBold14 = TextStyle(
        fontFamily = neueMontrealBold, fontSize = 14.sp
    ),
    neueMontrealBold18 = TextStyle(
        fontFamily = neueMontrealBold, fontSize = 18.sp
    ),
    neueMontrealBold20 = TextStyle(
        fontFamily = neueMontrealBold, fontSize = 20.sp
    ),
    neueMontrealBold26 = TextStyle(
        fontFamily = neueMontrealBold, fontSize = 26.sp
    ),

    )
