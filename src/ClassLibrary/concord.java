package ClassLibrary;

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
public class concord{
    int number_of_lines;
    String file_name;
    String[] file_lines, flat_words;
    ArrayList[] file_words;
    HashMap<String, Word> concord = new HashMap<String, Word>();
    HashMap<String, Integer> all_apperances = new HashMap<String, Integer>();
    ArrayList<String> unique_words;
    String flat_words_full;
    HashMap<String, Integer> apperance_ranks = new HashMap<String, Integer>();
    ArrayList<String> common_words = new ArrayList<String>();
    
  
    
    public concord(String file_name) throws IOException{
        this.file_name = file_name;
        this.number_of_lines = get_number_lines();
        this.file_lines = this.get_file_lines();
        this.file_words = this.get_file_words();
        this.flat_words_full = Arrays.toString(this.file_lines);
        this.flat_words = flat_words_full.split("[\\s.,;\\n\\t]");
        this.unique_words = this.get_unique_words();
        this.concord = this.get_concord();
        this.all_apperances = this.get_all_apperances();
        this.apperance_ranks = this.get_apperance_ranks();
        this.common_words = this.get_common_words();
    }
    
    public class Word{
        String word;
        ArrayList<Integer> list_lines;
        int number_occurances, number_lines, apperance_rank;
        
        public Word(String w, ArrayList<Integer> ll, int no, int a){
            this.word = w;
            this.list_lines = ll;
            this.number_lines = this.list_lines.size();
            this.number_occurances = no;
            this.apperance_rank = a;
        }
    }
    
    public int get_number_lines() throws IOException{
        LineNumberReader lnr = new LineNumberReader( new FileReader( new File(this.file_name)));
        lnr.skip(Long.MAX_VALUE);
        int number_of_lines = lnr.getLineNumber() + 1;
        lnr.close();
        return number_of_lines;
    }
    /*
        Parses the .txt file and writes each line to an array file_lines
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
    
    public HashMap<String, Word> get_concord() {
        HashMap<String, Word> concord = new HashMap<String, Word>();
        for(String word: this.unique_words){
            ArrayList<Integer> lines = this.get_list_lines(word);
            int num_occurances = this.get_number_occurances(word);
            int app_rank = this.get_apperance_rank(word);
            Word tword = new Word(word, lines, num_occurances, app_rank);
            concord.put(word, tword);
        }
        return concord;
    }

    public HashMap<String, Word> get_concord_nocommon() {
        HashMap<String, Word> concord = new HashMap<String, Word>();
        for(String word: this.unique_words){
            if (!(this.common_words.contains(word))){
                ArrayList<Integer> lines = this.get_list_lines(word);
                int num_occurances = this.get_number_occurances(word);
                int app_rank = this.get_apperance_rank(word);
                Word tword = new Word(word, lines, num_occurances, app_rank);
                concord.put(word, tword);
            }
        }
        return concord;
    }
    
    public ArrayList[] get_file_words() {
        ArrayList[] file_words = new ArrayList[this.number_of_lines];
        
        for (int i = 0; i < this.number_of_lines; i++) {
            String file_line = file_lines[i];
            if (file_line != null) {
                String[] split_file_line = file_line.split("[\\s.,;:?!--()'\"\\n\\t]");
                file_words[i] = new ArrayList<String>();
                for (int j = 0; j < split_file_line.length; j++) {
                    if(file_words[i] != null){
                    file_words[i].add(split_file_line[j].toLowerCase());
                    }
                }
            }
            
        }
        return file_words;
    }

    public ArrayList<String> get_unique_words() {
        ArrayList<String> unique_words = new ArrayList<String>();
        for (int i = 0; i < this.file_words.length; i++) {
            if (file_words[i]!=null){
                for (int j = 0; j < file_words[i].size(); j++) {
                    if (!(unique_words.contains((String) file_words[i].get(j)))){
                        unique_words.add((String) file_words[i].get(j));
                    }
                }
            }
        }
        return unique_words;
    }
    
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
 
    public int get_number_lines(String target_word){
        return this.get_list_lines(target_word).size();
    }
    
    public int get_number_occurances(String target_word){
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
    /***
     * @return Hashmap mapping every unique word in text to it's number of apperances
     */
    public HashMap<String, Integer> get_all_apperances(){
        HashMap<String, Integer> all_apperances = new HashMap<String, Integer>();
        int index = 0;
        for(String word: this.unique_words){
            all_apperances.put(word, this.get_number_occurances(word)); 
        }
        return all_apperances;
    }
    
    public int get_apperance_rank(String target_word){
        int num_apperances = this.get_number_occurances(target_word);
        int rank = 1;
        for (String word: this.all_apperances.keySet()){
            if (this.get_number_occurances(word) > num_apperances){
                rank++;
            }
        }
        return rank;
    }
    
    public HashMap<String, Integer> get_apperance_ranks(){
        HashMap<String, Integer> apperance_ranks = new HashMap<String, Integer>();
        for(String word: this.unique_words){
            apperance_ranks.put(word, this.get_apperance_rank(word));
        }
        return apperance_ranks;
    }
    
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
}