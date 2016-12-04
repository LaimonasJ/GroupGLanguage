package semantics;

import java.util.*;
import semantics.Block;

public class Semantics {

    public static ArrayList<String> parserTree = new ArrayList();
    public static ArrayList<Type> typeList = new ArrayList();
    public static ArrayList<Block> functionList = new ArrayList();

    public Semantics(String parserTree) {
        String[] temp = parserTree.split("\\n");
        for (String s : temp) {
            if (!s.trim().isEmpty()) {
                this.parserTree.add(s.trim());
                //System.out.println(s);
            }
        }

        typeList.add(new Type("string"));
        typeList.add(new Type("int"));
        typeList.add(new Type("char"));
    }

    public void run() {
        
        System.out.println("START");
        Block mainBlock = new Block(null, "main", null);
        int index;

        parserTree.remove(0);
        index = (parserTree.indexOf("<statement>"));

        while (index != -1) {
            parserTree.subList(0, index).clear();
            int identifierIndex = 0;
            index = 0;

            System.out.println(parserTree.get(index + 2));
            switch (parserTree.get(index + 2)) {

                case "<declarator>":
                    String varType = parserTree.get(index + 4);
                    //System.out.println(varType);
                    if (varType.equals("</declarator>")) {
                        varType = parserTree.get(3);
                        System.out.println(varType);
                    }
                    identifierIndex = parserTree.indexOf("<identifier-list>") + 1;

                    System.out.println(lookupType(varType) == null);
                    if ((lookupType(varType) != null) && mainBlock.lookup(parserTree.get(identifierIndex)) == null) {
                        mainBlock.variableList.add(new Variable(parserTree.get(identifierIndex), lookupType(varType)));
                        System.out.println("PUSH " + parserTree.get(identifierIndex));

                        identifierIndex++;
                        while (!parserTree.get(identifierIndex).equals("</identifier-list>")) {
                            identifierIndex += 2;
                            if (mainBlock.lookup(parserTree.get(identifierIndex)) == null) {
                                mainBlock.variableList.add(new Variable(parserTree.get(identifierIndex), lookupType(varType)));
                                System.out.println("PUSH " + parserTree.get(identifierIndex));

                            } else if (mainBlock.lookup(parserTree.get(identifierIndex)) != null) {
                                System.out.println("ERROR: identifier " + mainBlock.lookup(parserTree.get(identifierIndex)).name + " is already defined!");
                            }
                            identifierIndex++;
                        }
                    } else {
                        if (lookupType(varType) == null) {
                            System.out.println("ERROR: undefined type " + varType + "!");
                        }
                        if (mainBlock.lookup(parserTree.get(identifierIndex)) != null) {
                            System.out.println("ERROR: identifier " + mainBlock.lookup(parserTree.get(identifierIndex)).name + " is already defined!");
                        }
                    }
                    break;

                case "<assignment>":

                    varType = parserTree.get(index + 4);
                    System.out.println("DEBUG ASSIGN");
                    System.out.println(varType);
                    if (varType.equals("<type>")) {
                        varType = parserTree.get(5);
                        System.out.println(varType);
                    }
                    identifierIndex = parserTree.indexOf("<expression>") + 1;
                    

                default:
                    break;

            }

            parserTree.remove(0);
            System.out.println(parserTree.toString());
            index = (parserTree.indexOf("<statement>"));
        }
        
       int end= mainBlock.variableList.size()-1; 
       while (!mainBlock.variableList.isEmpty() || end >= 0)
       {
           System.out.println("POP " + mainBlock.variableList.get(end).name);
           mainBlock.variableList.remove(end);
           end--;
       }
      System.out.println("END");
        
        // System.out.println(lookupType("string").name);
        // System.out.println(mainBlock.lookup("a").type.name);

    }

    public ArrayList<String> parseRemoveRange(int fromIndex, int toIndex) {
        parserTree.subList(fromIndex, toIndex).clear();
        return parserTree;
    }

    public static Type lookupType(String typeName) {
        int index = typeList.indexOf(new Type(typeName));
        return index < 0 ? null : typeList.get(index);
    }

}
