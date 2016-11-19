package lexer;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import Lexer.RuleResult;


public class Parser {

    static Logger logger = new Logger(false);
    static Set<String> lexerSymbols = new HashSet();  //Set of BNF nodes predefined in lexer
    static Set<ParserNode> parserNodes = new HashSet();  //Set of Rules
    static LexemeReader lexReader;
    static int maxIndex;
    static long funcCall;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Lexer.lexer();
        funcCall=0;
        String lexemePath = "leksemos.txt";
        try {
            lexReader = new LexemeReader(lexemePath);
        } catch (IOException e) {
            System.out.println("Lexeme list file \"" + lexemePath + "\" could not be read!");
            return;
        } catch (Exception e) {
            System.out.println("Failed to parse lexeme file!");
            return;
        }

        String terminalPath = "LexerPredefinedSet.txt";
        String ParserBNFPath = "ParserBNF.txt";

        maxIndex = lexReader.getLexemeCount();
        //System.out.println(maxIndex);

        logger.log(lexReader.toString());
        logger.disable();

         for (Lexeme lexeme: lexReader.getLexemes())
         {
            if (lexeme.getLexType().equals("ERROR"))
            {
               System.out.println("Error found in lexem list: " + lexeme.getLexValue());
            }
         }
        
        
        try {
            readRules(terminalPath, ParserBNFPath);
        } catch (Exception e) {
            System.out.println("Failed to read " + terminalPath + " or " + ParserBNFPath + "!");
            return;
        }

        
        
        
           if(leftSideRecursion())
         {
            System.out.println("Left side recursion detected! Terminating parsing");
            return;
         }
         if(nullNodePointer())
         {
            System.out.println("Found null nodes! Terminating parsing");
            return;
         }

        ParserNode pradziosTaisykle = getParserNode("program");

        //logger.log(pradziosTaisykle);
        RuleResult newrez = checkRule(pradziosTaisykle, 0, 0, 0);
        if(newrez.output.isEmpty())
                {
                    System.out.println("Non parsable code");  
                    return;
                }
        else
        {
            System.out.println(newrez.output); 
           try{
    	    PrintWriter writer = new PrintWriter("medis.xml", "UTF-8");
            
    	        writer.print(("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n" + newrez.output));
    	      writer.close();
    	} catch (Exception e) {
    	   // do something
    	}  
        }

        
        
