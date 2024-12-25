package llc.bokadev.bokasafe.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val PrimaryRed = Color(0xFF78211E)
val MapRed = Color(0xFFf48282)
val TextRed = Color(0Xffff7070)
val ConfirmGreen = Color(0xFFA8E6CF)
val CancelPurple = Color(0xFFE1BEE7)
val SecondaryRed = Color(0xFFD05353)
val Yellow = Color(0xFFffd65c)
val Black = Color(0xFF000000)
val DefaultGray = Color(0XFF6f6f72)
val WoodyBrown = Color(0XFF483737)
val DarkBlue = Color(0xFF132958)
val LightBlue = Color(0xFFB9D9EB)
val DarkBlueHeader = Color(0xFF222B45)
val Red = Color(0xFFF16364)
val White = Color(0xFFFFFFFF)
val Gray = Color(0xFFE6E6E6)
val GrayButton = Color(0xFFDEDEDE)
val GrayBorder = Color(0xFFC3C3C3)
val DarkGreen = Color(0xFF103713)
val Green = Color(0xFF338e56)
val LightGreen = Color(0xFFADDFB3)
val LightGray = Color(0xFFEFEFEF)
val SpacerGray = Color(0xB7B7B7B7)
val PickLocationBarGray = Color(0xFFF6F6F4)
val OrderABuggyPickLocationBarGray = Color(0xFF1E1E1E)
val LightBlueLowAlpha = Color(0x6FB9D9EB)
val CountriesSearchFieldColor = Color(0xFFE9E9E9)


@Immutable
data class BokaBaySeaTrafficAppColors(
    val primaryRed: Color,
    val mapRed: Color,
    val textRed: Color,
    val confirmGreen: Color,
    val green: Color,
    val cancelPurple: Color,
    val secondaryRed: Color,
    val yellow: Color,
    val black: Color,
    val defaultGray: Color,
    val woodyBrown: Color,
    val darkBlue: Color,
    val lightBlue: Color,
    val darkBlueHeader: Color,
    val red: Color,
    val white: Color,
    val gray: Color,
    val grayButton: Color,
    val grayBorder: Color,
    val darkGreen: Color,
    val lightGreen: Color,
    val lightGray: Color,
    val pickLocationBarGray: Color,
    val spacerGray: Color,
    val lightBlueLowAlpha: Color,
    val countriesSearchFieldColor: Color
)

val LocalBokaBaySeaTrafficAppColors = staticCompositionLocalOf {
    BokaBaySeaTrafficAppColors(
        primaryRed = Color.Unspecified,
        mapRed = Color.Unspecified,
        textRed = Color.Unspecified,
        confirmGreen = Color.Unspecified,
        cancelPurple = Color.Unspecified,
        secondaryRed = Color.Unspecified,
        yellow = Color.Unspecified,
        black = Color.Unspecified,
        defaultGray = Color.Unspecified,
        woodyBrown = Color.Unspecified,
        darkBlue = Color.Unspecified,
        lightBlue = Color.Unspecified,
        darkBlueHeader = Color.Unspecified,
        red = Color.Unspecified,
        white = Color.Unspecified,
        gray = Color.Unspecified,
        grayButton = Color.Unspecified,
        grayBorder = Color.Unspecified,
        darkGreen = Color.Unspecified,
        green = Color.Unspecified,
        lightGreen = Color.Unspecified,
        lightGray = Color.Unspecified,
        pickLocationBarGray = Color.Unspecified,
        spacerGray = Color.Unspecified,
        lightBlueLowAlpha = Color.Unspecified,
        countriesSearchFieldColor = Color.Unspecified
    )
}

val bokaBaySeaTrafficAppColors = BokaBaySeaTrafficAppColors(
    primaryRed = PrimaryRed,
    mapRed = MapRed,
    textRed = TextRed,
    confirmGreen = ConfirmGreen,
    cancelPurple = CancelPurple,
    secondaryRed = SecondaryRed,
    yellow = Yellow,
    black = Black,
    defaultGray = DefaultGray,
    woodyBrown = WoodyBrown,
    darkBlue = DarkBlue,
    lightBlue = LightBlue,
    darkBlueHeader = DarkBlueHeader,
    red = Red,
    white = White,
    gray = Gray,
    grayButton = GrayButton,
    grayBorder = GrayBorder,
    darkGreen = DarkGreen,
    green = Green,
    lightGreen = LightGreen,
    lightGray = LightGray,
    pickLocationBarGray = PickLocationBarGray,
    spacerGray = SpacerGray,
    lightBlueLowAlpha = LightBlueLowAlpha,
    countriesSearchFieldColor = CountriesSearchFieldColor
)
