package llc.bokadev.bokasafe.presentation.more.my_routes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import llc.bokadev.bokasafe.core.navigation.Navigator
import llc.bokadev.bokasafe.core.navigation.Screen
import llc.bokadev.bokasafe.data.local.db.BokaBaySeaTrafficAppDatabase
import llc.bokadev.bokasafe.data.local.model.RouteEntity
import javax.inject.Inject

@HiltViewModel
class MyRoutesViewModel @Inject constructor(
    private val bokaBaySeaTrafficAppDatabase: BokaBaySeaTrafficAppDatabase,
    private val navigator: Navigator
) : ViewModel() {


    protected val state by lazy { MutableStateFlow(MyRoutesState()) }
    val viewStateFlow: StateFlow<MyRoutesState> by lazy { state }

    private val _activateCustomRouteChannel = Channel<Boolean>()
    val activateCustomRouteChannel = _activateCustomRouteChannel.receiveAsFlow()


    init {
        getMyRoutes()
    }

    fun onEvent(event: MyRoutesEvent) {
        when (event) {
            is MyRoutesEvent.OnBackClick -> {
                viewModelScope.launch {
                    navigator.navigateUp()
                }
            }

            is MyRoutesEvent.ToggleDeleteRouteAlertDialog -> {
                state.update {
                    it.copy(
                        shouldShowDeleteAlertDialog = !state.value.shouldShowDeleteAlertDialog,
                        selectedRoute = event.selectedRoute
                    )
                }
            }

            is MyRoutesEvent.OnConfirmDeleteClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    state.value.selectedRoute?.let {
                        bokaBaySeaTrafficAppDatabase.customRouteDao.deleteRoute(
                            it
                        )
                    }
                    state.update { it.copy(shouldShowDeleteAlertDialog = !state.value.shouldShowDeleteAlertDialog) }
                }.invokeOnCompletion {
                    state.update {
                        it.copy(myRoutes = bokaBaySeaTrafficAppDatabase.customRouteDao.getAllRoutes())
                    }
                }
            }

            is MyRoutesEvent.OnRouteClick -> {
                viewModelScope.launch {
                    navigator.navigateTo(Screen.CustomRouteDetailsScreen.passRouteId(event.routeId))
                }

            }


            is MyRoutesEvent.OnAddNewRouteClick -> {
                viewModelScope.launch {
                    _activateCustomRouteChannel.send(true)
                    navigator.navigateTo(Screen.BayMapScreen.route)
                }
            }
        }
    }

    private fun getMyRoutes() {
        viewModelScope.launch(Dispatchers.IO) {
            state.update {
                it.copy(
                    myRoutes = bokaBaySeaTrafficAppDatabase.customRouteDao.getAllRoutes()
                )
            }
        }

    }

}


data class MyRoutesState(
    val myRoutes: List<RouteEntity> = emptyList(),
    val shouldShowDeleteAlertDialog: Boolean = false,
    val selectedRoute: RouteEntity? = null
)

sealed class MyRoutesEvent {
    data class OnRouteClick(val routeId: Int) : MyRoutesEvent()
    object OnBackClick : MyRoutesEvent()
    data class ToggleDeleteRouteAlertDialog(val selectedRoute: RouteEntity?) : MyRoutesEvent()
    object OnConfirmDeleteClick : MyRoutesEvent()
    object OnAddNewRouteClick: MyRoutesEvent()
}