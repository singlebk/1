import os

serializers_code = """
from rest_framework import serializers
from campaigns.models import Campaign
from core.models import Opportunity, CommunityInitiative, AdvocacyCampaign, Patron
from leadership.models import Zone, LGA, Ward, RoleDefinition
from staff.models import User
from django.contrib.auth import get_user_model

User = get_user_model()

class CampaignSerializer(serializers.ModelSerializer):
    date = serializers.DateTimeField(source='published_at')
    excerpt = serializers.CharField(source='subheadline')
    body = serializers.CharField(source='content')
    image = serializers.ImageField(source='featured_image')

    class Meta:
        model = Campaign
        fields = ['slug', 'title', 'date', 'excerpt', 'body', 'image']

class OpportunitySerializer(serializers.ModelSerializer):
    organization = serializers.CharField(source='provider')
    external_apply_link = serializers.URLField(source='application_link')

    class Meta:
        model = Opportunity
        fields = ['title', 'category', 'organization', 'deadline', 'description', 'external_apply_link']

class CommunityInitiativeSerializer(serializers.ModelSerializer):
    class Meta:
        model = CommunityInitiative
        fields = ['title', 'description', 'status']

class AdvocacyCampaignSerializer(serializers.ModelSerializer):
    class Meta:
        model = AdvocacyCampaign
        fields = '__all__'

class PatronSerializer(serializers.ModelSerializer):
    class Meta:
        model = Patron
        fields = '__all__'

class ZoneSerializer(serializers.ModelSerializer):
    class Meta:
        model = Zone
        fields = ['id', 'name']

class LGASerializer(serializers.ModelSerializer):
    class Meta:
        model = LGA
        fields = ['id', 'name', 'zone']

class WardSerializer(serializers.ModelSerializer):
    class Meta:
        model = Ward
        fields = ['id', 'name', 'lga']

class RoleDefinitionSerializer(serializers.ModelSerializer):
    class Meta:
        model = RoleDefinition
        fields = '__all__'

class LeaderSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id', 'first_name', 'last_name', 'role', 'role_definition', 'photo']

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id', 'username', 'email', 'first_name', 'last_name', 'phone', 'bio', 'photo', 'gender', 'role', 'zone', 'lga', 'ward', 'role_definition', 'facebook_verified']

class RegisterSerializer(serializers.ModelSerializer):
    password = serializers.CharField(write_only=True)

    class Meta:
        model = User
        fields = ['username', 'password', 'email', 'first_name', 'last_name', 'phone', 'gender', 'photo', 'zone', 'lga', 'ward', 'role_definition', 'facebook_verified']

    def create(self, validated_data):
        user = User.objects.create_user(**validated_data)
        return user
"""

with open('rest_api/serializers.py', 'w') as f:
    f.write(serializers_code)

print("Serializers created.")
