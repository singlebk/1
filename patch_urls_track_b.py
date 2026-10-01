import os

urls_path = r"C:\Users\hp\Downloads\kconnect-main\rest_api\urls.py"

with open(urls_path, "r", encoding="utf-8") as f:
    content = f.read()

# Add the import for our new dashboard_views
if "from . import dashboard_views" not in content:
    content = content.replace("from . import views", "from . import views\nfrom . import dashboard_views")

# Insert the new routes before the schema endpoint
insertion_point = "path('schema/', SpectacularAPIView.as_view(), name='schema'),"
new_urls = """
    # Track B: Tier-Based Dashboard APIs
    path('dashboards/secretariat/', dashboard_views.SecretariatDashboardView.as_view(), name='dash-secretariat'),
    path('dashboards/finance/', dashboard_views.FinanceDashboardView.as_view(), name='dash-finance'),
    path('dashboards/community/', dashboard_views.CommunityEngagementDashboardView.as_view(), name='dash-community'),
    path('dashboards/zone/', dashboard_views.ZonalDashboardView.as_view(), name='dash-zone'),
    path('dashboards/lga/', dashboard_views.LgaDashboardView.as_view(), name='dash-lga'),
    path('dashboards/ward/', dashboard_views.WardDashboardView.as_view(), name='dash-ward'),
    
    """

if "dashboards/lga/" not in content:
    content = content.replace(insertion_point, new_urls + insertion_point)
    with open(urls_path, "w", encoding="utf-8") as f:
        f.write(content)
    print("Successfully patched urls.py with full Track B Dashboard APIs")
else:
    print("Dashboard URLs already patched.")
