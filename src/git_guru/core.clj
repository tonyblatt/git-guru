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

(defn foo
  "Basic test function for repl testing."
  [x]
  (println x "Hello, World!"))

(defn get-settings []
  (read-dats "../store/settings.txt"))

(defn gen-git [f]
  (Git/open f))

(defn status [git]
  (. git status))

(defn branch! [git args]
  (do-branch! git args false (get-settings)))

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
  ;(print "enter uname")
  (let [f (get-root-dir (new File loc))
        branching (= script "branch")
        committing (= script "commit")
        rebasing (= script "rebase")
        ;red (read-line)
        console (. System (console))]
        ;pwd (.readPassword console "tell me your password: ")]
    (println console)
    (println System/in)
    (println (System/console))
    ;(println (String/valueOf (.readPassword (System/console) "Password:" nil)))
    ;(println red)
    ;(println pwd)
    (println (get-settings))
    (cond committing (commit-top! (gen-git f))
          branching (branch! (gen-git f) (first d))
          rebasing (rebase-top! (gen-git f))
          :else (println "not a known script"))))
