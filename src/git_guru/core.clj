(ns git-guru.core
  (:require [git-guru.branching :refer :all])
  (:import org.eclipse.jgit.api.Git)
  (:import org.eclipse.jgit.internal.storage.file.FileRepository)
  (:import java.io.File))

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

(defn has-changes? [git]
  (.. git (status) (call) (hasUncommittedChanges)))

(defn pull! [git]
  (println "need to put pull code here"))

(defn rebase! [] nil)

(defn -main [& d]
  (println "here"))
