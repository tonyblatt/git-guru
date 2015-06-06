(ns git-guru.pushing
  (:require [clojure.data.json :as json])
  (:require [git-guru.questions :refer :all :as qs])
  (:require [git-guru.constants :refer :all :as consts])
  (:require [git-guru.branching :refer :all :as branch])
  (:require [git-guru.commands :refer :all])
  (:import org.eclipse.jgit.api.RebaseResult$Status)
  (:import (java.io BufferedWriter FileWriter)))

; generic procedure for writing text data to a file
(defn perform-write [file-name data]
  (with-open [wtr (BufferedWriter. (FileWriter.	file-name))]
    (.write wtr	data)))

; generic procedure for reading text data from a file
(defn read-dats [file-loc]
  (json/read-str (slurp file-loc)))

; writes a data structure to a file as a json text string
(defn write-dats [file-loc dats]
  (perform-write file-loc (json/write-str dats)))

(defn get-dats [])

(defn patch [git dats])

(defn update [git dats])

(defn create [git dats])

; commented out because this fails to compile at this point in time
(comment
; procedure for pushing data to the repository
; untested
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
