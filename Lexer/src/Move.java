package lexer;

/*
*  Contains instructions to follow if symbol is encoutered.
*  nextState is name of the following Lexer state
*  Iterator indicates how much characters to push the reading index;
*/
public class Move
{
   private CharsetDefinition charset;
   private String nextState;
   private int iterator;

   public Move(CharsetDefinition charset, String nextState, int iterator)
   {
      this.charset = charset;
      this.nextState = nextState;
      this.iterator = iterator;
   }
   
   public CharsetDefinition getCharset()
   {
      return charset;
   }

   public void setCharset(CharsetDefinition charset)
   {
      this.charset = charset;
   }

   public String getNextState()
   {
      return nextState;
   }

   public void setNextState(String nextState)
   {
      this.nextState = nextState;
   }

   public int getIterator()
   {
      return iterator;
   }

   public void setIterator(int iterator)
   {
      this.iterator = iterator;
   }
   
   public String toString()
   {
      return this.charset.getName() + " " + this.nextState + " " + this.iterator;
   }
}
