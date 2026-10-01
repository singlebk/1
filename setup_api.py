import os

os.system('python manage.py startapp rest_api')

settings_path = 'KPN/settings.py'
with open(settings_path, 'r') as f:
    settings_code = f.read()

# Add installed apps
if 'rest_framework' not in settings_code:
    settings_code = settings_code.replace(
        "INSTALLED_APPS = [",
        "INSTALLED_APPS = [\n    'rest_framework',\n    'rest_framework_simplejwt',\n    'drf_spectacular',\n    'corsheaders',\n    'rest_api',"
    )

# Add middleware
if 'corsheaders.middleware.CorsMiddleware' not in settings_code:
    settings_code = settings_code.replace(
        "MIDDLEWARE = [",
        "MIDDLEWARE = [\n    'corsheaders.middleware.CorsMiddleware',"
    )

# Add DRF config
if 'REST_FRAMEWORK' not in settings_code:
    settings_code += """

REST_FRAMEWORK = {
    'DEFAULT_AUTHENTICATION_CLASSES': (
        'rest_framework_simplejwt.authentication.JWTAuthentication',
    ),
    'DEFAULT_SCHEMA_CLASS': 'drf_spectacular.openapi.AutoSchema',
}

from datetime import timedelta
SIMPLE_JWT = {
    'ACCESS_TOKEN_LIFETIME': timedelta(minutes=60),
    'REFRESH_TOKEN_LIFETIME': timedelta(days=1),
}

SPECTACULAR_SETTINGS = {
    'TITLE': 'KPN API',
    'DESCRIPTION': 'KPN Mobile App API',
    'VERSION': '1.0.0',
    'SERVE_INCLUDE_SCHEMA': False,
}

CORS_ALLOW_ALL_ORIGINS = True

"""

with open(settings_path, 'w') as f:
    f.write(settings_code)

urls_path = 'KPN/urls.py'
with open(urls_path, 'r') as f:
    urls_code = f.read()

if 'rest_api.urls' not in urls_code:
    urls_code = urls_code.replace(
        "urlpatterns = [",
        "urlpatterns = [\n    path('api/v1/', include('rest_api.urls')),"
    )
    if 'include' not in urls_code:
        urls_code = urls_code.replace("from django.urls import path", "from django.urls import path, include")

with open(urls_path, 'w') as f:
    f.write(urls_code)

print("Setup complete")
