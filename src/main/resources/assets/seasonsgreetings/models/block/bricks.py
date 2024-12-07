import os
import json

# Define the base JSON structures from the provided files
templates = {
    "block": {
        "parent": "minecraft:block/cube_all",
        "textures": {
            "all": "minecraft:block/{material}"
        }
    },
    "slab": {
        "parent": "minecraft:block/slab",
        "textures": {
            "bottom": "minecraft:block/{material}",
            "side": "minecraft:block/{material}",
            "top": "minecraft:block/{material}"
        }
    },
    "slab_top": {
        "parent": "minecraft:block/slab_top",
        "textures": {
            "bottom": "minecraft:block/{material}",
            "side": "minecraft:block/{material}",
            "top": "minecraft:block/{material}"
        }
    },
    "stairs": {
        "parent": "minecraft:block/stairs",
        "textures": {
            "bottom": "minecraft:block/{material}",
            "side": "minecraft:block/{material}",
            "top": "minecraft:block/{material}"
        }
    },
    "stairs_inner": {
        "parent": "minecraft:block/inner_stairs",
        "textures": {
            "bottom": "minecraft:block/{material}",
            "side": "minecraft:block/{material}",
            "top": "minecraft:block/{material}"
        }
    },
    "stairs_outer": {
        "parent": "minecraft:block/outer_stairs",
        "textures": {
            "bottom": "minecraft:block/{material}",
            "side": "minecraft:block/{material}",
            "top": "minecraft:block/{material}"
        }
    }
}

def create_files(material_name):
    # Check if the material ends with 's' and adjust for slabs/stairs
    adjusted_name = material_name[:-1] if material_name.endswith("s") else material_name

    for file_type, structure in templates.items():
        # Check if the "block" file already exists
        if file_type == "block":
            block_filename = f"{material_name}.json"
            alt_block_filename = f"{material_name}_block.json"
            if os.path.exists(block_filename) or os.path.exists(alt_block_filename):
                print(f"File '{block_filename}' or '{alt_block_filename}' already exists. Skipping block file generation.")
                continue

            # Use original material name for the block file
            output_name = material_name
        else:
            # Use adjusted name for slabs and stairs
            output_name = adjusted_name

        # Replace placeholder {material} in the template
        updated_structure = json.dumps(structure).replace("{material}", output_name)
        
        # Convert JSON string to a dictionary
        output = json.loads(updated_structure)
        
        # Modify the texture paths to use "seasonsgreetings" instead of "minecraft"
        for key in output.get("textures", {}):
            output["textures"][key] = output["textures"][key].replace("minecraft", "seasonsgreetings")
        
        # Determine the filename for output
        filename = f"{output_name}_{file_type}.json" if file_type != "block" else f"{material_name}.json"
        
        # Write the updated JSON to a file
        with open(filename, "w") as f:
            json.dump(output, f, indent=2)
        print(f"File '{filename}' created.")

# Input the material name
if __name__ == "__main__":
    material = input("Enter the material name: ").strip()
    if material:
        create_files(material)
    else:
        print("No material name provided. Exiting.")
