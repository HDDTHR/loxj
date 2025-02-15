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
program        → decleration* EOF ;
decleration    → varDecl
               | statement ;
varDecl        → "var" IDENTIFIER ( "=" expression )? ";" ; 
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
primary    -> "nil" |
              IDENTIFIER
              | NUMBER
              | IDENTIFIER
              | STRING
              | "true"
              | "false"
              | "(" expression ")"
```