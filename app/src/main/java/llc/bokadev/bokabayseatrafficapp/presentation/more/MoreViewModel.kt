package llc.bokadev.bokabayseatrafficapp.presentation.more

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import llc.bokadev.bokabayseatrafficapp.core.navigation.Navigator
import llc.bokadev.bokabayseatrafficapp.domain.repository.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val navigator: Navigator,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    var state by mutableStateOf(MoreState())
    private val _launchIntentChannel = Channel<Intent>()
    val launchIntentChannel = _launchIntentChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            state = state.copy(
                preferredSpeedUnit = dataStoreRepository.getPreferredSpeedUnit().first()
            )
        }
    }

    fun onEvent(event: MoreEvent) {
        when (event) {
            is MoreEvent.OnBackClick -> {
                viewModelScope.launch {
                    navigator.navigateUp()
                }
            }

            is MoreEvent.OnOptionClick -> {
                when (event.optionId) {
                    1 -> {
                        state = state.copy(shouldShowAlertDialog = !state.shouldShowAlertDialog)
                    }

                    5 -> {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.safesea.me/obavjestenja-za-pomorce")
                        )
                        viewModelScope.launch {
                            _launchIntentChannel.send(intent)
                        }
                    }
                }
            }

            is MoreEvent.OnConfirmAlertDialogClick -> {
                viewModelScope.launch {
                    if (state.preferredSpeedUnit == "knots" || state.preferredSpeedUnit == "")
                        dataStoreRepository.savePreferredSpeedUnit("km/h") else dataStoreRepository.savePreferredSpeedUnit(
                        "knots"
                    )
                    state = state.copy(shouldShowAlertDialog = !state.shouldShowAlertDialog)
                }.invokeOnCompletion {
                    viewModelScope.launch {
                        state = state.copy(preferredSpeedUnit = dataStoreRepository.getPreferredSpeedUnit().first())
                    }

                }

            }

            is MoreEvent.OnCancelAlertDialogClick -> {
                state = state.copy(shouldShowAlertDialog = !state.shouldShowAlertDialog)
            }
        }
    }
}

data class MoreState(
    val preferredSpeedUnit: String = String(),
    val shouldShowAlertDialog: Boolean = false
)

sealed class MoreEvent {
    object OnBackClick : MoreEvent()
    data class OnOptionClick(val optionId: Int) : MoreEvent()
    object OnConfirmAlertDialogClick : MoreEvent()
    object OnCancelAlertDialogClick : MoreEvent()

}