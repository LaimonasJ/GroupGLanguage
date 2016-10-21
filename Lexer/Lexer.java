package lexer;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Lexer
{
   private static final String ERR_INVALID = "Invalid identifier";
   private static final String ERR_EOF = "End of file reached while parcing";
   
   public static void main(String[] args)
   {
      String programPath = System.getProperty("user.dir") + "\\programa.txt";
      String input;
      try
      {
         input = readFile(programPath, Charset.defaultCharset());   
      }
      catch(IOException e)
      {
         System.out.println("Input file \"" + programPath + "\" not Found!");
         return;
      }
      List<Token> tokens = lex(input);
      for(Token t : tokens)
      {
        System.out.println(t);
      }
   }
  

   static String readFile(String path, Charset encoding) 
   throws IOException 
   {
      byte[] encoded = Files.readAllBytes(Paths.get(path));
      return new String(encoded, encoding);
   }



   public static Token getNextToken(String input, int index)
   {
      index = (skipWhiteSpaceAndComments(input, index)).endIndex;      

      if (endOfFile(input, index))
      {
         return null;
      }
      return new Token(Type.LPAREN, "(", index, index + 1);
   }
   
   public static Token skipWhiteSpaceAndComments (String input, int index)
   {
      int startIndex = index;
      while (true)
      {
         if (endOfFile(input, index))
         {
            return new Token(Type.WHITESPACE, "", startIndex, index);
         }
         
         if (Character.isWhitespace(input.charAt(index)))
         {
            index++;
         }else if (input.charAt(index) == '/')
         {
            index++;
            if (endOfFile(input, index))
            { 
               //File end with '/'
               return new Token(Type.ERROR, ERR_INVALID, startIndex, index);
            }
            if (input.charAt(index) == '/')
            {
               //TODO parse one line comments
            }else
            if (input.charAt(index) == '*')
            {
               //TODO parse multiline comments
            }
            else{
               //line starts with '/', which is invalid
               return new Token(Type.ERROR, ERR_INVALID, startIndex, index);
            }
         }
         else break;
      }
      return new Token(Type.WHITESPACE, "", startIndex, index);
   }
   
   public static boolean nextCharExists(String input, int index)
   {
      return input.length() > index; 
   }
   
   public static boolean endOfFile(String input, int index)
   {
      return input.length() <= index;
   }
   
   public static List<Token> lex(String input)
   {
      List<Token> result = new ArrayList<Token>();
      Token token = null;
      int index = 0;
      do
      {
         token = getNextToken(input, index);
         if(token != null)
         {
            index = token.endIndex;
            result.add(token);
         }
      }while(token != null);
      
      return result;
   }
}




