(ns stubadub.core)

(def ^:dynamic *calls*)

(defmacro with-stub [func & opts-and-body]
  (let [has-return-value? (= :returns (first opts-and-body))
        has-return-value-by-args? (= :returns-by-args (first opts-and-body))
        has-opts? (or has-return-value? has-return-value-by-args?)
        body (if has-opts?
               (nnext opts-and-body)
               opts-and-body)
        return-value (when has-opts? (second opts-and-body))
        args (gensym)]
    `(with-redefs [*calls* (if (bound? #'*calls*)
                             *calls* (atom []))
                   ~func (fn [& ~args]
                           (swap! *calls*
                                  conj {:func ~func, :args ~args})
                           ~(cond
                              has-return-value? return-value
                              has-return-value-by-args? `(get ~return-value ~args)
                              :else nil))]
       ~@body)))

(defn calls-to [func]
  (if (bound? #'*calls*)
    (keep #(when (= func (:func %)) (:args %)) @*calls*)
    (throw (RuntimeException. "calls-to used outside of with-stub context!"))))
