
import ClassLibrary.CmdRepl;
import java.io.IOException;

public class ConcordanceMaker{
    public static void main(String[] args) {
        /*
        Bookshelf myBooks = new Bookshelf();
        System.out.println("The number of books are: " + myBooks.getNumberOfBooks());
        String[] bookTitle = myBooks.getAllBookTitles();
        for (String bookTitle1 : bookTitle) {
            System.out.println(bookTitle1);
        }
        Scanner search = new Scanner(System.in);
        System.out.print("Please enter a search term: ");
        String searchTerm = search.nextLine();
        String[] bookTitleBySearch = myBooks.getBookTitlesByKeyword(searchTerm);
        for (String bookTitleBySearch1 : bookTitleBySearch) {
            System.out.println(bookTitleBySearch1);
        }
        System.out.print("Please enter a book name: ");
        searchTerm = search.nextLine();
        String[] bookCreds = myBooks.pullBook(searchTerm);
        Concordance concord = new Concordance(bookCreds[0], bookCreds[1], bookCreds[2]);
        System.out.println(concord.makeConcordance());*/
        
        String[] start = {"Hello"};
        CmdRepl repl = new CmdRepl(start);
        
        // Test out get all book titles
        
        
        
        try {
            
            if (args.length != 0 && args[1].equals("gui")) {
                // Launch the gui from here
            } else if (args.length == 1 || args.length == 0) {
                repl.startRepl("");
            } else {
                // Print out usage to term
            }            
            
        } catch (IOException ex) {
            System.out.println("No File.");
        }
    }
}