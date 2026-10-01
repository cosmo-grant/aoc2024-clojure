(ns day5
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def example "47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13

75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47")

(def parsed-rules '('(47 53)
  '(97 13)
  '(97 61)
  '(97 47)
  '(75 29)
  '(61 13)
  '(75 53)
  '(29 13)
  '(97 29)
  '(53 29)
  '(61 53)
  '(97 53)
  '(61 29)
  '(47 13)
  '(75 47)
  '(97 75)
  '(47 61)
  '(75 61)
  '(47 29)
  '(75 13)
  '(53 13)))

(def example-update '(75 47 61 53 29))

(satisfies-rule? example-update '(47 13))

(def input (slurp (io/resource "5.txt")))

(defn middle [coll] (nth coll (/ (- (count coll) 1) 2)))
#_(middle [0])
#_(middle [4 5 6])
#_(middle '(4 5 6))

(defn parse [input] (let [[rules updates] (str/split input #"\n\n")
                          rules (str/split rules #"\n")
                          rules (map #(str/split % #"\|") rules)
                          rules (map #(map parse-long %) rules)
                          updates (str/split updates #"\n")
                          updates (map #(str/split % #",") updates)
                          updates (map #(map parse-long %) updates)]
                      {:rules rules :updates updates}))

#_(parse example)

(defn satisfies-rule? [update [left right]] (let [left-index (.indexOf update left)
                                                  right-index (.indexOf update right)]
                                              (or (= left-index -1) (= right-index -1) (< left-index right-index))))

#_(satisfies-rule? [75 47 61 53 29] [75 53])
#_(satisfies-rule? [75 47 61 53 29] [53 75])
#_(satisfies-rule? [75 47 61 53 29] [55553 75])

(defn satisfies-rules? [update rules] (every? (partial satisfies-rule? update) rules))

#_(satisfies-rules? [75 47 61 53 29] [[75 53] [47 61]])
#_(satisfies-rules? [75 47 61 53 29] [[53 75] [47 61]])
#_(satisfies-rules? [75 47 61 53 29] [[55553 75] [47 61] [75 29]])


(defn solve-part1 [input]
  (let [{rules :rules  updates :updates} (parse input)
        ok-updates (filter #(satisfies-rules? % rules) updates)]
    (->> ok-updates
         (map middle)
         (apply +))))

(solve-part1 example)
(solve-part1 input)
