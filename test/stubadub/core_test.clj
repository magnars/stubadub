(ns stubadub.core-test
  (:require [expectations :refer :all]
            [stubadub.core :refer :all]))

;; calls-to returns a list of the arguments passed to the stub

(expect [["test1.txt" "not written to disk"]
         ["test2.txt" "not written to disk either"]]
        (with-stub spit
          (spit "test1.txt" "not written to disk")
          (spit "test2.txt" "not written to disk either")
          (calls-to spit)))

;; a return value can be specified

(expect "not read from disk"
        (with-stub slurp :returns "not read from disk"
          (slurp "test3.txt")))

;; you can nest several stubs

(expect [["test4.txt" "not read from disk either"]]
        (with-stub spit
          (with-stub slurp :returns "not read from disk either"
            (spit "test4.txt" (slurp "test5.txt"))
            (calls-to spit))))
