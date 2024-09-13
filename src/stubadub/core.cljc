(ns stubadub.core)

(def ^:dynamic *calls* (atom nil))

(defmacro with-stub [func & opts-and-body]
  (let [has-return-value? (= :returns (first opts-and-body))
        has-return-fn? (= :return-fn (first opts-and-body))
        has-opts? (or has-return-value? has-return-fn?)
        body (if has-opts?
               (nnext opts-and-body)
               opts-and-body)
        args (gensym)]
    `(let [return-value# ~(when has-opts? (second opts-and-body))]
       (with-redefs [*calls* (if @*calls*
                               *calls* (atom []))
                     ~func (fn [& ~args]
                             (swap! *calls*
                                    conj {:func ~func, :args ~args})
                             (cond
                               ~has-return-value? return-value#
                               ~has-return-fn? (return-value# ~args)
                               :else nil))]
         ~@body))))

(defn calls-to [func]
  (if @*calls*
    (keep #(when (= func (:func %)) (:args %)) @*calls*)
    (throw (#?(:clj  RuntimeException.
               :cljs js/Error.) "calls-to used outside of with-stub context!"))))
