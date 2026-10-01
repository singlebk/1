views_code = """
from rest_framework import generics, viewsets, permissions
from rest_framework.views import APIView
from rest_framework.response import Response
from campaigns.models import Campaign
from core.models import Opportunity, CommunityInitiative, AdvocacyCampaign, Patron
from leadership.models import Zone, LGA, Ward, RoleDefinition
from staff.models import User
from .serializers import (
    CampaignSerializer, OpportunitySerializer, CommunityInitiativeSerializer,
    AdvocacyCampaignSerializer, PatronSerializer, ZoneSerializer, LGASerializer,
    WardSerializer, RoleDefinitionSerializer, LeaderSerializer, UserSerializer,
    RegisterSerializer
)

class NewsroomListView(generics.ListAPIView):
    queryset = Campaign.objects.filter(status='PUBLISHED')
    serializer_class = CampaignSerializer
    permission_classes = [permissions.AllowAny]

class NewsroomDetailView(generics.RetrieveAPIView):
    queryset = Campaign.objects.filter(status='PUBLISHED')
    serializer_class = CampaignSerializer
    lookup_field = 'slug'
    permission_classes = [permissions.AllowAny]

class OpportunityListView(generics.ListAPIView):
    serializer_class = OpportunitySerializer
    permission_classes = [permissions.AllowAny]

    def get_queryset(self):
        queryset = Opportunity.objects.all()
        category = self.request.query_params.get('category')
        if category:
            queryset = queryset.filter(category=category)
        return queryset

class CommunityInitiativeListView(generics.ListAPIView):
    queryset = CommunityInitiative.objects.all()
    serializer_class = CommunityInitiativeSerializer
    permission_classes = [permissions.AllowAny]

class CivicListView(generics.ListAPIView):
    queryset = Campaign.objects.filter(category='CIVIC', status='PUBLISHED')
    serializer_class = CampaignSerializer
    permission_classes = [permissions.AllowAny]

class AdvocacyListView(generics.ListAPIView):
    queryset = AdvocacyCampaign.objects.all()
    serializer_class = AdvocacyCampaignSerializer
    permission_classes = [permissions.AllowAny]

class LeadershipDirectoryView(generics.ListAPIView):
    serializer_class = LeaderSerializer
    permission_classes = [permissions.AllowAny]

    def get_queryset(self):
        return User.objects.filter(role__in=['STATE', 'ZONAL', 'LGA', 'WARD'])

class PatronListView(generics.ListAPIView):
    queryset = Patron.objects.filter(is_published=True)
    serializer_class = PatronSerializer
    permission_classes = [permissions.AllowAny]

class ImpactMetricsView(APIView):
    permission_classes = [permissions.AllowAny]

    def get(self, request):
        data = [
            {"label": "Total Members", "value": User.objects.count()},
            {"label": "Total Initiatives", "value": CommunityInitiative.objects.count()},
            {"label": "Total Opportunities", "value": Opportunity.objects.count()},
        ]
        return Response(data)

class ZoneListView(generics.ListAPIView):
    queryset = Zone.objects.all()
    serializer_class = ZoneSerializer
    permission_classes = [permissions.AllowAny]

class LGAListView(generics.ListAPIView):
    serializer_class = LGASerializer
    permission_classes = [permissions.AllowAny]

    def get_queryset(self):
        queryset = LGA.objects.all()
        zone = self.request.query_params.get('zone')
        if zone:
            queryset = queryset.filter(zone_id=zone)
        return queryset

class WardListView(generics.ListAPIView):
    serializer_class = WardSerializer
    permission_classes = [permissions.AllowAny]

    def get_queryset(self):
        queryset = Ward.objects.all()
        lga = self.request.query_params.get('lga')
        if lga:
            queryset = queryset.filter(lga_id=lga)
        return queryset

class RoleDefinitionListView(generics.ListAPIView):
    serializer_class = RoleDefinitionSerializer
    permission_classes = [permissions.AllowAny]
    
    def get_queryset(self):
        queryset = RoleDefinition.objects.all()
        tier = self.request.query_params.get('tier')
        if tier:
            queryset = queryset.filter(tier=tier)
        return queryset

class RegisterView(generics.CreateAPIView):
    serializer_class = RegisterSerializer
    permission_classes = [permissions.AllowAny]

class MeView(generics.RetrieveAPIView):
    serializer_class = UserSerializer
    permission_classes = [permissions.IsAuthenticated]

    def get_object(self):
        return self.request.user
"""

with open('rest_api/views.py', 'w') as f:
    f.write(views_code)

print("Views created.")
