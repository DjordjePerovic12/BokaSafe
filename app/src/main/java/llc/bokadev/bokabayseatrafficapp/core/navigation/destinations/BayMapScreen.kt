package llc.bokadev.bokabayseatrafficapp.core.navigation.destinations

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import llc.amplitudo.flourish_V2.core.utils.Constants
import llc.bokadev.bokabayseatrafficapp.core.navigation.Routes
import llc.bokadev.bokabayseatrafficapp.core.navigation.Screen
import llc.bokadev.bokabayseatrafficapp.presentation.BayMapScreen
import llc.bokadev.bokabayseatrafficapp.presentation.BayMapViewModel


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType")
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.bayMapScreenComposable(
    navController: NavController, showSnackBar: (message: String) -> Unit, launchPhoneIntent: (intent: Intent) -> Unit
) {
    composable(route = Screen.BayMapScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(Constants.ANIMATION_DURATION)) }) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(Routes.ROOT)
        }
        val guideScreenViewModel = hiltViewModel<BayMapViewModel>()
        BayMapScreen(
            viewModel = guideScreenViewModel,
            showSnackBar = showSnackBar,
            launchPhoneIntent = launchPhoneIntent
        )
    }
}