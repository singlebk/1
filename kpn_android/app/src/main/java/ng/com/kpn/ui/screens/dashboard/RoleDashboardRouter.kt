package ng.com.kpn.ui.screens.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ng.com.kpn.api.KpnApiService
import ng.com.kpn.data.local.SessionManager
import javax.inject.Inject

data class AuthenticatedUser(
    val id: Int = 0,
    val firstName: String = "",
    val role: String = "",              // Tier: STATE, ZONAL, LGA, WARD, GENERAL
    val roleTitle: String = "",         // Exact RoleDefinition title from backend
    val zone: String? = null,
    val lga: String? = null,
    val ward: String? = null,
    val status: String = "PENDING"
)

data class RoleRouterState(
    val isLoading: Boolean = true,
    val user: AuthenticatedUser? = null,
    val error: String? = null
)

@HiltViewModel
class RoleRouterViewModel @Inject constructor(
    private val apiService: KpnApiService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(RoleRouterState())
    val state: StateFlow<RoleRouterState> = _state.asStateFlow()

    init {
        loadCurrentUser()
    }

    private fun loadCurrentUser() {
        viewModelScope.launch {
            try {
                _state.value = RoleRouterState(isLoading = true)
                // TODO: Replace with real API call and model deserialization
                // val response = apiService.getMe()
                // Map response.role and response.role_definition.title to AuthenticatedUser

                // Temporary: simulate a verified President session for testing navigation
                kotlinx.coroutines.delay(500)
                _state.value = RoleRouterState(
                    isLoading = false,
                    user = AuthenticatedUser(
                        id = 1,
                        firstName = "Abubakar",
                        role = "STATE",
                        roleTitle = "President",
                        status = "VERIFIED"
                    )
                )
            } catch (e: Exception) {
                _state.value = RoleRouterState(isLoading = false, error = e.message)
            }
        }
    }
}

/**
 * ROLE ENGINE — Routes authenticated users to their unique dashboard.
 * Never shows a generic dashboard.
 */
@Composable
fun RoleDashboardRouter(
    viewModel: RoleRouterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    when {
        state.isLoading -> DashboardLoadingScreen()
        state.user == null -> GeneralMemberHomeScreen()
        else -> RouteByRoleTitle(state.user!!.roleTitle)
    }
}

@Composable
private fun RouteByRoleTitle(roleTitle: String) {
    when (roleTitle) {
        // ── STATE ROLES ──────────────────────────────────────────────────
        "President"                                       -> PresidentDashboardScreen()
        "Vice President"                                  -> VicePresidentDashboardScreen()
        "General Secretary"                               -> GeneralSecretaryDashboardScreen()
        "Assistant General Secretary"                     -> AssistantGeneralSecretaryDashboardScreen()
        "Director of Monitoring & Compliance"             -> MonitoringComplianceDashboardScreen()
        "Director of Legal Affairs & Ethics"              -> LegalEthicsDashboardScreen()
        "Director of Finance"                             -> TreasurerDashboardScreen()
        "Finance Operations Officer"                      -> FinanceOperationsOfficerDashboardScreen()
        "Director of Community Engagement"                -> CommunityEngagementDashboardScreen()
        "Assistant Director of Community Engagement"      -> AssistantCommunityEngagementDashboardScreen()
        "Director of Programmes & Events"                 -> ProgrammesEventsDashboardScreen()
        "Assistant Director of Programmes & Events"       -> AssistantProgrammesDashboardScreen()
        "Director of Audit & Accountability"              -> AuditorGeneralDashboardScreen()
        "Director of Member Support & Welfare"            -> MemberSupportWelfareDashboardScreen()
        "Director of Youth Development"                   -> YouthDevelopmentDashboardScreen()
        "Director of Women's Development"                 -> WomensDevelopmentDashboardScreen()
        "Assistant Director of Women's Development"       -> AssistantWomensDevelopmentDashboardScreen()
        "Director of Media & Communications"              -> MediaDirectorDashboardScreen()
        "Assistant Director of Media & Communications"   -> AssistantMediaDashboardScreen()
        "Director of Public Relations & Partnerships"     -> PublicRelationsDashboardScreen()

        // ── ZONAL ROLES ──────────────────────────────────────────────────
        "Senatorial Director"                             -> SenDirectorDashboardScreen()
        "Senatorial Administrative Officer"               -> SenAdminOfficerDashboardScreen()
        "Senatorial Communications Officer"               -> SenCommsOfficerDashboardScreen()

        // ── LGA ROLES ────────────────────────────────────────────────────
        "LGA Network Lead"                                -> LgaNetworkLeadDashboardScreen()
        "LGA Administrative Officer"                      -> LgaAdminOfficerDashboardScreen()
        "LGA Programmes Officer"                          -> LgaProgrammesOfficerDashboardScreen()
        "LGA Finance Officer"                             -> LgaFinanceOfficerDashboardScreen()
        "LGA Communications Officer"                      -> LgaCommsOfficerDashboardScreen()
        "LGA Monitoring Officer"                          -> LgaMonitoringOfficerDashboardScreen()
        "LGA Women's Development Officer"                 -> LgaWomensDevelopmentDashboardScreen()
        "LGA Member Support Officer"                      -> LgaMemberSupportDashboardScreen()
        "LGA Community Engagement Officer"                -> LgaCommunityEngagementDashboardScreen()
        "LGA Legal & Ethics Adviser"                      -> LgaLegalEthicsDashboardScreen()

        // ── WARD ROLES ───────────────────────────────────────────────────
        "Ward Community Lead"                             -> WardCommunityLeadDashboardScreen()
        "Ward Administrative Officer"                     -> WardAdminOfficerDashboardScreen()
        "Ward Programmes Officer"                         -> WardProgrammesOfficerDashboardScreen()
        "Ward Finance Officer"                            -> WardFinanceOfficerDashboardScreen()
        "Ward Communications Officer"                     -> WardCommsOfficerDashboardScreen()
        "Ward Monitoring Officer"                         -> WardMonitoringOfficerDashboardScreen()
        "Ward Women's Support Officer"                    -> WardWomensSupportDashboardScreen()
        "Ward Engagement Officer"                         -> WardEngagementOfficerDashboardScreen()

        // ── DEFAULT: General Member ───────────────────────────────────────
        else                                              -> GeneralMemberHomeScreen()
    }
}

@Composable
private fun DashboardLoadingScreen() {
    androidx.compose.material3.Surface(
        modifier = androidx.compose.ui.Modifier.fillMaxSize()
    ) {
        androidx.compose.foundation.layout.Box(
            modifier = androidx.compose.ui.Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            androidx.compose.material3.CircularProgressIndicator(
                color = ng.com.kpn.ui.theme.KpnPrimaryGreen
            )
        }
    }
}
