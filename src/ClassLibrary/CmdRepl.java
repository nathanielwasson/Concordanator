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
    private Concord concord;
    private String OSName = System.getProperty("os.name").substring(0, 3);
    private String userDir;
    private String conDir;
    private String booksDir;
    private boolean isWin;
  
    private enum Commands {
        load, 
        help, 
        listbooks, 
        listcons,
        build,
        search,
        numoccur,
        numlines,
        phrase,
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
       
       this.userDir = System.getProperty("user.dir");
       
       if (this.OSName.equals("Win")) {
           this.conDir = this.userDir + "\\cons";
           this.booksDir = this.userDir + "\\cons";
           this.isWin = true;
       } else {
           this.conDir = this.userDir + "/cons";
           this.booksDir = this.userDir + "/cons";
           this.isWin = false;
       }
       
       System.out.println("Con dir: " + this.conDir);
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
                + "type help for list of commands.\n");
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
        System.out.println("Command Arg: " + cmdArg.toString());
        try {
            command = Commands.valueOf(cmd.get(0));
        }
        catch(IllegalArgumentException e){
            command = Commands.valueOf("invalid");
        }
        switch (command) {
            case load :
                String title = "";
                String textPath = "";
                for (String s : cmdArg) {
                    title = title + s + " ";
                }
                
                if (!title.isEmpty()) {
                    title = title.substring(0, title.length() - 1);
                    System.out.println(title);
                } else {
                    System.out.println("Please provide a book name to load.");
                    break;
                }
                
                if (isWin) 
                    textPath = this.booksDir + "\\" + title + ".con";
                else
                    textPath = this.booksDir + "/" + title + ".con";
                System.out.println(textPath);
                // Now we call load with the textPath
                this.concord = this.loadConcordance(textPath);
                
                if (this.concord != null) {
                    System.out.println(title + " loaded.");
                    this.conLoaded = true;
                } else {
                    System.out.println(title + " failed to load, are you sure it exists?");
                    this.conLoaded = false;
                }
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
                
                break; 
            case build :
                this.buildConcordance(cmdArg.toString()
                        .substring(1, cmdArg.toString().length() - 1));
                break;
            case search :
                if (!conLoaded) {
                    System.out.println("Error: no concordance loaded.");
                } else {
                    
                }
                break;
            case numoccur :
                if (!conLoaded) {
                    System.out.println("Error: no concordance loaded.");
                } else {
                     if (cmdArg.toString().equals("[]")) {
                         System.out.println("Incorrect Usage: Number of word occurences requires an argument.");
                } else {
                    this.numOccurences(cmdArg.toString().substring(1, 
                            cmdArg.toString().length() - 1));
                }
                }
                break;
            case numlines :
                if (!conLoaded) {
                    System.out.println("Error: no concordance loaded.");
                } else {
                 if (cmdArg.toString().equals("[]")) {
                         System.out.println("Incorrect Usage: Number of lines requires an argument.");
                } else {
                    this.numberOfLines(cmdArg.toString().substring(1, 
                            cmdArg.toString().length() - 1));
                }
                }
                break;
            case phrase :
                if (!conLoaded) {
                    System.out.println("Error: no concordance loaded.");
                } else {
                    
                }
                break;
            case exit :
                this.exit = true;
                break;
            case invalid :
                System.out.println("Invalid command.  Type 'help' for list of commands.");
        }
    }
    
    /**
     * 
     * @param c - Concord object to be saved
     * @param conPath - The path to which to save the concordance
     *                  must also have the file name appended to it
     */
    public void saveConcordance(Concord c, String conPath) {
        IO io = new IO();
        
        io.serialize(c, conPath);
    }
    
    /***
     * 
     * @param conPath The path or the name of the concordance to load.
     */
    public Concord loadConcordance(String conPath) {
        IO io = new IO();
        
        return (Concord) io.deserialize(conPath);
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
        if (bookInformation == null){
            System.out.println("Error: Book not found. Please check the name and try again.");
        } else {
           try {
            this.concord = new Concord(bookInformation[2]);
            this.conLoaded = true;
        } catch (IOException ex) {
            System.out.println("File Error:  The file is not found on the system.");
        } 
        } 
    }
    
    private void numOccurences (String word){
        int temp = concord.get_number_occurrences(word);
        System.out.println("The word " + word + " appears " + temp + " times.");
        
        
    }
    
    private void numberOfLines(String word){
        int temp = concord.get_number_lines(word);
        System.out.println("The word " + word + " appears on " + temp + " lines.");
    }
    
    /**
     * Show the help text
     * Can make help text a file for ease of change in the future.
     */
    private void printHelp() {
        String helpTxt = "Available commmands: \n\n"
                + "load <title | path>     - load a concordance or create one and load it.\n"
                + "help                    - show this help.\n"
                + "listbooks [keyword]     - list all books matching keyword.\n"
                + "listcons [keyword]      - list concordances matching keyword.\n"
                + "search <keyword>        - find occurrences of keyword in loaded concordance.\n"
                + "numoccur <keyword>      - find number of occurrences of keyword in loaded concordance.\n"
                + "numlines <title>        - return the number of lines in the file.\n"
                + "phrase <phrase>         - find occurrences of phrase in loaded concordance.\n";
        
        System.out.println(helpTxt);
    }
}
