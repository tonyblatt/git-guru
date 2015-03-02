(ns git-guru.committing
  ;(:require [git-guru.branching :refer :all])
  (:require [clojure.java.shell :refer :all])
  (:require [git-guru.commands :refer :all])
  (:require [git-guru.questions :refer :all])
  (:require [git-guru.constants :refer :all])
  (:import java.lang.Runtime))

(defn print-ret [str ret]
  (let []
    (println str)
    ret))

(defn commit! [commit-base-fn git]
  (if (not (is-develop? git))
    (if (has-changes? git)
      (commit-base-fn git SUCCESS FAILURE)
      (print-ret "there are no changes to commit" SUCCESS))
    (print-ret "you cannot commit to develop/master" FAILURE)))

(defn gui [a success failure]
  (let [runtime (Runtime/getRuntime)
        proc (. runtime (exec "git gui"))]
    (. proc (waitFor))
    (. proc (destroy)))
  success)

(defn add-all []
  (sh "git" "add" "."))

(defn add-specific [])

(defn exec-command [config-property])
