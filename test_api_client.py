import os
import django
from django.test import Client
from django.core.files.uploadedfile import SimpleUploadedFile

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'KPN.settings')
django.setup()

from django.conf import settings
settings.ALLOWED_HOSTS.append('testserver')

client = Client()
BASE_URL = '/api/v1'

def test_endpoint(method, path, data=None, token=None, files=None):
    url = f"{BASE_URL}{path}"
    headers = {}
    if token:
        headers['HTTP_AUTHORIZATION'] = f'Bearer {token}'
    
    try:
        if method == 'GET':
            response = client.get(url, **headers)
        elif method == 'POST':
            if files:
                post_data = data.copy() if data else {}
                post_data.update(files)
                response = client.post(url, post_data, **headers)
            else:
                response = client.post(url, data, content_type='application/json', **headers)
        
        print(f"[{method}] {path} -> Status: {response.status_code}")
        if response.status_code >= 400:
            print(f"  Response: {response.content.decode('utf-8')[:200]}")
        return response
    except Exception as e:
        print(f"[{method}] {path} -> Error: {e}")
        return None

print("Starting API Tests with Django Test Client...\n")

test_endpoint('GET', '/newsroom/')
test_endpoint('GET', '/opportunities/')
test_endpoint('GET', '/opportunities/?category=SCHOLARSHIP')
test_endpoint('GET', '/community/')
test_endpoint('GET', '/civic/')
test_endpoint('GET', '/advocacy/')
test_endpoint('GET', '/leadership-directory/')
test_endpoint('GET', '/patrons/')
test_endpoint('GET', '/impact/')
test_endpoint('GET', '/locations/zones/')

res = test_endpoint('GET', '/locations/zones/')
zone_id = 1
if res and res.status_code == 200 and res.json():
    zone_id = res.json()[0]['id']

test_endpoint('GET', f'/locations/lgas/?zone={zone_id}')
res = test_endpoint('GET', f'/locations/lgas/?zone={zone_id}')
lga_id = 1
if res and res.status_code == 200 and res.json():
    lga_id = res.json()[0]['id']

test_endpoint('GET', f'/locations/wards/?lga={lga_id}')
test_endpoint('GET', '/roles/')

print("\n--- Auth Tests ---")
test_endpoint('GET', '/auth/me/')

res = test_endpoint('GET', '/roles/')
role_id = 1
if res and res.status_code == 200 and res.json():
    role_id = res.json()[0]['id']

# Register a new user
from io import BytesIO
from PIL import Image

def get_dummy_image():
    image = Image.new('RGB', (100, 100), color='red')
    file = BytesIO()
    image.save(file, 'jpeg')
    file.seek(0)
    return SimpleUploadedFile("photo.jpg", file.read(), content_type="image/jpeg")

dummy_photo = get_dummy_image()

register_data = {
    'username': 'testuser2',
    'password': 'TestPassword123!',
    'password_confirm': 'TestPassword123!',
    'email': 'testuser2@example.com',
    'first_name': 'Test',
    'last_name': 'User',
    'phone': '08099999999',
    'gender': 'M',
    'zone': zone_id,
    'lga': lga_id,
    'role_definition': role_id
}

res = test_endpoint('POST', '/auth/register/', data=register_data, files={'photo': dummy_photo})

login_data = {
    'username': 'testuser2',
    'password': 'TestPassword123!'
}
res = test_endpoint('POST', '/auth/login/', data=login_data)

access_token = None
refresh_token = None
if res and res.status_code == 200:
    access_token = res.json().get('access')
    refresh_token = res.json().get('refresh')

if access_token:
    test_endpoint('GET', '/auth/me/', token=access_token)

if refresh_token:
    test_endpoint('POST', '/auth/refresh/', data={'refresh': refresh_token})

print("\n--- OpenAPI Tests ---")
test_endpoint('GET', '/schema/')
test_endpoint('GET', '/docs/')

print("\nAPI Tests completed.")
