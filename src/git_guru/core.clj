(ns git-guru.core
  (:require [git-guru.branching :refer :all])
  (:require [git-guru.committing :refer :all])
  (:require [git-guru.commands :refer :all])
  (:require [git-guru.pushing :refer :all])
  (:import org.eclipse.jgit.api.Git)
  (:import org.eclipse.jgit.internal.storage.file.FileRepository)
  (:import java.io.File)
  (:import java.lang.System)
  (:gen-class))

; procedure for testing the REPL
(defn foo
  "Basic test function for repl testing."
  [x]
  (println x "Hello, World!"))

; procedure for reading in the settings
(defn get-settings []
  (read-dats "../store/settings.txt"))

; procedure for getting a Git object to manipulate
(defn gen-git [f]
  (Git/open f))

; gets the current git status object
(defn status [git]
  (. git status))

; procedure for switching from one branch to another
(defn branch! [git args]
  (do-branch! git args (System/console) false (get-settings)))

; procedure for commiting to the repository
(defn commit-top! [git]
  (commit! gui git))

; procedure for rebasing the current branch
(defn rebase-top! [git]
  (branch! git (get-current-branch git)))

; gets the root directory of the repository, this is necessary for creating the git object
(defn get-root-dir [f]
  (if (= nil f)
    nil
    (if (reduce (fn [b loc] (or b (= ".git" loc))) false (. f list))
      f
      (recur (. f getParentFile)))))

; entry point for the application
(defn -main [script loc & d]
  (let [f (get-root-dir (new File loc))
        branching (= script "branch")
        committing (= script "commit")
        rebasing (= script "rebase")]
    (cond committing (commit-top! (gen-git f))
          branching (branch! (gen-git f) (first d))
          rebasing (rebase-top! (gen-git f))
          :else (println "not a known script"))))
