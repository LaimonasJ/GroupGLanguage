package semantics;

import java.util.*;
import semantics.Block;


public class Semantics {

   public static ArrayList<String> parserTree = new ArrayList();
   public static ArrayList<Type> typeList = new ArrayList();
   public static ArrayList<Block> functionList = new ArrayList();
   public static ArrayList<String> expTree;
   public static int tempCount = 0;
   public static String varType;
   public static Block currentBlock;
   

   public Semantics(String parserTree) {
      String[] temp = parserTree.split("\\n");
      for (String s : temp) {
         if (!s.trim().isEmpty()) {
            this.parserTree.add(s.trim());
            //System.out.println(s);
         }
      }

      typeList.add(new Type("string"));
      typeList.add(new Type("int"));
      typeList.add(new Type("char"));
   }

   public void run() {

      //System.out.println("START");
      Block mainBlock = new Block(null, "main", null);
      currentBlock=mainBlock;
      int index;

      parserTree.remove(0);
      index = (parserTree.indexOf("<statement>"));

      while (index != -1) {
         parserTree.subList(0, index).clear();
         int identifierIndex = 0;
         index = 0;

         System.out.println("Statement case: " + parserTree.get(index + 2));
         switch (parserTree.get(index + 2)) {

            case "<declarator>":
               varType = parserTree.get(index + 4);
               //System.out.println(varType);
               if (varType.equals("</declarator>")) {
                  varType = parserTree.get(index + 3);
                  System.out.println(varType);
               }
               identifierIndex = parserTree.indexOf("<identifier-list>") + 1;
                System.out.println(identifierIndex);
                System.out.println(parserTree.get(identifierIndex));
               System.out.println(lookupType(varType) == null);
               
               if ((lookupType(varType) != null) && mainBlock.lookup(parserTree.get(identifierIndex)) == null) {
                  mainBlock.variableList.add(new Variable(parserTree.get(identifierIndex), lookupType(varType)));
                  System.out.println("PUSH " + parserTree.get(identifierIndex));

                  identifierIndex++;
                  while (!parserTree.get(identifierIndex).equals("</identifier-list>")) {
                     identifierIndex += 2;
                     if (mainBlock.lookup(parserTree.get(identifierIndex)) == null) {
                        mainBlock.variableList.add(new Variable(parserTree.get(identifierIndex), lookupType(varType)));
                        System.out.println("PUSH " + parserTree.get(identifierIndex));

                     } else if (mainBlock.lookup(parserTree.get(identifierIndex)) != null) {
                        System.out.println("ERROR: identifier " + mainBlock.lookup(parserTree.get(identifierIndex)).name + " is already defined!");
                     }
                     identifierIndex++;
                  }
               } else {
                  if (lookupType(varType) == null) {
                     System.out.println("ERROR: undefined type " + varType + "!");
                  }
                  if (mainBlock.lookup(parserTree.get(identifierIndex)) != null) {
                     System.out.println("ERROR: identifier " + mainBlock.lookup(parserTree.get(identifierIndex)).name + " is already defined!");
                  }
               }
               break;

            case "<assignment>":

               varType = parserTree.get(index + 4);
               //System.out.println("DEBUG ASSIGN");
               //System.out.println(varType);
               if (varType.equals("<type>")) {
                  varType = parserTree.get(5);
                  String varName = parserTree.get(7);
                  
                   //System.out.println(varName);
                   //System.out.println(varType);
                  
                  if (  lookupType(varType) != null && mainBlock.lookup(varName) == null)
                  {
                      mainBlock.variableList.add(new Variable(varName, new Type(varType))); 
                  }
                 else
                  {
                     if (  lookupType(varType) == null)
                     {
                         System.out.println("ERROR: undefined type " + varType + "!");
                         break;
                     }
                     
                         System.out.println("ERROR: identifier " + varName + " is already defined!");
                         break;
                  }
                  
                  
               }
               int subStart = parserTree.indexOf("<expression>");
               int subEnd = parserTree.indexOf("</assignment>");

               expTree = new ArrayList(parserTree.subList(index, parserTree.size()));
               //rightSide.remove(0);
               //System.out.println(rightSide.toString());
               findExpression(subStart);

            default:
               break;

         }

         parserTree.remove(0);
         //System.out.println(parserTree.toString());
         index = (parserTree.indexOf("<statement>"));
      }

      int end = mainBlock.variableList.size() - 1;
      while (!mainBlock.variableList.isEmpty() || end >= 0) {
         System.out.println("POP " + mainBlock.variableList.get(end).name);
         mainBlock.variableList.remove(end);
         end--;
      }
      //System.out.println("END");

      // System.out.println(lookupType("string").name);
      // System.out.println(mainBlock.lookup("a").type.name);
   }

