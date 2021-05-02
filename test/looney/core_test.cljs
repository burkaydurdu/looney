(ns looney.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [looney.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 2))))
