# git-guru

Utility scripts for automating common git action sets

## Setup

1. Clone Git-guru (git clone https://github.com/tonyblatt/git-guru.git)
2. Execute `setup.sh` in the setup directory

## Scripts

### checkout

Checks out the specified branch. More specifically, the script follows these steps:
1. checkout the primary branch
2. pulls the primary branch
3. checks out the branch (creates the branch if it doesn't already exist.)

The script also has 2 available flags: -p (--primary-branch) and -t (--tag). The primary branch flag allows you to set an alternate primary branch. The tag flag allows you to check out from a tag instead of a branch. Both the tag and the primary branch flags require that the tag/branch provided already exist. Likewise, the tag flag requires that the branch provided not already exist.

#### Usage

```shell
checkout branch_name
checkout -p <primary-branch-name> branch_name
checkout --primary-branch <primary-branch-name> branch_name
checkout -t <tag-name> branch_name
checkout --tag <tag-name> branch_name
```

### complete_branches

Deletes all branches specified as parameters. Protects from deleting the repository specified primary branch and other commonly specified primary branch names (develop, main, master). Switches you off the current branch if that branch is specified.

#### Usage

```shell
complete_branches a_branch another_branch a_third_branch
```

### finish_branch_and_new

Checks out the branch as specified by the parameter. Deletes the branch you are currently on. (Same as `checkout <new branch name>` followed by `complete_branches <the branch you started on>`)

#### Usage

```shell
finish_branch_and_new next_branch_to_work_on
```

### pave_branch

Deletes all saved data on the current branch before creating a new branch with the same name as the current one.

#### Usage

```shell
pave_branch
```

### push

Pushes the current branch up to the remote repository. If this is the first time the branch has been pushed, open the primary web browser and navigate to the start of the PR process.

```shell
push
```

### gui

Just `git gui`

## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
