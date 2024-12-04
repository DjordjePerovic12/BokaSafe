package llc.bokadev.bokabayseatrafficapp.core.navigation

import llc.bokadev.bokabayseatrafficapp.core.navigation.Routes.BAY_MAP_SCREEN
import llc.bokadev.bokabayseatrafficapp.core.navigation.Routes.CUSTOM_ROUTE_DETAILS_SCREEN
import llc.bokadev.bokabayseatrafficapp.core.navigation.Routes.MORE_SCREEN
import llc.bokadev.bokabayseatrafficapp.core.navigation.Routes.MY_ROUTES_SCREEN
import llc.bokadev.bokabayseatrafficapp.core.navigation.Routes.SAFETY_HUB_SCREEN
import llc.bokadev.bokabayseatrafficapp.core.navigation.destinations.ROUTE_ID_ARGUMENT_KEY


sealed class Screen(val route: String) {
    data object BayMapScreen : Screen(BAY_MAP_SCREEN)
    data object MoreScreen : Screen(MORE_SCREEN)
    data object SafetyHubScreen : Screen(SAFETY_HUB_SCREEN)
    data object MyRoutesScreen : Screen(MY_ROUTES_SCREEN)
    data object CustomRouteDetailsScreen : Screen(CUSTOM_ROUTE_DETAILS_SCREEN) {
        fun passRouteId(routeId: Int) = this.route.replace(
            oldValue = "{$ROUTE_ID_ARGUMENT_KEY}",
            newValue = routeId.toString()
        )
    }
}