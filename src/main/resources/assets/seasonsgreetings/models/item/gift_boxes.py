import json
import os

#################################################################

def dict_replace_value(d, old, new):
    """Replace values in a dictionary recursively."""
    x = {}
    for k, v in d.items():
        if isinstance(v, dict):
            v = dict_replace_value(v, old, new)
        elif isinstance(v, list):
            v = list_replace_value(v, old, new)
        elif isinstance(v, str):
            if old is not None and new is not None:
                v = v.replace(old, new)
        x[k] = v
    return x

def list_replace_value(l, old, new):
    """Replace values in a list recursively."""
    x = []
    for e in l:
        if isinstance(e, list):
            e = list_replace_value(e, old, new)
        elif isinstance(e, dict):
            e = dict_replace_value(e, old, new)
        elif isinstance(e, str):
            if old is not None and new is not None:
                e = e.replace(old, new)
        x.append(e)
    return x

#################################################################

def main():
    # Define colors
    colors = [
        "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink",
        "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"
    ]

    # Base file path
    base_file_path = "red_gift_box.json"

    # Check if the base file exists
    if not os.path.exists(base_file_path):
        print(f"Error: Base file '{base_file_path}' not found.")
        input("Press Enter to exit...")
        return

    # Load the base JSON data
    with open(base_file_path, "r") as base_file:
        base_data = json.load(base_file)

    # Output directory
    output_dir = "gift_boxes"
    os.makedirs(output_dir, exist_ok=True)

    # Process colors
    for color in colors:
        # Replace "red_gift" with the current color
        new_data = dict_replace_value(base_data, "red_gift", f"{color}_gift")

        # Generate new file name
        new_file_name = base_file_path.replace("red_gift", color)
        new_file_path = os.path.join(output_dir, new_file_name)

        # Save the modified data to a new file
        with open(new_file_path, "w") as output_file:
            json.dump(new_data, output_file, indent=4)

    print(f"All gift box variants have been successfully created in the '{output_dir}' directory.")
    input("Press Enter to exit...")  # Keeps the console open after execution

#################################################################

if __name__ == "__main__":
    main()
