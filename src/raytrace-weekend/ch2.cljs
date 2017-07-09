(ns raytrace-weekend.ch2
  (:require [raytrace-weekend.vec3 :as vec3]
            [reagent.core :as r]))

;; Most of this exercise is just reimplementing ch1 in terms of the new vec3
;; structure. Unfortunately, because we opted for simple vectors to represent a
;; vec3, there's nothing here to change!

(defn graphics-hello-world [width height]
  "Produces the \"Hello World\" of graphics according to the book. Given a width
  and height, produces a vector of [r g b] vectors (representing pixels) from
  0-255. The image is just sort of a rainbow kinda thing."
  (vec (for [y (range height)
             x (range width)]
         (let [r (/ x width)
               g (/ (- height (inc y)) height)
               b 0.2]
           (into []
                 (map #(int (* 255.99 %)))
                 [r g b])))))

(defn rgb-to-rgba [ps]
  "Converts a vector of [r g b]s into [r g b a] where a is fixed at 255."
  (into [] (map #(conj (vec %) 255)) ps))

;; Canvas related functions

(defn canvas-render [dom-node]
  "Given a canvas dom-node, renders a graphics \"Hello World\" to that canvas
  via an ImageData object."
  (let [ctx (.getContext dom-node "2d")
        width (.-offsetWidth dom-node)
        height (.-offsetHeight dom-node)
        pixel-array (-> (graphics-hello-world width height)
                        rgb-to-rgba
                        flatten
                        clj->js
                        js/Uint8ClampedArray.)
        img-data (js/ImageData. pixel-array width height)]
    (.putImageData ctx img-data 0 0)))

(defn canvas-component []
  "Creates a Reagent component rendering of canvas-render."
  (r/create-class
   {:component-did-mount
    (fn [this] (canvas-render (r/dom-node this)))

    :reagent-render
    (fn []
      [:canvas#ch1-canvas {:width 200
                           :height 100
                           :style {:border "1px solid #000000"}}])}))

(defn mount []
  "Main entry point."
  (r/render [canvas-component]
            (js/document.getElementById "ch2-app")))
