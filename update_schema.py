import yaml
import sys

with open('schema.yml', 'r', encoding='utf-8') as f:
    schema = yaml.safe_load(f)

# Define the new paths (filling all the API Gaps)
new_paths = {
  "/api/v1/notifications/devices/": {
    "post": {
      "operationId": "notifications_devices_create",
      "tags": ["notifications"],
      "security": [{"jwtAuth": []}],
      "responses": {"201": {"description": "Device registered"}}
    }
  },
  "/api/v1/notifications/devices/{token}/": {
    "delete": {
      "operationId": "notifications_devices_delete",
      "parameters": [{"in": "path", "name": "token", "schema": {"type": "string"}, "required": True}],
      "tags": ["notifications"],
      "security": [{"jwtAuth": []}],
      "responses": {"204": {"description": "Device deleted"}}
    }
  },
  "/api/v1/notifications/": {
    "get": {
      "operationId": "notifications_list",
      "tags": ["notifications"],
      "security": [{"jwtAuth": []}],
      "responses": {"200": {"description": "List of notifications"}}
    }
  },
  "/api/v1/notifications/send/": {
    "post": {
      "operationId": "notifications_send_create",
      "tags": ["notifications"],
      "security": [{"jwtAuth": []}],
      "responses": {"200": {"description": "Notification sent"}}
    }
  },
  "/api/v1/members/pending/": {
    "get": {
      "operationId": "members_pending_list",
      "tags": ["members"],
      "security": [{"jwtAuth": []}],
      "responses": {"200": {"description": "List of pending members"}}
    }
  },
  "/api/v1/members/{id}/approve/": {
    "post": {
      "operationId": "members_approve_create",
      "parameters": [{"in": "path", "name": "id", "schema": {"type": "integer"}, "required": True}],
      "tags": ["members"],
      "security": [{"jwtAuth": []}],
      "responses": {"200": {"description": "Member approved"}}
    }
  },
  "/api/v1/members/{id}/reject/": {
    "post": {
      "operationId": "members_reject_create",
      "parameters": [{"in": "path", "name": "id", "schema": {"type": "integer"}, "required": True}],
      "tags": ["members"],
      "security": [{"jwtAuth": []}],
      "responses": {"200": {"description": "Member rejected"}}
    }
  },
  "/api/v1/dashboard/metrics/": {
    "get": {
      "operationId": "dashboard_metrics_retrieve",
      "tags": ["dashboard"],
      "security": [{"jwtAuth": []}],
      "responses": {"200": {"description": "Dashboard metrics"}}
    }
  },
  "/api/v1/campaigns/drafts/": {
    "get": {
      "operationId": "campaigns_drafts_list",
      "tags": ["campaigns"],
      "security": [{"jwtAuth": []}],
      "responses": {"200": {"description": "List of drafted campaigns"}}
    },
    "post": {
      "operationId": "campaigns_drafts_create",
      "tags": ["campaigns"],
      "security": [{"jwtAuth": []}],
      "responses": {"201": {"description": "Drafted campaign created"}}
    }
  },
  "/api/v1/campaigns/{id}/submit-review/": {
    "post": {
      "operationId": "campaigns_submit_review_create",
      "parameters": [{"in": "path", "name": "id", "schema": {"type": "integer"}, "required": True}],
      "tags": ["campaigns"],
      "security": [{"jwtAuth": []}],
      "responses": {"200": {"description": "Campaign submitted for review"}}
    }
  },
  "/api/v1/campaigns/{id}/publish/": {
    "post": {
      "operationId": "campaigns_publish_create",
      "parameters": [{"in": "path", "name": "id", "schema": {"type": "integer"}, "required": True}],
      "tags": ["campaigns"],
      "security": [{"jwtAuth": []}],
      "responses": {"200": {"description": "Campaign published"}}
    }
  },
  "/api/v1/meetings/": {
    "get": {
      "operationId": "meetings_list",
      "tags": ["meetings"],
      "security": [{"jwtAuth": []}],
      "responses": {"200": {"description": "List of meetings"}}
    },
    "post": {
      "operationId": "meetings_create",
      "tags": ["meetings"],
      "security": [{"jwtAuth": []}],
      "responses": {"201": {"description": "Meeting created"}}
    }
  },
  "/api/v1/meetings/{id}/attendance/": {
    "put": {
      "operationId": "meetings_attendance_update",
      "parameters": [{"in": "path", "name": "id", "schema": {"type": "integer"}, "required": True}],
      "tags": ["meetings"],
      "security": [{"jwtAuth": []}],
      "responses": {"200": {"description": "Attendance updated"}}
    }
  },
  "/api/v1/reports/community/": {
    "post": {
      "operationId": "reports_community_create",
      "tags": ["reports"],
      "security": [{"jwtAuth": []}],
      "responses": {"201": {"description": "Community report created"}}
    }
  },
  "/api/v1/reports/community/pending/": {
    "get": {
      "operationId": "reports_community_pending_list",
      "tags": ["reports"],
      "security": [{"jwtAuth": []}],
      "responses": {"200": {"description": "List of pending community reports"}}
    }
  },
  "/api/v1/reports/community/{id}/escalate/": {
    "post": {
      "operationId": "reports_community_escalate_create",
      "parameters": [{"in": "path", "name": "id", "schema": {"type": "integer"}, "required": True}],
      "tags": ["reports"],
      "security": [{"jwtAuth": []}],
      "responses": {"200": {"description": "Community report escalated"}}
    }
  },
  "/api/v1/programs/youth/": {
    "post": {
      "operationId": "programs_youth_create",
      "tags": ["programs"],
      "security": [{"jwtAuth": []}],
      "responses": {"201": {"description": "Youth program created"}}
    }
  },
  "/api/v1/programs/women/": {
    "post": {
      "operationId": "programs_women_create",
      "tags": ["programs"],
      "security": [{"jwtAuth": []}],
      "responses": {"201": {"description": "Women program created"}}
    }
  },
  "/api/v1/programs/welfare/": {
    "post": {
      "operationId": "programs_welfare_create",
      "tags": ["programs"],
      "security": [{"jwtAuth": []}],
      "responses": {"201": {"description": "Welfare program created"}}
    }
  }
}

# Update the paths dictionary
if 'paths' not in schema:
    schema['paths'] = {}
schema['paths'].update(new_paths)

# Sort paths alphabetically for cleaner output
schema['paths'] = dict(sorted(schema['paths'].items()))

class Dumper(yaml.Dumper):
    def increase_indent(self, flow=False, *args, **kwargs):
        return super().increase_indent(flow=flow, indentless=False)

with open('schema.yml', 'w', encoding='utf-8') as f:
    yaml.dump(schema, f, Dumper=Dumper, default_flow_style=False, sort_keys=False)

print("SUCCESS: Updated schema.yml with all missing API gaps.")
