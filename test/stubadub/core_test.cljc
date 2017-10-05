(ns stubadub.core-test
  #?(:clj  (:require [clojure.test :refer [deftest is testing]]
                     [stubadub.core :as sut])
     :cljs (:require [cljs.test :refer-macros [deftest is testing]]
                     [stubadub.core :as sut :include-macros true])))

#?(:cljs (defn spit [& args])) ;; these don't exist when running cljs-tests on node.js
#?(:cljs (defn slurp [& args]))

(deftest stub
  (testing "a stub is a function"
    (is (ifn? (sut/stub))))

  (testing "a stub returns nil by default"
    (let [stub (sut/stub)]
      (is (nil? (stub)))))

  (testing "a stub can take any number of arguments"
    (let [stub (sut/stub)]
      (is (nil? (stub 1)))
      (is (nil? (stub 1 2)))
      (is (nil? (stub 1 2 3)))
      ;; more than 20 args to stub not supported on cljs
      #?(:clj (is (nil? (apply stub (range 1 100)))))))

  (testing "Wohoooo! cljs is now supporting more than 20 arguments to IFns!"
    #?(:cljs (is (thrown? js/Error (apply (sut/stub) (range 1 100))))))

  (testing "a return value can be specified"
    (let [stub (sut/stub :returns "awesomesauce")]
      (is (= (stub) "awesomesauce"))))

  (testing "return values by function can be specified"
    (let [stub (sut/stub :return-fn {[1] :a [2 3] :b})]
      (is (= (stub 1) :a))
      (is (= (stub 2 3) :b))
      (is (nil? (stub 2))))))

(deftest calls-to
  (testing "calls-to on stub returns arguments of every invocation"
    (let [stub (sut/stub)]
      (is (= (sut/calls-to stub)
             []))
      (stub)
      (is (= (sut/calls-to stub)
             [[]]))
      (stub 1)
      (is (= (sut/calls-to stub)
             [[] [1]]))
      (stub 1 2)
      (is (= (sut/calls-to stub)
             [[] [1] [1 2]]))))

  (testing "calls-to on non-stub object throws exception"
    (is (thrown-with-msg?
         #?(:clj  RuntimeException
            :cljs js/Error)
         #" is not a stub"
         (sut/calls-to inc)))))

(deftest called-with?
  (testing "called-with? answers whether stub has been called with the supplied arguments"
    (let [stub (sut/stub)]
      (is (not (sut/called-with? stub 1 2)))
      (stub 1 2)
      (is (sut/called-with? stub 1 2))
      (is (not (sut/called-with? stub 1)))
      (is (not (sut/called-with? stub 1 2 3)))
      (stub 1 2 3)
      (is (sut/called-with? stub 1 2 3))
      (is (sut/called-with? stub 1 2)))))

(deftest with-stub
  (testing "takes a var to be replaced with a stub"
    (is (= [["test1.txt" "not written to disk"]
            ["test2.txt" "not written to disk either"]]
           (sut/with-stub spit
             (spit "test1.txt" "not written to disk")
             (spit "test2.txt" "not written to disk either")
             (sut/calls-to spit)))))

  (testing "a return value can be specified"
    (is (= "not read from disk"
           (sut/with-stub slurp :returns "not read from disk"
             (slurp "test3.txt")))))

  (testing "return values by function can be specified"
    (is (= ["test4.txt not read from disk"
            "test5.txt not read from disk"]
           (sut/with-stub slurp
             :return-fn (fn [[file _ _]] (str file " not read from disk"))
             [(slurp "test4.txt" :x :y)
              (slurp "test5.txt" :y :z)]))))

  (testing "you can nest several stubs"
    (is (= [["test6.txt" "not read from disk either"]]
           (sut/with-stub spit
             (sut/with-stub slurp :returns "not read from disk either"
               (spit "test6.txt" (slurp "test7.txt"))
               (sut/calls-to spit)))))))
