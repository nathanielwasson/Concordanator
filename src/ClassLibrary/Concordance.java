package ClassLibrary;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Concordance implements Serializable {
    // private class fields.
    LinkedList[] hastable;
    String bookTitle, bookAuthor, filePath;
    int bookStartLine;
    
    public Concordance(String bt, String a, String fn) {
        this.hastable = new LinkedList[27];
        this.bookTitle = bt;
        this.bookAuthor = a;
        this.filePath = fn;
        this.bookStartLine = 0;
    }
    
    public boolean makeConcordance(){
        boolean success = false;
        int fileLineNumber = 0;
        int workLineNumber = 0;
        int hashKey;
        boolean bookStart = false;
        File file = new File(this.filePath);
        BufferedReader fileIn;
        if (file.isFile()){
            try {
                fileIn = new BufferedReader(new FileReader(file));
                while (fileIn.ready()){
                    fileLineNumber++;
                    String line = fileIn.readLine();
                    if (line.contains("*** END OF THIS PROJECT GUTENBERG")) bookStart = false;
                    if (bookStart){
                        workLineNumber++;
                        // If the line is bigger than 0, do the following.
                        // Split line into array
                        // filter out off bad chars and convert to lower case.
                        // Calculate hash for location of word.
                        // Check to see if word is already in the list, if not add it.
                        // If word is already in the list, update it.
                    }
                    else {
                        if (line.contains("*** START OF THIS PROJECT GUTENBERG")) {
                            bookStart = true;
                            this.bookStartLine = fileLineNumber + 1;
                        }
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Concordance.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Concordance.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else{
            System.out.println("The file " + file.getName() + " does not exist in the 'books' folder.");
        }
        
        return success;
    }
    
    private class Word{
        String word;
        ArrayList<Integer> line_numbers;
        int number_apperances, apperance_rank;
        
        public Word(String w, String b, ArrayList<Integer>lns, int n, int a){
            this.word = w;
            this.line_numbers = lns;
            this.number_apperances = n;
            this.apperance_rank = a;
        }
    }
}