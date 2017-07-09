(ns raytrace-weekend.test-runner
  (:require [clojure.test :refer [deftest is]]
            [doo.runner :refer-macros [doo-tests]]
            [raytrace-weekend.vec3-test]))

(enable-console-print!)
(doo-tests 'raytrace-weekend.vec3-test)
