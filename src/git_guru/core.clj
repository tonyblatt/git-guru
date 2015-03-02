(ns git-guru.core
  (:require [git-guru.branching :refer :all])
  (:require [git-guru.committing :refer :all])
  (:import org.eclipse.jgit.api.Git)
  (:import org.eclipse.jgit.internal.storage.file.FileRepository)
  (:import java.io.File))

(def master-branch "master")

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn gen-git []
  (Git/open (new File ".")))

;(defn gen-git []
;  (new Git (gen-repo)))

(defn status [git]
  (. git status))

;counts number of times flag appears in args
;tested and working
(defn count-of-params [flag args]
  (count (filter
           (fn [x]
             (= x flag))
           args)))


(defn branch! [args]
  (do-branch! (gen-git) (first args) false))

;push not completed
(defn push! [args]
  (let [default (= 1 (count args))
        update (and (= 1 (count-of-params "--update" args))
                    (= 2 (count args)))]
    (cond default (println "push default")
          update (println "updating")
          :else (println "un-recognized push!"))))

(defn commit-top! [params]
  (commit! gui (gen-git)))

(defn -main [& d]
  (println "here")
  (println d)
  (let [branching (= (first d) "branch")
        committing (= (first d) "commit")]
    (cond committing (commit-top! (rest d))
          branching (branch! (rest d))
          :else (println "not a known script"))))
