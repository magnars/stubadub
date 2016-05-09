(defproject stubadub "1.1.0"
  :description "A small stubbing library for Clojure"
  :url "https://github.com/magnars/stubadub"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies []

  :cljsbuild {:builds [{:id "test"
                        :source-paths ["src" "test"]
                        :compiler {:output-to "out/testable.js"
                                   :output-dir "out"
                                   :main "stubadub.test-runner"
                                   :optimizations :none
                                   :target :nodejs}}]}

  :profiles {:dev {:plugins [[com.jakemccrary/lein-test-refresh "0.14.0"]
                             [lein-doo "0.1.6"]]
                   :dependencies [[org.clojure/clojure "1.8.0"]
                                  [org.clojure/clojurescript "1.8.40"]
                                  [flare "0.2.9"]]
                   :injections [(require 'flare.clojure-test)
                                (flare.clojure-test/install!)]
                   :doo {:build "test"}}})
