import glob
for file in glob.glob('**/campaigns.html', recursive=True):
    print(file)
