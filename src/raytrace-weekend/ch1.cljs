(ns raytrace-weekend.ch1
  (:require [reagent.core :as r]
            [goog.object :as object]))

(defn canvas-component []
  (let [dom-node (r/atom nil)]
    (r/create-class
     {:component-did-mount
      (fn [this]
        (reset! dom-node (r/dom-node this))
        ;; Temporary renders for now
        (let [ctx (.getContext @dom-node "2d")]
          (object/set ctx "fillStyle" "green")
          (.fillRect ctx 10 10 100 100)))

      :reagent-render
      (fn []
        [:canvas#ch1-canvas {:width 400 :height 200 :style {:border "1px solid #000000"}}])})))

(defn ^:export mount []
  (r/render [canvas-component] (js/document.getElementById "ch1-app")))
