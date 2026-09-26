(ns day2
  (:require [clojure.string :as str]))

(def example1 "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")
(def example2 "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")
(def input (slurp (clojure.java.io/resource "3.txt")))

(defn sum-of-muls [string]
  (->> string
       (re-seq #"mul\((\d{1,3}),(\d{1,3})\)")
       (map #(map parse-long %))
       (map #(* (nth % 1) (nth % 2)))
       (reduce +)))

(defn dont-to-do-or-end [string] (re-seq #"(?s)don't\(\).*?(?:do\(\)|$)" string))

(->> example2
     (dont-to-do)
     (map sum-of-muls))

(defn sum-of-enabled-muls [string]
  (- (sum-of-muls string) (reduce + (map sum-of-muls (dont-to-do-or-end string)))))

(sum-of-muls example1)
(sum-of-muls input)

(dont-to-do example2)
(sum-of-enabled-muls example2)
(sum-of-enabled-muls input)
