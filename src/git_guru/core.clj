(ns git-guru.core
  (:require [git-guru.branching :refer :all])
  (:require [git-guru.committing :refer :all])
  (:require [git-guru.commands :refer :all])
  (:import org.eclipse.jgit.api.Git)
  (:import org.eclipse.jgit.internal.storage.file.FileRepository)
  (:import java.io.File))

(defn foo
  "Basic test function for repl testing."
  [x]
  (println x "Hello, World!"))

(defn gen-git [f]
  (Git/open f))

(defn status [git]
  (. git status))

(defn branch! [git args]
  (do-branch! git args false))

(defn commit-top! [git]
  (commit! gui git))

(defn rebase-top! [git]
  (branch! git (get-current-branch git)))

(defn get-root-dir [f]
  (if (= nil f)
    nil
    (if (reduce (fn [b loc] (or b (= ".git" loc))) false (. f list))
      f
      (recur (. f getParentFile)))))

(defn -main [script loc & d]
  (let [f (get-root-dir (new File loc))
        branching (= script "branch")
        committing (= script "commit")
        rebasing (= script "rebase")]
    (reduce (fn [x y] (println y)) false (.. (gen-git f) (status) (call) (getUntracked)))
    (cond committing (commit-top! (gen-git f))
          branching (branch! (gen-git f) (first d))
          rebasing (rebase-top! (gen-git f))
          :else (println "not a known script"))))
