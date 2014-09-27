(ns git-guru.committing
  (:require [git-guru.branching :refer :all])
  (:import org.eclipse.jgit.api.Git)
  (:require [clojure.java.shell :refer :all]))

(def FAILURE "FAILURE")

(def SUCCESS "SUCCESS")

(defn is-develop? [git]
  false)

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

(defn gui []
  (sh "git" "gui"))

(defn add-all []
  (sh "git" "add" "."))

(defn add-specific [])

(defn exec-command [config-property])
