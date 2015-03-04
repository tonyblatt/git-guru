(ns git-guru.core
  (:require [git-guru.branching :refer :all])
  (:require [git-guru.committing :refer :all])
  (:require [git-guru.commands :refer :all])
  (:import org.eclipse.jgit.api.Git)
  (:import org.eclipse.jgit.internal.storage.file.FileRepository)
  (:import java.io.File))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn gen-git [loc]
  (Git/open (new File loc)))

(defn status [git]
  (. git status))

(defn branch! [git args]
  (do-branch! git args false))

(defn commit-top! [git]
  (commit! gui git))

(defn rebase-top! [git]
  (branch! git [(get-current-branch git)]))

(defn -main [script loc & d]
  (let [branching (= script "branch")
        committing (= script "commit")
        rebasing (= script "rebase")]
    (cond committing (commit-top! (gen-git loc))
          branching (branch! (gen-git loc) (first d))
          rebasing (rebase-top! (gen-git loc))
          :else (println "not a known script"))))
