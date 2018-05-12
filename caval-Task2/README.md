Caval
=====

Caval is a Configurable And Value oriented LISP, targeting the JVM.

Language Reference
-------------
### Basic values
```clojure
;; Numbers
=> 5
=> 3.14159
=> -7e19

;; Strings
=> "This is a String!"
=> "Strings are Java Strings\n;-)"

;; Symbols reference other things
=> inc
=> zero?
=> with-hyphen

;; Keywords reference themselves
=> :key
=> :whatever

;; Special value
=> nil
=> true
=> false ;; Every value except nil and false are considered truthy
```
### Collection types
```clojure
;; Collections are immutable
;; Lists - think LinkedList - O(n)
=> '(1 2 3)
=> '(nil, 15, 1.6e-19, "hey") ;; Commas are whitespace, different types are possible

;; Vectors - think ArrayList - O(1)
=> [1 2 3]
=> [nil 15 "hey"]

;; Maps - think HashMap
=> {:key :value, "other key" "other value", 5 true} ;; again, commas are whitespace
```
### Functions
```clojure
;; calling a function
(fn-name arg1 arg2)
;; creating a function
(fn [argument] body)
```
### Variables
```clojure
;; define global bindings
=> (def a 1)
=> (def inc (fn [n] (+ n 1)))

;; define local bindings
=> (let [a 1
         b (* a 2)]
     (/ b a))
```
### Other features
```clojure
;; conditional expressions
=> (if condition then else)

;; Quoting
;; Normally, parameters to functions are evaluated and lists are considered function calls.
;; LISPs however have the feature to not evaluate the code and treat it as data instead.
;; Then some other code can manipulate the code and evaluate it, basically creating a domain-specific
;; language. The mechanism for this is quoting, using the special function quote or the reader-macro '
=> (quote (1 2 3)) ;; yields (1 2 3) as data, instead of a (failing) function-call
=> '(1 2 3) ;; expands to the above form
```
### Standard library
- Bindings (`def`, `let`)
- Arithmetic (`+`, `-`, `*`, `/`)
- Collections (`conj`, `first`, `rest`, `count`) Maps and Vectors are functions of their keys
- Logic (`not`, `=`, `<`, `<=`, `>`, `>=`)
- IO (`print`, `println`, `exit`)
- Functions (`fn`)
- Conditional (`if`)
- Quoting (`quote`, `eval`)
