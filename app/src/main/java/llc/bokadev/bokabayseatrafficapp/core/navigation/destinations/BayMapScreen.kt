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
import llc.bokadev.bokabayseatrafficapp.presentation.bay_map.BayMapScreen
import llc.bokadev.bokabayseatrafficapp.presentation.bay_map.BayMapViewModel

const val SHOULD_ENABLE_CUSTOM_ROUTE_ARGUMENT_KEY = "should_enable_custom_route"

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType")
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.bayMapScreenComposable(
    navController: NavController,
    showSnackBar: (message: String) -> Unit,
    viewModel: BayMapViewModel,
    launchPhoneIntent: (intent: Intent) -> Unit
) {
    composable(route = Screen.BayMapScreen.route,
        arguments = listOf(
            navArgument(ROUTE_ID_ARGUMENT_KEY) {
                type = NavType.BoolType
                defaultValue = false
            },
        ),
        enterTransition = { fadeIn(animationSpec = tween(Constants.ANIMATION_DURATION)) }) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(Routes.ROOT)
        }

        val shouldEnableCustomRoute = backStackEntry.arguments?.getBoolean(SHOULD_ENABLE_CUSTOM_ROUTE_ARGUMENT_KEY) ?: false

        BayMapScreen(
            viewModel = viewModel,
            showSnackBar = showSnackBar,
            launchPhoneIntent = launchPhoneIntent,
        )
    }
}