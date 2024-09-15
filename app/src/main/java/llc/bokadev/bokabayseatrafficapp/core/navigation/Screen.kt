package llc.bokadev.bokabayseatrafficapp.core.navigation

import llc.bokadev.bokabayseatrafficapp.core.navigation.Routes.BAY_MAP_SCREEN


sealed class Screen(val route: String) {
    data object BayMapScreen : Screen(BAY_MAP_SCREEN)
}