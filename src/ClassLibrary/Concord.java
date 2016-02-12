import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;


/***
 * @param file_name name of the file
 * @param flat_words 1D array of all words in the file
 * @param file_lines 1D array of strings, all lines in file
 * @param number_of_lines number of lines in the file
 * @author seth
 */
public class Concord implements Serializable{
    int number_of_lines;
    String file_name;
    String[] file_lines, flat_words;
    ArrayList[] file_words;
    HashMap<String, Word> concord = new HashMap<String, Word>();
    HashMap<String, Integer> all_appearances = new HashMap<String, Integer>();
    ArrayList<String> unique_words;
    String flat_words_full;
    HashMap<String, Integer> appearance_ranks = new HashMap<String, Integer>();
    ArrayList<String> common_words = new ArrayList<String>();
    
  
    /**
     * 
     * @param file_name name of the file to make the concordance from
     * @param number_of_lines the number of lines in the file
     * @throws IOException 
     */
    public Concord(String file_name) throws IOException{
        this.file_name = file_name;
        this.number_of_lines = get_number_lines();
        this.file_lines = this.get_file_lines();
        this.file_words = this.get_file_words();
        this.flat_words_full = Arrays.toString(this.file_lines);
        this.flat_words = flat_words_full.split("[\\s--.,;\\n\\t]");
        this.unique_words = this.get_unique_words();
        this.all_appearances = this.get_all_appearances();
        this.appearance_ranks = this.get_appearance_ranks();
        this.concord = this.get_concord();
        this.common_words = this.get_common_words();
        this.save();
    }
    
    public class Word implements Serializable{
        String word;
        ArrayList<Integer> list_lines;
        int number_occurrences, number_lines, appearance_rank;
        
        /**
         * 
         * @param w String containing the word itself
         * @param ll Arraylist of line numbers word's found on
         * @param no Number of occurrences for the word
         * @param a  Appearance rank of the word
         */
        public Word(String w, ArrayList<Integer> ll, int no, int a){
            this.word = w;
            this.list_lines = ll;
            this.number_lines = this.list_lines.size();
            this.number_occurrences = no;
            this.appearance_rank = a;
        }
    }
    
    /**
     * returns the number of lines in the text file
     * @return number of lines in file
     * @throws IOException 
     */
    public int get_number_lines() throws IOException{
        LineNumberReader lnr = new LineNumberReader( new FileReader( new File(this.file_name)));
        lnr.skip(Long.MAX_VALUE);
        int number_of_lines = lnr.getLineNumber() + 1;
        lnr.close();
        return number_of_lines;
    }
    
    /**
     * Parses the .txt file and writes each line to an array file_lines
     * @return String array containing all lines in file as one long string
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public String[] get_file_lines() throws FileNotFoundException, IOException {
               
            //open file
            FileReader file_reader = new FileReader(this.file_name);
            BufferedReader  buffered_reader = new BufferedReader(file_reader);
            
            //Writes each line in the file to an array 'file_lines'
            String[] file_lines = new String[this.number_of_lines];
            for (int i = 0; i < this.number_of_lines; i++) {
              file_lines[i] = buffered_reader.readLine();
            }
    
        return file_lines;
    }
    
    /**
     * Makes a Word object for each unique word in the text and writes it to a hash table
     * @return hashmap containing the concordance
     * @throws IOException 
     */
    public HashMap<String, Word> get_concord() throws IOException {
        HashMap<String, Word> concord = new HashMap<String, Word>();
        for(String word: this.unique_words){
            ArrayList<Integer> lines = this.get_list_lines(word);
            int num_occurrences = this.all_appearances.get(word);
            int app_rank = this.appearance_ranks.get(word);
            Word tword = new Word(word, lines, num_occurrences, app_rank);
            concord.put(word, tword);
        }
        return concord;
    }
    
