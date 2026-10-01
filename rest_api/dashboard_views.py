from rest_framework import permissions
from rest_framework.views import APIView
from rest_framework.response import Response
from django.db.models import Sum
from drf_spectacular.utils import extend_schema, OpenApiTypes

from staff.models import User
from campaigns.models import Campaign
from core.models import CommunityInitiative, CommunityReport
from leadership.models import LGA, Ward

# ==========================================
# STATE-LEVEL DASHBOARDS
# ==========================================

class SecretariatDashboardView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    @extend_schema(responses={200: OpenApiTypes.OBJECT})
    def get(self, request):
        total_members = User.objects.count()
        verified_members = User.objects.filter(status='VERIFIED').count()
        return Response({
            "total_members": total_members,
            "verified_members": verified_members,
            "active_meetings": 4, # Placeholder for Meeting model
            "pending_documents": 12
        })

class FinanceDashboardView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    @extend_schema(responses={200: OpenApiTypes.OBJECT})
    def get(self, request):
        # Placeholders until Finance models are implemented in Django
        return Response({
            "total_income": 4500000,
            "total_expenses": 1200000,
            "balance": 3300000,
            "pending_receipts": 14
        })

class CommunityEngagementDashboardView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    @extend_schema(responses={200: OpenApiTypes.OBJECT})
    def get(self, request):
        initiatives = CommunityInitiative.objects.count()
        pending_reports = CommunityReport.objects.filter(is_published=False).count()
        return Response({
            "active_initiatives": initiatives,
            "pending_reports": pending_reports
        })

# ==========================================
# ZONAL-LEVEL DASHBOARDS
# ==========================================

class ZonalDashboardView(APIView):
    """Dynamically scopes data to the user's Zone"""
    permission_classes = [permissions.IsAuthenticated]

    @extend_schema(responses={200: OpenApiTypes.OBJECT})
    def get(self, request):
        zone = request.user.zone
        if not zone:
            return Response({"error": "User has no assigned zone"}, status=400)
            
        lgas_in_zone = LGA.objects.filter(zone=zone).count()
        zonal_members = User.objects.filter(zone=zone, status='VERIFIED').count()
        
        return Response({
            "zone_name": zone.name,
            "lgas_in_zone": lgas_in_zone,
            "zonal_members": zonal_members,
            "documents_filed": 15,
            "zone_announcements": 3
        })

# ==========================================
# LGA-LEVEL DASHBOARDS
# ==========================================

class LgaDashboardView(APIView):
    """Dynamically scopes data to the user's LGA"""
    permission_classes = [permissions.IsAuthenticated]

    @extend_schema(responses={200: OpenApiTypes.OBJECT})
    def get(self, request):
        lga = request.user.lga
        if not lga:
            return Response({"error": "User has no assigned LGA"}, status=400)
            
        lga_members = User.objects.filter(lga=lga, status='VERIFIED').count()
        wards_active = Ward.objects.filter(lga=lga).count()
        
        return Response({
            "lga_name": lga.name,
            "lga_members": lga_members,
            "wards_active": wards_active,
            "pending_approvals": User.objects.filter(lga=lga, status='PENDING').count(),
            "lga_balance": 150000 # Placeholder for LGA finance
        })

# ==========================================
# WARD-LEVEL DASHBOARDS
# ==========================================

class WardDashboardView(APIView):
    """Dynamically scopes data to the user's Ward"""
    permission_classes = [permissions.IsAuthenticated]

    @extend_schema(responses={200: OpenApiTypes.OBJECT})
    def get(self, request):
        ward = request.user.ward
        if not ward:
            return Response({"error": "User has no assigned Ward"}, status=400)
            
        ward_members = User.objects.filter(ward=ward, status='VERIFIED').count()
        pending_ward_members = User.objects.filter(ward=ward, status='PENDING').count()
        
        return Response({
            "ward_name": ward.name,
            "ward_members": ward_members,
            "pending_approvals": pending_ward_members,
            "ward_balance": 45200,
            "notices_sent": 14
        })
