package llc.bokadev.bokasafe.core.navigation.destinations

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import llc.amplitudo.flourish_V2.core.utils.Constants
import llc.bokadev.bokasafe.core.navigation.Routes
import llc.bokadev.bokasafe.core.navigation.Screen
import llc.bokadev.bokasafe.presentation.more.my_routes.MyRoutesScreen
import llc.bokadev.bokasafe.presentation.more.my_routes.MyRoutesViewModel


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType")
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.myRoutesScreenComposable(
    navController: NavController,
    showSnackBar: (message: String) -> Unit,
    activateCustomRoute: () -> Unit
) {
    composable(route = Screen.MyRoutesScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(Constants.ANIMATION_DURATION)) }) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(Routes.ROOT)
        }
        val viewModel = hiltViewModel<MyRoutesViewModel>()
        MyRoutesScreen(
            viewModel = viewModel,
            showSnackBar = showSnackBar,
                    activateCustomRoute = activateCustomRoute
        )
    }
}