(defproject stubadub "1.1.0"
  :description "A small stubbing library for Clojure"
  :url "https://github.com/magnars/stubadub"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies []

  :profiles {:dev {:plugins [[lein-expectations "0.0.7"]
                             [lein-autoexpect "1.6.0"]]
                   :dependencies [[org.clojure/clojure "1.8.0"]
                                  [org.clojure/clojurescript "1.8.40"]
                                  [expectations "2.1.4"]
                                  [flare "0.2.9"]]
                   :injections [(require 'flare.expectations)
                                (flare.expectations/install!)]}})
