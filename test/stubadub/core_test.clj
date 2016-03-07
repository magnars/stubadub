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

;; return values by args can be specified

(expect ["not read from disk"
         "not read from disk either"
         nil]
        (with-stub slurp
          :returns-by-args {["test4.txt" :x :y] "not read from disk"
                            ["test5.txt" :y :z] "not read from disk either"}
          [(slurp "test4.txt" :x :y)
           (slurp "test5.txt" :y :z)
           (slurp "test5.txt" :not :matching)]))

;; you can nest several stubs

(expect [["test6.txt" "not read from disk either"]]
        (with-stub spit
          (with-stub slurp :returns "not read from disk either"
            (spit "test6.txt" (slurp "test7.txt"))
            (calls-to spit))))
