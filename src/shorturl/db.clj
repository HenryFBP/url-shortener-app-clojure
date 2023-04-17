(ns shorturl.db
  (:require [clojure.java.jdbc :as j]
            [honey.sql :as sql]
            [honey.sql.helpers :refer :all])
)

(def mysql-db {:host "aws.connect.psdb.cloud"
               :dbtype "mysql"
               :dbname "test-url-shortener"
               :user "q6fpmhvhh082ksz8z7tn"
               :password "pscale_pw_2q57gpe3Zx85cPFs8F1V00OsytNVaquOBLDvwQUYHN4"})

;; (j/query mysql-db
;;          ["select * from fruit where appearance = ?" "rosy"]
;;          {:row-fn :cost})

(defn get-redirects [] (j/query mysql-db (-> (select :*)
                                              (from :redirects)
    ;; (where [:= :foo.a "baz"])
                                              (sql/format))))

(get-redirects)



