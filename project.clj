(defproject raytrace-weekend "0.0.1-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [org.clojure/clojurescript "1.9.660"]
                 [binaryage/devtools "0.9.4"]
                 [reagent "0.7.0"]]
  :plugins [[lein-figwheel "0.5.10"]
            [lein-cljsbuild "1.1.6"]
            [lein-doo "0.1.7"]]
  :source-paths ["src" "test"]
  :profiles {:dev {:dependencies [[com.cemerick/piggieback "0.2.2"]
                                  [figwheel-sidecar "0.5.10"]
                                  [org.clojure/test.check "0.10.0-alpha2"]
                                  [lein-doo "0.1.7"]]}}
  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
  :clean-targets ^{:protect false} [:target-path "out" "resources/public/cljs"]
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src" "test"]
                        :figwheel {:on-jsload "raytrace-weekend.core/mount-from-url"}
                        :compiler {:preloads [devtools.preload]
                                   :main "raytrace-weekend.core"
                                   :asset-path "cljs/out"
                                   :output-to "resources/public/cljs/raytrace-weekend.js"
                                   :output-dir "resources/public/cljs/out"}}
                       {:id "test"
                        :source-paths ["src" "test"]
                        :compiler {:main raytrace-weekend.test-runner
                                   :output-to "resources/public/cljs/testable.js"
                                   :output-dir "resources/public/cljs/out/test"
                                   :optimizations :none}}]}
  :doo {:build "test"})            ;
