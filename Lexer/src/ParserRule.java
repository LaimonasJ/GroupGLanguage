package lexer;

import java.util.*;

/**
 *List nodes that define a rule.
 *EXAMPLE IN BNF:
 * 
 * program::= ---> <definitions><main> <--- This is ParserRule
 * 
 */
public class ParserRule
{
   public List<BNFSymbol> rules= new ArrayList<BNFSymbol>();
   
   public String toString()
   {
      String rezult = "";
      for (BNFSymbol bnf : rules)
      {
         rezult+= bnf.value + " ";
      }
      return rezult;
   }
}
