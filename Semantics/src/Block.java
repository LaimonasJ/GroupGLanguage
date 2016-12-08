package semantics;

import java.util.*;

public class Block
{
   public Block fatherBlock;
   public String name;
   public ArrayList<Variable> variableList = new ArrayList();
   public Type returnType;
   
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
}
