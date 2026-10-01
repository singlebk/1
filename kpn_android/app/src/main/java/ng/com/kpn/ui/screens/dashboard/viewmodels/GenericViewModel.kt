package ng.com.kpn.ui.screens.dashboard.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class GenericDashboardState(
    val isLoading: Boolean = false,
    val data: Any? = null,
    val error: String? = null
)

/**
 * A fallback ViewModel for State-level departmental roles that don't yet have 
 * specific Track B API endpoints defined (e.g. Legal, Youth, Women's, Audit).
 * Allows the UI architecture to remain consistent.
 */
@HiltViewModel
class GenericViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(GenericDashboardState())
    val state: StateFlow<GenericDashboardState> = _state.asStateFlow()

    init {
        // Will be wired to repository when specific endpoints are mapped
    }
}
