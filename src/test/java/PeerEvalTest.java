package peerEval;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;
import org.junit.*;

import java.io.File; 
import java.io.IOException;
import java.util.*;
import java.io.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Unit test for simple App.
 */
public class PeerEvalTest 
{
    public static PeerEval pc;
    public static Connection c;

    @BeforeClass
    public static void setUpDB() throws Exception {
	//	System.out.println("connecting...");
	pc = new PeerEval();
	c = pc.connect("jdbc:postgresql://localhost:5432/cs375v1", "mrblee", "purplewhite");
    }

    public void response_delete() {
	pc.nonquery("delete from response");
    }
    
    public void response_inserts() {
	pc.nonquery("insert into response (evalid, student1, student2, category, value) values " +
		    "(1,1,2,'C',5), (1,1,2,'H',4), (1,1,2,'I',3), (1,1,2,'K',2), (1,1,2,'E',1), (1,1,3,'C',1), (1,1,3,'H',2), (1,1,3,'I',3), (1,1,3,'K',4), (1,1,3,'E',5)"
		    );
	
    }

    
    public int count_rows (String table) {
	ResultSet rs;
	int n = -1;
	try {
	    rs = pc.query("select count(*) as n from " + table);
	} catch (Exception e) {
	    System.out.println("ERROR select count(*) as n: " + e.getMessage());
	    assertTrue(false);
	    return -1;
	}
	try {
	    rs.next();
	    n = rs.getInt("n");
	} catch (Exception e) {
	    System.out.println("ERROR rs.next() and getInt()");
	    assertTrue(false);
	    return -1;
	}
	return n;
    }



    @Test
    public void check_delete () {
	int n = -1;
	response_delete();
	n = count_rows("response");
	assertEquals("response table should be empty", 0, n);
    }

    /*
    @Test
    public void check_inserts () {
	int n = -1;
	response_delete();

	response_inserts();
	n = count_rows("response");
	assertEquals("should now be 10", 10, n);
    }*/

    //this tests the the input steam opens with given file name
    @Test
    public void ReadCSV()
    {
        PeerEval test = new PeerEval();
        InputStream is = test.loadFile("sampleCSV.csv");

        try{  
            assertNotEquals(is, null);
                }
        catch(Exception e){
            System.out.println("Read CSV failed");
        }
    }  

    

    //this tests that the buffered reader can be created with the given file name
    @Test
    public void getBR()
    {
        PeerEval test = new PeerEval();
        InputStream is = test.loadFile("sampleCSV.csv");

        try{  
            assertNotEquals(test.getBuffer(is), null);
                }
        catch(Exception e){
            System.out.println("Get Buffer failed");
        }
    }


    //confirm first value in file is eval number column name
    @Test
    public void ReadWordOne()
    {
        PeerEval test = new PeerEval();
        InputStream is = test.loadFile("sampleCSV.csv");
        BufferedReader br = test.getBuffer(is);

        try{  
        //get first line and turn into an array
           String[] lineOne = br.readLine().split(",");

           assertEquals(lineOne[0], "Eval");
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm second value in file is S1 column name
    @Test
    public void ReadWordTwo()
    {
        PeerEval test = new PeerEval();
        InputStream is = test.loadFile("sampleCSV.csv");
        BufferedReader br = test.getBuffer(is);

        try{  
        //get first line and turn into an array
           String[] lineOne = br.readLine().split(",");

           assertEquals(lineOne[1], "S1");
           br.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm 3rd value in file is S2 column name
    @Test
    public void ReadWordThree()
    {
        PeerEval test = new PeerEval();
        InputStream is = test.loadFile("sampleCSV.csv");
        BufferedReader br = test.getBuffer(is);

        try{  
        //get first line and turn into an array
           String[] lineOne = br.readLine().split(",");

           assertEquals(lineOne[2], "S2");
           br.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm 4th value in file is Question column name
    @Test
    public void ReadWordFour()
    {
        PeerEval test = new PeerEval();
        InputStream is = test.loadFile("sampleCSV.csv");
        BufferedReader br = test.getBuffer(is);

        try{  
        //get first line and turn into an array
           String[] lineOne = br.readLine().split(",");

           assertEquals(lineOne[3], "Question");
           br.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm 5th value in file is value column name
    @Test
    public void ReadWordFive()
    {
        PeerEval test = new PeerEval();
        InputStream is = test.loadFile("sampleCSV.csv");
        BufferedReader br = test.getBuffer(is);

        try{  
        //get first line and turn into an array
           String[] lineOne = br.readLine().split(",");

           assertEquals(lineOne[4], "Value");
           br.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm values out of column names are strings
    @Test
    public void getStrings()
    {
        PeerEval test = new PeerEval();
        InputStream is = test.loadFile("sampleCSV.csv");
        BufferedReader br = test.getBuffer(is);

        try{  
           assert(br.readLine().split(",")[0].getClass().getSimpleName().equals("String"));
           assert(br.readLine().split(",")[1].getClass().getSimpleName().equals("String"));
           assert(br.readLine().split(",")[2].getClass().getSimpleName().equals("String"));
           assert(br.readLine().split(",")[3].getClass().getSimpleName().equals("String"));
           assert(br.readLine().split(",")[4].getClass().getSimpleName().equals("String"));

           br.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm first value in table is 1
    @Test
    public void getInt1()
    {
        PeerEval test = new PeerEval();
        InputStream is = test.loadFile("sampleCSV.csv");
        BufferedReader br = test.getBuffer(is);

        try{  
           br.readLine();
           assertEquals(br.readLine().split(",")[0], "1");
           br.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm first value second column is 77
    @Test
    public void getInt2()
    {
        PeerEval test = new PeerEval();
        InputStream is = test.loadFile("sampleCSV.csv");
        BufferedReader br = test.getBuffer(is);

        try{  
           br.readLine();
           assertEquals(br.readLine().split(",")[1], "77");
           br.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

}
