import os

base_dir = r"C:\Users\hp\Downloads\kconnect-main\kpn_android\app\src\main\java\ng\com\kpn\ui\screens\dashboard"
viewmodels_dir = os.path.join(base_dir, "viewmodels")
os.makedirs(viewmodels_dir, exist_ok=True)

viewmodels = {
    "LgaViewModel": ("LgaDashboardDto", "getLgaMetrics"),
    "WardViewModel": ("WardDashboardDto", "getWardMetrics"),
    "ZonalViewModel": ("ZonalDashboardDto", "getZonalMetrics"),
    "FinanceViewModel": ("FinanceDashboardDto", "getFinanceMetrics"),
    "SecretariatViewModel": ("SecretariatDashboardDto", "getSecretariatMetrics")
}

template = """package ng.com.kpn.ui.screens.dashboard.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ng.com.kpn.core.network.Resource
import ng.com.kpn.data.remote.dto.{dto_name}
import ng.com.kpn.data.repository.DashboardRepository
import javax.inject.Inject

data class {state_name}(
    val isLoading: Boolean = false,
    val data: {dto_name}? = null,
    val error: String? = null
)

@HiltViewModel
class {vm_name} @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _state = MutableStateFlow({state_name}())
    val state: StateFlow<{state_name}> = _state.asStateFlow()

    init {
        fetchMetrics()
    }

    fun fetchMetrics() {
        repository.{repo_method}().onEach { result ->
            when (result) {
                is Resource.Loading -> _state.value = _state.value.copy(isLoading = true, error = null)
                is Resource.Success -> _state.value = {state_name}(isLoading = false, data = result.data)
                is Resource.Error -> _state.value = {state_name}(isLoading = false, error = result.message)
            }
        }.launchIn(viewModelScope)
    }
}
"""

for vm_name, (dto_name, repo_method) in viewmodels.items():
    state_name = vm_name.replace("ViewModel", "State")
    content = template.replace("{vm_name}", vm_name)\
                      .replace("{dto_name}", dto_name)\
                      .replace("{repo_method}", repo_method)\
                      .replace("{state_name}", state_name)
    
    file_path = os.path.join(viewmodels_dir, f"{vm_name}.kt")
    with open(file_path, "w", encoding="utf-8") as f:
        f.write(content)

print("Successfully generated all Tier-Based ViewModels.")
