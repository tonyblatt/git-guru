(ns git-guru.branching
  (:import org.eclipse.jgit.api.Git)
  (:import org.eclipse.jgit.api.CheckoutCommand)
  (:import org.eclipse.jgit.internal.storage.file.FileRepository))

; function here to determine if this name space can be reached
(defn foo-ha [] (println "hahaha"))

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

(defn create-branch! [git brch]
  (.. git
      (branchCreate)
      (setName brch)
      (call)))

(defn branch! [git dest pull])
