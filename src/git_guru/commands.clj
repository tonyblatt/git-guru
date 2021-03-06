(ns git-guru.commands
  (:import org.eclipse.jgit.api.Git)
  (:import org.eclipse.jgit.api.CheckoutCommand)
  (:import org.eclipse.jgit.internal.storage.file.FileRepository)
  (:import org.eclipse.jgit.api.RebaseResult$Status)
  (:import org.eclipse.jgit.api.RebaseCommand$Operation)
  (:import java.io.File)
  (:import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider)
  (:import java.lang.System)
  (:require [clojure.java.shell :refer :all])
  (:require [git-guru.logging :refer :all])
  (:require [git-guru.constants :refer :all])
  (:gen-class))

; utility for getting the path to the repo location
(defn get-repo-location [git]
  (.. git (getRepository) (getDirectory) (getParentFile) (getPath)))

; Procedure for executing an external command
(defn exec-comm [git comm-str]
  (let [runtime (Runtime/getRuntime)
        proc (. runtime (exec comm-str nil (new File (get-repo-location git))))]
    (. proc (waitFor))
    (. proc (destroy))))

; gets a password from a console
; does not work on cygwin because cygwin does not have access to the java console
(defn get-pass! [console]
  (println (str "System/console value = " (System/console))) ; debug statement
  (String/valueOf (.readPassword (console)
                                 "Password:" nil)))

; gets a user name from a console
; does not work on cygwin because cygwin does not have access to the java console
(defn get-uname! [console]
  (String/valueOf (.readLine (console)
                                 "User Name:" nil)))

; gets a user name and passowrd and returns a credential provider
(defn get-uname-and-pass! [console settings]
  (let [uname (if (settings "specify-user-name") (settings "user-name") (get-uname! console))
        pass (get-pass! console)]
    (new UsernamePasswordCredentialsProvider uname pass)))

; checkout! performs the action of checking out the specified branch
; RefNotFoundException Ref develop can not be resolved org.eclipse.jgit.api.CheckoutCommand.call (CheckoutCommand.java:241)
; indicates that the brch does not exist
; Note: develop == master
(defn checkout! [git brch]
  (.. git
      (checkout)
      (setName (str "refs/heads/" brch))
      (call))
  (log! (str "git checkout " brch)))

; utility funciton for extracting the name from a ref
(defn extract-name [r]
  (. r getName))

; lists the available branches (in string form) contained in git
; Note: branches come back in the form "refs/heads/BRANCH_NAME"
(defn list-branches [git]
  (log! "git branch --list")
  (map extract-name
       (.. git
           (branchList)
           (call))))

; creates a new branch in git
(defn create-branch! [git brch]
  (.. git
      (branchCreate)
      (setName brch)
      (call))
  (log! (str "git branch " brch)))

; gets the current branch from git
(defn get-current-branch [git]
  (.. git (getRepository) (getBranch)))

; performs a pull command against the repository
(defn pull! [git console settings]
  (log! "git pull")
  (if (settings "use-public-key-authentication")
    (.. git (pull) (call))
    (.. git (pull) (setCredentialsProvider (get-uname-and-pass! console settings)) (call))))

; performs a rebase operation against the repository
(defn rebase! [git dest tool]
  (log! "git rebase develop")
  (let [res (.. git (rebase) (setUpstream dest) (call))]
    (if (= (. res (getStatus)) (. RebaseResult$Status STOPPED))
      (let [com (str "git mergetool --tool=" tool " --no-prompt")]
        (log! com)
        (exec-comm git com)
        (log! "git rebase --continue")
        (.. git (rebase) (setUpstream dest) (setOperation (. RebaseCommand$Operation CONTINUE)) (call))
        (let [base-str (.. git (getRepository) (getDirectory) (getPath))]
          (log! "delete all new untracked files")
          (doall (map (fn [loc] (. (new File (clojure.string/replace base-str ".git" loc)) delete)) (.. git (status) (call) (getUntracked)))))))
    (. res (getStatus))))

(defn apply-patch! [])

(defn update-review! [])

(defn create-review![])
