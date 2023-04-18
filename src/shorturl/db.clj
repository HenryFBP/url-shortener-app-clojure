(ns shorturl.db
  (:require [clojure.java.jdbc :as j]
            [honey.sql :as sql]
            [honey.sql.helpers :refer :all]
            [clojure.edn :as edn]))

; Read a config file ("edn file") as a map
(defn read-config [path]
  (edn/read-string (slurp path)))

;; (read-config "deps.edn")

;; (read-config "db-creds.dev.edn")

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

(defn get-url [slug]
  (-> (query (->
              (select :*)
              (from :redirects)
              (where [:= :slug slug])
              (sql/format)))
      first
      :url))

(comment
  (->
   [{:url "asdf.com"} {:a "a"} {:b "b"}]
   first
   :url))

(defn insert-redirect! [slug url]
  (insert! (-> (insert-into :redirects)
               (columns :slug :url)
               (values [[slug url]])
               (sql/format))))

(comment
  (insert-redirect! "xyz" "https://www.youtube.com/watch?v=0mrguRPgCzI")
  (get-redirects)
  (get-url "xyz"))


