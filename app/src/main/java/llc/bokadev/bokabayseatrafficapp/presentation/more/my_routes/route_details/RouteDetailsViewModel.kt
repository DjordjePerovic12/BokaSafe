package llc.bokadev.bokabayseatrafficapp.presentation.more.my_routes.route_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import llc.bokadev.bokabayseatrafficapp.core.navigation.Navigator
import llc.bokadev.bokabayseatrafficapp.core.navigation.destinations.ROUTE_ID_ARGUMENT_KEY
import llc.bokadev.bokabayseatrafficapp.data.local.db.BokaBaySeaTrafficAppDatabase
import llc.bokadev.bokabayseatrafficapp.data.local.model.RouteEntity
import llc.bokadev.bokabayseatrafficapp.presentation.more.my_routes.MyRoutesState
import timber.log.Timber
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

    init {
        Timber.e("ROUTE ID $routeId")
    }
}


data class RouteDetailsState(
    val customRoute: RouteEntity? = null
)

sealed class RouteDetailsEvent {

}