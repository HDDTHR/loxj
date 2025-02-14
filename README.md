Order of precedence bottom to top
```
== !=
> < => =<
+ -
* /
(unary) + -
parenthesis primary
```

```
expression ->  equality
equality   ->  comparison ( ("==","!=") comparison )*
comparison ->  term       ( (">","<","<=",">=") term )*
term       ->  factor     ( ("-","+") factor )* 
factor     ->  unary      ( ("*","/") unary )* 
unary      ->  ("!","-") unary | primary
primary    -> "nil" | NUMBER | STRING | "true" | "false" | "(" expression ")"
```