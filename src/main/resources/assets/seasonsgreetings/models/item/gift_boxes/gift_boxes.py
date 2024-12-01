import json
import os

#################################################################

def dict_replace_value(d, old_gift, new_gift, old_bow, new_bow):
    x = {}
    for k, v in d.items():
        if isinstance(v, dict):
            v = dict_replace_value(v, old_gift, new_gift, old_bow, new_bow)
        elif isinstance(v, list):
            v = list_replace_value(v, old_gift, new_gift, old_bow, new_bow)
        elif isinstance(v, str):
            # Replace `old_gift` and `old_bow` with `new_gift` and `new_bow`
            v = v.replace(f"{old_gift}_gift", f"{new_gift}_gift")
            v = v.replace(f"{old_bow}_bow", f"{new_bow}_bow")
            v = v.replace("{namespace}", f"{namespace}")
        x[k] = v
    return x

def list_replace_value(l, old_gift, new_gift, old_bow, new_bow):
    x = []
    for e in l:
        if isinstance(e, list):
            e = list_replace_value(e, old_gift, new_gift, old_bow, new_bow)
        elif isinstance(e, dict):
            e = dict_replace_value(e, old_gift, new_gift, old_bow, new_bow)
        elif isinstance(e, str):
            # Replace `old_gift` and `old_bow` with `new_gift` and `new_bow`
            e = e.replace(f"{old_gift}_gift", f"{new_gift}_gift")
            e = e.replace(f"{old_bow}_bow", f"{new_bow}_bow")
            e = e.replace("{namespace}", f"{namespace}")
        x.append(e)
    return x

#################################################################

colors = [
    "orange",
    "magenta",
    "light_blue",
    "yellow",
    "lime",
    "pink",
    "gray",
    "light_gray",
    "cyan",
    "purple",
    "blue",
    "brown",
    "green",
    "red",
    "black",
    "white"
]

namespace = "another_furniture"

cwd = os.getcwd()

process_specific = input("Process specific file (or enter to skip): ")

if process_specific:
    files_to_process = [process_specific + ".json"]
else:
    # Process files containing "gift" and "bow" in the name
    files_to_process = [
        f for f in os.listdir(cwd)
        if os.path.isfile(os.path.join(cwd, f)) and "gift" in f and "bow" in f
    ]

for file in files_to_process:
    print(f"Processing file: {file}")
    # Extract file name components
    file_name_parts = file.split("_")
    
    if len(file_name_parts) < 4 or "gift" not in file_name_parts[1] or "bow" not in file_name_parts[-1]:
        print(f"Skipping file: {file} (invalid format)")
        continue

    gift_color = file_name_parts[0]  # Extract current gift color
    bow_color = file_name_parts[-2]  # Extract current bow color

    with open(file, "r") as f:
        data = json.load(f)

    # Generate all color combinations
    for new_gift_color in colors:
        for new_bow_color in colors:
            # Replace `gift_color` and `bow_color` in content
            new_data = dict_replace_value(data, gift_color, new_gift_color, bow_color, new_bow_color)
            
            # Create the new file name
            new_file_name = f"{new_gift_color}_gift_{new_bow_color}_bow.json"

            # Write the modified content to a new file
            with open(new_file_name, "w") as new_file:
                new_file.write(json.dumps(new_data, indent=4))
            print(f"Created file: {new_file_name}")
