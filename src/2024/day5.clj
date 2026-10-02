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

(defn violation-at [update [left right]] (let [left-index (.indexOf update left)
                                               right-index (.indexOf update right)]
                                           (if (or (= left-index -1) (= right-index -1) (< left-index right-index))
                                             nil
                                             [left-index right-index])))

;; returns a collection of [left right] pairs, indexes where update volates rules, maybe empty
(defn violations-at [update rules] (filter #(not (= nil %)) (map (partial violation-at update) rules)))

(violation-at [75 47 61 53 29] [75 53])
(violation-at [75 47 61 53 29] [53 75])
(violation-at [75 47 61 53 29] [55553 75])
(violations-at [75 47 61 53 29] [[75 53] [53 75] [1321 75]])

(defn satisfies-rules? [update rules] (empty? (violations-at update rules)))

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

(defn comp [rules x y] (if (contains? rules [x y]) -1 (if (contains? rules [y x]) 1 0)))
#_(comp 0 1 #{[0 1]})
#_(comp 0 1 #{[2 3] [1 0]})
#_(comp 0 1 #{[2 3] [2 4]})

(defn fix-order [rules update] (sort-by identity (partial comp rules) update))
#_(fix-order #{[1 3] [1 2] [2 3]} [3 2 1])

(defn solve-part2 [input]
  (let [{rules :rules  updates :updates} (parse input)
        bad-updates (filter #(not (satisfies-rules? % rules)) updates)
        rules (set rules)]
    (->> bad-updates
         (map (partial fix-order rules))
         (map middle)
         (apply +))))

(solve-part2 example)
(solve-part2 input)