   public ArrayList<String> parseRemoveRange(int fromIndex, int toIndex) {
      parserTree.subList(fromIndex, toIndex).clear();
      return parserTree;
   }

   public static Type lookupType(String typeName) {
      int index = typeList.indexOf(new Type(typeName));
      return index < 0 ? null : typeList.get(index);
   }

   public static Variable findExpression(int index) {
      
      //System.out.println("PUSH " + name);
      //System.out.println(parserTree.subList(index, parserTree.size()));
      //System.out.println(parserTree.indexOf(index + 1));
      Variable var = findLogical(index + 1);
      if (var == null) {
         return null;
      }
      //System.out.println("MOVE " + temp + ", " + var.name);
      //System.out.println(parserTree.subList(index, parserTree.size()));
      if (parserTree.get(index + 1).equals("?")) {
         
         
         Variable var2 = findExpression(index + 2);  //expression po ?
         if (var2 == null) {
            return null;
            
         }
         
         
         Variable var3 = findExpression(index + 3);  //expression po :
         if (var3 == null) {
            return null;
         }
         Variable rez = new Variable("T" + createID() + "_EXP", null);
         rez.type = var3.type;
         System.out.println(rez.name + "=" + var.name + "?" + var2.name + ":" + var3.name);
      }
      //System.out.println("POP " + temp);
      parserTree.subList(index, parserTree.indexOf("</expression>") + 1).clear();
      return var;
   }

   public static Variable findLogical(int index) {
      //System.out.println(parserTree.subList(index, parserTree.size()));
      //parserTree.subList(index, parserTree.indexOf("</logical-expression>") + 1).clear();
      //System.out.println("PUSH " + name);
      //System.out.println(parserTree.subList(index, parserTree.size()));
      Variable var = findEquality(index + 1);
      if (var == null) {
         return null;
      }

      //System.out.println("MOVE " + temp + ", " + var.name);
      //System.out.println(parserTree.subList(index, parserTree.size()));
      ArrayList<String> alternatives = new ArrayList(Arrays.asList("||", "&amp;&amp;"));
      String nextSymbol = (parserTree.get(index + 1));
      if (alternatives.contains(nextSymbol))
      {
         Variable var2 = findLogical(index + 2);  //expression po ?
         if (var2 == null) {
            return null;
         }
         String name = "T" + createID() + "_LOG";
         Variable rez = new Variable(name, var.type);
         System.out.println(rez.name + "=" + var.name + (nextSymbol.equals("||") ? nextSymbol : "&&") + var2.name);
         parserTree.subList(index, parserTree.indexOf("</logical-expression>") + 1).clear();
         return rez;
      }
      
      parserTree.subList(index, parserTree.indexOf("</logical-expression>") + 1).clear();
      return var;
   }

   public static Variable findEquality(int index) {

      Variable var = findRelational(index + 1);
      if (var == null) {
         return null;
      }

      ArrayList<String> alternatives = new ArrayList(Arrays.asList("!=", "=="));
      String nextSymbol = (parserTree.get(index + 1));
      if (alternatives.contains(nextSymbol))
      {
         Variable var2 = findEquality(index + 2);
         if (var2 == null) {
            return null;
         }
         String name = "T" + createID() + "_EQU";
         Variable rez = new Variable(name, var.type);
         System.out.println(rez.name + "=" + var.name + nextSymbol + var2.name);
         parserTree.subList(index, parserTree.indexOf("</equality-expression>") + 1).clear();
         return rez;
      }
      
      parserTree.subList(index, parserTree.indexOf("</equality-expression>") + 1).clear();
      return var;
   }

   public static Variable findRelational(int index) {
      Variable var = findAdditive(index + 1);
      if (var == null) {
         return null;
      }

      ArrayList<String> alternatives = new ArrayList(Arrays.asList("<", "<=", ">", ">="));
      String nextSymbol = (parserTree.get(index + 1));
      if (alternatives.contains(nextSymbol))
      {
         Variable var2 = findRelational(index + 2);
         if (var2 == null) {
            return null;
         }
         String name = "T" + createID() + "_REL";
         Variable rez = new Variable(name, var.type);
         System.out.println(rez.name + "=" + var.name + nextSymbol + var2.name);
         parserTree.subList(index, parserTree.indexOf("</relational-expression>") + 1).clear();
         return rez;
      }
      
      parserTree.subList(index, parserTree.indexOf("</relational-expression>") + 1).clear();
      return var;
   }

