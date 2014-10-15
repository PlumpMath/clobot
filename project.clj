(defproject clobot "0.1.0-SNAPSHOT"
  :description "Slack Webhook Integration in Clojure"
  :url "http://github.com/opyate/clobot"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.9"]
                 [org.clojure/core.match "0.2.1"]
                 [org.clojure/java.jdbc "0.3.5"]
                 [postgresql "9.3-1102.jdbc41"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [ring/ring-json "0.3.1"]
                 [com.taoensso/carmine "2.7.1"]
                 [java-jdbc/dsl "0.1.0"]]
  :plugins [[lein-ring "0.8.12"]]
  :ring {:handler clobot.handler/app}
  :main ^:skip-aot clobot.handler
  :uberjar-name "clobot-standalone.jar"
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}
   :uberjar {:aot :all}})
