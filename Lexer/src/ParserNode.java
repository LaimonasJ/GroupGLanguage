package lexer;

import java.util.*;


/*
 *ParserNode embodies seperate rule from BNF.
 *Rule has a name and a list of alternative path it may follow
 *EXAMPLE IN BNF for ParserNode:
 *
 * nodeName-->program::=  <main>              <--ParserRule
 *                       |<definitions><main> <--ParserRule
 */
public class ParserNode
{
   public String name;
   public List<ParserRule> alter = new ArrayList<ParserRule>(); //list of alternative rules.

   public ParserNode(String name)
   {
      this.name = name;
   }
   
   public String toString()
   {
      String rezult = "";
      rezult += "name: " + name + "\n";
      for (ParserRule rule: alter)
      {
         rezult += "alter: " + rule.toString() + "\n";
      }
      rezult += "\n";
      return rezult;
   }
   
 
}