    /**
     * Creates a concordance excluding all words in commonwords.txt
     * @return hashmap containing the concordance
     */
    public HashMap<String, Word> get_concord_nocommon() {
        HashMap<String, Word> concord = new HashMap<String, Word>();
        //exclude all words in the common words file
        for(String word: this.unique_words){
            if (!(this.common_words.contains(word))){
                ArrayList<Integer> lines = this.get_list_lines(word);
                int num_occurrences = this.get_number_occurrences(word);
                int app_rank = this.get_appearance_rank(word);
                Word tword = new Word(word, lines, num_occurrences, app_rank);
                concord.put(word, tword);
            }
        }
        return concord;
    }
    
    /**
     * Gets an arraylist with number_of_lines worth's of elements, each containing
     * an arraylist with each word on the line in order
     * @return Arraylist of each word on each line
     */
    public ArrayList[] get_file_words() {
        ArrayList[] file_words = new ArrayList[this.number_of_lines];
        
        //for each line in the file
        for (int i = 0; i < this.number_of_lines; i++) {
            String file_line = file_lines[i];
            if (file_line != null) {
                //split each string line of the file
                String[] split_file_line = file_line.split("[\\s.,;:?!--()'\"\\n\\t]");
                file_words[i] = new ArrayList<String>();
                //add each word to it's respective list
                for (int j = 0; j < split_file_line.length; j++) {
                    if(file_words[i] != null){
                        //make sure words are all lowercase
                        file_words[i].add(split_file_line[j].toLowerCase());
                    }
                }
            }
            
        }
        return file_words;
    }

    /**
     * Gets an arraylist of all of the unique words in the text
     * @return arraylist of all of the unique words in the text
     */
    //Gets an arraylist of all of the unique words in the text
    public ArrayList<String> get_unique_words() {
        ArrayList<String> unique_words = new ArrayList<String>();
        //for line in file
        for (int i = 0; i < this.file_words.length; i++) {
            if (file_words[i]!=null){
                //for word in line
                for (int j = 0; j < file_words[i].size(); j++) {
                    if (!(unique_words.contains((String) file_words[i].get(j)))){
                        unique_words.add((String) file_words[i].get(j));
                    }
                }
            }
        }
        return unique_words;
    }
    
    /**
     * Gets a list of all the line numbers a word appears on
     * @param target_word
     * @return list of all the line numbers a word appears on
     */
    public ArrayList<Integer> get_list_lines(String target_word){
        ArrayList<Integer> list_lines = new ArrayList<Integer>();
        int counter = 1;
        for(ArrayList<String> str: this.file_words){
            if (str!=null){
                for(String word: str){
                    if(target_word.equals(word) && !list_lines.contains(counter)){
                        list_lines.add(counter);
                    }
                }
            }
            counter++;
        }
        return list_lines;
    }
 
    /**
     * Gets total number of lines that a word appears on
     * @param target_word
     * @return 
     */
    //Gets the number of lines that a word appears on
    public int get_number_lines(String target_word){
        return this.get_list_lines(target_word).size();
    }
    
    /**
     *Gets the total number of occurrences for given word 
     * @param target_word
     * @return integer number of occurrences for the given word
     */
    public int get_number_occurrences(String target_word){
        int counter = 0;
        for(ArrayList<String> str: this.file_words){
            if (str!=null){
                for(String word: str){
                    if(target_word.equals(word)){
                    counter++                    ;
                    }
                }
            }
        }
        return counter;
    }
    
    /**
     * Gets number of appearances for each unique word and writes to a hash table
     * @return Hashmap mapping every unique word in text to it's number of appearances
     */
    public HashMap<String, Integer> get_all_appearances(){
        HashMap<String, Integer> all_appearances = new HashMap<String, Integer>();
        for(String word: this.unique_words){
            all_appearances.put(word, this.get_number_occurrences(word)); 
        }
        return all_appearances;
    }
    
