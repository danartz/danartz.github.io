; Daniel Artz
; Recursive Definitions Assignment 1

; Develop the function symSumSep. It consumes one flat list, L.
; Depending on the list entered, a new flat list is returned or an empty list.
; CONTRACT: symNumSep: list -> {new list} 
; PURPOSE: symNumSep consumes one flat and separates integers from characters.
;  It then returns the result as a list with integers being separated from characters.
; CODE:
(define (symNumSep L)
  (append (appendAlpha L) (appendNumber L))
  )

(define (appendNumber lis)
  (cond
    ((null? lis) lis)
    ((integer? (car lis))
    (cons (car lis) (appendNumber (cdr lis))) 
  )
    (else (appendNumber (cdr lis)))
  )
  )

(define (appendAlpha lis)
  (cond
    ((null? lis) lis)
    ((not (integer? (car lis)))     
    (cons (car lis) (appendAlpha (cdr lis))) 
  )
    (else (appendAlpha (cdr lis)))
  )
  )
; TEST CASES and EXPECTED OUTCOMES - 
;(symNumSep '()) = '()
;(symNumSep '(5)) = '(5)
;(symNumSep '(1 a c 2)) = '(a c 1 2)
;(symNumSep  '(a 1 c 8 5 e d f)) = '(a c e d f 1 8 5)

;Develop the function dupSymSpl. It consumes one flat or nested list lis1.
; It returns an empty list or a list with characters and integers and characters duplicated.
; CONCTRACT: dupSymSpl: list -> {new list ignoring strings and duplicating characters}
; PURPOSE: dupSymSpl takes one list and returns a list. Strings are deleted
; and characters are duplicated. Nested list's keep their structure and order
; is maintained.
; CODE:
(define (dupSymSpl lis1)
  (cond ((null? lis1) '())
        ((list? (car lis1))
         (cons (dupSymSpl (car lis1)) (dupSymSpl (cdr lis1)))
         )
         ((not (String? (car lis1)))
            (cond
              ((integer? (car lis1))
               (cons (car lis1) (dupSymSpl (cdr lis1)))
               )
              (else (cons (car lis1) (cons (car lis1) (dupSymSpl (cdr lis1)))))
            )
            
         )
         (else (dupSymSpl (cdr lis1)) )             
  )
  )
;TEST CASES and EXPECTED OUTCOMES -
; (dupSymSpl '(())) = '(())
; (dupSymSpl '(a)) = '(a a)
; (dupSymSpl '(a 1 b (1 c))) = '(a a 1 b b (1 c c))
; (dupSymSpl '(a 1 b ("test" 1 c))) = '(a a 1 b b (1 c c))
; (dupSymSpl '(a 1 b ("test" 1 c))) = '(a a 1 b b (1 c c))
; (dupSymSpl '(a 1 b ("test" 1 c (a)))) = '(a a 1 b b (1 c c (a a)))

; Develop the function dotProduct. It consumes 2 lists lis1 and lis2
; The two lists are treated as vectors and the dot product of both lists is returned as a list.
; CONTRACT: dotProduct: lis1 lis2 -> {dot product of lis1 and lis2}
; PURPOSE: dotProdct takes two lists and returns the dot product of both lists.
; CODE:
(define (dotProduct lis1 lis2)
  (cond
    ((null? lis1) 0)
    ((= (length lis1) (length lis2))
    (+ (* (car lis1) (car lis2)) (dotProduct (cdr lis1) (cdr lis2)))
     )
    (else (display "INVALID EXPRESSION: The lists don't have equal lengths"))
  )
  )
;TEST CASES and EXPECTED OUTCOMES -
; (dotProduct '(0) '(0)) = 0
; (dotProduct '(5 2) '(1 3)) = 11
; (dotProduct '(5 2) '(1 3 0)) = "INVALID EXPRESSION: The lists don't have equal lengths"
; (dotProduct '() '()) = 0
; (dotProduct '(5 2 5) '(1 3 9)) = 56

; Develop the function postfix which consumes one list lis1.
; Lis1 is a prefix expression which is converted and returned as a list in postfix form.
; CONTRACT: postfix: lis1 -> {'X, '(X1 X2 X3 O2 O1)}
; PURPOSE: postfix consumes an atomic value or list in prefix form and returns the atomic value, or list in postfix form.
; CODE:
(define (postfix lis1)
  (cond ((not (pair? lis1))
        lis1 
        )
  (else(postfixHelper lis1))
  )
  )

(define (postfixHelper lis1)
  (cond ((null? lis1) lis1)
        ((eqv? '+ (car lis1))
         (append (postfixHelper (cdr lis1)) (list (car lis1)))
         )
        ((eqv? '* (car lis1))
         (append (postfixHelper (cdr lis1)) (list (car lis1)))
         
         )
        ((not(list? (car lis1)))
         (cons (car lis1) (postfixHelper (cdr lis1)))
         )
        (else (cons (postfixHelper (car lis1)) (postfixHelper (cdr lis1))))             
  )
  )
; TEST CASES and EXPECTED OUTCOMES
; (postfix '5) = 5
; (postfix '(+ 5 4)) = '(5 4 +)
; (postfix '(+ 5 4 (* 3 2))) = '(5 4 (3 2 *) +)
; (postfix '(+ * 5 4 2 (* 3 2))) = '(5 4 2 (3 2 *) * +)

; Develop the function foldr which consumes 3 arguments. The 3 arguments
; are used to fold instructions and values from right to left between the elements
; of argument 3.
; CONTRACT: foldr arg1 arg2 arg3 -> {right folded list}
; PURPOSE: foldr takes 3 arguments and returns a list with arg2 being folded in arg3's list
; based on the operation entered in arg1.
; CODE:
(define (foldr arg1 arg2 arg3)
  (if(null? arg3)
     arg2(arg1(car arg3)(foldr arg1 arg2(cdr arg3)))
     )
  )
; TEST CASES and EXPECTED OUTCOMES
; (foldr cons '() (list)) = '()
; (foldr + 1 (list 1 2 3)) = 7
; (foldr cons '(1) (list 1 2 3)) = '(1 2 3 1)
; (foldr * 2 (list 1 2 3)) = 12

(define (lengt L)
  (cond
    ((null? L) 0)
    (else (+ 1(lengt (foldr(cons '() (cdr L))))))
  )
  )

(define (hello lst)
  (cond ((null? lst) 0)
        
        (else (+ 1 (hello (cdr lst))))))