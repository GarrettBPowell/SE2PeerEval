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

    public void delete_con(String table, String where) {
	pc.nonquery("delete from " + table + " " + where);
    }
    
    public void response_inserts() {
	pc.nonquery("insert into response (evalid, student1, student2, category, value) values " +
		    "(10,1,2,'C',5), (10,1,2,'H',4), (10,1,2,'I',3), (10,1,2,'K',2), (10,1,2,'E',1), (10,1,3,'C',1), (10,1,3,'H',2), (10,1,3,'I',3), (10,1,3,'K',4), (10,1,3,'E',5)"
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

    public int count_rows_con (String table, String where) {
	ResultSet rs;
	int n = -1;
	try {
	    rs = pc.query("select count(*) as n from " + table + " " + where);
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

    //tests loading in the given file into the given table name
    @Test
    public void loadDataTestRes() {
        String fileName = "response";
        String tableName = "response";
        pc.loadData(fileName, tableName);

        int n = -1;
        n = count_rows_con(tableName, "where evalid = '1'");
         try{  
                  assertEquals(n, 10);
                }
        catch(Exception e){
            System.out.println("loadDataTest Response fail");
        }
 
        delete_con(tableName, "where evalid = '1'");
    }

    //tests loading in teams csv into teams table and checking count of values of evalid = 1 
    @Test
    public void loadDataTestTeam1() {
        String fileName = "teams";
        String tableName = "team";
        pc.loadData(fileName, tableName);

        int n = -1;
        n = count_rows_con(tableName, "where evalid = '1'");
         try{  
                  assertEquals(n, 4);
                }
        catch(Exception e){
            System.out.println("loadDataTest Response fail");
        }
 
        delete_con(tableName, "where evalid = '1'");
        delete_con(tableName, "where evalid = '2'");
    }

    //tests loading in teams csv into teams table and checking count of values of evalid = 2
    @Test
    public void loadDataTestTeam2() {
        String fileName = "teams";
        String tableName = "team";
        pc.loadData(fileName, tableName);

        int n = -1;
        n = count_rows_con(tableName, "where evalid = '2'");
         try{  
                  assertEquals(n, 4);
                }
        catch(Exception e){
            System.out.println("loadDataTest Response fail");
        }
 
        delete_con(tableName, "where evalid = '1'");
        delete_con(tableName, "where evalid = '2'");
    }
    
    //checks deleting from tables conditionally
    @Test
    public void check_delete_con () {
	int n = -1;
	delete_con("response", "where evalid = '10'");
	n = count_rows_con("response", "where evalid = '10'");
	assertEquals("response table where evalid = 10 should be empty", 0, n);
    }

    //checks inserting into response table
    @Test
    public void check_inserts () {
	int n = -1;

	response_inserts();
	n = count_rows_con("response", "where evalid = '10'");
	assertTrue(n == 10);
    }

    //this tests the the input steam opens with given file name
    @Test
    public void ReadCSV()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
            assertNotEquals(s, null);
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
        Scanner s = test.loadFile("response");

        try{  
            assertNotEquals(s, null);
                }
        catch(Exception e){
            System.out.println("Get Scanner failed");
        }
    }

    /*
    //confirm first value in file is eval number column name
    @Test
    public void ReadWordOne()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
        //get first line and turn into an array
           String[] lineOne = s.nextLine().split(",");

           assertEquals(lineOne[0], "evalid");
           s.close();
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
        Scanner s = test.loadFile("response");
        

        try{  
        //get first line and turn into an array
           String[] lineOne = s.nextLine().split(",");

           assertEquals(lineOne[1], "student1");
           s.close();
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
        Scanner s = test.loadFile("response");

        try{  
        //get first line and turn into an array
        String[] lineOne = s.nextLine().split(",");

           assertEquals(lineOne[2], "student2");
           s.close();
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
        Scanner s = test.loadFile("response");      

        try{  
        //get first line and turn into an array
           String[] lineOne = s.nextLine().split(",");

           assertEquals(lineOne[3], "category");
           s.close();
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
        Scanner s = test.loadFile("response");

        try{  
        //get first line and turn into an array
           String[] lineOne = s.nextLine().split(",");

           assertEquals(lineOne[4], "value");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

        //confirm first value in file is eval number column name
    @Test
    public void ReadWordOneTeam()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("teams");

        try{  
        //get first line and turn into an array
           String[] lineOne = s.nextLine().split(",");

           assertEquals(lineOne[0], "evalid");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm second value in file is S1 column name
    @Test
    public void ReadWordTwoTeam()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("teams");

        try{  
        //get first line and turn into an array
           String[] lineOne = s.nextLine().split(",");

           assertEquals(lineOne[1], "teamid");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm 3rd value in file is S2 column name
    @Test
    public void ReadWordThreeTeam()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("team");

        try{  
        //get first line and turn into an array
           String[] lineOne = s.nextLine().split(",");

           assertEquals(lineOne[2], "student");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }
    //confirm values out of column names are strings
    @Test
    public void getStringsResponse()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           assert(s.nextLine().split(",")[0].getClass().getSimpleName().equals("String"));
           assert(s.nextLine().split(",")[1].getClass().getSimpleName().equals("String"));
           assert(s.nextLine().split(",")[2].getClass().getSimpleName().equals("String"));
           assert(s.nextLine().split(",")[3].getClass().getSimpleName().equals("String"));
           assert(s.nextLine().split(",")[4].getClass().getSimpleName().equals("String"));

           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }
        
    //confirm values out of column names are strings
    @Test
    public void getStringsTeams()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("teams");

        try{  
           assert(s.nextLine().split(",")[0].getClass().getSimpleName().equals("String"));
           assert(s.nextLine().split(",")[1].getClass().getSimpleName().equals("String"));
           assert(s.nextLine().split(",")[2].getClass().getSimpleName().equals("String"));

           s.close();
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
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           assertEquals(s.nextLine().split(",")[0], "1");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm first value second column is 1
    @Test
    public void getInt2()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           assertEquals(s.nextLine().split(",")[1], "1");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //--------------------------------------------------------------

    //confirm first value third column is 2
    @Test
    public void getInt3()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           assertEquals(s.nextLine().split(",")[2], "2");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm first value fourth column is 'C'
    @Test
    public void getChar1()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           assertEquals(s.nextLine().split(",")[3], "'C'");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm first value fifth column is 5
    @Test
    public void getInt4()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           assertEquals(s.nextLine().split(",")[4], "5");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm second value in table is 1
    @Test
    public void getInt5()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           s.nextLine();
           assertEquals(s.nextLine().split(",")[0], "1");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm second value second column is 1
    @Test
    public void getInt6()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           s.nextLine();
           assertEquals(s.nextLine().split(",")[1], "1");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm second value third column is 2
    @Test
    public void getInt7()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           s.nextLine();
           assertEquals(s.nextLine().split(",")[2], "2");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm second value fourth column is 'H'
    @Test
    public void getChar2()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           s.nextLine();
           assertEquals(s.nextLine().split(",")[3], "'H'");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm second value fifth column is 4
    @Test
    public void getInt8()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           s.nextLine();
           assertEquals(s.nextLine().split(",")[4], "4");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm third value in table is 1
    @Test
    public void getInt9()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
            s.nextLine();
            s.nextLine();
            s.nextLine();
            assertEquals(s.nextLine().split(",")[0], "1");
            s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm third value second column is 1
    @Test
    public void getInt10()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
            s.nextLine();
            s.nextLine();
            s.nextLine();
           assertEquals(s.nextLine().split(",")[1], "1");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm third value third column is 2
    @Test
    public void getInt11()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
            s.nextLine();
            s.nextLine();
            s.nextLine();
           assertEquals(s.nextLine().split(",")[2], "2");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm third value fourth column is 'I'
    @Test
    public void getChar3()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           s.nextLine();
           s.nextLine();
           assertEquals(s.nextLine().split(",")[3], "'I'");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm third value fifth column is 3
    @Test
    public void getInt12()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           s.nextLine();
           s.nextLine();
           assertEquals(s.nextLine().split(",")[4], "3");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }
    */

}
