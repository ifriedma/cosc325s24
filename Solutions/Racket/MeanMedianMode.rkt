#lang racket

; calculate mean of a list
(define (mean lis)
  (/ (foldl + 0 lis) (length lis)))

; calculate median of a list
(define (median lis)
  (define sorted-lis (sort lis <))
  (define len (length sorted-lis))
  (define middle (quotient len 2))

  (if (even? len)
      (/ (+ (list-ref sorted-lis middle)
            (list-ref sorted-lis (- middle 1)))
         2)
      (list-ref sorted-lis middle)))

; countAll lis1 lis2
;   lis1 will be traversed recursively
;   lis2 will never change
;   returns a list of how many times each item in lis1 appear in lis2
(define (countAll lis1 lis2)
  (if (= (length lis1) 1)
      (list (cons (car lis1) (countAppearances (car lis1) lis2)))
      (append (list (cons (car lis1) (countAppearances (car lis1) lis2))) (countAll (cdr lis1) lis2))))

;base case - list is empty
(define (countAppearances atm lis)
  (cond
    [(empty? lis) 0]
    [(equal? (car lis)) (+ 1 (countAppearances atm (cdr lis)))]
    [else (countAppearances atm (cdr lis))]))

; orders the given values in a list
(define (pairorder a b)
  (> (cdr a) (cdr b)))

; calculates the mode of a given list
(define (mode lis)
  (let
      [
       (listcounts (countAll (remove-duplicates lis) lis))
       ]
    (car (car (sort listcounts pairorder)))))
