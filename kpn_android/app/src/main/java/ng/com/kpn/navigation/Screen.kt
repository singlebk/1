package ng.com.kpn.navigation

sealed class Screen(val route: String) {
    // Auth Graph
    object Login : Screen("login")
    object Register : Screen("register")
    
    // Public Graph
    object Home : Screen("home")
    object Newsroom : Screen("newsroom")
    object Opportunities : Screen("opportunities")
    object Leadership : Screen("leadership")
    
    // Authenticated Graph
    object Dashboard : Screen("dashboard")
    object Profile : Screen("profile")
    object Notifications : Screen("notifications")
}
