(ns clobot.models.slack
  (:require [clojure.java.jdbc :as sql]))

(comment "Represents a webhook payload.")

(def spec (or (System/getenv "DATABASE_URL")
              "postgresql://localhost:5432/clobot"))

(defn all []
  (into [] (sql/query spec ["select * from slacks order by id desc limit 128"])))

(defn create [slack]
  (sql/insert! spec :slacks (keys slack) (vals slack)))
