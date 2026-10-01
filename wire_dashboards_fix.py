import os
import re

base_dir = r"C:\Users\hp\Downloads\kconnect-main\kpn_android\app\src\main\java\ng\com\kpn\ui\screens\dashboard"

count = 0
for file_name in os.listdir(base_dir):
    if not file_name.endswith("DashboardScreen.kt"): continue
    if file_name in ["MediaDirectorDashboardScreen.kt", "PresidentDashboardScreen.kt", "VicePresidentDashboardScreen.kt"]:
        continue

    vm_class = None
    if file_name.startswith("Lga"): vm_class = "LgaViewModel"
    elif file_name.startswith("Ward"): vm_class = "WardViewModel"
    elif file_name.startswith("Sen") or "Zonal" in file_name: vm_class = "ZonalViewModel"
    elif "Finance" in file_name or "Treasurer" in file_name: vm_class = "FinanceViewModel"
    elif "Secretary" in file_name: vm_class = "SecretariatViewModel"
    
    if not vm_class: 
        print(f"Skipped {file_name}: No matching Tier ViewModel")
        continue

    path = os.path.join(base_dir, file_name)
    with open(path, "r", encoding="utf-8") as f:
        content = f.read()

    if "hiltViewModel" in content: 
        print(f"Skipped {file_name}: Already wired")
        continue 

    # 1. Add necessary imports
    if "import androidx.hilt.navigation.compose.hiltViewModel" not in content:
        imports = (
            "import androidx.hilt.navigation.compose.hiltViewModel\n"
            "import androidx.compose.runtime.collectAsState\n"
            "import androidx.compose.runtime.getValue\n"
            "import ng.com.kpn.ui.screens.dashboard.viewmodels.*\n"
        )
        content = content.replace("import androidx.compose.runtime.Composable", "import androidx.compose.runtime.Composable\n" + imports)

    # 2. Modify Composable signature using Regex to be safe
    func_name = file_name.replace(".kt", "")
    
    # Matches: fun WardCommsOfficerDashboardScreen() {
    # Or:      fun WardCommsOfficerDashboardScreen() \n {
    pattern = r"fun\s+" + func_name + r"\s*\(\)\s*\{"
    
    replacement = (
        f"fun {func_name}(\n"
        f"    viewModel: {vm_class} = hiltViewModel()\n"
        f") {{\n"
        f"    val state by viewModel.state.collectAsState()\n"
    )
    
    new_content = re.sub(pattern, replacement, content)
    
    if new_content != content:
        with open(path, "w", encoding="utf-8") as f:
            f.write(new_content)
        print(f"Wired: {file_name} -> {vm_class}")
        count += 1
    else:
        print(f"Skipped {file_name}: Regex did not match signature")

print(f"Successfully wired {count} dashboards.")
