(ns clobot.models.migration
  (:require [clojure.java.jdbc :as sql]
            [clobot.models.slack :as slack]))

(defn migrated? []
  (-> (sql/query slack/spec
                 [(str "select count(*) from information_schema.tables "
                       "where table_name='slacks'")])
      first :count pos?))

(defn migrate []
  (when (not (migrated?))
    (.println System/out "Creating database structure...") (flush)
    (sql/db-do-commands slack/spec
                        (sql/create-table-ddl
                         :slacks
                         [:id :serial "PRIMARY KEY"]
                         [:token :varchar "NOT NULL"]
                         [:team_id :varchar "NOT NULL"]
                         [:channel_id :varchar "NOT NULL"]
                         [:channel_name :varchar "NOT NULL"]
                         [:timestamp :varchar "NOT NULL"]
                         [:user_id :varchar "NOT NULL"]
                         [:user_name :varchar "NOT NULL"]
                         [:text :text "NOT NULL"]
                         [:trigger_word :varchar "NOT NULL"]))
    (.println System/out " done")))
