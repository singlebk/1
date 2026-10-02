import urllib.request
import json
import ssl

ctx = ssl.create_default_context()
ctx.check_hostname = False
ctx.verify_mode = ssl.CERT_NONE

url = "https://api.github.com/repos/singlebk/1/actions/runs"
req = urllib.request.Request(url, headers={"User-Agent": "Mozilla/5.0"})
try:
    with urllib.request.urlopen(req, context=ctx) as response:
        data = json.loads(response.read().decode())
        runs = data.get("workflow_runs", [])
        if runs:
            run_id = runs[0]["id"]
            jobs_url = runs[0]["jobs_url"]
            with urllib.request.urlopen(urllib.request.Request(jobs_url, headers={"User-Agent": "Mozilla/5.0"}), context=ctx) as j_resp:
                j_data = json.loads(j_resp.read().decode())
                jobs = j_data.get("jobs", [])
                if jobs:
                    job_id = jobs[0]["id"]
                    log_url = f"https://api.github.com/repos/singlebk/1/actions/jobs/{job_id}/logs"
                    log_req = urllib.request.Request(log_url, headers={"User-Agent": "Mozilla/5.0"})
                    try:
                        with urllib.request.urlopen(log_req, context=ctx) as l_resp:
                            log_content = l_resp.read().decode('utf-8')
                            errors = [line for line in log_content.split('\n') if 'e: ' in line or 'FAILED' in line]
                            print("ERRORS FOUND:")
                            for e in errors[-50:]:  # Print last 50 matches
                                print(e.strip())
                    except Exception as e:
                        print("Could not fetch log directly:", e)
except Exception as e:
    print("Error:", e)
