package llc.bokadev.bokabayseatrafficapp.core.navigation.graphs

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import llc.bokadev.bokabayseatrafficapp.core.navigation.NavType
import llc.bokadev.bokabayseatrafficapp.core.navigation.Navigator
import llc.bokadev.bokabayseatrafficapp.core.navigation.Routes.BAY_MAP_SCREEN
import llc.bokadev.bokabayseatrafficapp.core.navigation.Routes.ROOT
import llc.bokadev.bokabayseatrafficapp.core.navigation.destinations.bayMapScreenComposable
import llc.bokadev.bokabayseatrafficapp.core.utils.observeWithLifecycle
import timber.log.Timber

@Composable
fun BokaBaySeaTrafficAppNavigation(
    navController: NavHostController,
    navigator: Navigator,
    showSnackBar: (message: String) -> Unit,
    launchIntent: (intent: Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    navigator.navigationFlow.observeWithLifecycle { navType ->
        Timber.e("navType $navType")
        navigate(
            navController = navController, navType = navType
        )
    }
    NavHost(
        navController = navController,
        route = ROOT,
        startDestination = BAY_MAP_SCREEN,
        modifier = modifier
    ) {
        bayMapScreenComposable(
            navController = navController, showSnackBar = showSnackBar, launchPhoneIntent = launchIntent
        )
    }
}

private fun navigate(
    navController: NavHostController, navType: NavType
) {
    when (navType) {
        is NavType.NavigateToRoute -> {
            navController.navigate(navType.route)
        }

        is NavType.PopToRoute -> {
            navController.navigate(navType.route) {
                popUpTo(navType.staticRoute) {
                    inclusive = navType.inclusive
                }
            }
        }

        is NavType.PopBackStack -> {
            if (navType.route != null && navType.inclusive != null) navController.popBackStack(
                route = navType.route, inclusive = navType.inclusive
            )
            else navController.popBackStack()
        }

        is NavType.NavigateUp -> {
            navController.navigateUp()
        }

        else -> {}
    }
}
