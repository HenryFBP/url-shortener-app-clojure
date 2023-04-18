(ns shorturl.db
  (:require [clojure.java.jdbc :as j]
            [honey.sql :as sql]
            [honey.sql.helpers :refer :all]
            [clojure.edn :as edn]))

; Read a config file ("edn file") as a map
(defn read-config [path]
  (edn/read-string (slurp path)))

(read-config "deps.edn")

(read-config "db-creds.dev.edn")

(def mysql-db (read-config "db-creds.dev.edn"))

(defn query [q] 
  (j/query mysql-db q))

(defn insert! [q] 
  (j/db-do-prepared mysql-db q))

(defn get-redirects []
  (query
   (-> (select :*)
       (from :redirects)
       (sql/format))))

(get-redirects)



