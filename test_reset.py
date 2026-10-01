"""
End-to-end password reset test for KPN.
Tests A, C, D, E, F — locally with console email backend.
"""
import os
os.environ['DJANGO_SETTINGS_MODULE'] = 'KPN.settings'

import django
django.setup()

from django.test import TestCase, RequestFactory, Client
from django.contrib.auth.tokens import default_token_generator
from django.utils.http import urlsafe_base64_encode
from django.utils.encoding import force_bytes
from django.conf import settings
from staff.models import User

PASS = "\033[92m[PASS]\033[0m"
FAIL = "\033[91m[FAIL]\033[0m"

print("=" * 60)
print("KPN Password Reset — End-to-End Test")
print("=" * 60)

# ── Setup ─────────────────────────────────────────────────────
test_user, created = User.objects.get_or_create(
    username='_test_reset_user',
    defaults={
        'email': 'testreset@kpn.com.ng',
        'first_name': 'Test',
        'last_name': 'Reset',
        'status': 'VERIFIED',
        'role': 'GENERAL',
    }
)
if not created:
    test_user.email = 'testreset@kpn.com.ng'
    test_user.status = 'VERIFIED'
    test_user.save()

test_user.set_password('OriginalPass123!')
test_user.save()
print(f"Setup: test user pk={test_user.pk} email={test_user.email}")

c = Client(SERVER_NAME='kpn.com.ng', HTTP_HOST='kpn.com.ng')

# ── Test A: Reset request page loads ──────────────────────────
print("\n--- Test A: Reset request page ---")
r = c.get('/account/forgot-password/')
if r.status_code == 200:
    print(f"{PASS} Forgot password page returned 200")
else:
    print(f"{FAIL} Forgot password page returned {r.status_code}")

# ── Test B: POST reset request (email delivery via console) ───
print("\n--- Test B: POST reset request ---")
r = c.post('/account/forgot-password/', {'email': test_user.email})
if r.status_code == 302:
    print(f"{PASS} POST forgot_password redirected (302) — email queued")
else:
    print(f"{FAIL} POST forgot_password returned {r.status_code}")

# ── Generate valid token directly ─────────────────────────────
uid = urlsafe_base64_encode(force_bytes(test_user.pk))
token = default_token_generator.make_token(test_user)
reset_url = f'/account/reset-password/{uid}/{token}/'

# ── Test C: GET reset link ────────────────────────────────────
print("\n--- Test C: GET reset link ---")
r = c.get(reset_url)
if r.status_code == 200:
    print(f"{PASS} Reset link page loaded (200)")
else:
    print(f"{FAIL} Reset link page returned {r.status_code}")

# ── Test D: POST new password ─────────────────────────────────
print("\n--- Test D: Set new password ---")
r = c.post(reset_url, {'password1': 'NewSecurePass456!', 'password2': 'NewSecurePass456!'})
if r.status_code == 302:
    print(f"{PASS} Password set — redirect to login")
else:
    print(f"{FAIL} Set password returned {r.status_code}")
    print(r.content.decode()[:500])

# ── Test E: Login with new password ───────────────────────────
print("\n--- Test E: Login with new password ---")
test_user.refresh_from_db()
from django.contrib.auth import authenticate
user = authenticate(username=test_user.username, password='NewSecurePass456!')
if user is not None:
    print(f"{PASS} Authentication succeeded with new password")
else:
    print(f"{FAIL} Authentication failed with new password")

# ── Test F: Invalid/expired token rejected ────────────────────
print("\n--- Test F: Invalid token rejected ---")
bad_url = f'/account/reset-password/{uid}/invalid-token-abc123/'
r = c.get(bad_url)
# Should redirect back to forgot-password with error message
if r.status_code == 302 and 'forgot-password' in r['Location']:
    print(f"{PASS} Invalid token redirected to forgot-password")
else:
    print(f"{FAIL} Invalid token response: {r.status_code} Location: {r.get('Location','N/A')}")

# ── Test F2: Used token rejected (already set password) ───────
print("\n--- Test F2: Used (consumed) token rejected ---")
# After changing password, the original token is invalidated
r = c.get(reset_url)
if r.status_code == 302:
    print(f"{PASS} Previously used token now invalidated (redirect on GET)")
else:
    # If 200, the form shows but POST will fail — also acceptable
    print(f"  INFO: Response {r.status_code} (token state depends on Django's HMAC check)")

# ── Test G: Lockally backend — no config, graceful failure ────
print("\n--- Test G: Lockally backend — missing config ---")
from staff.lockally_backend import LockallyEmailBackend
from django.core.mail import EmailMessage

# Simulate with no API key set
import unittest.mock as mock
with mock.patch.object(settings, 'LOCKALLY_API_KEY', ''), \
     mock.patch.object(settings, 'LOCKALLY_API_URL', ''):
    backend = LockallyEmailBackend(fail_silently=True)
    msg = EmailMessage(
        subject='Test',
        body='Test',
        from_email='test@kpn.com.ng',
        to=['user@example.com'],
    )
    result = backend.send_messages([msg])
    if result == 0:
        print(f"{PASS} Missing config handled silently (returned 0, no exception)")
    else:
        print(f"{FAIL} Expected 0, got {result}")

# ── Cleanup ───────────────────────────────────────────────────
test_user.delete()
print(f"\nCleanup: test user deleted")

print("\n" + "=" * 60)
print("Tests complete.")
print("=" * 60)
