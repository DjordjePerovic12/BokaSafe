package llc.bokadev.bokasafe.presentation.more.my_routes.route_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import llc.bokadev.bokasafe.core.navigation.Navigator
import llc.bokadev.bokasafe.core.navigation.destinations.ROUTE_ID_ARGUMENT_KEY
import llc.bokadev.bokasafe.data.local.db.BokaBaySeaTrafficAppDatabase
import llc.bokadev.bokasafe.data.local.model.RouteEntity
import javax.inject.Inject

@HiltViewModel
class CustomRouteDetailsViewModel @Inject constructor(
    private val navigator: Navigator,
    private val database: BokaBaySeaTrafficAppDatabase,
    private val savedStateHandle: SavedStateHandle

) : ViewModel() {

    protected val state by lazy { MutableStateFlow(RouteDetailsState()) }
    val viewStateFlow: StateFlow<RouteDetailsState> by lazy { state }

    val routeId = savedStateHandle.get<Int>(ROUTE_ID_ARGUMENT_KEY)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            state.update {
                it.copy(customRoute = database.customRouteDao.getRouteById(routeId ?: -1))
            }
        }
    }

    fun onEvent(event: RouteDetailsEvent) {
        when (event) {
            is RouteDetailsEvent.OnBackClick -> {
                viewModelScope.launch {
                    navigator.navigateUp()
                }
            }

        }
    }


}


data class RouteDetailsState(
    val customRoute: RouteEntity? = null
)

sealed class RouteDetailsEvent {
    object OnBackClick : RouteDetailsEvent()

}