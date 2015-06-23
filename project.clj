(defproject cljs-firmata "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.7.0-RC2"]
                 [org.clojure/clojurescript "0.0-3308"]
                 [clj-firmata "2.1.1-SNAPSHOT"]]

  :plugins [[lein-cljsbuild "1.0.4"]
            [lein-npm "0.5.0"]] 

  :hooks [leiningen.cljsbuild]

  :node-dependencies [[serialport "1.7.4"]]

  :main "run.js"

  :clean-targets ["out"]

  :source-paths ["src"]

  :cljsbuild {
    :builds [{:id "cljs-firmata"
              :source-paths ["src"]
              :compiler {
                :output-to "out/cljs_firmata.js"
                :output-dir "out"
                :target :nodejs
                :optimizations :none
                :source-map true}}
             ]})
