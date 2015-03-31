# git-guru

A Clojure application designed to aid the user in using Git.

## Setup

1. Clone Git-guru to ~/bin/ (git clone https://github.com/tonyblatt/git-guru.git)
2. Add ~/bin/git-guru/scripts to your path
3. install wget, git and git-gui on your cygwin
3.1 from the windows command pompt, execute: \path\to\cygwin\installer -q -P wget,git,git-gui
4. Install whatever git mergetool you would like to use
5. Edit ~/bin/git-guru/store/settings.txt merge-tool to be your preferred merge tool (meld is the default)
6. execute install-git-guru in git-guru/scripts
7. add git-guru/scripts to your system path

## Usage

### Commit
#### Description

Commits your code to the current branch

#### Command

commit

#### What the Script Does

1. Checks to make sure you are not currently on develop/master
2. Brings up git gui for you

### Branch
#### Description

Move from one branch to another.

#### Command

branch BRANCH_NAME

#### What the Script Does

1. Commits your code (see the commit script for how this works)
2. Prevents you from switching branches if you have code to commit and are not on develop/master
3. Switches you to develop/master
4. Executes a git pull
5. If BRANCH_NAME is develop/master, exits
6. If BRANCH_NAME exists, switches to and rebases (this is the git rebase not the script rebase) that branch, otherwise (BRANCH_NAME does not exist), creates BRANCH_NAME and switches to it

### Rebase
#### Description

Rebases the current branch to the most current code from the server. This is the same as executing 'branch CURRENT_BRANCH'.

#### Command

rebase

#### What the Script Does

1. Commits your code (see the commit script for how this works)
2. Prevents you from switching branches if you have code to commit and are not on develop/master
3. Stores the CURRENT_BRANCH as BRANCH_NAME
4. Switches you to develop/master
5. Executes a git pull
6. Switches to BRANCH_NAME
7. Rebases BRANCH_NAME

### Push
#### Description

Not yet implemented.

#### Command

#### What the Script Does

=================================
Developer Notes
=================================
## cases

1. commit (necessary)
2. rebase (necessary)
3. rebase --all -> scripts not ready for yet
4. branch BRANCH_NAME (necessary) -> top level
5. branch BRANCH_NAME --rebase
6. branch BRANCH_NAME --no-pull
7. push (necessary) -> to level
8. push --update

## What each script does

1. Commit

Commits changes to the local repository. Checks performed:

1. that we are not on develop
2. that there are changes to be committed
3. check for previous commits to branch

Actions performed:

1. commit everything (commit)
2. commit specific file (commit FILE_NAME)
3. open gui (commit --gui, calls user editable script which executes git gui)
4. always add all files when appropriate

if branch != develop
	if there are changes
		if there was a previous commit
			if user requested gui
				run user defined script
			else
				amend previous commit
			end if
		else
			if user requested gui
				run user defined script
			else
				create new commit
			end if
		end if
		return success
	else
		print “there are no changes to commit”
		return success
	end if
else
	print “you cannot commit to develop”
	return failure
end if

2. Rebase

Branch to the current branch
(branch CURRENT_BRANCH --rebase)

3. Branch

Checks performed:

1. switching to develop
2. switch to branch already exists
3. should rebase
4. check if changes exist
5. should pull

Future:

1. similar branch name exists

Actions performed:

1. branch (branch BRANCH_NAME)
2. branch and rebase (branch BRANCH_NAME --rebase)
3. branch no pull (branch BRANCH_NAME --no-pull; does not do a pull of rebase)

define perform-branch: (perform-branch!)
	if requested branch exists (contains-branch?)
		switch to requested branch (checkout!)
		return success
	else
		create requested branch (create-branch!)
		switch to requested branch (checkout!)
		return success
	end if

define branch-from-develop:
	if should pull?
		pull
		perform-branch to requested branch (perform-branch!)
		if should rebase?
			rebase
		end if
	else
		perform-branch to requested branch (perform-branch!)
	end if

if branch has no changes (has-changes?)
	perform-branch to develop (perform-branch!)
	branch-from-develop (branch-from-master!)
else
	if branch is develop
		perform-branch to requested branch (perform-branch!)
  else
	  commit
		if there are no changes anymore (has-changes?)
			perform-branch to develop (perform-branch!)
			perform-branch-from-develop (branch-from-master!)
		else
			should-stash?
			perform-branch to develop (perform-branch!)
			perform-branch-from-develop (branch-from-master!)
		end if
	end if
end if

4. Push

Checks performed:

1. check if branch is up to date
2. check if branch has ship it
3. check if there are any local changes to commit
4. check if there is already a review board post from this branch
5. check if there are changes to the local branch
6. check if rebase had any impact

Actions performed:

1. push (push) (description: sends to review board, updates review board, applies patch)
2. update specific review (push --update)

Pseudo-code:

if commit == success
	if there are still changes to the branch
		print “cannot push with uncommitted changes”
	else
		rebase
		if rebase had an impact
			print “update from server made changes. please retest before pushing.”
		else
			if branch has already been pushed from
				if the user requested an update or review board does not have a shippit
					update review on review board
					print “need to publish changes to have code review done”
				else
					download diff and push diff to server -- apply patch
					finish the branch
				end if
			else
				post changes to review board
			print out review number
				print “need to publish changes to have code review done”
			end if
		end if
	end if
else
	print “cannot push from this branch”
end if

## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
