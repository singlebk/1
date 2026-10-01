import requests
import json
import sys

BASE_URL = 'http://127.0.0.1:8000/api/v1'

def test_endpoint(method, path, data=None, token=None, files=None):
    url = f"{BASE_URL}{path}"
    headers = {}
    if token:
        headers['Authorization'] = f'Bearer {token}'
    
    try:
        if method == 'GET':
            response = requests.get(url, headers=headers)
        elif method == 'POST':
            if files:
                response = requests.post(url, headers=headers, data=data, files=files)
            else:
                response = requests.post(url, headers=headers, json=data)
        
        print(f"[{method}] {path} -> Status: {response.status_code}")
        if response.status_code >= 400:
            print(f"  Error: {response.text}")
        return response
    except Exception as e:
        print(f"[{method}] {path} -> Failed to connect: {e}")
        return None

print("Starting API Tests...\n")

# Public GET Endpoints
test_endpoint('GET', '/newsroom/')
test_endpoint('GET', '/opportunities/')
test_endpoint('GET', '/opportunities/?category=scholarships')
test_endpoint('GET', '/community/')
test_endpoint('GET', '/civic/')
test_endpoint('GET', '/advocacy/')
test_endpoint('GET', '/leadership-directory/')
test_endpoint('GET', '/patrons/')
test_endpoint('GET', '/impact/')
test_endpoint('GET', '/locations/zones/')

# Try to get a valid Zone ID for LGA filtering
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

# Auth tests
print("\n--- Auth Tests ---")
# 1. Unauthenticated /me
test_endpoint('GET', '/auth/me/')

# 2. Register a new user
# First create a dummy image
with open('dummy.jpg', 'wb') as f:
    f.write(b'\x00' * 1024)

# Get a role definition
res = test_endpoint('GET', '/roles/')
role_id = 1
if res and res.status_code == 200 and res.json():
    role_id = res.json()[0]['id']

register_data = {
    'username': 'testuser123',
    'password': 'TestPassword123!',
    'password_confirm': 'TestPassword123!',
    'email': 'testuser123@example.com',
    'first_name': 'Test',
    'last_name': 'User',
    'phone': '08012345678',
    'gender': 'M',
    'zone': zone_id,
    'lga': lga_id,
    'role_definition': role_id
}
with open('dummy.jpg', 'rb') as f:
    res = test_endpoint('POST', '/auth/register/', data=register_data, files={'photo': f})

# 3. Login
login_data = {
    'username': 'testuser123',
    'password': 'TestPassword123!'
}
res = test_endpoint('POST', '/auth/login/', data=login_data)

access_token = None
refresh_token = None
if res and res.status_code == 200:
    access_token = res.json().get('access')
    refresh_token = res.json().get('refresh')

# 4. Authenticated /me
if access_token:
    test_endpoint('GET', '/auth/me/', token=access_token)

# 5. Refresh token
if refresh_token:
    test_endpoint('POST', '/auth/refresh/', data={'refresh': refresh_token})

# 6. Check OpenAPI Schema
print("\n--- OpenAPI Tests ---")
test_endpoint('GET', '/schema/')
test_endpoint('GET', '/docs/')

print("\nAPI Tests completed.")
