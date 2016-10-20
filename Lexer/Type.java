package lexer;

public enum Type
{
   LPAREN, // (
   RPAREN, // )
   SEMICOLON,  // ;
   LBRACK, // [
   RBRACK, // ]
   PERRIOD, // .
   IDENTIFIER, // tekstas5_
   STRING, // "A\"A"
   CHAR, // 'A' '\n'  ')'
   INT,  //  5
   BASEWORD,  //void, struct, int
   ERROR  // type to store information about error
}
