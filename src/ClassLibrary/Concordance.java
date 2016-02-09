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
    int wordCount;
    
    public Concordance(String bt, String a, String fn) {
        this.hastable = new LinkedList[27];
        this.bookTitle = bt;
        this.bookAuthor = a;
        this.filePath = fn;
        this.wordCount = 0;
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
                                if (hashKey > 27) hashKey = 0;
                                if (this.hastable[hashKey] == null){
                                    this.hastable[hashKey] = new LinkedList();
                                }
                                Word addWord = new Word(word);
                                if (this.hastable[hashKey].contains(addWord.getWord())){
                                    
                                }
                                else{
                                this.hastable[hashKey].add(addWord);
                                }
                                this.wordCount++;
                                System.out.println("Hash Key = " + hashKey);
                                System.out.println("Word = " +word);
                            
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
                        }
                    }
                }
                System.out.println(bookTitle + " " + bookAuthor + " " + filePath);
                System.out.println("Total line numbers of text file: " + fileLineNumber);
                System.out.println("Total number of words found: " + this.wordCount);
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
        
        private Word(String w){
            this.word = w;
            this.line_numbers = new ArrayList<>();
            this.number_apperances = 1;
            this.apperance_rank = 0;
        }
        
        private String getWord(){
            return this.word;
        }
        
        private ArrayList<Integer> getLineNumbers(){
            return this.line_numbers;
        }
        
        private void addLineNumber(int num){
            this.line_numbers.add(num);
        }
        
        private void incrementWordCount(){
            this.number_apperances++;
        }
        
        private int getWordRank(){
            return this.apperance_rank;
        }
    }
}