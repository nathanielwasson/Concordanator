/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.  Tony made a change.
 */
package ClassLibrary;

import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cory Sabol - cssabol@uncg.edu
 * 
 * This class depends on the Concordance class. 
 */
public class CmdRepl implements Serializable {
    
    private String[] args;
    private boolean exit;
    private String prompt = "> ";
    private InputStreamReader reader;
    private BufferedReader in;
    private String cmdStr;
    private boolean conLoaded;
    private Bookshelf shelf;
    private Concordance concord;
  
    private enum Commands {
        load, 
        help, 
        listbooks, 
        listcons,
        build,
        invalid, 
        exit;
    }
    
    /***
     * Constuctor for the repl.
     * @param args 
     */
    public CmdRepl() {
       //this.args = args;
       this.reader = new InputStreamReader(System.in);
       this.in = new BufferedReader(reader);
       this.exit = false;
       this.conLoaded = false;
       this.shelf = new Bookshelf();
       
//       for (String arg : args) {
//           if (arg.equals("gui")) {
//              // Launch gui from here.
//           }
//           // Otherwise do continue and do nothing
//       }
    }
    
    /***
     * Start the interactive interface to of the program.
     */
    public void startRepl(String prompt) throws IOException {
        this.args = args;
        
        if (!prompt.isEmpty() && prompt.length() < 5 && prompt.endsWith(" ")) {
            this.prompt = prompt;
        }
        
        /**
         * commands available:
            load <title | path>
            help
            listbooks [keyword]
            listcons [keyword]
            prompt => [title of con] >
            build <title | path> (possibly redundant)
            search <keyword>
            numoccur <keyword>
            numlines <title>
            surrwords <keyword, offset>
            phrase <phrase>
         */
        
        System.out.println("Welcome to Concordanator, "
                + "the best darn concordance tool around ;^)\n"
                + "type help for list of commands.");
        // Main repl loop.
        do {
            
            System.out.print(this.prompt);
            cmdStr = in.readLine();            
            // Tokenize the command entered.
            ArrayList<String> cmd = tokenizeCmd(cmdStr, " ");        
            evalCmd(cmd);
    
        } while (!exit);
    }
    
    /***
     * Is this method actually necessary? Look into this more - cory
     * @param cmdStr - The command string to tokenize
     * @param delim - the delimiter to separate tokens by
     * @return ArrayList<String> of tokens
     */
    private ArrayList<String> tokenizeCmd(String cmdStr, String delim) {
        ArrayList<String> tknStrs = new ArrayList<String>();
        // Break the command str into tokens
        StringTokenizer tknz = new StringTokenizer(cmdStr);
        
        while (tknz.hasMoreElements()) {
            tknStrs.add(tknz.nextToken(delim));
        }
        
        return tknStrs;
    }
    
    private void evalCmd(ArrayList<String> cmd) {
        List<String> cmdArg = new ArrayList<String>();
        Commands command;
        
        if (cmd.size() > 1) {
            cmdArg = cmd.subList(1, cmd.size());
        }
        //System.out.println("Command Arg: " + cmdArg.toString());
        try {
            command = Commands.valueOf(cmd.get(0));
        }
        catch(IllegalArgumentException e){
            command = Commands.valueOf("invalid");
        }
        switch (command) {
            case load :
                // Find the text in here.
                break;
            case help :
                this.printHelp();
                break;
            case listbooks :
                if (cmdArg.toString().equals("[]")) {
                    this.listbooks();
                } else {
                    this.listbooks(cmdArg.toString().substring(1, 
                            cmdArg.toString().length() - 1));
                }
                break;
            case listcons :
                // handle
                break; 
            case build :
                this.buildConcordance(cmdArg.toString().substring(1, cmdArg.toString().length() - 1));
                break;
            case exit :
                this.exit = true;
                break;
            case invalid :
                System.out.println("Invalid command.  Type 'help' for list of commands.");
        }
        
        if (conLoaded) {
            // Allow checks for the commands that require 
        }
    }
    
    /***
     * 
     * @param conPath The path or the name of the concordance to load.
     */
    public void loadConcordance(String conPath) {
        throw new UnsupportedOperationException();
    }
    
    /***
     * 
     * @param q
     * @return 
     * 
     * Should return the string representaion of the results of the search.
     */
    public String searchCon(String q) {
        throw new UnsupportedOperationException();
    }
    
    /***
     * 
     * @param arg
     * @return integer position of the argument
     */
    public int findArg(String arg) {
        throw new UnsupportedOperationException();
    }
    
    private void listbooks() {
        String[] titles = shelf.getAllBookTitles();
        
        System.out.println("Titles: ====================");
        for (String s : titles) {
            System.out.println(s);
        }
    }
    
    private void listbooks(String book) {
        String[] titles = shelf.getBookTitlesByKeyword(book);
        
        System.out.println("Titles: ====================");
        for (String s : titles) {
            System.out.println(s);
        }
    }
    
        private void buildConcordance(String title) {
            title = title.replace(",", "");
            String[] bookInformation = shelf.pullBook(title);
            this.concord = new Concordance(bookInformation[0], bookInformation[1], bookInformation[2]);

    }
    
    /**
     * Show the help text
     * Can make help text a file for ease of change in the future.
     */
    private void printHelp() {
        String helpTxt = "Available commmands: \n"
                + "load <title | path> - load a concordance or create one and load it.\n"
                + "help - show this help.\n"
                + "listbooks [keyword] - list all books matching keyword.\n"
                + "listcons [keyword]- list concordances matching keyword.\n"
                + "search <keyword> - find occurrences of keyword in loaded concordance.\n"
                + "numoccur <keyword> - find number of occurrences of keyword in loaded concordance.\n"
                + "numlines <title> - return the number of lines in the file.\n"
                + "phrase <phrase> - find occurrences of phrase in loaded concordance.\n";
        
        System.out.println(helpTxt);
    }
}

