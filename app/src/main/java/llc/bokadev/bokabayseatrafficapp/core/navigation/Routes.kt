package llc.bokadev.bokabayseatrafficapp.core.navigation

import llc.bokadev.bokabayseatrafficapp.core.navigation.destinations.ROUTE_ID_ARGUMENT_KEY


object Routes {
    const val ROOT = "root"
    const val BAY_MAP_SCREEN = "bay_map_screen"
    const val MORE_SCREEN = "more_screen"
    const val SAFETY_HUB_SCREEN = "safety_hub_screen"
    const val MY_ROUTES_SCREEN = "my_routes_screen"
    const val CUSTOM_ROUTE_DETAILS_SCREEN = "custom_routes_details_screen?$ROUTE_ID_ARGUMENT_KEY={$ROUTE_ID_ARGUMENT_KEY}"
}