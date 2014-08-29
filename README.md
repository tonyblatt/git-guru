# git-guru

A Clojure application designed to aid the user in using Git.

## Usage

FIXME

## What each script does

1. Commit

if the branch has changes
	if the branch is develop
		print "develop cannot accept changes"
		return failure
	else
		call user defined script (default to "git gui")
		if the branch still has changes
			check if user want to stash changes
			return success
		else
			return success
		end if
	end if
else
	print "nothing to commit"
	return success
end if

2. Rebase

Branch to the current branch

3. Branch

Commit
checkout develop
pull
if input branch is not develop
	checkout input branch
	rebase
end if

4. Push

if the branch is not develop
	Commit
	if this branch has been committed to before
		if ok to push according to a user defined push script (defaults to checking a review board instance)
			push to the root repository
		else
			push to the user defined code review repository
		end if
	else
		push to the user defined code review repository
	end if
else
	print "You cannot push from the develop branch"
end if

## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
