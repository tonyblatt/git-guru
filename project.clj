(defproject git-guru "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.11.0"]
                 [org.eclipse.jgit/org.eclipse.jgit "3.4.1.201406201815-r"]
                 [com.mashape.unirest/unirest-java "1.4.5"]
                 [org.apache.httpcomponents/httpclient "4.3.6"]
                 [org.apache.httpcomponents/httpasyncclient "4.0.2"]
                 [org.apache.httpcomponents/httpmime "4.3.6"]
                 [org.json/json "20140107"]
                 [org.clojure/data.json "0.2.5"]]
  :plugins [[jonase/eastwood "1.3.0"]]
  :main git-guru.core)
