
_branch_name_complete()
{
  branch_list=$(git branch)
  branch_string=$(echo "$branch_list" | tr '\n' ' ' | tr -d '*' | tr -s ' ' | sed 's/^ *//')
    local current_word=${COMP_WORDS[COMP_CWORD]}
    COMPREPLY=($(compgen -W "$branch_string" -- "$current_word"))
}

complete -F _branch_name_complete "complete_branches"
