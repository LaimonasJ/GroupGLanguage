package lexer;

/**
 *
 * @author Laimonas
 */
public class Token
{
   public final String t;          //type of lexeme
   public final String val;      //value of lexeme
   public final int startIndex;  //index of a start of the lexeme for error reporting
   public final int endIndex;    //start of the next lexeme/whitespace after lexeme.
                                 //endIndex is end of token + 1;public int endIndex;

   public Token(String t, String val, int startIndex, int endIndex)
   {
      this.t = t;
      this.val = val;
      this.startIndex = startIndex;
      this.endIndex = endIndex;
   }
   public String toString()
   {
      return t.toString() + " " + val;
   }
}
