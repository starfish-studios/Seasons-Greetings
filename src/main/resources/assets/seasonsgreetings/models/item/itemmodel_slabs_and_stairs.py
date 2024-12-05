import os
import json

# Define the base JSON structures for item models
templates = {
    "block": {
        "parent": "minecraft:block/{material}"
    },
    "stairs": {
        "parent": "minecraft:block/{material}_stairs"
    },
    "slab": {
        "parent": "minecraft:block/{material}_slab"
    }
}

def create_item_models(material_name):
    # Adjust the material name for file names and models by removing the trailing 's' if necessary
    adjusted_name = material_name[:-1] if material_name.endswith("s") else material_name

    for file_type, structure in templates.items():
        # Replace placeholder {material} with the material name
        updated_structure = json.dumps(structure).replace("{material}", material_name)

        # Convert updated structure back to dictionary
        output = json.loads(updated_structure)

        # Replace "minecraft" with "seasonsgreetings" in the parent path
        output["parent"] = output["parent"].replace("minecraft", "seasonsgreetings")

        # Adjust the model paths if the material contains "bricks"
        output["parent"] = output["parent"].replace("_bricks", "_brick")

        # Adjust file name dynamically, removing the last "s" if necessary
        filename = f"{adjusted_name}_{file_type}.json"

        # Write the JSON structure to a file
        with open(filename, "w") as f:
            json.dump(output, f, indent=2)
        print(f"Generated file: {filename}")

# Input the material name
if __name__ == "__main__":
    material = input("Enter the material name: ").strip()
    if material:
        create_item_models(material)
    else:
        print("No material name provided. Exiting.")
