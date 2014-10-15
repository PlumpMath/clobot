(ns clobot.bot
  (:require [compojure.core :refer [defroutes GET POST]]
            [clojure.string :as str]
            [ring.util.response :as ring]
            [clobot.models.slack :as model]))

(defn make-response [params] {:body {:text "Roger that"}})

(defn handle [params]
  (str (pr-str params))
  (model/create params)
  (make-response params))
