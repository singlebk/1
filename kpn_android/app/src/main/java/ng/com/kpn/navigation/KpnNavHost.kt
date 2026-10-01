package ng.com.kpn.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ng.com.kpn.ui.screens.auth.LoginScreen
import ng.com.kpn.ui.screens.auth.RegisterScreen
import ng.com.kpn.ui.screens.dashboard.RoleDashboardRouter
import ng.com.kpn.ui.screens.public.HomeScreen
import ng.com.kpn.ui.screens.public.NewsroomScreen
import ng.com.kpn.ui.screens.public.OpportunitiesScreen

@Composable
fun KpnNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // ==========================================
        // PUBLIC GRAPH
        // ==========================================
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onNavigateToNewsroom = { navController.navigate(Screen.Newsroom.route) }
            )
        }

        composable(Screen.Newsroom.route) {
            NewsroomScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Opportunities.route) {
            OpportunitiesScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ==========================================
        // AUTH GRAPH
        // ==========================================
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateBack = { navController.popBackStack() },
                onRegistrationComplete = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        // ==========================================
        // AUTHENTICATED GRAPH — Role-Routed
        // ==========================================
        composable(Screen.Dashboard.route) {
            // RoleDashboardRouter reads the authenticated user from the backend
            // and dispatches to the correct unique role dashboard.
            // This single composable handles all 42 roles.
            RoleDashboardRouter()
        }
    }
}
