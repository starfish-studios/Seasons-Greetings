import os
import json

# Define the colors to process
colors = ["red", "orange", "yellow", "green", "purple"]

# Function to replace instances of "red" with the respective color
def replace_color_in_file(file_path, old_color, new_color):
    with open(file_path, "r") as file:
        content = file.read()
    # Replace the old color with the new color
    new_content = content.replace(old_color, new_color)
    return new_content

# Function to create new files with replaced content
def generate_color_variants(input_file, colors):
    # Extract base name and extension
    base_name, ext = os.path.splitext(input_file)
    original_color = "red"

    for color in colors:
        # Skip the original color file as it's already present
        if color == original_color:
            continue

        # Generate new file name
        new_file_name = f"{color}_gumdrop{ext}"

        # Replace content in the file
        new_content = replace_color_in_file(input_file, original_color, color)

        # Write to the new file
        with open(new_file_name, "w") as new_file:
            new_file.write(new_content)
        print(f"Generated file: {new_file_name}")

# Main execution
input_file = "red_gumdrop.json"  # Adjust this if the file has a different extension
if os.path.exists(input_file):
    generate_color_variants(input_file, colors)
else:
    print(f"Input file '{input_file}' not found.")
