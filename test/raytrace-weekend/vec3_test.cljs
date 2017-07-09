(ns raytrace-weekend.vec3-test
  (:require [clojure.spec.test.alpha :as stest]
            [clojure.test :refer [are deftest is testing]]
            [raytrace-weekend.vec3 :as vec3]))

;; NOTE: Initially I had a lot of generative testing in here, but took it out
;; after I realized I didn't have a good story for running it within tests.

(defn =eps [eps x y]
  "Returns true when x and y are equal within some epsilon eps."
  (<= (Math/abs (- x y))
      eps))

(deftest test-add
  (are [x y] (= x y)
    (vec3/add) [0 0 0]
    (vec3/add [5 7 -1]) [5 7 -1]
    (vec3/add [1 2 3] [4 5 6]) [5 7 9]
    (vec3/add [1 1 2] [-6 1 4] [5 3 1]) [0 5 7]))

(deftest test-sub
  (are [x y] (= x y)
    (vec3/sub [1 2 -3]) [-1 -2 3]
    (vec3/sub [4 5 -1] [5 -2 -3]) [-1 7 2]
    (vec3/sub [1 2 -1] [4 -99 1] [8 -1 -2]) [-11 102 0]))

(deftest test-length
  (are [x y] (=eps 0.00001 (vec3/length x) y)
    [0 0 0] 0
    [1 2 3] 3.74165
    [-1 -1 -1] 1.73205
    [0.5 0.1 -0.2] 0.547722))

;; TODO more tests await...
