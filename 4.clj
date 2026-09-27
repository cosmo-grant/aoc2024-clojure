(ns day4
  (:require [clojure.string :as str]))

(def input (slurp (clojure.java.io/resource "4.txt")))
(def example "MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX")

(def wordsearch (str/split-lines input))

(defn coordinates [wordsearch] (let [row-max (count wordsearch)
                                     col-max (count (get wordsearch 0))]
                                 (for [row (range row-max) col (range col-max) :when (= \X (get (get wordsearch row) col))] [row col])))

(def deltas [[[0 0] [1 0] [2 0] [3 0]] ;; down
             [[0 0] [-1 0] [-2 0] [-3 0]] ;; up
             [[0 0] [0 -1] [0 -2] [0 -3]]  ;; left
             [[0 0] [0 1] [0 2] [0 3]] ;; right
             [[0 0] [-1 -1] [-2 -2] [-3 -3]] ;; up-left
             [[0 0] [1 -1] [2 -2] [3 -3]] ;; down-left
             [[0 0] [-1 1] [-2 2] [-3 3]] ;; up-right
             [[0 0] [1 1] [2 2] [3 3]] ;; down-right
             ])

(defn add-coordinates [[row1, col1] [row2 col2]] [(+ row1 row2) (+ col1 col2)])

#_(add-coordinates [1 2] [3 4])

(defn map-add-coordinates [base-coordinates coll-coordinates] (map #(add-coordinates base-coordinates %) coll-coordinates))

#_(map-add-coordinates [1 2] [[3 4] [5 6]])

(defn paths [base-coordinates] (map #(map-add-coordinates base-coordinates %) deltas))

#_(paths [1, 2])

(defn get-char [wordsearch [row, col]] (get (get wordsearch row "") col ""))
(defn word [wordsearch coll-coordinates] (str/join (map #(get-char wordsearch %) coll-coordinates)))

#_(word [[0 0] [1 0] [2 0] [3 0]])

(defn solve [input] (let [wordsearch (str/split-lines input)]
                      (->> (coordinates wordsearch)
                           (mapcat paths)
                           (map (partial word wordsearch))
                           (filter #(= "XMAS" %))
                           count)))

(solve example)
(solve input)

