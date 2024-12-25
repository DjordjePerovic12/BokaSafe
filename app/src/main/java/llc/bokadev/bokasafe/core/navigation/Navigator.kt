package llc.bokadev.bokasafe.core.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

/** Acts as a middle layer for navigation.
 * [See](https://developer.android.com/jetpack/compose/architecture#example)
 * */
@Singleton
class NavigatorImpl @Inject constructor() : Navigator {
    private val _navigationChannel = Channel<NavType>()
    override val navigationFlow = _navigationChannel.receiveAsFlow()

    override suspend fun navigateTo(route: String) = navigate(NavType.NavigateToRoute(route))
    override suspend fun popBackStack(route: String?, inclusive: Boolean?) {
        navigate(NavType.PopBackStack(route = route, inclusive = inclusive))
    }

    override suspend fun navigateUp() {
        navigate(NavType.NavigateUp)
    }

    override suspend fun popUpTo(route: String, staticRoute: String, inclusive: Boolean) =
        navigate(NavType.PopToRoute(route, staticRoute, inclusive))

    private suspend fun navigate(navType: NavType) {
        _navigationChannel.send(navType)
    }
}

interface Navigator {
    val navigationFlow: Flow<NavType>
    suspend fun navigateTo(route: String)
    suspend fun popBackStack(route: String? = null, inclusive: Boolean? = null)
    suspend fun navigateUp()
    suspend fun popUpTo(route: String, staticRoute: String, inclusive: Boolean)
}

sealed class NavType {
    object NavigateUp : NavType()
    data class PopBackStack(val route: String? = null, val inclusive: Boolean? = null) : NavType()
    data class NavigateToRoute(val route: String) : NavType()
    data class PopToRoute(
        val route: String,
        val staticRoute: String,
        val inclusive: Boolean = false
    ) : NavType()
}
