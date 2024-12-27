package llc.bokadev.bokasafe.presentation.more.external_resources

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.googlejavaformat.Doc
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
import llc.bokadev.bokasafe.BuildConfig
import llc.bokadev.bokasafe.core.navigation.Navigator
import llc.bokadev.bokasafe.core.utils.Resource
import llc.bokadev.bokasafe.domain.model.Document
import llc.bokadev.bokasafe.domain.repository.BokaSafeRepository
import llc.bokadev.bokasafe.domain.repository.LocationRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExternalResourcesViewModel @Inject constructor(
    private val repository: BokaSafeRepository,
    private val navigator: Navigator
) : ViewModel() {

    protected val state by lazy { MutableStateFlow(ExternalResourcesState()) }
    val viewStateFlow: StateFlow<ExternalResourcesState> by lazy { state }

    private val _launchIntentChannel = Channel<Intent>()
    val launchIntentChannel = _launchIntentChannel.receiveAsFlow()

    private val _snackbarChannel = Channel<String>()
    val snackbarChannel = _snackbarChannel.receiveAsFlow()

    init {
        getAllDocuments()
    }

    fun onEvent(event: ExtenralResourcesEvent) {
        when (event) {
            is ExtenralResourcesEvent.OnDocumentClick -> {
                openPdf(event.url)
            }

            is ExtenralResourcesEvent.OnBackClick -> {
                viewModelScope.launch {
                    navigator.navigateUp()
                }
            }
        }
    }

    private fun getAllDocuments() {
        viewModelScope.launch {
            val result = repository.getAllDocuments()
            result.let { resource ->
                when (resource) {
                    is Resource.Success -> {
                        state.update {
                            it.copy(
                                documents = resource.data?.toMutableList() ?: mutableListOf()
                            )
                        }
                        Timber.e("List ${state.value.documents}")
                    }

                    is Resource.Error -> {

                    }

                    is Resource.Loading -> {
                        Timber.e("LOADING")
                    }
                }
            }
        }
    }

    private fun openPdf(url: String) {
        val baseUrl = BuildConfig.BASE_URL
        // First, check if the file URL ends with ".pdf" or if it's a valid PDF URL
        if (url.endsWith(".pdf", ignoreCase = true)) {
            Timber.e("docs ${baseUrl + url}")
            // If it's a PDF, open it
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(baseUrl + url)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            viewModelScope.launch {
                _launchIntentChannel.send(intent)
            }
        } else {
            // If it's not a PDF, show an error message
            viewModelScope.launch {
                _snackbarChannel.send("The selected document is not a PDF.")
            }
        }
    }
}

data class ExternalResourcesState(
    val documents: List<Document> = emptyList()
)

sealed class ExtenralResourcesEvent {
    data class OnDocumentClick(val url: String) : ExtenralResourcesEvent()
    object OnBackClick : ExtenralResourcesEvent()
}