package lexer;


public class Logger
{
   private boolean enabled;
   Logger(boolean isEnabled)
   {
      this.enabled = isEnabled;
   }
   
   public boolean isEnabled()
   {
      return enabled;
   }
    
   public void enable()
   {
      this.enabled = true;
   }
   
   public void disable()
   {
      this.enabled = false;
   }

   public void log(Object o)
   {  
      if (isEnabled())
      {
         System.out.println(o.toString());
      }
   }
}