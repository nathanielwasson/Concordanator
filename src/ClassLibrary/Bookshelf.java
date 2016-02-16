
package ClassLibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Bookshelf object emulates a virtual bookshelf containing Books 
 * acquired from the project Gutenberg web site.  This class will
 * attempt to locate books within a specific directory entitled 'books'
 * located one level outside of this class.
 * @author Anthony Ratliff
 */
public final class Bookshelf {

    // Private class fields
    private int numElements;    // Holds the number of books on the shelf.
    private Book header;    // Empty book which servers as a header to the linked data.
    private final String BOOK_DIRECTORY = "books";  // Name of the folder which contains the books.
    private final String userDir;   // Current path to which the program is running in.
    private String bookDirectory;   // Holds the exact path of the book based on the user's OS environment.

    // Class constructor

    /**
     * Public class constructor which takes no arguments and builds
     * a virtual bookshelf from text files acquired from the project
     * Gutenberg web site.  The text files must be in the books folder
     * and contain the project Gutenberg disclaimer.
     */
        public Bookshelf() {
        this.header = new Book();  // Creates empty book for header.
        this.numElements = 0;   // Initialize the book counter to 0.
        this.userDir = System.getProperty("user.dir");  // get the program path.
        this.bookDirectory = this.userDir + File.separator + BOOK_DIRECTORY;    // set the book directory.
        if (!new File(this.bookDirectory).isDirectory()){
            // This checks to see if the book directory is present in the command line.
            // If not, then use is running in Netbeans and the proper folder as be appended.
            this.bookDirectory = this.userDir + File.separator + "src" + File.separator + "books";
        }
        this.inventoryBooks();  // Search the folder for Gutenberg books and add them to the shelf.
    }

    // Class methods

    /**
     * Method returns the number of books on the bookshelf.
     * @return
     */
        public int getNumberOfBooks() {
        return this.numElements;
    }

    /**
     * Method will return a string array consisting of all books found in
     * the 'books' directory containing the project Gutenberg disclaimer.
     * The strings in the array will list books by title and author.
     * @return
     */
    public String[] getAllBookTitles() {
        String temp[] = new String[this.numElements];
        int index = 0;
        Book curr = header.next;
        while (curr != header) {
            temp[index] = curr.getTitle() + " by " + curr.getAuthor();
            index++;
            curr = curr.next;
        }
        String[] temp2 = new String[index];
        System.arraycopy(temp, 0, temp2, 0, index);
        
        return temp2;
    }

    /**
     * Method will return a string array consisting of all books found on
     * the shelf based on a keyword search string.  The books must be located in
     * the 'books' directory and contain the project Gutenberg disclaimer.
     * The strings in the array will list books by title and author.
     * @param keyword
     * @return
     */
    public String[] getBookTitlesByKeyword(String keyword) {
        String[] temp = new String[this.numElements];
        int index = 0;
        Book curr = header.next;
        while (curr != header) {
            if (curr.getTitle().toLowerCase().contains(keyword.toLowerCase()) || curr.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                temp[index] = curr.getTitle() + " by " + curr.getAuthor();
                index++;
            }
            curr = curr.next;
        }
        String[] temp2 = new String[index];
        System.arraycopy(temp, 0, temp2, 0, index);

        return temp2;
    }

    /**
     * Method will refresh the Bookshelf to include any new titles added since
     * the Bookshelf class was first instantiated.
     */
    public void inventoryBooks() {

        if (new File(this.bookDirectory).isDirectory()) {
            File dir = new File(this.bookDirectory);
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    // Strip the file extension and chop the file names into a String array using the keyword 'by'
                    if (child.getName().substring(child.getName().length() - 4).equals(".txt")) {    // Exclude files that do not have a .txt file extention
                        String[] pieces = this.retrieveCredentialsFromFile(child.getName());
                        this.addBook(pieces[0], pieces[1], child.getName());
                    }
                }
            } else {
                // Handle the case where dir is not really a directory.
                // Checking dir.isDirectory() above would not be sufficient
                // to avoid race conditions with another process that deletes
                // directories.
            }
        }
    }
    
    /**
     * Method will return a string array consisting of 3 elements based on 
     * a keyword search if the book exists. If no book is found, the method
     * will return null.  The elements of the returned array are: book title, 
     * book author, and the local path to the book.
     * @param book
     * @return
     */
    public String[] pullBook(String book){
        String[] temp = null;
        Book curr = header.next;
        while (curr != header) {
            if (book.contains(curr.getTitle())){
                temp = new String[3];
                temp[0] = curr.getTitle();
                temp[1] = curr.getAuthor();
                temp[2] = this.bookDirectory + File.separator + curr.getFileName();
                curr = header;              
            } else {
               curr = curr.next; 
            }     
        }
        
        return temp;
    }

    private boolean addBook(String titl, String auth, String fn) {
        boolean success = false;
        Book curr = header.next;  // Create a pointer named curr.
        Book add = new Book(titl, auth, fn);   // Create a DNode with the data.
        // if the list is empty, then just insert the item as the first item in the list.
        if (curr == header) {
            // Just attach the newly created node to the header.
            add.next = header;
            add.prev = header;
            header.next = add;
            header.prev = add;
            numElements = 1;
            success = true;
        } // However, if the list is not empty, iterate through the list until you find the correct
        // location to add the new item.
        else {
            // Iterate through the list until the proper location to place the data has been reached.
            while (curr != header) {
                int result = add.title.compareTo(curr.title);
                if (result < 0) {
                    // Once the proper location has been found, add the new item.
                    curr.prev.next = add;
                    add.prev = curr.prev;
                    add.next = curr;
                    curr.prev = add;
                    numElements++;
                    success = true;
                    curr = header;  // once the item has been added, break the loop.
                } else if (curr.next == header) {
                    // If the proper location is at the end of the list, add it here.
                    curr.next.prev = add;
                    curr.next = add;
                    add.prev = curr;
                    add.next = header;
                    numElements++;
                    success = true;
                    curr = header;  // once the item has been added, break the loop.
                } else {
                    curr = curr.next; // Else, advance the pointer to the next location and try there.
                }
            }
        }
        return success;
    }

    private String[] retrieveCredentialsFromFile(String fn) {
        String[] temp = new String[2];
        File bookFile;
        BufferedReader fileIn;
        bookFile = new File(this.bookDirectory + File.separator + fn);
        if (bookFile.isFile()) {
            try {
                fileIn = new BufferedReader(new FileReader(bookFile));
                int parts = 0;
                while (fileIn.ready() && parts != 2) {
                    String line = fileIn.readLine();
                    if (line.length() >= 7) {
                        if (line.substring(0, 7).contains("Title:")) {
                            temp[0] = line.substring(7, line.length());
                            parts++;
                        }
                        if (line.substring(0, 7).contains("Author:")) {
                            temp[1] = line.substring(8, line.length());
                            parts++;
                        }
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Bookshelf.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Bookshelf.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            // Do something if file does not exist.
        }

        return temp;
    }

    // Private inner Book class.
    private final class Book {

        // Private class fields
        private final String title, author, fileName;
        private Book next, prev;

        // Private class constructor for creating the header
        private Book() {
            this.title = null;
            this.author = null;
            this.fileName = null;
            next = prev = this;
        }

        // Private class constructor
        private Book(String titl, String auth, String fn) {
            this.title = titl;
            this.author = auth;
            this.fileName = fn;
            next = prev = this;
        }

        public String getTitle() {
            return this.title;
        }

        public String getAuthor() {
            return this.author;
        }

        public String getFileName() {
            return this.fileName;
        }
    }
}