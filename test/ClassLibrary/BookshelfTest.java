/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassLibrary;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Cory Sabol
 */
public class BookshelfTest {
    
    private static String OS = System.getProperty("os.name").toLowerCase();
    private String userDir = System.getProperty("user.dir");
    private String winPath = "src\\books";
    private String nixPath = "src/books";
    private String path;
    
    public BookshelfTest() {
        
        System.out.println(this.userDir + "/" + this.nixPath);
        this.path = this.userDir + "\\" + this.winPath;
        // Create the list of titles to be expected
           
        if (isNix()) {
            // Adhere to *nix paths
            this.path = this.userDir + "/" + this.nixPath;            
        }
        
        //System.out.println("path to books" + this.path);
        
    }
    
    public static boolean isWindows() {

    	return (OS.indexOf("win") >= 0);

    }

    public static boolean isMac() {

    	return (OS.indexOf("mac") >= 0);

    }

    public static boolean isNix() {

	return (OS.indexOf("nix") >= 0 
                || OS.indexOf("nux") >= 0 
                || OS.indexOf("aix") > 0 );
		
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getNumberOfBooks method, of class Bookshelf.
     */
    @Test
    public void testGetNumberOfBooks() {
        System.out.println("getNumberOfBooks");
        Bookshelf instance = new Bookshelf();
        int expResult = 20;
        int result = instance.getNumberOfBooks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getAllBookTitles method, of class Bookshelf.
     */
    @Test
    public void testGetAllBookTitles() {
        System.out.println("getAllBookTitles\n " + this.path);
        Bookshelf instance = new Bookshelf();
       
        File folder = new File(this.path);
        
        // get the files in books dir
        File[] files = folder.listFiles();
        
        String[] expResult = new String[20];
        
        // Create the exp results
        System.out.println("Books: ");
        for (int i = 0; i < files.length; i++) {
            // Strip .txt from name
            String title = files[i].getName().substring(0,
                    files[i].getName().length() - 4);
            
            expResult[i] = title;
            System.out.println(expResult[i]);
        }
                
        String[] result = instance.getAllBookTitles();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getBookTitlesByKeyword method, of class Bookshelf.
     */
    @Test
    public void testGetBookTitlesByKeyword() {
        System.out.println("getBookTitlesByKeyword");
        String keyword = "";
        Bookshelf instance = new Bookshelf();
        String[] expResult = null;
        String[] result = instance.getBookTitlesByKeyword(keyword);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of inventoryBooks method, of class Bookshelf.
     */
    @Test
    public void testInventoryBooks() {
        System.out.println("inventoryBooks");
        Bookshelf instance = new Bookshelf();
        instance.inventoryBooks();
    }

    /**
     * Test of pullBook method, of class Bookshelf.
     */
    @Test
    public void testPullBook() {
        System.out.println("pullBook");
        String book = "";
        Bookshelf instance = new Bookshelf();
        String[] expResult = null;
        String[] result = instance.pullBook(book);
        assertArrayEquals(expResult, result);
    }
    
}