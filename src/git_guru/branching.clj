(ns git-guru.branching
  (:require [git-guru.commands :refer :all])
  (:require [git-guru.questions :refer :all])
  (:require [git-guru.constants :refer :all])
  (:require [git-guru.committing :refer :all]))

; function here to determine if this name space can be reached
(defn foo-ha [] (println "hahaha"))

(defn stash! [git] (println "stashing needs to be written (and moved)"))

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
; tested and working
(defn branch-from-master! [git brch should-pull? should-rebase?]
  (if should-pull?
    (let []
      (pull! git)
      (perform-branch! git brch)
      (if should-rebase?
        (rebase! git)))
    (perform-branch! git brch)))

(defn tlb-no-changes! [git brch should-pull? should-rebase?]
  (perform-branch! git "master")
  (branch-from-master! git brch should-pull? should-rebase?))

(defn tlb-not-master! [git brch should-pull? should-rebase?]
  (commit! nil git)
  (if (has-changes? git)
    (tlb-no-changes! git brch should-pull? should-rebase?)
    (let []
      (stash! git)
      (tlb-no-changes! git brch should-pull? should-rebase?))))

(defn tlb-has-changes! [git brch should-pull? should-rebase?]
  (if (= (get-current-branch git) "master")
    (perform-branch! git brch)
    (tlb-not-master! git brch should-pull? should-rebase?)))

(defn top-level-branch [git brch should-pull? should-rebase?]
  (if (has-changes? git)
    (tlb-has-changes! git brch should-pull? should-rebase?)
    (tlb-no-changes! git brch should-pull? should-rebase?)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn get-develop-branch-name [git]
  (if (contains-branch? git "develop")
    "develop"
    "master"))

(defn do-branch! [git brch-nm force?]
  (commit! gui git)
  (if (has-changes? git)
    (print-ret "Cannot switch branch with outstanding changes" FAILURE)
    (let []
      (checkout! git (get-develop-branch-name git))
      (pull! git)
      (if (= brch-nm (get-develop-branch-name git))
        SUCCESS
        (if (contains-branch? git brch-nm)
          (let []
            (checkout! git brch-nm)
            (rebase! git (get-develop-branch-name git)))
          (let []
            (create-branch! git brch-nm)
            (checkout! git brch-nm)))))))
  ;(if (and (not force?) (has-changes? git))
  ;  (print-ret "Cannot commit branch with changes (use --force option if you wish to switch anyway)" FAILURE)))
