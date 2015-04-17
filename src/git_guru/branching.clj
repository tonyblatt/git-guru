(ns git-guru.branching
  (:require [git-guru.commands :refer :all])
  (:require [git-guru.questions :refer :all])
  (:require [git-guru.constants :refer :all])
  (:require [git-guru.committing :refer :all]))

; function here to determine if this name space can be reached
(defn foo-ha [] (println "hahaha"))

(defn get-develop-branch-name [git]
  (if (contains-branch? git "develop")
    "develop"
    "master"))

(defn do-branch! [git brch-nm console force? settings]
  (commit! gui git)
  (if (and (has-changes? git) (not (= (get-develop-branch-name git) (get-current-branch git))))
    (print-ret "Cannot switch branch with outstanding changes" FAILURE)
    (let []
      (checkout! git (get-develop-branch-name git))
      (pull! git  console settings)
      (if (= brch-nm (get-develop-branch-name git))
        SUCCESS
        (if (contains-branch? git brch-nm)
          (let []
            (checkout! git brch-nm)
            (rebase! git (get-develop-branch-name git) (settings "merge-tool")))
          (let []
            (create-branch! git brch-nm)
            (checkout! git brch-nm))))
      (println (str "you are now on branch " (get-current-branch git))))))
