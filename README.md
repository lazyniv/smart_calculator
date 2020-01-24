# CLI smart calculator
## Overview
CLI calculator that supports variables and complex expressions.

The calculator has the following set of operators:
```
1) addition as '+'
2) subtraction as '-'
3) multiplication as '*'
4) power as '^'
5) unary plus
6) unary minus
```
The calculator also supports parentheses *()* to prioritize calculations.

## Requirements

Java 1.9 or latter

## Build and run
### If you have gradle:
```
$ grudle build
$ java -jar build/libs/smart_calculator-1.0.jar
```
### Otherwise

for Unix like system

```
$ ./gradlew build
$ java -jar build/libs/smart_calculator-1.0.jar
```

for Windows

```
$ gradlew.bat build
$ java -jar build/libs/smart_calculator-1.0.jar
```
## Examples

```
> 8 * 3 + 12 * (4 - 2)
48
```

```
> 2 - 2 + 3
3
```

```
> -10
-10
```

```
> a=4

> b=5

> c=6

> a*2+b*3+c*(2+3)
53
```

```
> 1 +++ 2 * 3 -- 4
11
```

```
> 3 *** 5
Invalid expression
```

```
> 4 * (2 + 3
Invalid expression
```

```
> a = 8

> b = c
Unknown variable
```

```
> -10 - -20
20
```

```
> a = 2

> a^7
128
```
```
> x = 10

> y = 20

> z = 30

> /vars
(x, 10)
(y, 10)
(z, 10)
```
