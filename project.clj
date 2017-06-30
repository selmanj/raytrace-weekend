(defproject raytrace-weekend "0.0.1-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [org.clojure/clojurescript "1.9.660"]
                 [binaryage/devtools "0.9.4"]
                 [reagent "0.7.0"]]
  :plugins [[lein-figwheel "0.5.10"]]
  :clean-targets ^{:protect false} [:target-path "out" "resources/public/cljs"]
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src"]
                        :figwheel {:on-jsload "raytrace-weekend.core/mount-from-url"}
                        :compiler {:preloads [devtools.preload]
                                   :main "raytrace-weekend.core"
                                   :asset-path "cljs/out"
                                   :output-to "resources/public/cljs/raytrace-weekend.js"
                                   :output-dir "resources/public/cljs/out"}}]})
