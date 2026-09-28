(ns day5
  (:require
   [clojure.string :as str]))

(def example "3-5
10-14
16-20
12-18

1
5
8
11
17
32")

(defn parse-range [range]
  (mapv parse-long (str/split range #"-")))

(defn parse [input]
  (let [[ranges ids] (->> (str/split input #"\n\n")
                          (map str/split-lines))]

    {:ranges (map parse-range ranges)
     :ids (map parse-long ids)}))

(defn fresh? [ranges id]
  (some (fn [[lower upper]] (<= lower id upper)) ranges))

(defn merge-ranges [[left1 right1] [left2 right2]] [(min left1 left2) (max right1 right2)])

(defn overlapping? [[left1 right1] [left2 right2]] (and left1 right1 left2 right2 (not (or (< right1 left2) (< right2 left1)))))

(conj '(1 3) 5)

(defn compact-ranges [ranges] (->> ranges
                                   sort
                                   (reduce (fn [acc range]
                                             (if (overlapping? (first acc) range)
                                               (conj (pop acc) (merge-ranges (first acc) range))
                                               (conj acc range)))
                                           (list))))

(compact-ranges [[0 2] [1 4] [4 6] [-1 3]])
#_(overlapping? [1 5] [3 8])
#_(overlapping? [1 5] [6 8])
#_(overlapping? [1 6] [6 8])

(fresh? [[1 3] [4 8]] 2)
(fresh? [[1 3] [4 8]] 10)

(defn solve-part1 [input]
  (let [{:keys [ranges ids]} (parse input)]
    (->> ids
         (filter (partial fresh? ranges))
         count)))

(defn solve-part2 [input] (->> input
                               parse
                               :ranges
                               compact-ranges
                               (map #(- (second %) (first %)))
                               (map inc)
                               (apply +)))

(solve-part1 (slurp "resources/year2025day5.txt"))
(solve-part2 (slurp "resources/year2025day5.txt"))
(solve-part2 example)
