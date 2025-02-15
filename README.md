```
program        → decleration* EOF 
decleration    → varDecl
               | statement 
varDecl        → "var" IDENTIFIER ( "=" expression )? ";" 
statement      → exprStmt
               | printStmt
               | ifStmt
               | block

ifStmt         → "if" "(" expression ")" statement
               ( "else" statement )? ;
block          → "{" declaration* "}"
exprStmt       → expression ";"
printStmt      → "print" expression ";"

expression     → assignment
assignment     → IDENTIFIER "=" assignment
               | logic_or ;
logic_or       → logic_and ( "or" logic_and )* ;
logic_and      → equality ( "and" equality )* ; 
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