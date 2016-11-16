/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer;

/**
 *
 * @author vyve2358
 */
public class RuleResult {
    
    public int shiftIndex;
    public int AltIndex;
    public String output;

    public RuleResult(int shiftIndex, int AltIndex, String output) {
        this.shiftIndex = shiftIndex;
        this.AltIndex = AltIndex;
        this.output = output;
    }
    

    
}
