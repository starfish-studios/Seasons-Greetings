import os
import json

# Define base JSON structures for blockstates
templates = {
    "block": {
        "variants": {
            "": {
                "model": "minecraft:block/{material}"
            }
        }
    },
    "slab": {
        "variants": {
            "type=bottom": {
                "model": "minecraft:block/{material}_slab"
            },
            "type=double": {
                "model": "minecraft:block/{material}"
            },
            "type=top": {
                "model": "minecraft:block/{material}_slab_top"
            }
        }
    },
        "stairs": {
        "variants": {
            "facing=east,half=bottom,shape=inner_left": {
                "model": "minecraft:block/{material}_stairs_inner",
                "uvlock": True,
                "y": 270
            },
            "facing=east,half=bottom,shape=inner_right": {
                "model": "minecraft:block/{material}_stairs_inner"
            },
            "facing=east,half=bottom,shape=outer_left": {
                "model": "minecraft:block/{material}_stairs_outer",
                "uvlock": True,
                "y": 270
            },
            "facing=east,half=bottom,shape=outer_right": {
                "model": "minecraft:block/{material}_stairs_outer"
            },
            "facing=east,half=bottom,shape=straight": {
                "model": "minecraft:block/{material}_stairs"
            },
            "facing=east,half=top,shape=inner_left": {
                "model": "minecraft:block/{material}_stairs_inner",
                "uvlock": True,
                "x": 180
            },
            "facing=east,half=top,shape=inner_right": {
                "model": "minecraft:block/{material}_stairs_inner",
                "uvlock": True,
                "x": 180,
                "y": 90
            },
            "facing=east,half=top,shape=outer_left": {
                "model": "minecraft:block/{material}_stairs_outer",
                "uvlock": True,
                "x": 180
            },
            "facing=east,half=top,shape=outer_right": {
                "model": "minecraft:block/{material}_stairs_outer",
                "uvlock": True,
                "x": 180,
                "y": 90
            },
            "facing=east,half=top,shape=straight": {
                "model": "minecraft:block/{material}_stairs",
                "uvlock": True,
                "x": 180
            },
            "facing=north,half=bottom,shape=inner_left": {
                "model": "minecraft:block/{material}_stairs_inner",
                "uvlock": True,
                "y": 180
            },
            "facing=north,half=bottom,shape=inner_right": {
                "model": "minecraft:block/{material}_stairs_inner",
                "uvlock": True,
                "y": 270
            },
            "facing=north,half=bottom,shape=outer_left": {
                "model": "minecraft:block/{material}_stairs_outer",
                "uvlock": True,
                "y": 180
            },
            "facing=north,half=bottom,shape=outer_right": {
                "model": "minecraft:block/{material}_stairs_outer",
                "uvlock": True,
                "y": 270
            },
            "facing=north,half=bottom,shape=straight": {
                "model": "minecraft:block/{material}_stairs",
                "uvlock": True,
                "y": 270
            },
            "facing=north,half=top,shape=inner_left": {
                "model": "minecraft:block/{material}_stairs_inner",
                "uvlock": True,
                "x": 180,
                "y": 270
            },
            "facing=north,half=top,shape=inner_right": {
                "model": "minecraft:block/{material}_stairs_inner",
                "uvlock": True,
                "x": 180
            },
            "facing=north,half=top,shape=outer_left": {
                "model": "minecraft:block/{material}_stairs_outer",
                "uvlock": True,
                "x": 180,
                "y": 270
            },
            "facing=north,half=top,shape=outer_right": {
                "model": "minecraft:block/{material}_stairs_outer",
                "uvlock": True,
                "x": 180
            },
            "facing=north,half=top,shape=straight": {
                "model": "minecraft:block/{material}_stairs",
                "uvlock": True,
                "x": 180,
                "y": 270
            },
            "facing=south,half=bottom,shape=inner_left": {
                "model": "minecraft:block/{material}_stairs_inner"
            },
            "facing=south,half=bottom,shape=inner_right": {
                "model": "minecraft:block/{material}_stairs_inner",
                "uvlock": True,
                "y": 90
            },
            "facing=south,half=bottom,shape=outer_left": {
                "model": "minecraft:block/{material}_stairs_outer"
            },
            "facing=south,half=bottom,shape=outer_right": {
                "model": "minecraft:block/{material}_stairs_outer",
                "uvlock": True,
                "y": 90
            },
            "facing=south,half=bottom,shape=straight": {
                "model": "minecraft:block/{material}_stairs",
                "uvlock": True,
                "y": 90
            },
            "facing=south,half=top,shape=inner_left": {
                "model": "minecraft:block/{material}_stairs_inner",
                "uvlock": True,
                "x": 180,
                "y": 90
            },
            "facing=south,half=top,shape=inner_right": {
                "model": "minecraft:block/{material}_stairs_inner",
                "uvlock": True,
                "x": 180,
                "y": 180
            },
            "facing=south,half=top,shape=outer_left": {
                "model": "minecraft:block/{material}_stairs_outer",
                "uvlock": True,
                "x": 180,
                "y": 90
            },
            "facing=south,half=top,shape=outer_right": {
                "model": "minecraft:block/{material}_stairs_outer",
                "uvlock": True,
                "x": 180,
                "y": 180
            },
            "facing=south,half=top,shape=straight": {
                "model": "minecraft:block/{material}_stairs",
                "uvlock": True,
                "x": 180,
                "y": 90
            },
            "facing=west,half=bottom,shape=inner_left": {
                "model": "minecraft:block/{material}_stairs_inner",
                "uvlock": True,
                "y": 90
            },
            "facing=west,half=bottom,shape=inner_right": {
                "model": "minecraft:block/{material}_stairs_inner",
                "uvlock": True,
                "y": 180
            },
            "facing=west,half=bottom,shape=outer_left": {
                "model": "minecraft:block/{material}_stairs_outer",
                "uvlock": True,
                "y": 90
            },
            "facing=west,half=bottom,shape=outer_right": {
                "model": "minecraft:block/{material}_stairs_outer",
                "uvlock": True,
                "y": 180
            },
            "facing=west,half=bottom,shape=straight": {
                "model": "minecraft:block/{material}_stairs",
                "uvlock": True,
                "y": 180
            },
            "facing=west,half=top,shape=inner_left": {
                "model": "minecraft:block/{material}_stairs_inner",
                "uvlock": True,
                "x": 180,
                "y": 180
            },
            "facing=west,half=top,shape=inner_right": {
                "model": "minecraft:block/{material}_stairs_inner",
                "uvlock": True,
                "x": 180,
                "y": 270
            },
            "facing=west,half=top,shape=outer_left": {
                "model": "minecraft:block/{material}_stairs_outer",
                "uvlock": True,
                "x": 180,
                "y": 180
            },
            "facing=west,half=top,shape=outer_right": {
                "model": "minecraft:block/{material}_stairs_outer",
                "uvlock": True,
                "x": 180,
                "y": 270
            },
            "facing=west,half=top,shape=straight": {
                "model": "minecraft:block/{material}_stairs",
                "uvlock": True,
                "x": 180,
                "y": 180
            }
        }
    }

}

def create_blockstates(material_name):
    # Adjust material references to singular form for stairs and slab suffixes
    def singularize(word):
        return word[:-1] if word.endswith("s") and not word.endswith("_slabs") else word

    for file_type, structure in templates.items():
        # Replace placeholder {material} with the modified material name in content
        updated_structure = json.dumps(structure).replace("{material}", singularize(material_name))

        # Convert updated structure back to dictionary
        output = json.loads(updated_structure)

        # Replace "minecraft" with "seasonsgreetings" in all model paths
        for variant, data in output["variants"].items():
            if isinstance(data, dict) and "model" in data:
                data["model"] = data["model"].replace("minecraft", "seasonsgreetings")

        # Adjust file name dynamically
        filename = (
            f"{material_name}_{file_type}.json" if file_type != "block"
            else f"{material_name}_block.json"
        )

        # Write the JSON structure to a file
        with open(filename, "w") as f:
            json.dump(output, f, indent=2)
        print(f"Generated file: {filename}")

# Input the material name
if __name__ == "__main__":
    material = input("Enter the material name: ").strip()
    if material:
        create_blockstates(material)
    else:
        print("No material name provided. Exiting.")