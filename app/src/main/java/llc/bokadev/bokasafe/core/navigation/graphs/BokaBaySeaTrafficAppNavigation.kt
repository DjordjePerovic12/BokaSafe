package llc.bokadev.bokasafe.core.navigation.graphs

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import llc.bokadev.bokasafe.core.navigation.NavType
import llc.bokadev.bokasafe.core.navigation.Navigator
import llc.bokadev.bokasafe.core.navigation.Routes.BAY_MAP_SCREEN
import llc.bokadev.bokasafe.core.navigation.Routes.ROOT
import llc.bokadev.bokasafe.core.navigation.destinations.bayMapScreenComposable
import llc.bokadev.bokasafe.core.navigation.destinations.customRouteDetailsScreenComposable
import llc.bokadev.bokasafe.core.navigation.destinations.externalResourcesScreenComposable
import llc.bokadev.bokasafe.core.navigation.destinations.moreScreenComposable
import llc.bokadev.bokasafe.core.navigation.destinations.myRoutesScreenComposable
import llc.bokadev.bokasafe.core.navigation.destinations.safetyHubScreenComposable
import llc.bokadev.bokasafe.core.utils.observeWithLifecycle
import llc.bokadev.bokasafe.presentation.bay_map.BayMapViewModel
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BokaBaySeaTrafficAppNavigation(
    navController: NavHostController,
    navigator: Navigator,
    viewModel: BayMapViewModel,
    showSnackBar: (message: String) -> Unit,
    activateCustomRoute: () -> Unit,
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
            navController = navController,
            showSnackBar = showSnackBar,
            launchPhoneIntent = launchIntent,
            viewModel = viewModel,
        )
        moreScreenComposable(
            navController = navController,
            showSnackBar = showSnackBar,
            launchWebBrowserIntent = launchIntent
        )

        safetyHubScreenComposable(
            navController = navController,
            showSnackBar = showSnackBar,
            launchIntent = launchIntent
        )

        myRoutesScreenComposable(
            navController = navController,
            showSnackBar = showSnackBar,
            activateCustomRoute = activateCustomRoute
        )

        customRouteDetailsScreenComposable(
            navController = navController,
            showSnackBar = showSnackBar
        )

        externalResourcesScreenComposable(
            navController = navController,
            showSnackBar = showSnackBar,
            launchIntent = launchIntent
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
