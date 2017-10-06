(ns stubadub.core-test
  #?(:clj  (:require [clojure.test :refer [deftest is]]
                     [stubadub.core :refer [with-stub calls-to]])
     :cljs (:require [cljs.test :refer-macros [deftest is]]
                     [stubadub.core :refer [calls-to] :refer-macros [with-stub]])))

#?(:cljs (defn spit [& args])) ;; these don't exist when running cljs-tests on node.js
#?(:cljs (defn slurp [& args]))

(deftest calls-to-returns-a-list-of-the-arguments-passed-to-the-stub
  (is (= [["test1.txt" "not written to disk"]
          ["test2.txt" "not written to disk either"]]
         (with-stub spit
           (spit "test1.txt" "not written to disk")
           (spit "test2.txt" "not written to disk either")
           (calls-to spit)))))

(deftest a-return-value-can-be-specified
  (is (= "not read from disk"
         (with-stub slurp :returns "not read from disk"
           (slurp "test3.txt")))))

(deftest return-values-by-function-can-be-specified
  (is (= ["test4.txt not read from disk"
          "test5.txt not read from disk"]
         (with-stub slurp
           :return-fn (fn [[file _ _]] (str file " not read from disk"))
           [(slurp "test4.txt" :x :y)
            (slurp "test5.txt" :y :z)]))))

(deftest you-can-nest-several-stubs
  (is (= [["test6.txt" "not read from disk either"]]
         (with-stub spit
           (with-stub slurp :returns "not read from disk either"
             (spit "test6.txt" (slurp "test7.txt"))
             (calls-to spit))))))
