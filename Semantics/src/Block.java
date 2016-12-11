package semantics;

import java.util.*;

public class Block
{
   public Block fatherBlock;
   public String name;
   public ArrayList<Variable> variableList = new ArrayList();
   public ArrayList<Variable> argumentList = new ArrayList();
   public ArrayList<Variable> tempList = new ArrayList();
   public Type returnType;
   public Variable returnValue;
   public ArrayList<String> currentTree = new ArrayList();
   
   
   Block(Block fatherBlock, String name, Type returnType)
   {
      this.fatherBlock = fatherBlock;
      this.name = name;
      this.returnType = returnType;
   }
   
   Variable lookup (String variableName)
   {
      if (variableList.indexOf(new Variable(variableName, null))==-1)
              {
                  return null;
              }
      return variableList.get(variableList.indexOf(new Variable(variableName, null)));
   }
   
      Variable lookupArg (String variableName)
   {
      if (argumentList.indexOf(new Variable(variableName, null))==-1)
              {
                  return null;
              }
      return argumentList.get(argumentList.indexOf(new Variable(variableName, null)));
   }
   
   void printVar ()
   {
       for (Variable cur:variableList)
       {
           System.out.println(cur.name);
       }
       return;
   }
   
   
}
