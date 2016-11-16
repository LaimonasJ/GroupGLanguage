package lexer;

/**
 * The smallest part of BNF. This class is for every symbol ParselRule has.
 * EXAMPLE IN BNF:
 *    <program> ::= <declarations><main>"{" "}"
 *    BNFSymbols: declarations main { }
 * 
 * BNFSymbols can       be terminal: "{" "void"
 *                  be non terminal: <program> <string>
 *                    have children: <program> <declaration-list>
 *                 have no children: "void" <string>
 */
class BNFSymbol
{
   public String value = "";            //value of a symbol. May be name of next rule, may be value of terminal eg.: main   program
   public boolean terminal;        //all symbols in BNF that are between ""
   public boolean hasChildNodes;   //only non terminal symbols can have children. Some BNF rules may alredy be used by lexer, for example String.
                            //Thefore symbols like <String> , <Char> and <Integer> have no children.
   
   public String fatherNodeName;
   public BNFSymbol previousBNF;
   public BNFSymbol nextBNF;

   public BNFSymbol(String value, String father, BNFSymbol previousBNF, boolean terminal, boolean hasChildNodes)
   {
      this.value = value;
      this.terminal = terminal;
      this.hasChildNodes = hasChildNodes;
      this.fatherNodeName = father;
      this.previousBNF = previousBNF;
   }
   
   public String toString()
   {
      return this.value + " |father node: " + fatherNodeName + "| |previous node: " + (previousBNF == null ? "" : previousBNF.value) + "|" + (terminal ? " |terminal| " : " |non terminal| ") + (hasChildNodes ? " |has children| " : " |no children| ");
   }
}
