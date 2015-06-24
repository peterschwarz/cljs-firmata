(ns cljs-firmata.core
    (:require [cljs.nodejs :as nodejs]
              [firmata.core :as f]
              [firmata.receiver :as r]
              [cljs.nodejs :as nodejs]
              [cljs.core.async :as a :refer [timeout <!]])
    (:require-macros [cljs.core.async.macros :refer [go]]))

(nodejs/enable-util-print!)

(defonce app-state (atom {:board nil
                          :pin 13
                          :sensor-pin 0
                          :blink-time 1000 }))

(defn on [board pin]
  (f/set-digital board pin :high))

(defn off [board pin]
  (f/set-digital board pin :low))

(defn blink-board [board]
  (let [ {:keys [pin sensor-pin]} @app-state]
    (swap! app-state assoc :board board) 

    (println "Enabling analog-in reporting")
    (f/enable-analog-in-reporting board sensor-pin true)

    (println "Creating analog event handler")
    (r/on-analog-event board sensor-pin
                       #(swap! app-state assoc :blink-time (:value %)))

    (f/set-pin-mode board pin :output)

    (println "connected to board:" (f/firmware board)
             "\nversion:" (f/version board))
    (go 
      (loop []
        (let [blink-time (:blink-time @app-state)]
          (on board pin)

          (<! (timeout blink-time))

          (off board pin)

          (<! (timeout blink-time)))

        (recur)))))

(defn -main [& args]
  (when-let [time-in-ms (first args)]
    (swap! app-state assoc :blink-time (js/parseInt time-in-ms)))

  (f/open-serial-board :auto-detect  blink-board :reset-on-connect? true))

(set! *main-cli-fn* -main)
