package lexer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.lang.Math;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import lexer.RuleSet;

/*
Laimonas Juras
Vytautas V�g�l�
Kompiuteri� mokslas 2 grup�
*/


public class Lexer
{
   private static Debugger debugger = new Debugger(false);
   private static final String ERR_INVALID = "Invalid identifier";
   private static final String ERR_EOF = "End of file reached while parcing";
   private static State currentState;
   private static int index;
   private static int line;
   private static String lexem;
   
   public static void lexer()
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
      
      //System.out.println(ruleSet.toString());
      
      
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
      
      try{
    	    PrintWriter writer = new PrintWriter("leksemos.txt", "UTF-8");
    	      for(Token t : tokens)
    	      {
    	        writer.println(t);
    	      }
    	      writer.close();
    	} catch (Exception e) {
    	   // do something
    	}
      
      

   }
  

   static String readFile(String path, Charset encoding) 
   throws IOException 
   {
      byte[] encoded = Files.readAllBytes(Paths.get(path));
      return new String(encoded, encoding);
   }



   public static Token getNextToken(RuleSet ruleSet, String input)
   {
      
      if (!nextCharExists(input, index))
      {
         return null;
      }
      //TODO write logic what is considered a token, when token ends, etc..
      char currentChar = input.charAt(index);
      //int newIndex = index;
      String currentCharDefinition =  Character.toString(currentChar);
      int startIndex = index; 
      
      String oldStateName = currentState.getName();
      
      
      
      
      //System.out.println (currentChar);
      

      Set<Move> moves = currentState.getMoveSet();
      Set<String> keys = ruleSet.getKeywords();
      
      //System.out.println("Acquired new MoveSet");
      
      Move currentMove = null;

        
      
      boolean MoveFound = false;
      
      for (Move move : moves)
      {
    	  //System.out.println(move.toString());
    	  if (move.getCharset().getName().equals(currentCharDefinition))
    	  {
    		  currentMove = move;
    		  MoveFound = true;
                  break;
    	  }
      }
      
      
      
      if (currentChar == ' ' || currentChar == '\t' || currentChar == '\r')
      {
          
    	  currentCharDefinition = "WHITESPACE";
    	  //System.out.print(currentCharDefinition + " ");
		  //System.out.println(currentChar);
      }
      
      if (currentChar == '\n')
      {
          
          currentCharDefinition = "ENDOFLINE";
    	  //System.out.print(currentCharDefinition + " ");
		  //System.out.println(currentChar);
      }
      
      else
    	  if (!MoveFound)
    	  {
    		  Set<CharsetDefinition> definitions = ruleSet.getDefinitionSet();
              for (CharsetDefinition definition : definitions)
              {
            	  if (definition.contains(currentChar))
            	  {
            		  currentCharDefinition = definition.getName();
            		  debugger.log(currentCharDefinition + " ");
            		  debugger.log(currentChar);
            		  break;
            	  }
              } 
    	  
             
    	  
    	  }

      for (Move move : moves)
      {
    	  //System.out.println(move.toString());
    	  if (move.getCharset().getName().equals(currentCharDefinition))
    	  {
    		  currentMove = move;
    		  MoveFound = true;
    	  }
      }
      

      
      if (!MoveFound)
      {
    	  currentCharDefinition = "DEFAULT";
    	  
    	  for (Move move : moves)
          {
    		  //System.out.println("DEFAULTED MOVE");
        	  if (move.getCharset().getName().equals(currentCharDefinition))
        	  {
        		  currentMove = move;
        	  }
          }
      }
      
      //if (currentMove != null)
      //System.out.println("Expected next state for this char: " + currentChar + " is " + currentMove.getNextState() );
      //else
      //System.out.println("NULL POINTER WARNING");
      /*Set<State> states = ruleSet.getStateSet();
      
      for (State state : states)
      {
    	  if (state.getName().equals(currentMove.getNextState()))
    	  {
    		  currentState = state;
    		  newIndex+= currentMove.getIterator();
    	  }
    			  
    		  
      }
      */
      
      
      
      currentState = ruleSet.getStateByName(currentMove.getNextState());
      index+= currentMove.getIterator();
      if(currentMove.getIterator()>0)
      {
          if (currentChar == '\n')
          {
              line++;
          }
    	  lexem += Character.toString(currentChar);
      }
      
      debugger.log("Found next state for this char: " + currentState.getName() );
    

    Token token = null;  
      //if (nextCharExists(input, index))
    
    
    String tokenType = " ";
    String tokenValue = " " ;
    String nextStateName = currentState.getName();
    
    if (nextStateName.equals("LEXEMEND"))
    		{
    	tokenType = oldStateName;
    	tokenValue = lexem;
        for (String key :keys)
        {
            if (lexem.equals(" "+key))
            {
                tokenType = "KEYWORD";
            }
        }
        
    		}
    
    if (nextStateName.equals("START"))
    {
    	lexem = " ";
    }
    
     if (nextStateName.equals("ERROR"))
    {
    	lexem = " ";
        tokenType = nextStateName;
    	tokenValue = "INDEX:" + index;
    }
    
    //if(nextCharExists(input, index))
      //{
    	  token =  new Token(tokenType, tokenValue, startIndex, index, line); 
      //}
    
	return token; 

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
      index = 0;
      line = 1;
      
      currentState = ruleSet.getStateByName("START");
      
      
      debugger.log("Initializing");
      lexem = " ";
      for (Move move : currentState.getMoveSet())
      {
         debugger.log(move.toString());
      }
      debugger.log("Starting token creation:");
      debugger.log(" ");
      
      
      do
      {
        
		token = getNextToken(ruleSet, input);
         if(token != null)
         {
            index = token.endIndex;
            if (token.t != " ")
            {
            	result.add(token);	
            }
            
         }
      }while(token != null);
      
      debugger.log(currentState.getName());
      debugger.log(index);
      return result;
   }
}