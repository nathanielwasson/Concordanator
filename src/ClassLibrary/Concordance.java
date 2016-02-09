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
                    if (line.contains("*** START OF THIS PROJECT GUTENBERG")) {
                        line = fileIn.readLine();
                        bookStart = true;
                    }
                    if (bookStart){
                        // If the line is bigger than 0, do the following.
                        if (line.length() > 0){
                           // Split line into array
                            String[] words = line.split(" ");
                            for (int i = 0;i< words.length;i++){
                                String word = words[i].toLowerCase();
                                if (word.length() > 0){
                                char firstChar = word.charAt(0);
                                hashKey = (((int)firstChar % 97) + 1);
                                if (hashKey < 28){
                                System.out.println(hashKey);
                                if (this.hastable[hashKey] == null){
                                    this.hastable[hashKey] = new LinkedList();
                                }
                                Word addWord = new Word(word);
                                this.hastable[hashKey].add(addWord);
                                System.out.println("Hash Key = " + hashKey);
                                System.out.println("Word = " +word);
                            }
                            }
                            }
                        }
                        
                        // filter out off bad chars and convert to lower case.
                        // Calculate hash for location of word.
                        // Check to see if word is already in the list, if not add it.
                        // If word is already in the list, update it.
                    }
                    else {
                        if (line.contains("*** START OF THIS PROJECT GUTENBERG")) {
                            bookStart = true;
                            this.bookStartLine = fileLineNumber + 1;    //No longer really need this..?
                        }
                    }
                }
                System.out.println(bookTitle + " " + bookAuthor + " " + filePath);
                System.out.println("Total line numbers of text file: " + fileLineNumber);
                System.out.println("Book begins on line: " + this.bookStartLine);
                success = true;
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
        
        public Word(String w){
            this.word = w;
            this.line_numbers = null;
            this.number_apperances = 1;
            this.apperance_rank = 0;
        }
    }
}