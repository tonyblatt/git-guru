
_tab_complete()
{
  prev_index=$((COMP_CWORD - 1))
  prev_word=${COMP_WORDS[prev_index]}
  local current_word=${COMP_WORDS[COMP_CWORD]}
  first_letter=${current_word:0:1}
  if [ "$prev_word" = "-t" ] || [ "$prev_word" = "--tag" ]; then
    complete_string=$(git tag | tr '\n' ' ')
  elif [ "-" = "$first_letter" ]; then
      complete_string="-t --tag -p --primary-branch -h --help"
  else
    branch_list=$(git branch)
    complete_string=$(echo "$branch_list" | tr '\n' ' ' | tr -d '*' | tr -s ' ' | sed 's/^ *//')
  fi
  COMPREPLY=($(compgen -W "$complete_string" -- "$current_word"))
}

complete -F _tab_complete "checkout"