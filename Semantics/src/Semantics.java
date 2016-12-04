package semantics;

import java.util.*;


public class Semantics
{
   ArrayList<String> parserTree = new ArrayList();
   ArrayList<Type> typeList = new ArrayList();
   ArrayList<Block> functionList = new ArrayList();
   ArrayList<Variable> tempList = new ArrayList();
   static int tempCount = 1;
   
   public Semantics(String parserTree)
   {
      String[] temp = parserTree.split("\\n");
      for(String s : temp)
      {
         if(!s.trim().isEmpty())
         {
            this.parserTree.add(s.trim());
            //System.out.println(s.trim());
            
         }
      }
      

      
      typeList.add(new Type("string"));
      typeList.add(new Type("int"));
      typeList.add(new Type("char"));
   }
   
   public void run()
   {
      Block mainBlock = new Block(null, "main", null);
      int index=(parserTree.indexOf("<main>"));
      parseRemoveRange(0, index, parserTree);
      //System.out.println(parserTree.toString());
      index=(parserTree.indexOf("<statement>"));
      parseRemoveRange(0, index, parserTree);
      //System.out.println(parserTree.toString());
      index=1;
      // System.out.println(parserTree.get(index));
      
      if (parserTree.get(index+1).equals("<declarator>"))
      { 
          String varType = parserTree.get(index+2);
          System.out.println(varType);
          int identifierIndex = parserTree.indexOf("<identifier-list>")+1;
          mainBlock.variableList.add(new Variable(parserTree.get(identifierIndex), typeList.get(1)));
          
          System.out.println("PUSH " + parserTree.get(identifierIndex));
          identifierIndex++;
          while (!parserTree.get(identifierIndex).equals("</identifier-list>"))
          {
              identifierIndex+=2;
              //System.out.println(parserTree.get(identifierIndex));
              //if (!mainBlock.variableList.contains(parserTree.get(indentifierIndex))
              System.out.println("PUSH " + parserTree.get(identifierIndex));
              identifierIndex++;
          }
      }
      
      
       
   }
   
   public void parseRemoveRange (int fromIndex, int toIndex, ArrayList<String> parserTree)
   {
             while (fromIndex <= toIndex)
   {
       parserTree.remove(fromIndex);
       fromIndex++;
   } 
             
   }
   
   
}
