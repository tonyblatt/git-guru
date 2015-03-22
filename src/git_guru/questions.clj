(ns git-guru.questions
  (:require [git-guru.commands :refer :all]))

; figures out if git contains brch
; tested and working
(defn contains-branch? [git brch]
  (< 0
     (count (filter (fn [x]
                      (= (str "refs/heads/" brch) x))
                    (list-branches git)))))

; (true/false) the current branch has uncommited changes
; tested and working
(defn has-changes? [git]
  (.. git (status) (call) (hasUncommittedChanges)))

(defn is-develop? [git]
  (let [brch (get-current-branch git)]
    (or (= "master" brch) (= "develop" brch))))

(defn has-ship-it? [] true)
