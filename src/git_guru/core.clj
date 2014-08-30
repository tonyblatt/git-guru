(ns git-guru.core
  (:import org.eclipse.jgit.api.Git)
  (:import org.eclipse.jgit.internal.storage.file.FileRepository))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn extract-name [r]
  (. r getName))

(defn gen-repo []
  (new FileRepository "."))

(defn gen-git []
  (new Git (gen-repo)))

(defn status [git]
  (. git status))

(defn has-changes? [git]
  (.. git (status) (call) (hasUncommittedChanges)))

(defn checkout! [git brch]
  (.. git
      (checkout)
      (setName brch)
      (call)))

(defn list-branches [git]
  (map extract-name
       (.. git
           (branchList)
           (call))))

(defn pull! [git]
  (println "need to put pull code here"))

(defn rebase! [] nil)

(defn branch! [git brch]
  (checkout! git "develop")
  (pull! git)
  (if (= "develop" brch)
    nil
    (let [n (checkout! git brch)]
      (rebase!))))

(defn -main [& d]
  (println "here"))
