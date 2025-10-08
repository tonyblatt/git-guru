import sys

with open("branches.txt", 'r') as f:
    for line in f:
        line = line.replace("*", "")
        line = line.strip()
        line = line.split("/")[-1]
        if sys.argv[1] == line:
            sys.exit(0)

sys.exit(1)