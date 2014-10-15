(ns clobot.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [ring.adapter.jetty :as ring]
            [ring.middleware.json :as middleware]
            [compojure.route :as route]
            [clobot.bot :as bot]
            [clobot.models.migration :as schema]))

(defroutes app-routes
  (GET "/" [] "<h1><a href='https://github.com/opyate/clobot'>Clobot<a/></h1>")
  (POST "/" {params :params} (bot/handle params))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-params)
      (middleware/wrap-json-response)))

(defn start [port]
  (ring/run-jetty app {:port port
                       :join? false}))

(defn -main []
  (schema/migrate)
  (let [port (Integer. (or (System/getenv "PORT") "3000"))]
    (start port)))
