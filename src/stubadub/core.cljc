(ns stubadub.core)

(defn invoke-stub [this-stub & args]
  (swap! (.-invocations this-stub) conj (vec args))
  (let [return-fn (.-return-fn this-stub)]
    (return-fn args)))

(deftype Stub [invocations return-fn]
  #?@(:cljs
      [cljs.core/IFn
       (invoke [this]
               (invoke-stub this))
       (invoke [this x1]
               (invoke-stub this x1))
       (invoke [this x1 x2]
               (invoke-stub this x1 x2))
       (invoke [this x1 x2 x3]
               (invoke-stub this x1 x2 x3))
       (invoke [this x1 x2 x3 x4]
               (invoke-stub this x1 x2 x3 x4))
       (invoke [this x1 x2 x3 x4 x5]
               (invoke-stub this x1 x2 x3 x4 x5))
       (invoke [this x1 x2 x3 x4 x5 x6]
               (invoke-stub this x1 x2 x3 x4 x5 x6))
       (invoke [this x1 x2 x3 x4 x5 x6 x7]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17 x18]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17 x18))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17 x18 x19]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17 x18 x19))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17 x18 x19 x20]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17 x18 x19 x20))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17 x18 x19 x20 more]
               (apply invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17 x18 x19 x20 more))]
      :clj
      [clojure.lang.IFn
       (invoke [this]
               (invoke-stub this))
       (invoke [this x1]
               (invoke-stub this x1))
       (invoke [this x1 x2]
               (invoke-stub this x1 x2))
       (invoke [this x1 x2 x3]
               (invoke-stub this x1 x2 x3))
       (invoke [this x1 x2 x3 x4]
               (invoke-stub this x1 x2 x3 x4))
       (invoke [this x1 x2 x3 x4 x5]
               (invoke-stub this x1 x2 x3 x4 x5))
       (invoke [this x1 x2 x3 x4 x5 x6]
               (invoke-stub this x1 x2 x3 x4 x5 x6))
       (invoke [this x1 x2 x3 x4 x5 x6 x7]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17 x18]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17 x18))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17 x18 x19]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17 x18 x19))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17 x18 x19 x20]
               (invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17 x18 x19 x20))
       (invoke [this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17 x18 x19 x20 more]
               (apply invoke-stub this x1 x2 x3 x4 x5 x6 x7 x8 x9 x10 x11 x12 x13 x14 x15 x16 x17 x18 x19 x20 more))
       (applyTo [this args]
                (apply invoke-stub this args))]))

(defn create-stub [{:keys [return-value return-fn]}]
  (Stub. (atom []) (or return-fn (constantly (or return-value nil)))))

(defn- parse-opts-and-body [opts-and-body]
  (let [return-value (and (= :returns (first opts-and-body))
                          (second opts-and-body))
        return-fn    (and (= :return-fn (first opts-and-body))
                          (second opts-and-body))]
    {:opts {:return-value return-value
            :return-fn    return-fn}
     :body (if (or return-value return-fn)
             (nnext opts-and-body)
             opts-and-body)}))

;;;;;;;
;; API

(defn stub [& opts]
  (create-stub (:opts (parse-opts-and-body opts))))

(defmacro with-stub [func & opts-and-body]
  (let [{:keys [opts body]} (parse-opts-and-body opts-and-body)]
    `(with-redefs [~func (create-stub ~opts)]
       ~@body)))

(defn calls-to [stub]
  (if (instance? Stub stub)
    @(.-invocations stub)
    (throw (#?(:clj  RuntimeException.
               :cljs js/Error.)
            (str (type stub) " is not a stub")))))

(defn called-with? [stub & args]
  (boolean (some #{args} (calls-to stub))))
