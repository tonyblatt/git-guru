(ns git-guru.commands
  (:import org.eclipse.jgit.api.Git)
  (:import org.eclipse.jgit.api.CheckoutCommand)
  (:import org.eclipse.jgit.internal.storage.file.FileRepository)
  (:import org.eclipse.jgit.api.RebaseResult$Status)
  (:import org.eclipse.jgit.api.RebaseCommand$Operation)
  (:require [clojure.java.shell :refer :all]))

; Procedure for executing an external command
(defn exec-comm [comm-str]
  (let [runtime (Runtime/getRuntime)
            proc (. runtime (exec comm-str))]
        (. proc (waitFor))
        (. proc (destroy))))

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
  (let [res (.. git (rebase) (setUpstream dest) (call))]
    (if (= (. res (getStatus)) (. RebaseResult$Status STOPPED))
      (let []
        (exec-comm "git mergetool --tool=meld --no-prompt")
        (println "here now")
        (.. git (rebase) (setUpstream dest) (setOperation (. RebaseCommand$Operation CONTINUE)) (call))))))
