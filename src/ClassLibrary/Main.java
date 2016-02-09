/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassLibrary;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cory Sabol
 */
public class Main {
    
    public static void main(String[] args) {
        
        // Start the repl from here
        CmdRepl repl = new CmdRepl();
        
        try {
            
            if (args.length != 0 && args[1].equals("gui")) {
                // Launch the gui from here
            } else if (args.length == 1 || args.length == 0) {
                repl.startRepl("");
            } else {
                // Print out usage to term
            }            
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
