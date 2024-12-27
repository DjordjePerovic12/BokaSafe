package llc.bokadev.bokasafe.core.navigation

import llc.bokadev.bokasafe.core.navigation.Routes.BAY_MAP_SCREEN
import llc.bokadev.bokasafe.core.navigation.Routes.CUSTOM_ROUTE_DETAILS_SCREEN
import llc.bokadev.bokasafe.core.navigation.Routes.EXTERNAL_RESOURCES_SCREEN
import llc.bokadev.bokasafe.core.navigation.Routes.MORE_SCREEN
import llc.bokadev.bokasafe.core.navigation.Routes.MY_ROUTES_SCREEN
import llc.bokadev.bokasafe.core.navigation.Routes.SAFETY_HUB_SCREEN
import llc.bokadev.bokasafe.core.navigation.destinations.ROUTE_ID_ARGUMENT_KEY
import llc.bokadev.bokasafe.core.navigation.destinations.SHOULD_ENABLE_CUSTOM_ROUTE_ARGUMENT_KEY


sealed class Screen(val route: String) {
    data object BayMapScreen : Screen(BAY_MAP_SCREEN) {
        fun passShouldActivateCustomRoute(shouldActivate: Boolean) = this.route.replace(
            oldValue = "{$SHOULD_ENABLE_CUSTOM_ROUTE_ARGUMENT_KEY}",
            newValue = shouldActivate.toString()
        )
    }
    data object MoreScreen : Screen(MORE_SCREEN)
    data object SafetyHubScreen : Screen(SAFETY_HUB_SCREEN)
    data object MyRoutesScreen : Screen(MY_ROUTES_SCREEN)
    data object ExternalResourcesScreen : Screen(EXTERNAL_RESOURCES_SCREEN)
    data object CustomRouteDetailsScreen : Screen(CUSTOM_ROUTE_DETAILS_SCREEN) {
        fun passRouteId(routeId: Int) = this.route.replace(
            oldValue = "{$ROUTE_ID_ARGUMENT_KEY}",
            newValue = routeId.toString()
        )
    }
}