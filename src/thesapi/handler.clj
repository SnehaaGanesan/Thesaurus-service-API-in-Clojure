(ns thesapi.handler
  (:require [compojure.core :refer :all]
            [thesapi.views :as views]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  (:gen-class))

(defroutes app-routes
  (GET "/" [] (views/home-page))
  (GET "/lettercount/:word" word (views/lettercountfn word))
  ;;(GET "/" [] "Hello World")
  (GET "/synonyms/:word" [word] (views/findsynonyms word))
  (GET "/printing/:op" [op] (views/printing op))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

(defn -main
  [& [port]]
  (let [port (Integer. (or port
                           (System/getenv "PORT")
                           5000))]
    (jetty/run-jetty #'app {:port  port
                            :join? false})))
