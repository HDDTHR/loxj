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
program        → statement* EOF ;
statement      → exprStmt
               | printStmt ;
exprStmt       → expression ";" ;
printStmt      → "print" expression ";" ;

expression ->  equality
equality   ->  comparison ( ("==","!=") comparison )*
comparison ->  term       ( (">","<","<=",">=") term )*
term       ->  factor     ( ("-","+") factor )* 
factor     ->  unary      ( ("*","/") unary )* 
unary      ->  ("!","-") unary | primary
primary    -> "nil" | NUMBER | STRING | "true" | "false" | "(" expression ")"
```