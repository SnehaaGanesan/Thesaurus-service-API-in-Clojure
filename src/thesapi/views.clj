(ns thesapi.views
  (:use ring.util.response)
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.core :refer :all]
            [clojure.data.csv :as csv]))

(defn home-page
  []
  "Hello World, This is the home-page!"

(defn findsynonyms
  [word]
  (with-open [dict (io/reader "newdict.csv")]
    (let [dictionary         (doall (csv/read-csv dict))
          dictionary-as-sets (map #(into #{} (str/split (first %) #",")) dictionary)
          result             (filter #(% word) dictionary-as-sets)
          result-as-str      (reduce (fn [acc curr]
                                       (str acc ", " curr))
                                     ""
                                     result)]
      {:status  200
       :headers {"Content-Type" "text/plain"}
       :body result-as-str

       }
      )))
