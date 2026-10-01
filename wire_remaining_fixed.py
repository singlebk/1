import os
import re

base_dir = r"C:\Users\hp\Downloads\kconnect-main\kpn_android\app\src\main\java\ng\com\kpn\ui\screens\dashboard"

count = 0
for file_name in os.listdir(base_dir):
    if not file_name.endswith("DashboardScreen.kt"): continue
    
    path = os.path.join(base_dir, file_name)
    with open(path, "r", encoding="utf-8") as f:
        content = f.read()

    # Skip files that already have a ViewModel injected
    if "hiltViewModel" in content: continue 

    vm_class = "GenericViewModel"

    # 1. Add necessary imports
    if "import androidx.hilt.navigation.compose.hiltViewModel" not in content:
        imports = (
            "import androidx.hilt.navigation.compose.hiltViewModel\n"
            "import androidx.compose.runtime.collectAsState\n"
            "import androidx.compose.runtime.getValue\n"
            "import ng.com.kpn.ui.screens.dashboard.viewmodels.*\n"
        )
        content = content.replace("import androidx.compose.runtime.Composable", "import androidx.compose.runtime.Composable\n" + imports)

    # 2. Modify Composable signature. Note: It may have parameters like (onNavigateBack: () -> Unit = {})
    func_name = file_name.replace(".kt", "")
    pattern = r"fun\s+" + func_name + r"\s*\([^)]*\)\s*\{"
    
    replacement = (
        f"fun {func_name}(\n"
        f"    onNavigateBack: () -> Unit = {{}},\n"
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
        print(f"Failed to match signature in {file_name}")

print(f"Successfully wired {count} remaining generic dashboards.")