         System.out.println(funcCall);

    }

    static void readRules(String terminalPath, String parseRulePath)
            throws IOException {
        //logger.enable();
        try (BufferedReader br = new BufferedReader(new FileReader(terminalPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String lexerSymbol = line.trim();
                if (lexerSymbol.length() != 0) {
                    lexerSymbols.add(lexerSymbol);
                }
            }
        }

        String fileString;
        try (BufferedReader br = new BufferedReader(new FileReader(parseRulePath))) {
            fileString = new String(Files.readAllBytes(Paths.get(parseRulePath)),
                    Charset.defaultCharset()
            );

        }

        Matcher m1 = Pattern.compile("\\s*<([a-zA-Z0-9_-])+>\\s*::=((\\s*(<([a-zA-Z0-9_-])+>|\"[\\S]+\")\\s*)+)(\\|((\\s*(<([a-zA-Z0-9_-])+>|\"[\\S]+\")\\s*)+))*(\\n|$)").matcher(fileString);
        while (m1.find()) {

            String nodeString = m1.group();
            Matcher m2 = Pattern.compile("(?<=^\\s*<)([a-zA-Z0-9_-])+(?=>)").matcher(nodeString);
            m2.find();
            String parserNodeName = m2.group();
            ParserNode node = new ParserNode(parserNodeName);
            //logger.log(parserNodeName);

            m2 = Pattern.compile("(?<=::=)([\\s\\S]*)").matcher(nodeString);
            m2.find();
            String parserRuleListString = m2.group();
            //logger.log(parserRuleList);

            m2 = Pattern.compile("((\\s*(<([a-zA-Z0-9_-])+>|\"[\\S]+\")\\s*)+)(?=\\||$)").matcher(parserRuleListString);
            while (m2.find()) {
                ParserRule rule = new ParserRule();
                String parserSingleRuleString = m2.group();
                BNFSymbol previous = null;
                //logger.log("-----"+parserSingelRule+"-----");
                Matcher m3 = Pattern.compile("(<([a-zA-Z0-9_-])+>)|(\"[\\S]+\")").matcher(parserSingleRuleString);
                while (m3.find()) {
                    BNFSymbol bnf;
                    String bnfSymbolString = m3.group();
                    //logger.log("-----"+bnfSymbol+"-----");
                    if (bnfSymbolString.startsWith("\"") && bnfSymbolString.endsWith("\"")) {
                        bnf = new BNFSymbol(bnfSymbolString.substring(1, bnfSymbolString.length() - 1), node.name, previous, true, false);
                    } else if (bnfSymbolString.startsWith("<") && bnfSymbolString.endsWith(">")) {
                        if (lexerSymbols.contains((bnfSymbolString.substring(1, bnfSymbolString.length() - 1).toUpperCase()))) {
                            bnf = new BNFSymbol(bnfSymbolString.substring(1, bnfSymbolString.length() - 1), node.name, previous, false, false);
                        } else {
                            bnf = new BNFSymbol(bnfSymbolString.substring(1, bnfSymbolString.length() - 1), node.name, previous, false, true);
                        }
                    } else {
                        logger.log("Syntax error in " + parseRulePath + "!");
                        throw new IOException();
                    }
                    previous = bnf;
                    logger.log(bnf);
                    rule.rules.add(bnf);
                }
                node.alter.add(rule);
            }
            parserNodes.add(node);
        }
    }

    /**
     * Gets Rule by its name. Example: "expression" will get <expression> rule
     */
    public static ParserNode getParserNode(String ruleName) {
        for (ParserNode node : parserNodes) {
            if (node.name.equals(ruleName)) {
                return node;
            }
        }
        return null;
    }

    public static boolean leftSideRecursion() {
        logger.enable();
        boolean lfsRecursion = false;
        for (ParserNode node : parserNodes) {
            for (ParserRule rule : node.alter) {
                if (rule.rules.get(0).value.equals(node.name) && !rule.rules.get(0).terminal) {
                    logger.log("Left Side Recursion at " + node.name + "\n");
                    lfsRecursion = true;
                }
            }
        }
        return lfsRecursion;
    }

    public static RuleResult checkRule(ParserNode node, int index, int indexA, int depth) {
        if (depth>1000)
            return new RuleResult(0, 0, "");
        int startIndex = index;
        int altIndex = indexA;
        int shiftIndex = 0;
        String output="";
        funcCall++;
        
        for (int i = 0; (i < node.alter.size()); i++) {
            ParserRule rule = node.alter.get(i);
            //altIndex = i;

            for (BNFSymbol bnf : rule.rules) {
                
                //System.out.println(bnf.toString());
                //System.out.println(lexReader.getLexeme(startIndex + shiftIndex).getLexValue());
                //System.out.println(depth);
                
                
                if ((startIndex + shiftIndex < maxIndex) || (shiftIndex == 0)) {
                    
                    if (bnf.terminal) {
                        //System.out.println(lexReader.getLexeme(startIndex + shiftIndex).getLexValue());
                        
                        if (bnf.value.equals(lexReader.getLexeme(startIndex + shiftIndex).getLexValue())) {
                            //SUCCESS
                            shiftIndex++;
                            if (startIndex + shiftIndex< maxIndex-1){
                            output += "\n" + tabString(depth) + "<terminal> " + (lexReader.getLexeme(startIndex + shiftIndex).getLexValue()) + " </terminal>" + "\n";}
                            //output = " ";
                        } else {
                            //Failure
                            shiftIndex = 0;
                            output="";
                            break;
                        }
                    } else if (!bnf.hasChildNodes) {
                        //System.out.println(lexReader.getLexeme(startIndex + shiftIndex).getLexValue());
                        if (bnf.value.toUpperCase().equals(lexReader.getLexeme(startIndex + shiftIndex).getLexType().toUpperCase())) {
                            shiftIndex++;
                           if (startIndex + shiftIndex< maxIndex-1) {
                           output +=  "\n" + tabString(depth) + "<" + lexReader.getLexeme(startIndex + shiftIndex).getLexType() +"> " + (lexReader.getLexeme(startIndex + shiftIndex).getLexValue()) + "</" + lexReader.getLexeme(startIndex + shiftIndex).getLexType() +"> " + "\n";}
                           //output = " ";
                        } else {
                            shiftIndex = 0;
                            output="";
                            break;
                        }
                    } else {

                        RuleResult newResult = checkRule(getParserNode(bnf.value), startIndex + shiftIndex, 0, depth+1);
                        if (newResult.shiftIndex > 0) {
                            shiftIndex +=  newResult.shiftIndex;
                            output += newResult.output;
                        } else {
                            shiftIndex = 0;
                            output="";
                            break;
                        }
                    }
                } else {
                    shiftIndex = 0;
                    output="";
                    break;
                }
                
                
                
            }

            if (shiftIndex > 0) {
                String newOutput="";
                newOutput += '\n' + tabString(depth) +  "<" + node.name +"> " +  output + "\n" + tabString(depth) + "</" + node.name +"> " + "\n";
                return new RuleResult(shiftIndex, 0, newOutput);
            }
            shiftIndex = 0;
            output="";
        }

        return new RuleResult(0, 0, "");
    }
    
    public static boolean nullNodePointer()
      {
         logger.enable();
         boolean nullPointer = false;
         for(ParserNode node : parserNodes)
         {
            for(ParserRule rule: node.alter)
            {
               for (BNFSymbol symbol: rule.rules)
               {
                  boolean found = false;
                  if(symbol.hasChildNodes)
                  {
                    
                     for(ParserNode nodeCheck: parserNodes)
                     {
                        if (symbol.value.equals(nodeCheck.name))
                        {
                           found = true;
                        }
                        
                     }
                     if(!found)
                     {
                        logger.log("Symbol not found: " + symbol.value);
                        nullPointer = true;
                     }
                     
                  }
               }
               
            }
         }
         return nullPointer;
      }
    
    public static String tabString(int tabs)
    {
        String output="";
        for(int i=0; i<tabs; i++)
        {
            output+='\t';
        }
        return output;
    }
    
    
}
