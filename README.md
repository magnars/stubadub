# stubadub

A small stubbing library for Clojure and ClojureScript.

To quote [Kris Jenkins](https://twitter.com/krisajenkins) in his [Which Programming Languages Are Functional?](http://blog.jenkster.com/2015/12/which-programming-languages-are-functional.html) blog post:

> Seen through the lens of side-effects, mocks are a flag that your code is
> impure, and in the functional programmer's eye, proof that something is wrong.

So yeah, you should treat stubbing your code much like stubbing your toe: It's
painful and you should strive to avoid it.

Sometimes tho, the code you're testing needs to do some side-effecting. Or maybe
all the code you're maintaining isn't a dreamy pure function landscape.

In that case, feel free to use stubadub.

## Install

Add `[stubadub "2.0.0"]` to `[:profiles :dev :dependencies]` in your `project.clj`.

### Breaking change in 2.0

The old `:returns-by-args` has been rechristened `:return-fn`. The former took
only maps, while the latter can be any function. Its use with maps is unchanged,
apart from the name.

## Usage

```clj
(ns example-test
  (:require [stubadub.core :refer [with-stub calls-to]]))
```

`with-stub` takes a function symbol and replaces it in the scope of its body.
Use `calls-to` to return the list of calls to the stub.

Like this:

```clj
(with-stub spit
  (spit "test1.txt" "not written to disk")
  (spit "test2.txt" "not written to disk either")
  (calls-to spit))

;; => (("test1.txt" "not written to disk")
;;     ("test2.txt" "not written to disk either"))
```

You can specify a return value for the stub with `:returns`:

```clj
(with-stub slurp :returns "not read from disk"
  (slurp "test3.txt"))

;; => "not read from disk"
```

Also, you can supply a function to produce the desired return value with
`:return-fn`. The supplied function will be called with a vector containing
the arguments of the stubbed function:

```clj
(with-stub slurp :return-fn (fn [[file _ _]] (str file " not read from disk"))
  [(slurp "test4.txt" :x :y)
   (slurp "test5.txt" :y :z)])

;; => ["test4.txt not read from disk" "test5.txt not read from disk"]
```

Since the arguments to your function are supplied in a vector, and Clojure maps
indeed are functions themselves, you can conveniently supply a map of arguments
to return value:

```clj
(with-stub slurp :return-fn {["test4.txt" :x :y] "not read from disk"
                             ["test5.txt" :y :z] "not read from disk either"}
  [(slurp "test4.txt" :x :y)
   (slurp "test5.txt" :y :z)])

;; => ["not read from disk" "not read from disk either"]
```

And you can nest several stubs, at which point you're probably not a happy
person:

```clj
(with-stub spit
  (with-stub slurp :returns "not read from disk either"
    (spit "test4.txt" (slurp "test5.txt"))
    (calls-to spit)))

;; => (("test4.txt" "not read from disk either"))
```

### Usage from ClojureScript

Replace

```clj
(:require [stubadub.core :refer [with-stub calls-to]])
```

with

```clj
(:require [stubadub.core :refer [calls-to] :refer-macros [with-stub])
```

### A word of warning

Since stubadub uses `with-redefs` under the hood, your stubs are not
thread-local. This means that parallell tests are out. Open an issue if this is
a blocker for you, and we can look at making a set of thread-local functions.

## Contributors

- [Anders Furseth](https://github.com/andersfurseth) added `:return-fn` and fixed a bug with using stubadub across threads.

Thanks!

## Contribute

I mainly consider this library complete, but if you have a wonderful addition to
the library, please don't let that stop you. :)

Remember to add tests for your feature or fix tho, or I'll
certainly break it later.

### Running the tests

Run tests with

    lein test

Run tests automatically on changes with

    lein test-refresh

Make sure to run the tests for ClojureScript as well:

    lein doo node

You'll have to have the `node` binary installed.

## License

Copyright © 2016 Magnar Sveen

Distributed under the Eclipse Public License, the same as Clojure.
