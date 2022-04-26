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
		    "(999,1,2,'C',5), (999,1,2,'H',4), (999,1,2,'I',3), (999,1,2,'K',2), (999,1,2,'E',1), (999,1,3,'C',1), (999,1,3,'H',2), (999,1,3,'I',3), (999,1,3,'K',4), (999,1,3,'E',5)"
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

    public int avg_rows (String table, String column) {
	ResultSet rs;
	int n = -1;
	try {
	    rs = pc.query("select avg(" + column + ") as n from " + table);
	} catch (Exception e) {
	    System.out.println("ERROR select avg(value) as n: " + e.getMessage());
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
    public void sql_loadDataTestRes() {
        String fileName = "response";
        String tableName = "response";
        delete_con(tableName, "where evalid = '999'");

        try{  
        pc.loadData(fileName, tableName);

        int n = -1;
        n = count_rows_con(tableName, "where evalid = '999'");
                  assertEquals(10, n);
                }
        catch(Exception e){
            System.out.println("loadDataTest Response fail");
        }
 
        delete_con(tableName, "where evalid = '999'");
    }

    //tests loading in teams csv into teams table and checking count of values of evalid = 1 
    @Test
    public void sql_loadDataTestTeam1() {
         String fileName = "teams";
         String tableName = "team";
         
         try{  
                pc.loadData(fileName, tableName);

                int n = -1;
                n = count_rows_con(tableName, "where evalid = '999'");
                assertEquals(4, n);
        }
        catch(Exception e){
            System.out.println("loadDataTest Response fail");
        }
 
        delete_con(tableName, "where evalid = '999'");
        delete_con(tableName, "where evalid = '1000'");
    }

    //tests loading in teams csv into teams table and checking count of values of evalid = 2
    @Test
    public void sql_loadDataTestTeam2() {
        String fileName = "teams";
        String tableName = "team";

        try{  
                pc.loadData(fileName, tableName);

                int n = -1;
                n = count_rows_con(tableName, "where evalid = '1000'");
                assertEquals(4, n);
        }
        catch(Exception e){
            System.out.println("loadDataTest Response fail");
        }
 
        delete_con(tableName, "where evalid = '999'");
        delete_con(tableName, "where evalid = '1000'");
    }
    
    //checks deleting from tables conditionally
    @Test
    public void sql_check_delete_con () {
	int n = -1;
	delete_con("response", "where evalid = '999'");
	n = count_rows_con("response", "where evalid = '999'");
	assertEquals("response table where evalid = 999 should be empty", 0, n);
    }

    //checks inserting into response table
    @Test
    public void sql_check_inserts () {
    delete_con("response", "where evalid = '999'");
	int n = -1;

	response_inserts();
	n = count_rows_con("response", "where evalid = '999'");
	assertTrue(n == 10);
    delete_con("response", "where evalid = '999'");
    }

    //this tests the the input steam opens with given file name
    @Test
    public void java_ReadCSV()
    {

        try{  
            PeerEval test = new PeerEval();
            Scanner s = test.loadFile("response");
            assertNotEquals(s.next(), null);
                }
        catch(Exception e){
            System.out.println("Read CSV failed");
        }
    }  

    

    //this tests that the buffered reader can be created with the given file name
    @Test
    public void java_getScanner()
    {
        PeerEval test = new PeerEval();
        

        try{  
            Scanner s = test.loadFile("response");
            assertNotEquals(s, null);
                }
        catch(Exception e){
            System.out.println("Get Scanner failed");
        }
    }

    
    //confirm first value in file is eval number column name
    @Test
    public void java_ReadWordOne()
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
    public void java_ReadWordTwo()
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
    public void java_ReadWordThree()
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
    public void java_ReadWordFour()
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
    public void java_ReadWordFive()
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
    public void java_ReadWordOneTeam()
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
    public void java_ReadWordTwoTeam()
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
    public void java_ReadWordThreeTeam()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("teams");

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
    public void java_getStringsResponse()
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
    public void java_getStringsTeams()
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
    public void java_getInt1()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           assertEquals(s.nextLine().split(",")[0], "999");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm first value second column is 1
    @Test
    public void java_getInt2()
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
    public void java_getInt3()
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
    public void java_getChar1()
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
    public void java_getInt4()
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
    public void java_getInt5()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           s.nextLine();
           assertEquals(s.nextLine().split(",")[0], "999");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm second value second column is 1
    @Test
    public void java_getInt6()
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
    public void java_getInt7()
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
    public void java_getChar2()
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
    public void java_getInt8()
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
    public void java_getInt9()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
            s.nextLine();
            s.nextLine();
            s.nextLine();
            assertEquals(s.nextLine().split(",")[0], "999");
            s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm third value second column is 1
    @Test
    public void java_getInt10()
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
    public void java_getInt11()
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
    public void java_getChar3()
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
    public void java_getInt12()
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
    
    
    //checks averages in value column of response table
    @Test
    public void sql_check_valAvg () {
	    float n = -1;
        delete_con("response", "where evalID = '999'");
	    response_inserts();
	    n = avg_rows("response", "value");

    
	    try{ 
           assertEquals(n, 3.0, 0.001);
           delete_con("response", "value");
                }
        catch(Exception e){
            System.out.println("Average value failed");
        }
        delete_con("response", "where evalID = '999'");
    }

    //counts rows with category C
    @Test
    public void sql_count_categoriesC () {
        delete_con("response", "where evalID = '999'");
	    int n = -1;

	    response_inserts();
	    n = count_rows_con("response", "where category = 'C'");
	    assertTrue(n == 2);
        delete_con("response", "where evalID = '999'");
    }

    //counts rows with category H
    @Test
    public void sql_count_categoriesH () {
        delete_con("response", "where category = 'H'");
	    int n = -1;

	    response_inserts();
	    n = count_rows_con("response", "where category = 'H'");
	    assertTrue(n == 2);
        delete_con("response", "where category = 'H'");
    }

    //counts rows with student2 = 2
    @Test
    public void sql_count_student2s2 () {
   
	    int n = -1;
        delete_con("response", "where evalID = '999'");
	    response_inserts();
	    n = count_rows_con("response", "where student2 = '2' and evalID = '999'");
	    assertEquals(5, n);

        delete_con("response", "where evalID = '999'");
   
    }

    //counts rows with student2 = 3
    @Test
    public void sql_count_student2s3 () {
     
	    int n = -1;
        delete_con("response", "where evalID = '999'");
	    response_inserts();

	    n = count_rows_con("response", "where student2 = '3' and evalID = '999'");
	    assertEquals(5, n);
        delete_con("response", "where evalID = '999'");
     
    }

    //-----------------------------------------------------------------------

    //confirm fourth value in table is 999
    @Test
    public void java_getInt13()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
            s.nextLine();
            s.nextLine();
            s.nextLine();
            s.nextLine();
            assertEquals(s.nextLine().split(",")[0], "999");
            s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm fourth value second column is 1
    @Test
    public void java_getInt14()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
            s.nextLine();
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

    //confirm fourth value third column is 2
    @Test
    public void java_getInt15()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
            s.nextLine();
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

    //confirm fourth value fourth column is 'K'
    @Test
    public void java_getChar4()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           s.nextLine();
           s.nextLine();
           s.nextLine();
           assertEquals(s.nextLine().split(",")[3], "'K'");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm fourth value fifth column is 2
    @Test
    public void java_getInt16()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           s.nextLine();
           s.nextLine();
           s.nextLine();
           assertEquals(s.nextLine().split(",")[4], "2");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm fifth value in table is 999
    @Test
    public void java_getInt17()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
            s.nextLine();
            s.nextLine();
            s.nextLine();
            s.nextLine();
            s.nextLine();
            assertEquals(s.nextLine().split(",")[0], "999");
            s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm fifth value second column is 1
    @Test
    public void java_getInt18()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
            s.nextLine();
            s.nextLine();
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

    //confirm fifth value third column is 2
    @Test
    public void java_getInt19()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
            s.nextLine();
            s.nextLine();
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

    //confirm fifth value fourth column is 'E'
    @Test
    public void java_getChar5()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           s.nextLine();
           s.nextLine();
           s.nextLine();
           s.nextLine();
           assertEquals(s.nextLine().split(",")[3], "'E'");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm fifth value fifth column is 3
    @Test
    public void java_getInt20()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           s.nextLine();
           s.nextLine();
           s.nextLine();
           s.nextLine();
           assertEquals(s.nextLine().split(",")[4], "1");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }




    //confirm sixth value in table is 999
    @Test
    public void java_getInt21()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
            s.nextLine();
            s.nextLine();
            s.nextLine();
            s.nextLine();
            s.nextLine();
            assertEquals(s.nextLine().split(",")[0], "999");
            s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm sixth value second column is 1
    @Test
    public void java_getInt22()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
            s.nextLine();
            s.nextLine();
            s.nextLine();
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

    //confirm sixth value third column is 3
    @Test
    public void java_getInt23()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
            s.nextLine();
            s.nextLine();
            s.nextLine();
            s.nextLine();
            s.nextLine();
            s.nextLine();
           assertEquals(s.nextLine().split(",")[2], "3");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm sixth value fourth column is 'C'
    @Test
    public void java_getChar6()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           s.nextLine();
           s.nextLine();
           s.nextLine();
           s.nextLine();
           s.nextLine();
           assertEquals(s.nextLine().split(",")[3], "'C'");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }

    //confirm sixth value fifth column is 1
    @Test
    public void java_getInt24()
    {
        PeerEval test = new PeerEval();
        Scanner s = test.loadFile("response");

        try{  
           s.nextLine();
           s.nextLine();
           s.nextLine();
           s.nextLine();
           s.nextLine();
           s.nextLine();
           assertEquals(s.nextLine().split(",")[4], "1");
           s.close();
                }
        catch(Exception e){
            System.out.println("Read line failed");
        }
    }


    //this tests the the input steam opens with given file name
    @Test
    public void html_ReadHTML()
    {

        String html = "<!DOCTYPE html><html><head></head><body><h1>Test HTML File</h1>" +
        "<table><tr>Sample Data</tr><td>Sample Data Piece</td></table></body></html>";

        File f = new File("src/main/html/test.html");
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(html);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{  
            PeerEval test = new PeerEval();
            Scanner s = test.loadFileHTML("test");
            assertNotEquals(s.nextLine(), null);
                }
        catch(Exception e){
            System.out.println("Read HTML failed");
        }
    } 

    //this tests the html put into the test html file
    @Test
    public void html_getHTMLData()
    {

        String html = "<!DOCTYPE html><html><head></head><body><h1>Test HTML File</h1>" +
        "<table><tr>Sample Data</tr><td>Sample Data Piece</td></table></body></html>";

        File f = new File("src/main/html/test.html");
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(html);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{  
            PeerEval test = new PeerEval();
            Scanner s = test.loadFileHTML("test");
            String data = s.nextLine();
            assertEquals(data, "<!DOCTYPE html><html><head></head><body><h1>Test HTML File</h1>" +
        "<table><tr>Sample Data</tr><td>Sample Data Piece</td></table></body></html>");
                }
        catch(Exception e){
            System.out.println("Read HTML failed");
        }
    } 

    //this tests that the file contains the correct header
    @Test
    public void html_getHTMLHeader()
    {

        String html = "<!DOCTYPE html><html><head></head><body><h1>Test HTML File</h1>" +
        "<table><tr>Sample Data</tr><td>Sample Data Piece</td></table></body></html>";

        File f = new File("src/main/html/test.html");
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(html);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{  
            PeerEval test = new PeerEval();
            Scanner s = test.loadFileHTML("test");
            String data = s.nextLine();
            assertEquals(data.contains("<h1>Test HTML File</h1>"), true);
                }
        catch(Exception e){
            System.out.println("Read HTML failed");
        }
    } 

    //this tests that the file contains the correct table row
    @Test
    public void html_getHTMLRow()
    {

        String html = "<!DOCTYPE html><html><head></head><body><h1>Test HTML File</h1>" +
        "<table><tr>Sample Data</tr><td>Sample Data Piece</td></table></body></html>";

        File f = new File("src/main/html/test.html");
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(html);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{  
            PeerEval test = new PeerEval();
            Scanner s = test.loadFileHTML("test");
            String data = s.nextLine();
            assertEquals(data.contains("<tr>Sample Data</tr>"), true);
                }
        catch(Exception e){
            System.out.println("Read HTML failed");
        }
    } 

    //this tests that the file contains the correct table data
    @Test
    public void html_getHTMLDataPiece1()
    {

        String html = "<!DOCTYPE html><html><head></head><body><h1>Test HTML File</h1>" +
        "<table><tr>Sample Data</tr><td>Sample Data Piece</td></table></body></html>";

        File f = new File("src/main/html/test.html");
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(html);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{  
            PeerEval test = new PeerEval();
            Scanner s = test.loadFileHTML("test");
            String data = s.nextLine();
            assertEquals(data.contains("<td>Sample Data Piece</td>"), true);
                }
        catch(Exception e){
            System.out.println("Read HTML failed");
        }
    } 

    //this tests that the file contains the correct table data
    @Test
    public void html_getHTMLDataPiece2()
    {

        String html = "<!DOCTYPE html><html><head></head><body><h1>Test HTML File</h1>" +
        "<table><tr>Sample Data1</tr><tr>Sample Data2</tr><tr>Sample Data3</tr><tr>Sample Data4</tr>" +
        "<td>Sample Data Piece1</td><td>Sample Data Piece2</td>" +
        "<td>Sample Data Piece3</td><td>Sample Data Piece4</td></table></body></html>";

        File f = new File("src/main/html/test.html");
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(html);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{  
            PeerEval test = new PeerEval();
            Scanner s = test.loadFileHTML("test");
            String data = s.nextLine();
            assertEquals(data.contains("<td>Sample Data Piece1</td><td>Sample Data Piece2</td>"), true);
                }
        catch(Exception e){
            System.out.println("Read HTML failed");
        }
    } 

    //this tests that the file contains <!DOCTYPE html><html>
    @Test
    public void html_getHTMLdoctype()
    {

        String html = "<!DOCTYPE html><html><head></head><body><h1>Test HTML File</h1>" +
        "<table><tr>Sample Data1</tr><tr>Sample Data2</tr><tr>Sample Data3</tr><tr>Sample Data4</tr>" +
        "<td>Sample Data Piece1</td><td>Sample Data Piece2</td>" +
        "<td>Sample Data Piece3</td><td>Sample Data Piece4</td></table></body></html>";

        File f = new File("src/main/html/test.html");
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(html);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{  
            PeerEval test = new PeerEval();
            Scanner s = test.loadFileHTML("test");
            String data = s.nextLine();
            assertEquals(data.contains("<!DOCTYPE html><html>"), true);
                }
        catch(Exception e){
            System.out.println("Read HTML failed");
        }
    } 

    //this tests that the file contains a closing html tag
    @Test
    public void html_getHTMLTag()
    {

        String html = "<!DOCTYPE html><html><head></head><body><h1>Test HTML File</h1>" +
        "<table><tr>Sample Data1</tr><tr>Sample Data2</tr><tr>Sample Data3</tr><tr>Sample Data4</tr>" +
        "<td>Sample Data Piece1</td><td>Sample Data Piece2</td>" +
        "<td>Sample Data Piece3</td><td>Sample Data Piece4</td></table></body></html>";

        File f = new File("src/main/html/test.html");
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(html);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{  
            PeerEval test = new PeerEval();
            Scanner s = test.loadFileHTML("test");
            String data = s.nextLine();
            assertEquals(data.contains("</html>"), true);
                }
        catch(Exception e){
            System.out.println("Read HTML failed");
        }
    } 

    //this tests that the file contains a head section
    @Test
    public void html_getHTMLDataPiece3()
    {

        String html = "<!DOCTYPE html><html><head></head><body><h1>Test HTML File</h1>" +
        "<table><tr>Sample Data1</tr><tr>Sample Data2</tr><tr>Sample Data3</tr><tr>Sample Data4</tr>" +
        "<td>Sample Data Piece1</td><td>Sample Data Piece2</td>" +
        "<td>Sample Data Piece3</td><td>Sample Data Piece4</td></table></body></html>";

        File f = new File("src/main/html/test.html");
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(html);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{  
            PeerEval test = new PeerEval();
            Scanner s = test.loadFileHTML("test");
            String data = s.nextLine();
            assertEquals(data.contains("<head></head>"), true);
                }
        catch(Exception e){
            System.out.println("Read HTML failed");
        }
    }

    //this tests that the file contains a body section
    @Test
    public void html_getHTMLDataPiece4()
    {

        String html = "<!DOCTYPE html><html><head></head><body><h1>Test HTML File</h1>" +
        "<table><tr>Sample Data1</tr><tr>Sample Data2</tr><tr>Sample Data3</tr><tr>Sample Data4</tr>" +
        "<td>Sample Data Piece1</td><td>Sample Data Piece2</td>" +
        "<td>Sample Data Piece3</td><td>Sample Data Piece4</td></table></body></html>";

        File f = new File("src/main/html/test.html");
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(html);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{  
            PeerEval test = new PeerEval();
            Scanner s = test.loadFileHTML("test");
            String data = s.nextLine();
            assertEquals(data.contains("<body>"), true);
                }
        catch(Exception e){
            System.out.println("Read HTML failed");
        }
    }
}
