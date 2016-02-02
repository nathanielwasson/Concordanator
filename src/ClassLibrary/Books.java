package ClassLibrary;

public class Books {
    // Private class fields
    private int numElements;
    private Book header;
    
    // Class constructor
    public Books(){
        header = new Book();  // Creates empty node for header.
        numElements = 0;
    }
    // Class methods
    
    // Private inner Book class.
    private class Book{
        // Private class fields
        String title, author;
        Book next, prev;
        
        // Private class constructor for creating the header
        private Book(){
            this.title = null;
            this.author = null;
            next = prev = this;
        }
        // Private class constructor
        private Book(String titl, String auth){
            this.title = titl;
            this.author = auth;
            next = prev = this;
        }
    }
}