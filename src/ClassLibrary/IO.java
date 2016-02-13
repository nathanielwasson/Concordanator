/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassLibrary;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Serialize and deserialize serializable classes
 * @author Cory Sabol
 */
public class IO<T> implements Serializable {
    
    private T obj;
    private String OSName = System.getProperty("os.name").substring(0, 3);
    private String path;
    
    /**
     * 
     * @param obj
     * @param path Path to serialize to including the file name and extension
     *        EX: /tmp/catcher in the rye.con
     */
    public void serialize(T obj, String path) {
        this.obj = obj;

		// Check if the path already exists
		// If not then create it.
		File f; 
			
		if (this.OSName.equals("Win"))
			f = new File(path.substring(0, path.lastIndexOf('\\')));
		else
			f = new File(path.substring(0, path.lastIndexOf('/')));

		if (!f.exists()) {
			// create it
			f.mkdir();
		}
        
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.obj);
            fileOut.close();
            out.close();
        } catch (Exception ex) {
            //Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param path Path to the file including name and extension
     * @return T obj The deserialized object
     */
    public T deserialize(String path) {
        try {
            
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.obj = (T) in.readObject();
            fileIn.close();
            in.close();
            
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            //Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.obj;
    }
    
}