    /**
     * gets a words appearance rank within the text file
     * @param target_word
     * @return 
     */
    public int get_appearance_rank(String target_word){
        //get list of appearances for all words
        int[] appearance_numbers = new int[this.all_appearances.size()];
        int index = 0;
        for(String word: this.all_appearances.keySet()){
            appearance_numbers[index] = this.all_appearances.get(word);     
            index++;
        }
        
        //get the number of appearances for the target word
        int target_num = this.get_number_occurrences(target_word);
        
        int rank = 1;
        
        //check target num against all numbers in list and return number greater than
        for (int i = 0; i < appearance_numbers.length; i++) {
            if (appearance_numbers[i] > target_num){
                rank = rank + 1;
            }
        }
        return rank;
    }
    
    /**
     * gets a hash map of all of the appearance ranks for each word in file
     * @return hash map of all appearance ranks for each unique word in text
     */
    public HashMap<String, Integer> get_appearance_ranks(){
        HashMap<String, Integer> appearance_ranks = new HashMap<String, Integer>();
        for(String word: this.unique_words){
            appearance_ranks.put(word, this.get_appearance_rank(word));
        }
        return appearance_ranks;
    }
    
    /**
     * parses commonwords.txt and returns an arraylist containing all the words
     * that should be excluded from the concord
     * @return arraylist containing all the words that should be excluded from the concord
     * @throws FileNotFoundException
     * @throws IOException 
     */
    //parses commonwords.txt and returns an arraylist containing all the words
    //that should be excluded from the concord
    public ArrayList<String> get_common_words() throws FileNotFoundException, IOException{
            ArrayList<String> common_words = new ArrayList<String>();
            
            //open file
            FileReader file_reader = new FileReader("commonwords.txt");
            BufferedReader  buffered_reader = new BufferedReader(file_reader);
            String line;
            //Write each line in file to element in aray
            while((line = buffered_reader.readLine())!=null){
                common_words.add(line);
            }
            return common_words;
    }

    /**
     * Get's all the words within some given distance of all occurrences of a target_word
     * @param target_word 
     * @param dist
     * @return ArrayList of all of the words in the file that are 
     * found within the given distance of the target word
     */
    public ArrayList<String> get_words_within_distance(String target_word, int dist){
        ArrayList<String> words = new ArrayList<String>();
        
        //for word in array of flat words (leaving off 'dist' worth of space on both ends)
        for(int i=dist; i < this.flat_words.length-dist; i++){
            if (this.flat_words[i].equals(target_word)){
                //add all words within distance on either side to arraylist
                for(int j=i-dist; j <= i+dist; j++){
                    //exclude the target word
                    if(i!=j && !words.contains(this.flat_words[j])){
                        words.add(this.flat_words[j]);
                    }
                }
            }
        }
        return words;
    }
    
    /**
     * saves the seralized concord to filename.con
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void save() throws FileNotFoundException, IOException{
        String name = this.file_name.split("[.]")[0] + ".con";
        FileOutputStream fileOut = new FileOutputStream(name);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this);
        out.close();
        fileOut.close();
    }
    /**
     * 
     * @param phrase
     * @return ArrayList of lines containing line numbers of phrase occurrences
     */
    public ArrayList<Integer> find_phrase(String phrase){
        ArrayList<Integer> phrase_lines = new ArrayList<Integer>();
        
        //if phrase not found in file, return empty arraylist
        if (!this.flat_words_full.contains(phrase)){
            return phrase_lines;
        }
        
        //get words in phrase
        String[] words = phrase.split("[\\s.,;\\n\\t]");
        
        //get line numbers for each word in phrase
        for (int i = 0; i < words.length; i++) {
            phrase_lines.add(this.get_number_lines(words[i]));
        }
        
        //find numbers within tolerance
        phrase_lines.get(0);
        
        //lines that completely contain phrase
        ArrayList<Integer> full_phrase_lines = new ArrayList<Integer>();
        
        //parse this.file_lines and see what lines contain whole phrase
        for(int line = 0; line < this.file_lines.length; line++){
            if(this.file_lines[line] != null){
                if(this.file_lines[line].contains(phrase) && !full_phrase_lines.contains(line+1)){
                    full_phrase_lines.add(line+1);
                }
            }
        }
        
        return full_phrase_lines;
    }
}