package lexer;

/**
 *
 * @author Laimonas
 */
public class Token
{
   public final Type t;          //type of lexeme
   public final String c;        //value of lexeme
   public final int startIndex;  //index of a start of the lexeme for error reporting
   public final int endIndex;    //index of the end of lexeme to know where to read next.
                                 //end index is tokenEnd + 1;

   public Token(Type t, String c, int startIndex, int endIndex)
   {
      this.t = t;
      this.c = c;
      this.startIndex = startIndex;
      this.endIndex = endIndex;
   }
   public String toString()
   {
      return t.toString() + " " + c;
   }
}
