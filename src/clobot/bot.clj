(ns clobot.bot
  (:require [compojure.core :refer [defroutes GET POST]]
            [clojure.string :as str]
            [ring.util.response :as ring]
            [clobot.models.slack :as model]
            [clojure.string :as str]
            [clojure.core.match :as matcher]))

(defn body [text] {:body {:text text}})

(defn defer
  "Defers to a bot most suited to this command."
  [id params]
  ;; arr: 0 botname, 1+ the rest
  ;; TODO below few lines needs a rewrite
  (def arr (str/split (params :text) #"\s+" 2))
  (def botname (clojure.string/replace (get arr 0) #":" ""))
  (load-file (str  "src/clobot/bots/" botname "/main.clj"))
  (let [thebot (symbol (str "clobot.bots." botname ".main") "handle")]
    ((-> thebot resolve deref) id params)
    )
  )

(defn process
  "Gets the id associated with params, then defers to the matcher."
  [params]
  (let [result  (model/query params)]
    (if (= (count result) 1)
      (defer (:id (first result)) params)
      (do
        (.println
         System/out
         (str "Error: Multiple results was returned for " params))
        (body "Something untoward happened.")
        )
      )
    )
  )

(defn handle
  "Persist incoming payload, then process it."
  [params]
  (model/create params)
  (process params))
