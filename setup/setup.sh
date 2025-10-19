#!/bin/bash

if [[ "$SHELL" == *zsh ]]; then
  profile_location="$HOME/.zshrc"
elif [ -f "$HOME/.bash_profile" ]; then
  profile_location="$HOME/.bash_profile"
elif [ -f "$HOME/.bashrc" ]; then
  profile_location="$HOME/.bashrc"
elif [ -f "$HOME/.bash_login" ]; then
  profile_location="$HOME/.bash_login"
else
  profile_location="$HOME/.profile"
fi

setup_dir=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
script_dir="${setup_dir%?????}scripts"

add_scripts_to_path="export PATH=\"\$PATH:$script_dir\""
if grep -Fxq "$add_scripts_to_path" "$profile_location"; then
    echo "Skipping adding script dir to the bash profile because it was already present."
else
    printf "\n%s\n" "$add_scripts_to_path" >> "$profile_location"
    echo "Added script dir to bash profile."
fi

add_branch_name_completion_command="source $script_dir/branch-name-completion.bash"
echo "$add_branch_name_completion_command"
if grep -Fxq "$add_branch_name_completion_command" "$profile_location"; then
    echo "Skipping adding branch name completion to the bash profile because it was already present."
else
    printf "\n%s\n" "$add_branch_name_completion_command" >> "$profile_location"
    echo "Added branch name completion bash."
fi