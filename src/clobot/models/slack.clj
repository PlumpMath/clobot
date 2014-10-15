(ns clobot.models.slack
  (:require [clojure.java.jdbc :as sql]
            [java-jdbc.sql :as dsl]))

(comment "Represents a webhook payload.")

(def spec (or (System/getenv "DATABASE_URL")
              "postgresql://localhost:5432/clobot"))

;; interface to DSL
(defn find-sql [table conditions]
  (dsl/select * table
              (dsl/where conditions)))

;; sql-try
(defmacro sql-try
  "
  Evaluates SQL op in a try expression, and prints out the error if
  it occurs.
  The error can be SQLException, which defines getNextException,
  or an exception, which is used as-is.
  "
  [body]
  `(try
    ~body
    (catch java.sql.SQLException e#
      (.println System/out (str  "Error " (.getNextException e#))))
    (catch Exception e#
      (.println System/out (str  "Error " e# )))
    )
  )

(defn all []
  (into [] (sql/query spec ["select * from slacks order by id desc limit 128"])))

(defn create [slack]
  (sql-try
   (sql/insert! spec :slacks (keys slack) (vals slack)))
  )

(defn query [slack]
  (sql-try
   (sql/query spec (find-sql :slacks slack)))
  )
