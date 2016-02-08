/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassLibrary;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zeroxff
 */
public class BookshelfTest {
    
    public BookshelfTest() {
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
        int expResult = 0;
        int result = instance.getNumberOfBooks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllBookTitles method, of class Bookshelf.
     */
    @Test
    public void testGetAllBookTitles() {
        System.out.println("getAllBookTitles");
        Bookshelf instance = new Bookshelf();
        String[] expResult = null;
        String[] result = instance.getAllBookTitles();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inventoryBooks method, of class Bookshelf.
     */
    @Test
    public void testInventoryBooks() {
        System.out.println("inventoryBooks");
        Bookshelf instance = new Bookshelf();
        instance.inventoryBooks();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
