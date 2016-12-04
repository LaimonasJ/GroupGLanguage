package lexer;

import java.util.*;


public class Semantics
{
   ArrayList<String> parserTree = new ArrayList();
   ArrayList<Type> typeList = new ArrayList();
   ArrayList<Block> functionList = new ArrayList();
   
   public Semantics(String parserTree)
   {
      String[] temp = parserTree.split("\\n");
      for(String s : temp)
      {
         if(!s.trim().isEmpty())
         {
            this.parserTree.add(s.trim());
            System.out.println(s.trim());
         }
      }
      
      typeList.add(new Type("string"));
      typeList.add(new Type("int"));
      typeList.add(new Type("char"));
   }
   
   public void run()
   {
      
   }
}
