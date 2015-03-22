(ns git-guru.pushing
  (:require [clojure.data.json :as json])
  (:require [git-guru.questions :refer :all :as qs])
  (:require [git-guru.constants :refer :all :as consts])
  (:require [git-guru.branching :refer :all :as branch])
  (:require [git-guru.commands :refer :all])
  (:import org.eclipse.jgit.api.RebaseResult$Status)
  (:import (java.io BufferedWriter FileWriter)))

(defn perform-write [file-name data]
  (with-open [wtr (BufferedWriter. (FileWriter.	file-name))]
    (.write wtr	data)))

(defn read-dats [file-loc]
  (json/read-str (slurp file-loc)))

(defn write-dats [file-loc dats]
  (perform-write file-loc (json/write-str dats)))

(defn get-dats [])

(defn patch [git dats])

(defn update [git dats])

(defn create [git dats])

(comment
(defn push! [git curr-cranch]
  (if (qs/is-develop? git)
    (let []
      (println "cannot push from develop")
      consts/FAILURE)
    (let [local-changes (qs/has-changes? git)
          branch-res (branch! git (get-current-branch git))
          rebase-changes (= branch-res (. RebaseResult$Status UP_TO_DATE))
          dats (get-dats)
          brch (get-current-branch git)
          branch-id (dats brch)]
      (cond
       (= nil branch-id) (let [new-branch-id (create-branch! git)] (println "creating a new review from this branch"))
       (or rebase-changes local-changes) (let [] (println "detected updates, updating the diff") (update-review! git))
       :else (apply-patch! git)))))
)
