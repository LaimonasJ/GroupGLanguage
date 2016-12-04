package semantics;

import java.util.*;


public class Semantics
{
   public static ArrayList<String> parserTree = new ArrayList();
   public static ArrayList<Type> typeList = new ArrayList();
   public static ArrayList<Block> functionList = new ArrayList();
   
   public Semantics(String parserTree)
   {
      String[] temp = parserTree.split("\\n");
      for(String s : temp)
      {
         if(!s.trim().isEmpty())
         {
            this.parserTree.add(s.trim());
            //System.out.println(s);
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
         
         System.out.println(lookupType("string").name);
         System.out.println(mainBlock.lookup("a").type.name);
      }
      
      
       
   }
   
   public ArrayList<String> parseRemoveRange (int fromIndex, int toIndex)
   {
      parserTree.subList(fromIndex, toIndex).clear();
      return parserTree;      
   }
   
   public static Type lookupType(String typeName)
   {
      return typeList.get(typeList.indexOf(new Type(typeName)));
   }
   
}
