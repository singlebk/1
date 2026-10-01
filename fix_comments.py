import os
import re

path = r'c:\Users\hp\Downloads\kconnect-main\core\templates\core\patrons.html'
with open(path, 'r', encoding='utf-8') as f:
    content = f.read()

# Replace multi-line and single-line Django comments with standard HTML comments
content = re.sub(r'\{#(.*?)#\}', lambda m: '<!--' + m.group(1) + '-->', content, flags=re.DOTALL)

with open(path, 'w', encoding='utf-8') as f:
    f.write(content)
print('Fixed comments in patrons.html')
