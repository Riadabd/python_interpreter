# Python Interpreter

This project implements a subset of Python (Assignment, Concatenation, Slicing and Simplified Comprehension) using ANTLR.

## Supported Operations

### Assignment

The following assignment operations are supported:

- `L = []` (This defines an empty list)
- `L = [val_1, val_2, …]` where the values can be of different types. For example, `L = [‘hello’, 2, ‘world’]` defines a list of three elements.
- `L[i] = value` assigns a value to the i<sup>th</sup> element of the list. For example, `L[1] = 4` modifies the above list to: `[‘hello’, 4, ‘world’]`.

### Concatenation

The concatention of multiple lists is supported; `L1 + L2` yields a new list L. For example, `[1, 2] + [‘a’, ’b’, ’c’]` creates the list `[1, 2, ‘a’, ’b’, ’c’]`.

### Slicing

- `L[begin_index:end_index]` returns the portion starting at begin_index (inclusive) and ending at end_index (exclusive). For example, the following code: 
```python
  L = [‘a’,’b’,’c’,’d’,’e’] 
  L[1:3] 
```
returns `[’b’,’c’]`.

- `L[begin_index:]` returns the portion from begin_index (inclusive) till the end of the list.

- `L[:end_index]` returns the portion from the beginning of the list till end_index (exclusive).

### Simplified Comprehension

Comprehension of the form: `[for val in list if condition]` is supported. It defines a new list containing a subset of the elements in the original list satisfying the condition. Only lists of integers are supported.

Conditions are restricted to the form: `operand1 op operand2` where **op** is a comparison operator (==, !=, <, <=, >, >=) and each of the operands is either an integer or another expression. `and`, `or`, and `not` used to chain boolean expressions are also supported.

## Example Input and Output

Consider the following input text:
```python
L1 = [3,'hello']    	        print(L1)
L2 = [3,5,'HEY']                print(L2)

L2[0] = 1                       print(L2)

L3 = [L2, 'Add']                print(L3)
L4 = ['First', L3, 'Last']      print(L4)

L5 = L1 + L3                    print(L5)
L1 = L1 + L1 + L1               print(L1)
L6 = L1 + L2 + L3               print(L6)

L7 = L6[:3]                     print(L7)
L8 = L6[:]                      print(L8)

L9 = [1, 2, 3, 4, 5, 6, 7, 8, 9]                                                        print(L9)

c1 = [for x in L9 if (x > 4)]			                                                print(c1)

c2 = [for x in L9 if (5 == 2)]		                                                print(c2)

c3 = [for x in L9 if (5 > x)]			                                                print(c3)

c4 = [for x in L9 if (x == x)]		                                                print(c4)

c5 = [for x in L9 if not (x == 4)]		                                            print(c5)

c6 = [for x in L9 if (x != 4)]		                                                print(c6)

c7 = [for y in L9 if (x != 4)]		                                                print(c7)

c8 = [for x in L9 if (7 != y)]		                                                print(c8)

c10 = [for x in L9 if ((((x > 2) and (7 > x)) or (x == 9)) and (x > 5))] 		    print(c10)

c11 = [for x in L9 if ((((x > 2) and (7 > x)) or not(x == 9)) and (x > 5))]
print(c11)

c12 = [for x in L9 if (( not ((x > 2) and (7 > x)) or not(x == 9)) and (x > 5))]
print(c12)
```

The expected output is as follows:
```
L1 is [3, 'hello']
L2 is [3, 5, 'HEY']
L2 is [1, 5, 'HEY']
L3 is [1, 5, 'HEY', 'Add']
L4 is ['First', 1, 5, 'HEY', 'Add', 'Last']
L5 is [3, 'hello', 1, 5, 'HEY', 'Add']
L1 is [3, 'hello', 3, 'hello', 3, 'hello']
L6 is [3, 'hello', 3, 'hello', 3, 'hello', 1, 5, 'HEY', 1, 5, 'HEY', 'Add']
L7 is [3, 'hello', 3]
L8 is [3, 'hello', 3, 'hello', 3, 'hello', 1, 5, 'HEY', 1, 5, 'HEY', 'Add']
L9 is [1, 2, 3, 4, 5, 6, 7, 8, 9]
c1 is [5, 6, 7, 8, 9]
c2 is []
c3 is [1, 2, 3, 4]
c4 is [1, 2, 3, 4, 5, 6, 7, 8, 9]
c5 is [1, 2, 3, 5, 6, 7, 8, 9]
c6 is [1, 2, 3, 5, 6, 7, 8, 9]
Unknown variable x. Did you mean y?
c7 is []
Unknown variable y. Did you mean x?
c8 is []
c10 is [6, 9]
c11 is [6, 7, 8]
c12 is [6, 7, 8, 9]
```