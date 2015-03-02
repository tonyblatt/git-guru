(ns git-guru.pushing
  (:require [clojure.data.json :as json])
  (:require [git-guru.questions :refer :all :as qs])
  (:require [git-guru.constants :refer :all :as consts])
  (:require [git-guru.branching :refer :all :as branch])
  (:import (java.io BufferedWriter FileWriter)))

(defn perform-write [file-name data]
  (with-open [wtr (BufferedWriter. (FileWriter.	file-name))]
    (.write wtr	data)))

(defn read-dats [file-loc]
  (json/read-str (slurp file-loc)))

(defn write-dats [file-loc dats]
  (perform-write file-loc (json/write-str dats)))



(defn push! [git curr-cranch force-update?]
  (if (qs/is-develop? git)
    (let []
      (println "cannot push from develop")
      consts/FAILURE)
    (let []
      (branch/rebase!))))
