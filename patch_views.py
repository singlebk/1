import os

views_path = r"C:\Users\hp\Downloads\kconnect-main\rest_api\views.py"

with open(views_path, "r", encoding="utf-8") as f:
    content = f.read()

new_views = """

# =========================================================
# TRACK B: ANDROID REQUIRED API EXTENSIONS
# =========================================================

class MediaPipelineView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    @extend_schema(responses={200: OpenApiTypes.OBJECT})
    def get(self, request):
        pending_count = Campaign.objects.filter(status='DRAFT').count()
        published_count = Campaign.objects.filter(status='PUBLISHED').count()
        active_campaigns = Campaign.objects.filter(status='PUBLISHED', category='CIVIC').count()
        trusted_reporters = User.objects.filter(is_trusted_reporter=True).count()
        
        return Response({
            "pending_review": pending_count,
            "published_news": published_count,
            "active_campaigns": active_campaigns,
            "trusted_reporters": trusted_reporters
        })

class VPOversightView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    @extend_schema(responses={200: OpenApiTypes.OBJECT})
    def get(self, request):
        zonal_directors = User.objects.filter(role='ZONAL', status='VERIFIED').count()
        lga_coordinators = User.objects.filter(role='LGA', status='VERIFIED').count()
        
        return Response({
            "zonal_directors_active": zonal_directors,
            "lga_coordinators_active": lga_coordinators,
            "disciplinary_cases": 2, # Connect to actual models later
            "operational_reports": 5
        })
"""

if "MediaPipelineView" not in content:
    with open(views_path, "a", encoding="utf-8") as f:
        f.write(new_views)
    print("Successfully patched views.py")
else:
    print("Views already patched.")
