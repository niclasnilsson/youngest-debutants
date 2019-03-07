(ns guitarists-compact
  (:require [clojure.string :refer [blank? split split-lines trim]]))
  
(def indata
  "
  Duane Allman, 1946, Hour Glass, 1967
  Eric Clapton, 1945, Five Live Yardbirds, 1964
  John Frusciante, 1970, Mother's Milk, 1989
  Jimi Hendrix, 1942, Are You Experienced?, 1967
  David Howell Evans (The Edge), 1961, Boy, 1980
  Jimmy Page, 1944, Little Games, 1967
  Steve Vai, 1960, Tinsel Town Rebellion, 1981
  Eddie Van Halen, 1955, Van Halen, 1978
  Tom Morello, 1964, Rage against the machine, 1992
  ")

(defrecord Guitarist [name year-of-birth record-name release-year])

(defn ->int-or-str [v]
  (if (re-matches #"\d+" v) (read-string v) v))

(defn str->guitarist [line]
  (->> (split line #",") (map trim) (map ->int-or-str) (apply ->Guitarist)))

(defn str->guitarists [s]
  (->> s (split-lines) (remove blank?) (map str->guitarist)))

(defn debut-age [guitarist]
  (- (:release-year guitarist) (:year-of-birth guitarist)))

(defn youngest-debutants [guitarists]
  (->> guitarists (group-by debut-age) sort first second))

(let [guitarists (str->guitarists indata)]
  (doseq [guitarist (youngest-debutants guitarists)]
    (println
      (format "%s was %d when %s was released.", 
              (:name guitarist)
              (debut-age guitarist)
              (:record-name guitarist)))))
