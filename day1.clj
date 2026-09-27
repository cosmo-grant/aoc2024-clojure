(ns day1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "1.txt")))

(defn solve-part-1 [input]
  (let [rows (map #(str/split % #"\s+") (str/split-lines input))
        col1 (sort (map #(Integer/parseInt (get % 0)) rows))
        col2 (sort (map #(Integer/parseInt (get % 1)) rows))
        distances (map abs (mapv - col1 col2))
        total (reduce + distances)]
    total))

(solve-part-1 input)

(defn solve-part-2 [input]
  (let [rows (map #(str/split % #"\s+") (str/split-lines input))
        col1 (sort (map #(Integer/parseInt (get % 0)) rows))
        col2 (sort (map #(Integer/parseInt (get % 1)) rows))
        col2-freq (frequencies col2)
        scores (map #(* % (get col2-freq % 0)) col1)]
    (reduce + scores)))

(solve-part-2 input)
