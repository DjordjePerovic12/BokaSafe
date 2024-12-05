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
    val ralewayRegular14: TextStyle,
    val ralewayRegular16: TextStyle,
    val ralewayRegular18: TextStyle,
    val ralewayRegular20: TextStyle,
    val ralewayBold14: TextStyle,
    val ralewayBold16: TextStyle,
    val ralewayBold18: TextStyle,
    val ralewayBold20: TextStyle,
    val nunitoRegular12: TextStyle,
    val nunitoRegular14: TextStyle,
    val nunitoRegular16: TextStyle,
    val nunitoRegular18: TextStyle,
    val nunitoRegular20: TextStyle,
    val nunitoBold14: TextStyle,
    val nunitoBold16: TextStyle,
    val nunitoBold18: TextStyle,
    val nunitoBold20: TextStyle,
    val nunitoLight14: TextStyle

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
        ralewayRegular14 = TextStyle.Default,
        ralewayRegular16 = TextStyle.Default,
        ralewayRegular18 = TextStyle.Default,
        ralewayRegular20 = TextStyle.Default,
        ralewayBold14 = TextStyle.Default,
        ralewayBold16 = TextStyle.Default,
        ralewayBold18 = TextStyle.Default,
        ralewayBold20 = TextStyle.Default,
        nunitoRegular12 = TextStyle.Default,
        nunitoRegular14 = TextStyle.Default,
        nunitoRegular16 = TextStyle.Default,
        nunitoRegular18 = TextStyle.Default,
        nunitoRegular20 = TextStyle.Default,
        nunitoBold14 = TextStyle.Default,
        nunitoBold16 = TextStyle.Default,
        nunitoBold18 = TextStyle.Default,
        nunitoBold20 = TextStyle.Default,
        nunitoLight14 = TextStyle.Default,


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
    ralewayRegular14 = TextStyle(
        fontFamily = ralewayRegular, fontSize = 14.sp
    ),
    ralewayRegular16 = TextStyle(
        fontFamily = ralewayRegular, fontSize = 16.sp
    ),
    ralewayRegular18 = TextStyle(
        fontFamily = ralewayRegular, fontSize = 18.sp
    ),
    ralewayRegular20 = TextStyle(
        fontFamily = ralewayRegular, fontSize = 20.sp
    ),
    ralewayBold14 = TextStyle(
        fontFamily = ralewayBold, fontSize = 14.sp
    ),
    ralewayBold16 = TextStyle(
        fontFamily = ralewayBold, fontSize = 16.sp
    ),
    ralewayBold18 = TextStyle(
        fontFamily = ralewayBold, fontSize = 18.sp
    ),
    ralewayBold20 = TextStyle(
        fontFamily = ralewayBold, fontSize = 20.sp
    ),
    nunitoRegular12 = TextStyle(
        fontFamily = nunitoRegular, fontSize = 12.sp
    ),
    nunitoRegular14 = TextStyle(
        fontFamily = nunitoRegular, fontSize = 14.sp
    ),
    nunitoRegular16 = TextStyle(
        fontFamily = nunitoRegular, fontSize = 16.sp
    ),
    nunitoRegular18 = TextStyle(
        fontFamily = nunitoRegular, fontSize = 18.sp
    ),
    nunitoRegular20 = TextStyle(
        fontFamily = nunitoRegular, fontSize = 20.sp
    ),
    nunitoBold14 = TextStyle(
        fontFamily = nunitoBold, fontSize = 14.sp
    ),
    nunitoBold16 = TextStyle(
        fontFamily = nunitoBold, fontSize = 16.sp
    ),
    nunitoBold18 = TextStyle(
        fontFamily = nunitoBold, fontSize = 18.sp
    ),
    nunitoBold20 = TextStyle(
        fontFamily = nunitoBold, fontSize = 20.sp
    ),
    nunitoLight14 = TextStyle(
        fontFamily = nunitoLight, fontSize = 14.sp
    )

    )
