(ns git-guru.commands
  (:import org.eclipse.jgit.api.Git)
  (:import org.eclipse.jgit.api.CheckoutCommand)
  (:import org.eclipse.jgit.internal.storage.file.FileRepository))

; checkout! performs the action of checking out the specified branch
; tested and working
; RefNotFoundException Ref develop can not be resolved org.eclipse.jgit.api.CheckoutCommand.call (CheckoutCommand.java:241)
; indicates that the brch does not exist
; Note: develop == master
(defn checkout! [git brch]
  (.. git
      (checkout)
      (setName (str "refs/heads/" brch))
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

; creates a new branch in git
; tested and working
(defn create-branch! [git brch]
  (.. git
      (branchCreate)
      (setName brch)
      (call)))

; gets the current branch from git
; tested and working
(defn get-current-branch [git]
  (.. git (getRepository) (getBranch)))

(defn pull! [git]
  (.. git (pull) (call)))

(defn rebase! [git dest]
  (.. git (rebase) (setUpstream dest) (call)))
