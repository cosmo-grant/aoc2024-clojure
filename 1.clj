(ns day1
  (:require [clojure.string :as str]))

(def input (slurp (clojure.java.io/resource "1.txt")))

(defn solve [input]
  (let [rows (map #(str/split % #"\s+") (str/split-lines input))
        col1 (sort (map #(Integer/parseInt (get % 0)) rows))
        col2 (sort (map #(Integer/parseInt (get % 1)) rows))
        distances (map abs (mapv - col1 col2))
        total (reduce + distances)]
    total))

(solve input)
