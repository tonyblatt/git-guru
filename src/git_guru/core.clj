(ns git-guru.core
  (:require [git-guru.branching :refer :all])
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

;(defn pull! [git]
;  (println "need to put pull code here"))

;(defn rebase! [] nil)


;(defn branch! [git brch should-pull? should-rebase?]
;  (if (has-changes? git)
;    (let []
;      (perform-branch! git master-branch)
;      (branch-from-master! git brch should-pull? should-rebase?))
;    (if (= master-branch (get-current-branch git))
;      )))

(defn -main [& d]
  (println "here"))
