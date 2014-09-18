(defproject cljs-firmata "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2311"]
                 [clj-firmata "2.0.0-SNAPSHOT"]]

  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]
            [org.bodil/lein-noderepl "0.1.11"]]

  :hooks [leiningen.cljsbuild]

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
