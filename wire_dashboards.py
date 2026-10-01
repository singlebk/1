import os

base_dir = r"C:\Users\hp\Downloads\kconnect-main\kpn_android\app\src\main\java\ng\com\kpn\ui\screens\dashboard"

count = 0
for file_name in os.listdir(base_dir):
    if not file_name.endswith("DashboardScreen.kt"): continue
    # Skip already fully wired ones
    if file_name in ["MediaDirectorDashboardScreen.kt", "PresidentDashboardScreen.kt", "VicePresidentDashboardScreen.kt"]:
        continue

    # Map Screen to appropriate Tier ViewModel
    vm_class = None
    if file_name.startswith("Lga"): vm_class = "LgaViewModel"
    elif file_name.startswith("Ward"): vm_class = "WardViewModel"
    elif file_name.startswith("Sen"): vm_class = "ZonalViewModel"
    elif "Finance" in file_name or "Treasurer" in file_name: vm_class = "FinanceViewModel"
    elif "Secretary" in file_name: vm_class = "SecretariatViewModel"
    
    if not vm_class: continue

    path = os.path.join(base_dir, file_name)
    with open(path, "r", encoding="utf-8") as f:
        content = f.read()

    # Skip if already wired
    if "hiltViewModel" in content: continue 

    # 1. Add necessary imports
    imports = (
        "\nimport androidx.hilt.navigation.compose.hiltViewModel\n"
        "import androidx.compose.runtime.collectAsState\n"
        "import androidx.compose.runtime.getValue\n"
        "import ng.com.kpn.ui.screens.dashboard.viewmodels.*\n"
    )
    content = content.replace("import androidx.compose.runtime.Composable", "import androidx.compose.runtime.Composable" + imports)

    # 2. Modify Composable signature to inject ViewModel and collect StateFlow
    func_name = file_name.replace(".kt", "")
    sig_search = f"fun {func_name}() {{"
    sig_replace = (
        f"fun {func_name}(\n"
        f"    viewModel: {vm_class} = hiltViewModel()\n"
        f") {{\n"
        f"    val state by viewModel.state.collectAsState()\n"
    )
    
    content = content.replace(sig_search, sig_replace)

    # Write back to file
    with open(path, "w", encoding="utf-8") as f:
        f.write(content)
        
    count += 1

print(f"Successfully wired {count} dashboards with their respective Tier ViewModels.")
