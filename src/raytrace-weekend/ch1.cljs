(ns raytrace-weekend.ch1
  (:require [reagent.core :as r]
            [goog.object :as object]))

(defn canvas-render [dom-node]
  (let [ctx (.getContext dom-node "2d")]
    (object/set ctx "fillStyle" "green")
    (.fillRect ctx 10 10 100 100)))

(defn canvas-component []
  (r/create-class
   {:component-did-mount
    (fn [this] (canvas-render (r/dom-node this)))

    :reagent-render
    (fn []
      [:canvas#ch1-canvas {:width 400
                           :height 200
                           :style {:border "1px solid #000000"}}])}))

(defn ^:export mount []
  (r/render [canvas-component]
            (js/document.getElementById "ch1-app")))
