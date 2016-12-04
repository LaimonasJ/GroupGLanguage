package semantics;

import java.util.*;

public class Type
{
   public String name;
   public ArrayList<Variable> variableList= new ArrayList();
   
   Type(String name)
   {
      this.name = name;
   }
}
