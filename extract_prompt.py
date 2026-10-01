import json

transcript_path = r'C:\Users\hp\.gemini\antigravity\brain\3ba9c211-66a2-4f78-86b2-3fce318f4748\.system_generated\logs\transcript_full.jsonl'
output_path = r'C:\Users\hp\Downloads\kconnect-main\master_prompt.txt'

with open(transcript_path, 'r', encoding='utf-8') as f:
    for line in f:
        data = json.loads(line)
        if data.get('type') == 'USER_INPUT' and 'KPN WEBSITE 2.0 — MASTER ENGINEERING' in data.get('content', ''):
            with open(output_path, 'w', encoding='utf-8') as out:
                out.write(data['content'])
            print(f"Master prompt extracted to {output_path}")
            break
