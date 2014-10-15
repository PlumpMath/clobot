(ns clobot.bots.pollbot.main
  (:require [compojure.core :refer [defroutes GET POST]]
            [clojure.string :as str]
            [ring.util.response :as ring]
            [clobot.models.slack :as model]
            [clojure.string :as str]
            [clojure.core.match :as matcher]
            [clobot.bot :as helper]
            [taoensso.carmine :as car :refer (wcar)]))

(def server1-conn
  {
   :pool {}
   :spec {:uri  (or
                 (System/getenv "REDISTOGO_URL")
                 "redis://localhost:6379/")}})

(defmacro wcar* [& body] `(car/wcar server1-conn ~@body))

(defn pollen-create [id poll]
  (wcar* (car/set (str "pollbot:poll:" id) (first poll)))
  )

(defn pollen-vote [id choice]
  (wcar* (car/hincrby (str "pollbot:vote:" id) (first choice) 1 )))

(defn pollen-results [id]
  (wcar* (car/hgetall (str "pollbot:vote:" id))))

;; everything down to here to be extracted to pollen module

(def ^{:private true} help (str "Help coming soon."))

(defn ^{:private true} create [id _botname _command type & query]
  (pollen-create id query)
  (helper/body (str "Poll created. Vote with 'pollbot: vote " id " <answer>'"))
  )

(defn ^{:private true} vote [voteid _botname _command pollid & choice]
  (pollen-vote pollid choice)
  (helper/body (str "Vote cast on poll " pollid " with choice " (first  choice) ))
  )

(defn ^{:private true} results [id _botname _command pollid]
  (helper/body (str "Results " (pollen-results pollid))))

(defn handle [id params]
;; arr: 0 botname 1 command 2 command-type 3+ query
  (def arr (str/split (params :text) #"\s+" 4))
  (let [action (str/lower-case  (get arr 1 ))]
    (.println System/out (str "process: id=" id " arr=" arr))
    (matcher/match [action]
                   ["help"] (helper/body help)
                   ["new"] (apply create id arr)
                   ["vote"] (apply vote id arr)
                   ["results"] (apply results id arr)
                   :else (helper/body "No idea... try 'help'."))
    )
  )
