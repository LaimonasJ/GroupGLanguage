package semantics;

import java.util.*;

public class Block
{
   Block fatherBlock;
   String name;
   ArrayList<Variable> variableList = new ArrayList();
   
   Block(Block fatherBlock, String name)
   {
      this.fatherBlock = fatherBlock;
      this.name = name;
   }
}
