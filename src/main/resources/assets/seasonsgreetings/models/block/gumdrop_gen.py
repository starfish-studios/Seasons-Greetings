import os
import json

# Define the base name to replace and the colors to generate variants
base_name = "red_gumdrop"
colors = ["red", "orange", "yellow", "green", "purple"]

# Function to replace the base name in the file content
def replace_base_name(content, old_name, new_name):
    return content.replace(old_name, new_name)

# Function to create new files based on the template files
def generate_variants_from_files(base_name, colors):
    # List all files in the current directory
    for file_name in os.listdir():
        # Process only files containing the base name
        if base_name in file_name:
            # Read the content of the existing file
            with open(file_name, "r") as file:
                content = file.read()

            # Generate variants for each color
            for color in colors:
                # Skip if the file already exists
                new_file_name = file_name.replace(base_name, f"{color}_gumdrop")
                if os.path.exists(new_file_name):
                    print(f"File '{new_file_name}' already exists. Skipping.")
                    continue

                # Replace the base name in content and save to a new file
                new_content = replace_base_name(content, base_name, f"{color}_gumdrop")
                with open(new_file_name, "w") as new_file:
                    new_file.write(new_content)
                print(f"Generated file: {new_file_name}")

# Main execution
if __name__ == "__main__":
    generate_variants_from_files(base_name, colors)
