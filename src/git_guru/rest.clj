(ns git-guru.rest)

(defn perform-call [call-fn field-fn url-str param-seq]
  (let [params (split-at 2 param-seq)]
    (reduce (fn [call-base [fst snd]] (. call-base field-fn fst snd)) (call-fn url-str) params)))
