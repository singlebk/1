import os

file_path = r"C:\Users\hp\Downloads\kconnect-main\kpn_android\app\src\main\java\ng\com\kpn\di\NetworkModule.kt"

with open(file_path, "r", encoding="utf-8") as f:
    content = f.read()

injection = """
    @Provides
    @Singleton
    fun provideDashboardApiService(retrofit: retrofit2.Retrofit): ng.com.kpn.data.remote.api.DashboardApiService {
        return retrofit.create(ng.com.kpn.data.remote.api.DashboardApiService::class.java)
    }
"""

if "provideDashboardApiService" not in content:
    # Safely insert before the last closing brace
    last_brace_idx = content.rfind("}")
    if last_brace_idx != -1:
        new_content = content[:last_brace_idx] + injection + "\n}\n"
        with open(file_path, "w", encoding="utf-8") as f:
            f.write(new_content)
        print("Successfully injected DashboardApiService into NetworkModule")
else:
    print("DashboardApiService already injected.")
