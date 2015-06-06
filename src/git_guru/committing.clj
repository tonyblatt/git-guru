(ns git-guru.committing
  ;(:require [git-guru.branching :refer :all])
  (:require [clojure.java.shell :refer :all])
  (:require [git-guru.commands :refer :all])
  (:require [git-guru.questions :refer :all])
  (:require [git-guru.constants :refer :all])
  (:require [git-guru.logging :refer :all])
  (:import java.lang.Runtime))

; unitility for printing a message and returning a value (similar to spy)
(defn print-ret [s ret]
  (let []
    (println s)
    ret))

; does some checks and performs the commit
(defn commit! [commit-base-fn git]
  (if (not (is-develop? git))
    (if (has-changes? git)
      (commit-base-fn git SUCCESS FAILURE)
      (print-ret "there are no changes to commit" SUCCESS))
    (print-ret "you cannot commit to develop/master" FAILURE)))

; typical base procedure for committing, brings up git gui
(defn gui [git success failure]
  (let [com "git gui"]
    (log! com)
    (exec-comm git "git gui"))
  success)
