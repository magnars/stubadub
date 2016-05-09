(ns stubadub.test-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [stubadub.core-test]))

(doo-tests 'stubadub.core-test)
