(ns raytrace-weekend.ch3
  (:require [reagent.core :as r]))

(defn canvas-component []
  [:p "Loaded. Nothing here yet. :("])

(defn mount []
  "Main entry point."
  (r/render [canvas-component]
            (js/document.getElementById "ch3-app")))
