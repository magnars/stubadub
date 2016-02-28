(ns stubadub.core)

(def ^:dynamic *calls*)

(defmacro with-stub [func & opts-and-body]
  (let [has-return-value? (= :returns (first opts-and-body))
        body (if has-return-value? (nnext opts-and-body) opts-and-body)
        return-value (when has-return-value? (second opts-and-body))]
    `(with-redefs [*calls* (if (bound? #'*calls*)
                             *calls* (atom []))
                   ~func (fn [& args#]
                           (swap! *calls*
                                  conj {:func ~func, :args args#})
                           ~return-value)]
       ~@body)))

(defn calls-to [func]
  (if (bound? #'*calls*)
    (keep #(when (= func (:func %)) (:args %)) @*calls*)
    (throw (RuntimeException. "calls-to used outside of with-stub context!"))))
