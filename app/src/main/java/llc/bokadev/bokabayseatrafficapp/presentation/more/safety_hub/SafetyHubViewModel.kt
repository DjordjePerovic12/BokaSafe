package llc.bokadev.bokabayseatrafficapp.presentation.more.safety_hub

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import llc.bokadev.bokabayseatrafficapp.core.navigation.Navigator
import llc.bokadev.bokabayseatrafficapp.core.utils.toKnots
import llc.bokadev.bokabayseatrafficapp.domain.repository.LocationRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SafetyHubViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val navigator: Navigator
) : ViewModel() {

    protected val state by lazy { MutableStateFlow(SafetyHubState()) }
    val viewStateFlow: StateFlow<SafetyHubState> by lazy { state }

    private val _launchIntentChannel = Channel<Intent>()
    val launchIntentChannel = _launchIntentChannel.receiveAsFlow()

    init {
        observeUserLocation()
    }

    fun onEvent(event: SafetyHubEvent) {
        when (event) {
            is SafetyHubEvent.OnCallNowClick -> {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${state.value.nearestSARContact}")
                }
                viewModelScope.launch {
                    _launchIntentChannel.send(intent)
                }
            }

            is SafetyHubEvent.OnBackClick -> {
                viewModelScope.launch {
                    navigator.navigateUp()
                }
            }
        }
    }


    private fun observeUserLocation() {
        locationRepository.getLocationUpdates().catch { e -> e.printStackTrace() }
            .onEach { location ->
//                updateDirection(location)
                Timber.e("Location vm ${location.latitude}, ${location.latitude}")
                val latitudeCurrent = location.latitude
                val longitudeCurrent = location.longitude
                state.update { it.copy(userLocation = LatLng(latitudeCurrent, longitudeCurrent)) }
            }.launchIn(viewModelScope)
    }
}

data class SafetyHubState(
    val userLocation: LatLng? = null,
    val nearestSARContact: String = "+382 30 313 088"
)

sealed class SafetyHubEvent {
    object OnCallNowClick : SafetyHubEvent()
    object OnBackClick : SafetyHubEvent()
}