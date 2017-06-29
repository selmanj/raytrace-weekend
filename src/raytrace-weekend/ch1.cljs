(ns raytrace-weekend.ch1
  (:require [reagent.core :as r]))

(defn canvas-component []
  [:div.canvas [:p "Hello world."]])

(defn ^:export mount []
  (r/render [canvas-component] (js/document.getElementById "ch1-app")))
