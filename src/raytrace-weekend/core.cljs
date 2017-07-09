;; Core namespace just pulls in all chapters.
(ns raytrace-weekend.core
  (:require [raytrace-weekend.ch1 :as ch1]
            [raytrace-weekend.ch2 :as ch2]
            [raytrace-weekend.ch3 :as ch3]
            [clojure.string :as str]))

(defn ^:export mount-from-url []
  "Tries to intelligently defer to the proper namespace based on the current
  url."
  (case (peek (str/split (.-pathname js/location) #"/"))
    "ch1.html" (ch1/mount)
    "ch2.html" (ch2/mount)
    "ch3.html" (ch3/mount)
    :else (.log js/console "Unable to find matching mount function for url")))
