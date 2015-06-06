(ns git-guru.questions
  (:require [git-guru.commands :refer :all]))

; figures out if git contains brch
(defn contains-branch? [git brch]
  (< 0
     (count (filter (fn [x]
                      (= (str "refs/heads/" brch) x))
                    (list-branches git)))))

; (true/false) the current branch has uncommited changes
(defn has-changes? [git]
  (.. git (status) (call) (hasUncommittedChanges)))

; checks to see if we are on the develop or master branch
(defn is-develop? [git]
  (let [brch (get-current-branch git)]
    (or (= "master" brch) (= "develop" brch))))

; checks if a code review has a ship it
; untested
(defn has-ship-it? [] true)
