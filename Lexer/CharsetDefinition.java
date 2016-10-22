package lexer;


/*
*Example of Charset: name=DIGIT,  charset=0123456789
*Used to allow defining charsets in a file;
*NOTE too difficult to define special characters like \t\s\r (and how to define EOF?!) etc.. in file,
*     so SOME CharsetDefinition NEED TO BE CREATED IN PROGRAM! (as of now, they are defined in RuleSet 
*/
public class CharsetDefinition
{
   private String name;
   private String charSet;

   public CharsetDefinition(String name, String charSet)
   {
      this.name = name;
      this.charSet = charSet;
   }

   
   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getCharSet()
   {
      return charSet;
   }

   public void setCharSet(String charSet)
   {
      this.charSet = charSet;
   }
   
   public boolean contains(String stringToFind)
   {
      return charSet.contains(stringToFind);
   }
   
   public boolean contains(char charToFind)
   {
      return (charSet.indexOf(charToFind) >= 0);
   }
}