   public static Variable findAdditive(int index) {
      Variable var = findMultiplicative(index + 1);
      if (var == null) {
         return null;
      }

      ArrayList<String> alternatives = new ArrayList(Arrays.asList("+", "-"));
      String nextSymbol = (parserTree.get(index + 1));
      if (alternatives.contains(nextSymbol))
      {
         Variable var2 = findAdditive(index + 2);
         if (var2 == null) {
            return null;
         }
         String name = "T" + createID() + "_ADD";
         Variable rez = new Variable(name, var.type);
         System.out.println(rez.name + "=" + var.name + nextSymbol + var2.name);
         parserTree.subList(index, parserTree.indexOf("</additive-expression>") + 1).clear();
         return rez;
      }
      
      parserTree.subList(index, parserTree.indexOf("</additive-expression>") + 1).clear();
      return var;
   }
   
   public static Variable findMultiplicative(int index)
   {
      Variable var = findUnary(index + 1);
      if (var == null) {
         return null;
      }

      ArrayList<String> alternatives = new ArrayList(Arrays.asList("*", "/"));
      String nextSymbol = (parserTree.get(index + 1));
      if (alternatives.contains(nextSymbol))
      {
         Variable var2 = findMultiplicative(index + 2);
         if (var2 == null) {
            return null;
         }
         String name = "T" + createID() + "_MUL";
         Variable rez = new Variable(name, var.type);
         System.out.println(rez.name + "=" + var.name + nextSymbol + var2.name);
         parserTree.subList(index, parserTree.indexOf("</multiplicative-expression>") + 1).clear();
         return rez;
      }
      
      parserTree.subList(index, parserTree.indexOf("</multiplicative-expression>") + 1).clear();
      return var;
   }
   
   public static Variable findUnary(int index)
   {
      String operator = null;
      if (parserTree.get(index + 1).equals("<unary-operator>")) {
         operator = parserTree.get(index + 2);
         Variable var = findPrimary(index + 4);
         if (var == null)
         {
            return null;
         }
         String name = "T" + createID() + "_UNA";
         Variable rez = new Variable(name, var.type);
         System.out.println(rez.name + "=" + operator + var.name);
         parserTree.subList(index, parserTree.indexOf("</unary-expression>") + 1).clear();
         return rez;
      }
      
      Variable var = findPrimary(index + 1);
      parserTree.subList(index, parserTree.indexOf("</unary-expression>") + 1).clear();
      return var;
   }
   
   public static Variable findPrimary(int index)
   {
      String primaryValue = (parserTree.get(index + 1)); 
       //System.out.println("Variable:" + primaryValue);
       //System.out.println("Is variable int?" + primaryValue.matches("^-?\\d+$"));
       //System.out.println("Is variable string?" + primaryValue.matches("\".*\""));
       //System.out.println("Is variable char?" + primaryValue.matches("\'.*\'"));
       //System.out.println("Current declared variables:") ;
       //currentBlock.printVar();
       // System.out.println("Is declared identifier?" + (currentBlock.lookup(primaryValue) != null));
       if(primaryValue.matches("^-?\\d+$"))
       {
          if (varType.equals("int"))
          {
         System.out.println("CONST INT");
         Variable var = new Variable(primaryValue, new Type("int"));  
         return var;  
          }
          System.out.println("ERROR: Assignment right side constant "+primaryValue+" type int does not match left side variable type " + varType   );
      }
       
      if ((primaryValue.matches("\".*\"")) )
      {
          if (varType.equals("string"))
          {
         System.out.println("CONST STRING");
         Variable var = new Variable(primaryValue, new Type("string"));  
         return var;  
          }
          System.out.println("ERROR: Assignment right side constant "+primaryValue+" type string does not match left side variable type " + varType   );
      }
      
      if ((primaryValue.matches("\'.*\'") ))
      {
          if (varType.equals("char"))
          {
         System.out.println("CONST CHAR");
         Variable var = new Variable(primaryValue, new Type("char"));  
         return var;  
          }
          System.out.println("ERROR: Assignment right side constant "+primaryValue+" type char does not match left side variable type " + varType   );
      }
      
      if (currentBlock.lookup(primaryValue) != null )
      {
         if (currentBlock.lookup(primaryValue).type.name.equals(varType)) 
         {
             System.out.println("DEFINED VAR");
             return currentBlock.lookup(primaryValue);
         }
             System.out.println("ERROR: Assignment right side variable "+primaryValue+" type "+currentBlock.lookup(primaryValue).type.name +" does not match left side variable type " + varType   );
      }
      
     
     
      //System.out.println(parserTree.get(index + 1));
      parserTree.subList(index, parserTree.indexOf("</primary-expression>") + 1).clear();
      return null;
   }
   

   private static long idCounter = 0;

   public static synchronized String createID() {
      return String.valueOf(idCounter++);
   }
}
