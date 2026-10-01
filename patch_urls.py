import os

urls_path = r"C:\Users\hp\Downloads\kconnect-main\rest_api\urls.py"

with open(urls_path, "r", encoding="utf-8") as f:
    content = f.read()

# We need to insert the paths before the schema definition
insertion_point = "path('schema/', SpectacularAPIView.as_view(), name='schema'),"
new_urls = """
    # Track B: Role Dashboard APIs
    path('media/pipeline/', views.MediaPipelineView.as_view(), name='media-pipeline'),
    path('operations/oversight/', views.VPOversightView.as_view(), name='vp-oversight'),
    
    """

if "media/pipeline/" not in content:
    content = content.replace(insertion_point, new_urls + insertion_point)
    with open(urls_path, "w", encoding="utf-8") as f:
        f.write(content)
    print("Successfully patched urls.py")
else:
    print("URLs already patched.")
