package lexer;

import java.io.*;
import java.util.*;


/*
*  Parses the Rule file and contains all the program reading rules for lexer.
*/
public class RuleSet
{
   //special character sets. These are needed to be checked while reading symbols;
   public final static String EOF = "EOF";
   public final static String WHITESPACE = "WHITESPACE";
   public final static String ENDOFLINE = "ENDOFLINE";
   
   private Set<State> stateSet;
   private Set<CharsetDefinition> definitionSet;
   
   public RuleSet(String path)
           throws IOException, NumberFormatException, ArrayIndexOutOfBoundsException, Exception
   {
      stateSet = new HashSet<State>();
      
      definitionSet = new HashSet<CharsetDefinition>();
      definitionSet.add(new CharsetDefinition(EOF, EOF));
      definitionSet.add(new CharsetDefinition(WHITESPACE, WHITESPACE));
      definitionSet.add(new CharsetDefinition(ENDOFLINE, ENDOFLINE));
      CharsetDefinition defaultDef = new CharsetDefinition(State.DEFAULT, State.DEFAULT);
      definitionSet.add(defaultDef);
      
      try (BufferedReader br = new BufferedReader(new FileReader(path)))
      {
         String line;
         while ((line = br.readLine()) != null)
         {
            String[] tokens = line.split("\\s+");
            if (tokens.length != 0)
            {
               switch(tokens[0])
               {
                  case "<RULE>":
                     State state = new State(tokens[1]);
                     state.addMove(new Move(defaultDef,
                                            tokens[tokens.length - 2],
                                            Integer.parseInt(tokens[tokens.length - 1])
                                           )
                                   );

                     //tokens[1] is current State
                     //tokens[last-1] and tokens[last]  are for default move
                     for (int i = 2; i < tokens.length - 2; i = i + 3)
                     {
                        //if length is 1, then create new CharsetDefinition to hold new value
                        //                OR use existing CharsetDefinition
                        //EXAMPLE: symbol '*' will use CharsetDefinition with name '*' and charset '*'
                        if (tokens[i].length() == 1)
                        {
                           if(!definitionSetContainsName(tokens[i]))
                           {
                              CharsetDefinition newDef = new CharsetDefinition(tokens[i], tokens[i]);
                              definitionSet.add(newDef);
                              state.addMove(new Move(newDef,
                                                     tokens[i + 1],
                                                     Integer.parseInt(tokens[i + 2])
                                                    )
                                           );
                           }
                        }
                        else
                        {
                           //if symbol definition is longer than one
                           //and it's not already defined, then throw error
                           if(!definitionSetContainsName(tokens[i]))
                           {
                              throw new Exception();
                           }
                        }
                          
                        
                     }
                     stateSet.add(state);
                     break;
                  case "<DEF>":
                     CharsetDefinition def = new CharsetDefinition(tokens[1], tokens[2]);
                     definitionSet.add(def);
                     break;
                  default:
                     break;
               }
               
            }

         } 
      }
   }
   
   
   public Set<State> getStateSet()
   {
      return stateSet;
   }

   public void setStateSet(Set<State> stateSet)
   {
      this.stateSet = stateSet;
   }

   public Set<CharsetDefinition> getDefinitionSet()
   {
      return definitionSet;
   }

   public void setDefinitionSet(Set<CharsetDefinition> definitionSet)
   {
      this.definitionSet = definitionSet;
   }
   
   public boolean definitionSetContainsName(String charsetName)
   {
      for (CharsetDefinition definitionSet : definitionSet)
      {
         if(definitionSet.getName().equals(charsetName))
         {
            return true;
         }
      }
      return false;
   }
   
   
   public CharsetDefinition getDefByName(String charsetName)
   {
      for (CharsetDefinition definitionSet : definitionSet)
      {
         if(definitionSet.getName().equals(charsetName))
         {
            return definitionSet;
         }
      }
      return null;
   }
   
   
   public String toString()
   {
      StringBuilder builder = new StringBuilder();
      for (State state : stateSet)
      {
         builder.append("<RULE> " + state.getName() + "\t\t");
         for (Move move : state.getMoveSet())
         {
            builder.append(move.toString() + "\t");
         }
         builder.append("\n");
      }
      builder.append("\n");
      for (CharsetDefinition def : definitionSet)
      {
         builder.append("<DEF> " + def.getName() + " " + def.getCharSet() + "\n");
      }
      builder.append("\n");
      return builder.toString();
   }
}
