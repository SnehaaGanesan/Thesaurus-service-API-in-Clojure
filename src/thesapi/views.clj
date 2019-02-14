(ns thesapi.views
  (:use ring.util.response)
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.core :refer :all]
            [clojure.data.csv :as csv]))

(defn home-page
  []
  "HEllo World")

(defn lettercountfn
  [word]
  (let [number-of-letters (count (map #(into #{} (str/split % "")) word))]
    {:status 200
     :header {"Content-type" "text/plain"}
     :body   number-of-letters
     }
    )
  )

(defn printing
  [request]
  (if request
    {:status 200
     :body (println request)
     :headers {}}
    {:status 404
     :body "Sorry, can't do!!"
     :headers {}}))

(defn findsynonyms
  [word]
  (with-open [dict (io/reader "newdict.csv")]
    (let [dictionary         (doall (csv/read-csv dict))
          dictionary-as-sets (map #(into #{} (str/split (first %) #",")) dictionary)
          _                  (println (first dictionary-as-sets))
          _                  (println "contains? word ... " ((first dictionary-as-sets) "come,advance,approach,arrive,near,reach"))
          result             (filter #(% word) dictionary-as-sets)
          _                  (prn "RESULT IS = " result)
          result-as-str      (reduce (fn [acc curr]
                                       (str acc " " curr))
                                     ""
                                     result)]
     ;; (str result-as-str)
      {:status  200
       :headers {"Content-Type" "text/plain"}
       :body result-as-str

       }
      )))
