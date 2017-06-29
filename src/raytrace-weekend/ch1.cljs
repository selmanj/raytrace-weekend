(ns raytrace-weekend.ch1
  (:require [reagent.core :as r]
            [goog.object :as object]))

(defn graphics-hello-world [width height]
  (vec (for [y (range height)
             x (range width)]
         (let [r (/ x width)
               g (/ (- height (inc y)) height)
               b 0.2]
           (into []
                 (map #(int (* 255.99 %)))
                 [r g b])))))

(defn add-alpha [ps]
  (into [] (map #(conj % 255)) ps))

(defn canvas-render [dom-node]
  (let [ctx (.getContext dom-node "2d")
        pixel-array (-> (graphics-hello-world 200 100)
                        add-alpha
                        flatten
                        clj->js
                        js/Uint8ClampedArray.)
        img-data (js/ImageData. pixel-array 200 100)]
    (.putImageData ctx img-data 0 0)))

(defn canvas-component []
  (r/create-class
   {:component-did-mount
    (fn [this] (canvas-render (r/dom-node this)))

    :reagent-render
    (fn []
      [:canvas#ch1-canvas {:width 200
                           :height 100
                           :style {:border "1px solid #000000"}}])}))

(defn ^:export mount []
  (r/render [canvas-component]
            (js/document.getElementById "ch1-app")))
