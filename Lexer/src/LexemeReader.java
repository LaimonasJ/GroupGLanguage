package lexer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Vector;

public class LexemeReader {

private Vector<Lexeme> lexemeVector;	


	
public LexemeReader (String path)  throws IOException, NumberFormatException, ArrayIndexOutOfBoundsException, Exception
{
	lexemeVector = new Vector<Lexeme>();
	
	try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        String line;
        
        
        while ((line = br.readLine()) != null) {
            String[] tokens = line.split("\\s+", 3);
            if (tokens.length != 0) {
            	lexemeVector.add(new Lexeme(tokens[0], tokens[2], Integer.parseInt(tokens[1])));  	
            }
            else break;
        }
	
	
	
	}
	
}

public Lexeme getLexeme(int index)
{
 	Lexeme lexeme = this.lexemeVector.get(index);
	return lexeme;
}

public String toString()
{
	String output = "";
	for (Lexeme lexeme : lexemeVector) {
       output+= lexeme.getLexType() + " " + lexeme.getLexValue() + "\n";
    }
	
	
	
	return output;
	
}

public Vector<Lexeme> getLexemes()
{
   return this.lexemeVector;
}

public int getLexemeCount()
{
	return lexemeVector.size();
}

}