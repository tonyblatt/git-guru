#!/bin/bash

if [ ! -f "$HOME/.bash_profile" ]; then
    echo "bash profile not found. Creating it now..."
    if touch "$HOME/.bash_profile"; then
        echo "Successfully created bash profile'."
    else
        echo "Error: Failed to create bash profile." >&2
        exit 1
    fi
else
    echo "File bash profile already exists. Skipping creation."
fi

setup_dir=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
script_dir="${setup_dir%?????}scripts"

add_scripts_to_path="export PATH=\"\$PATH:$script_dir\""
if grep -Fxq "$add_scripts_to_path" "$HOME/.bash_profile"; then
#if grep -Fxq "$add_scripts_to_path" "$script_dir/gui"; then
    echo "Skipping adding script dir to the bash profile because it was already present."
else
    printf "\n%s\n" "$add_scripts_to_path" >> "$HOME/.bash_profile"
    echo "Added script dir to bash profile."
fi