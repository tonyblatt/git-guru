(ns git-guru.branching
  (:import org.eclipse.jgit.api.Git)
  (:import org.eclipse.jgit.api.CheckoutCommand)
  (:import org.eclipse.jgit.internal.storage.file.FileRepository))

; function here to determine if this name space can be reached
(defn foo-ha [] (println "hahaha"))

(defn pull! [git] (println "pulling needs to be filled in (and moved)"))

(defn rebase! [git] (println "rebasing needs to be written (and moved)"))

; checkout! performs the action of checking out the specified branch
; tested and working
; RefNotFoundException Ref develop can not be resolved org.eclipse.jgit.api.CheckoutCommand.call (CheckoutCommand.java:241)
; indicates that the brch does not exist
; Note: develop == master
(defn checkout! [git brch]
  (.. git
      (checkout)
      (setName brch)
      (call)))

; utility funciton for extracting the name from a ref
; tested and working
(defn extract-name [r]
  (. r getName))

; lists the available branches (in string form) contained in git
; Note: branches come back in the form "refs/heads/BRANCH_NAME"
; tested and working
(defn list-branches [git]
  (map extract-name
       (.. git
           (branchList)
           (call))))

; figures out if git contains brch
; tested and working
(defn contains-branch? [git brch]
  (< 0
     (count (filter (fn [x]
                      (= (str "refs/heads/" brch) x))
                    (list-branches git)))))

; creates a new branch in git
; tested and working
(defn create-branch! [git brch]
  (.. git
      (branchCreate)
      (setName brch)
      (call)))

; takes care of basic branching logistics
; tested and working
(defn perform-branch! [git brch]
  (if (contains-branch? git brch)
    (checkout! git brch)
    (let []
      (create-branch! git brch)
      (checkout! git brch))))

; assumes that you are on master and wish to go to a different branch
; paramter rundown:
; git (git instance) - the git instance to use
; brch (string) - the name of the branch you wish to go to
; should-pull (boolean) - we should pull on the master branch.
; should-rebase (boolean) - we should rebase on the branch we move to.
(defn branch-from-master! [git brch should-pull? should-rebase?]
  (if should-pull?
    (let []
      (pull! git)
      (perform-branch! git brch)
      (if should-rebase?
        (rebase! git)))
    (perform-branch! git brch)))

; (true/false) the current branch has uncommited changes
; tested and working
(defn has-changes? [git]
  (.. git (status) (call) (hasUncommittedChanges)))
