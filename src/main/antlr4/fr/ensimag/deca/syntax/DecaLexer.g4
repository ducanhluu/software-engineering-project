lexer grammar DecaLexer;

options {
   language=Java;
   // Tell ANTLR to make the generated lexer class extend the
   // the named class, which is where any supporting code and
   // variables will be placed.
   superClass = AbstractDecaLexer;
}

@members {
}

// Deca lexer rules.

//Identificateur
fragment LETTER : 'a'  ..  'z' | 'A' .. 'Z';
fragment DIGIT : '1' .. '9';
IDENT : (LETTER | '$' | '_')(LETTER | DIGIT | '$' | '_')*;

//Litteraux entiers
fragment POSITIVE_DIGIT : '1' .. '9';
INT : POSITIVE_DIGIT DIGIT*;

//Litteraux flottant
fragment NUM : DIGIT+;
fragment SIGN : ('+' | '-' )?;
fragment EXP : ('E' | 'e') SIGN NUM; 
fragment DEC : NUM '.' NUM;
fragment FLOATDEC : (DEC | DEC EXP) ('F' | 'f')?;
fragment DIGITHEX : '0' .. '9' | 'A' .. 'F' | 'a' .. 'f';
fragment NUMHEX : DIGITHEX+;
fragment FLOATHEX : ('0x' | '0X') NUMHEX '.' NUMHEX ('P' | 'p') SIGN NUM ('F' | 'f' )?;
FLOAT : FLOATDEC + FLOATHEX;

//Chaines de caracteres
fragment EOL : '\n';
fragment STRING_CAR : ~('\\'|'"') ;
STRING : '"' (STRING_CAR | '\\"' | '\\\\')* '"';
MULTI_LINE_STRING : '"' (STRING_CAR | EOL |'\\"' | '\\\\')* '"';

//Separateur
WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) {
              skip(); // avoid producing a token
          }
    ;
    
//Inclusion de fichier
fragment FILENAME : (LETTER | DIGIT | '.' | '-' | '_')+;
INCLUDE : '#include' (' ')* '"' FILENAME '"';

//Mots reserves
ASM: 'asm';
CLASS: 'class';
PROTECTED: 'protected';
EXTENDS: 'extends';
ELSE: 'else';
FALSE: 'false';
IF: 'if';
INSTANCEOF: 'instanceof';
NEW: 'new';
NULL: 'null';
READINT: 'readint';
READFLOAT: 'readfloat';
PRINT: 'print';
PRINTLN: 'println';
PRINTX: 'printx';
RETURN: 'return';
THIS: 'this';
TRUE: 'true';
WHILE: 'while';



//Commentaires
COMMENT : '/*' .*? '*/' { skip(); } ;

//Symbole speciaux
// a completer 


