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
      String ruleListPath = "Rules.txt";
      RuleSet ruleSet;
      try
      {
         ruleSet = new RuleSet(ruleListPath);
      }
      catch(IOException e)
      {
         System.out.println("Rule list file \"" + ruleListPath + "\" could not be read!");
         return;
      }
      catch(Exception e)
      {
         System.out.println("Failed to parse rule file!");
         return;
      }
      
      System.out.println(ruleSet.toString());
      
      
      String programPath = "programa.txt";
      String input;
      try
      {
         input = readFile(programPath, Charset.defaultCharset());   
      }
      
      catch(IOException e)
      {
         System.out.println("Input file \"" + programPath + "\" could not be read!");
         return;
      }
      
      
      List<Token> tokens = lex(input, ruleSet);
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



   public static Token getNextToken(RuleSet ruleSet, String input, int index)
   {
      //TODO write logic what is considered a token, when token ends, etc..
      Token token =  new Token(Type.INT, "-5", 0, index + 1);
      return  index == 3 ? null : token;
   }

   
   public static boolean nextCharExists(String input, int index)
   {
      return input.length() > index; 
   }
   
   public static boolean endOfFile(String input, int index)
   {
      return input.length() <= index;
   }
   
   public static List<Token> lex(String input, RuleSet ruleSet)
   {
      List<Token> result = new ArrayList<Token>();
      Token token = null;
      int index = 0;
      do
      {
         token = getNextToken(ruleSet, input, index);
         if(token != null)
         {
            index = token.endIndex;
            result.add(token);
         }
      }while(token != null);
      
      return result;
   }
}




