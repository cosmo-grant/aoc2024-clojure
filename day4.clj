(ns day4
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "4.txt")))
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

(defn get-coords [wordsearch character] (let [row-max (count wordsearch)
                                              col-max (count (get wordsearch 0))]
                                          (for [row (range row-max) col (range col-max) :when (= character (get (get wordsearch row) col))] [row col])))

(def xmas-deltas [[[0 0] [1 0] [2 0] [3 0]] ;; down
                  [[0 0] [-1 0] [-2 0] [-3 0]] ;; up
                  [[0 0] [0 -1] [0 -2] [0 -3]]  ;; left
                  [[0 0] [0 1] [0 2] [0 3]] ;; right
                  [[0 0] [-1 -1] [-2 -2] [-3 -3]] ;; up-left
                  [[0 0] [1 -1] [2 -2] [3 -3]] ;; down-left
                  [[0 0] [-1 1] [-2 2] [-3 3]] ;; up-right
                  [[0 0] [1 1] [2 2] [3 3]] ;; down-right
                  ])

(def x-mas-deltas [[[-1 -1] [0 0] [1 1]]
                   [[1 -1] [0 0] [-1 1]]])

(defn add-componentwise [[row1, col1] [row2 col2]] [(+ row1 row2) (+ col1 col2)])

(defn path [base deltas]
  (map (partial add-componentwise base) deltas))

#_(path  [1 2] [[0 0] [1 0]])

(defn paths [base deltas-coll]
  (map (partial path base) deltas-coll))

#_(paths [10 20] xmas-deltas)

(defn get-char [wordsearch [row, col]] (get (get wordsearch row "") col ""))

(defn word [wordsearch path] (str/join (map #(get-char wordsearch %) path)))

(defn count-xmases [words] (count (filter #(= "XMAS" %) words)))

(defn mas? [word] (or (= "MAS" word) (= "SAM" word)))

(defn x-mas? [[first second]] (and (mas? first) (mas? second)))

(defn solve-part1 [input] (let [wordsearch (str/split-lines input)]
                            (->> (get-coords wordsearch \X)
                                 (map #(paths % xmas-deltas))
                                 (map #(map (partial word wordsearch) %))
                                 (map count-xmases)
                                 (reduce +))))

(defn solve-part2 [input] (let [wordsearch (str/split-lines input)]
                            (->> (get-coords wordsearch \A)
                                 (map #(paths % x-mas-deltas))
                                 (map #(map (partial word wordsearch) %))
                                 (filter x-mas?)
                                 (count))))

(solve-part1 example)
(solve-part1 input)

(solve-part2 example)
(solve-part2 input)
