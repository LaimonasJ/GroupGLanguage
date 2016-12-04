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
}
