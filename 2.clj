(ns day2
  (:require [clojure.string :as str]))

(def input (slurp (clojure.java.io/resource "2.txt")))

(defn decreasing? [levels] (every? true? (map < levels (drop 1 levels))))

(defn increasing? [levels] (every? true? (map > levels (drop 1 levels))))

(defn differ-by-at-least-1? [levels] (every? #(>= % 1) (map #(abs (- %1 %2)) levels (drop 1 levels))))

(defn differ-by-at-most-3? [levels] (every? #(<= % 3) (map #(abs (- %1 %2)) levels (drop 1 levels))))

(defn safe? [report] (and
                      (and (differ-by-at-least-1? report) (differ-by-at-most-3? report))
                      (or (decreasing? report) (increasing? report))))

(defn solve-part-1 [input] (let [reports (map #(map parse-long (str/split % #"\s+")) (str/split-lines input))]

                             (count (filter safe? reports))))

(solve-part-1 input)

(defn remove-at [coll i] (concat (take i coll) (drop (inc i) coll)))

(defn but1 [coll] (map remove-at (repeat coll) (range (count coll))))

(defn safe-but1? [report] (boolean (some safe? (but1 report))))

(defn solve-part-2 [input] (let [reports (map #(map parse-long (str/split % #"\s+")) (str/split-lines input))]

                             (count (filter safe-but1? reports))))

(safe-but1? [7 6 4 2 1])
(safe-but1? [1 2 7 8 9])

(solve-part-2 input)
