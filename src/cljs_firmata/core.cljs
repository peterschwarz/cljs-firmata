(ns cljs-firmata.core
    (:require [cljs.nodejs :as nodejs]
              [firmata.core :as f]
              [firmata.util :as futil :refer [detect-arduino-port]]
              [firmata.receiver :as r]
              [cljs.core.async :as a :refer [timeout <!]])
    (:require-macros [cljs.core.async.macros :refer [go]]))

(nodejs/enable-util-print!)

(defn on [board pin]
  (f/set-digital board pin :high))

(defn off [board pin]
  (f/set-digital board pin :low))

(defn -main [& args]
  (let [blink-time (atom (or (first args) 1000))]
    (detect-arduino-port (fn [err port]
      (if port
        (f/open-serial-board port (fn [board]
      ; (f/open-network-board "192.168.2.100" 5000 (fn [board]

          (println "Enabling analog-in reporting")
          (f/enable-analog-in-reporting board 0 true)

          (println "Creating analog event handler")
          (r/on-analog-event board 0 #(reset! blink-time (:value %)))


          (f/set-pin-mode board 3 :output)

          (println "connected to board " (f/firmware board))
          (go 
            (loop []
              (on board 3)

              (<! (timeout @blink-time))

              (off board 3)

              (<! (timeout @blink-time))

              (recur)))))
        (println "No arduino port detected: " (if err err "check connection")))))))

(set! *main-cli-fn* -main)
