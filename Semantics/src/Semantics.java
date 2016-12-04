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
        int index;
      
      parserTree.remove(0);  
      index=(parserTree.indexOf("<statement>"));
      
      while(index!=-1)
      {
          
          
      parserTree.subList(0, index).clear();
      int identifierIndex = 0;
      index=0;
      // System.out.println(parserTree.toString());
      // System.out.println(parserTree.get(index+2));
      if (parserTree.get(index+2).equals("<declarator>"))
      { 
          String varType = parserTree.get(index+4);
          System.out.println(varType);
          identifierIndex = parserTree.indexOf("<identifier-list>")+1;
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
      
      parserTree.remove(0);
      System.out.println(parserTree.toString());
      index=(parserTree.indexOf("<statement>")); 
      }
      
      
       
   }
   
   public ArrayList<String> parseRemoveRange (int fromIndex, int toIndex)
   {
             while (fromIndex <= toIndex)
   {
       parserTree.remove(0);
       fromIndex++;
   } 
       return parserTree;      
   }
   
   
}
