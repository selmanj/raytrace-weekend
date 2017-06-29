(defproject raytrace-weekend "0.0.1-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [org.clojure/clojurescript "1.9.660"]]
  :plugins [[lein-figwheel "0.5.10"]]
  :clean-targets [:target-path "out"]
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src"]
                        :figwheel true
                        :compiler {:main "raytrace-weekend.core"}}]})
