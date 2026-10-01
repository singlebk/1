import os

search_dir = "c:/Users/hp/Downloads/kconnect-main"
count = 0
for root, dirs, files in os.walk(search_dir):
    if "venv" in root or ".git" in root or "__pycache__" in root:
        continue
    for file in files:
        if not file.endswith(".py") and not file.endswith(".html"):
            continue
        path = os.path.join(root, file)
        try:
            with open(path, 'r', encoding='utf-8') as f:
                lines = f.readlines()
                for i, line in enumerate(lines):
                    if "APPROVED" in line:
                        print(f"{path}:{i+1}:{line.strip()}")
                        count += 1
                        if count > 50:
                            break
        except Exception:
            pass
        if count > 50:
            break
    if count > 50:
        break
