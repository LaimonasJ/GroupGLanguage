package semantics;

import java.util.Objects;

class Variable
{
   public String name;
   public Type type;

   public Variable(String name, Type type)
   {
      this.name = name;
      this.type = type;
   }

   @Override
   public int hashCode()
   {
      int hash = 7;
      hash = 71 * hash + Objects.hashCode(this.name);
      return hash;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      if (obj == null)
      {
         return false;
      }
      if (getClass() != obj.getClass())
      {
         return false;
      }
      final Variable other = (Variable) obj;
      if (!Objects.equals(this.name, other.name))
      {
         return false;
      }
      return true;
   }
   
   
}
