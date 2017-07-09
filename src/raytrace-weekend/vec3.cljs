;; In the book, Shirley creates a vec3 class. Here instead we spec a vec3 as a simple vector of three components. Note that there's probably no need to restrict these functions to only vectors of size 3, but to keep things close to the book I've left it that way.
(ns raytrace-weekend.vec3
  (:refer-clojure :rename {+ cljs+
                           - cljs-
                           * cljs*
                           / cljsdiv} ; Can't call it cljs/ as it looks like a namespace
                  :exclude [+ - * /])
  (:require [clojure.spec.alpha :as s]))

;; A vec3 is just a vector of 3 numbers
(s/def ::vec3 (s/coll-of (s/double-in :infinite? false :NaN? false)
                         :kind vector?
                         :count 3))

(defn vec3? [x]
  (s/valid? ::vec3 x))

;; Note that I've neglected to add xyz/grb functions like in the book - Clojure
;; destructuring is likely a much better solution. See (NEEDED) for an example.

(s/fdef map-by-coord
  :args (s/cat :f (s/fspec :args (s/cat :a :number?
                                        :b :number?)
                           :ret number?)
               :x ::vec3
               :y ::vec3)
  :ret ::vec3)
(defn map-by-coord [f x y]
  (let [[i j k] x
        [l m n] y]
    [(f i l) (f j m) (f k n)]))

(s/fdef add
  :args (s/cat :x    ::vec3
               :y    ::vec3
               :more (s/* ::vec3))
  :ret  ::vec3)
(defn add
  ([] [0 0 0])
  ([x] x)
  ([x y] (map-by-coord cljs+ x y))
  ([x y & more] (reduce add (add x y) more)))

(s/fdef sub
  :args (s/cat :x    ::vec3
               :y    ::vec3
               :more (s/* ::vec3))
  :ret  ::vec3)
(defn sub
  ([x] (sub [0 0 0] x))
  ([x y] (map-by-coord cljs- x y))
  ([x y & more]
   (reduce sub (sub x y) more)))

(s/fdef mul
  :args (s/cat :x    ::vec3
               :y    ::vec3
               :more (s/* ::vec3))
  :ret  ::vec3)
(defn mul
  ([] [1 1 1])
  ([x] x)
  ([x y] (map-by-coord cljs* x y))
  ([x y & more] (reduce mul (mul x y) more)))


(defn div
  ([x] (div [1 1 1] x))
  ([x y] (map-by-coord cljsdiv x y))
  ([x y & more] (reduce div (div x y) more)))
(s/fdef sub
  :args (s/cat :x    ::vec3
               :y    ::vec3
               :more (s/* ::vec3))
  :ret  ::vec3)

(defn +
  ([] (cljs+))
  ([x] x)
  ([x y] (if (and (vec3? x) (vec3? y))
           (add x y)
           (cljs+ x y)))
  ([x y & more] (reduce + (+ x y) more)))

(defn -
  ([x]
   (if (vec3? x)
     (sub x)
     (cljs- x)))
  ([x y]
   (if (and (vec3? x) (vec3? y))
     (sub x)
     (cljs- x y)))
  ([x y & more]
   (reduce - (- x y) more)))

(defn *
  ([] (cljs*))
  ([x] x)
  ([x y] (if (and (vec3? x) (vec3? y))
           (mul x y)
           (cljs* x y)))
  ([x y & more] (reduce * (* x y) more)))

(defn /
  ([x] (cljsdiv x))
  ([x y] (if (and (vec3? x) (vec3? y))
           (div x y)
           (cljsdiv x y)))
  ([x y & more] (reduce / (/ x y) more)))


(defn square [x]
  (* x x))

(s/fdef length
  :args (s/cat :x ::vec3)
  :ret number?)
(defn length [x]
  (let [[a b c] x]
    (.sqrt js/Math (+ (square a)
                      (square b)
                      (square c)))))

(s/fdef normalize
  :args (s/cat :x ::vec3)
  :ret ::vec3
  :fn  #(<= (Math/abs (- 1.0 (length (:ret %))))
            0.0001))                     ; accurate to three decimal places
(defn normalize [x]
  (let [[a b c] x
        l       (length x)]
    [(/ a l) (/ b l) (/ c l)]))

(s/fdef dot
  :args (s/cat :x ::vec3
               :y ::vec3)
  :ret number?)
(defn dot [x y]
  (reduce + 0 (mul x y)))

(s/fdef cross
  :args (s/cat :u ::vec3
               :v ::vec3)
  :ret ::vec3)
(defn cross [u v]
  (let [[u1 u2 u3] u
        [v1 v2 v3] v]
    [(- (* u2 v3) (* u3 v2))
     (- (* u3 v1) (* u1 v3))
     (- (* u1 v2) (* u2 v1))]))
