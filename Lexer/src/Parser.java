package lexer;

import java.io.IOException;

public class Parser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Lexer.lexer();
		String lexemePath = "C:\\Users\\vytau\\Desktop\\leksemos.txt";
		LexemeReader lexReader;
		try
	      {
			lexReader = new LexemeReader(lexemePath);
	      }
	      catch(IOException e)
	      {
	         System.out.println("Lexeme list file \"" + lexemePath + "\" could not be read!");
	         return;
	      }
		 catch(Exception e)
	      {
	         System.out.println("Failed to parse lexeme file!");
	         return;
	      }
		
		System.out.println(lexReader.toString());
		
	}

}
