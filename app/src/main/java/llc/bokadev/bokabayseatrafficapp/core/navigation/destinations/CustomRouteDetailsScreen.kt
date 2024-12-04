package llc.bokadev.bokabayseatrafficapp.core.navigation.destinations

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import llc.amplitudo.flourish_V2.core.utils.Constants
import llc.bokadev.bokabayseatrafficapp.core.navigation.Routes
import llc.bokadev.bokabayseatrafficapp.core.navigation.Screen
import llc.bokadev.bokabayseatrafficapp.presentation.more.MoreScreen
import llc.bokadev.bokabayseatrafficapp.presentation.more.MoreViewModel
import llc.bokadev.bokabayseatrafficapp.presentation.more.my_routes.route_details.CustomRouteDetailsScreen
import llc.bokadev.bokabayseatrafficapp.presentation.more.my_routes.route_details.CustomRouteDetailsViewModel

const val ROUTE_ID_ARGUMENT_KEY = "route_id"
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType")
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.customRouteDetailsScreenComposable(
    navController: NavController,
    showSnackBar: (message: String) -> Unit
) {
    composable(route = Screen.CustomRouteDetailsScreen.route,
        arguments = listOf(
            navArgument(ROUTE_ID_ARGUMENT_KEY) {
                type = NavType.IntType
                defaultValue = -1
            },
        ),
        enterTransition = { fadeIn(animationSpec = tween(Constants.ANIMATION_DURATION)) }) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(Routes.ROOT)
        }
        val viewModel = hiltViewModel<CustomRouteDetailsViewModel>()
        CustomRouteDetailsScreen(
            viewModel = viewModel,
            showSnackBar = showSnackBar
        )
    }
}