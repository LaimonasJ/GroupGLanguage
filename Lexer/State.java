package lexer;

import java.util.*;

/*
 *Keeps the INFO of behaviour what to do at each STATE of Lexer
 *For example:   State name: START
 *               Move List:  -EOF END 0
 *                           -LETTER IDENTIFIER 1
 *                           -' CHARACTER 1
 */
public class State
{
   //Default move doesn't have charset. 
   public final static String DEFAULT = "DEFAULT";
   
   private String name; 
   private Set<Move> moveSet;

   public State(String name)
   {
      moveSet = new HashSet<Move>();
      this.name = name;
   }
   

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public Set<Move> getMoveSet()
   {
      return moveSet;
   }

   public void setMoveSet(Set<Move> moveSet)
   {
      this.moveSet = moveSet;
   }
   
   public void addMove(Move move)
   {
      this.moveSet.add(move);
   }
}
