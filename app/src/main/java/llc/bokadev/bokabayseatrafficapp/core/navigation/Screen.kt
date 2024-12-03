package llc.bokadev.bokabayseatrafficapp.core.navigation

import llc.bokadev.bokabayseatrafficapp.core.navigation.Routes.BAY_MAP_SCREEN
import llc.bokadev.bokabayseatrafficapp.core.navigation.Routes.MORE_SCREEN
import llc.bokadev.bokabayseatrafficapp.core.navigation.Routes.SAFETY_HUB_SCREEN


sealed class Screen(val route: String) {
    data object BayMapScreen : Screen(BAY_MAP_SCREEN)
    data object MoreScreen : Screen(MORE_SCREEN)
    data object SafetyHubScreen : Screen(SAFETY_HUB_SCREEN)
}