import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.io.File; 
import java.util.*;
import java.util.Scanner;
import java.io.*;



/***********************************
*	Authors: Christa Greenwood, Garrett Powell, Megan Skeen
*	Class: CS 375.01 - Spring 2022
*	Project - Peer Evaluations
*
*
*	How to compile/build:
*       make sure y ou are cd into SE2PeerEval file 
*		mvn compile
*	
*	How to run/execute:
*       mvn test
*		mvn install assembly:assembly
*       java -cp target/java_postgres-1.0-SNAPSHOT-jar-with-dependencies.jar PeerEval
*
*	How to test:
*		mvn test
*
************************************/

public class PeerEval
{
    public static String filePath;
    private static String url = "jdbc:postgresql://localhost:5432/SE375v1";
    private static String user = "mrblee";
    private static String password = "purplewhite";
    public static Connection c;

    public static void main(final String[] args) throws IOException 
    {
        System.out.println("Hello");
        loadData("response", "response");

        
     }

    public static void loadData(String fileName, String tableName)
    {
        PeerEval pe = new PeerEval();
        c = null;
        try{
            Class.forName("org.postgresql.Driver");
            c = pe.connect("jdbc:postgresql://localhost:5432/cs375v1", "mrblee", "purplewhite");
        } catch(Exception e)
        {
            e.printStackTrace();
        }


        Scanner s;

        String columnNames = "";
        try{
            s = pe.loadFile(fileName);

            columnNames = s.nextLine();
            //System.out.println(columnNames + "\n");

            String query = "insert into " + tableName + "(" + columnNames + ") values";
            String line = "";
            while(s.hasNextLine())
            {
                line = s.nextLine();
                //System.out.println(line);
                query = query + " (" + line + "),";
            }

            query = query.substring(0, query.length() -1);
            //System.out.println(query);
            pe.nonquery(query);
        } 
        catch(Exception e){
            System.out.println("load data failed");
        }
    }

    public static Connection connect(String url, String userN, String pass) {

        try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager.getConnection(url, userN, pass);
            } 
            catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName()+": "+e.getMessage());
                System.exit(0);
            }
        return c;
    }

    public Scanner loadFile(final String fileName) 
    {
        Scanner s;
        String fullFileName = "";
        try{
            //
            File fullFile = new File("src/main/resources/" + fileName + ".csv");

            s = new Scanner(fullFile);

        } catch (IOException e){
            e.printStackTrace();
            throw new IllegalArgumentException(fullFileName + " is not found");
        }
        //this.getClass().getClassLoader().getResourceAsStream("..\\..\\resources\\" +fileName);
        
        return s;
    }

    public void printFile(Scanner s)
    {
        try 
        {
            s.useDelimiter(", ");

            while (s.hasNext()) {
                System.out.print(s.next());
            }

            s.close();
            System.out.println("\n");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public ResultSet query(String inQuery) throws Exception {
	    //	System.out.println("query: [" + inQuery + "]");
	    ResultSet rs = null;

	    PreparedStatement pstmt = c.prepareStatement(inQuery);
	    rs = pstmt.executeQuery();

	    return rs;
    }


    public void nonquery(String inQuery)  {
	//	System.out.println("nonquery: [" + inQuery + "]");
	ResultSet rs;
	    try {
	        PreparedStatement pstmt = c.prepareStatement(inQuery);
	        rs = pstmt.executeQuery();
	    }  catch(Exception e) {
	        if (! ("No results were returned by the query.".equals(e.getMessage()))) {
		    System.out.println("ERROR response delete");
		    System.out.println(e.getMessage());
	        }
	    }
    }

    public void v_response_print(ResultSet rs) {
	System.out.println("evalid\tstudent1\tstudent2\tcategory\tvalue");
	    try {
	        while(rs.next()) {
		    System.out.print(rs.getInt(1));
		    System.out.print("\t");
		    System.out.print(rs.getInt(2));
		    System.out.print("\t\t");
		    System.out.print(rs.getInt(3));
		    System.out.print("\t\t"+ rs.getString(4));
		    System.out.print("\t\t");
		    System.out.println(rs.getInt(5));
	        } }
	    catch(Exception exec) {
	        System.out.println("v_response_print not happy");
	        exec.printStackTrace();
	    }
    }

    /*
    public void print_view(ResultSet rs){
        try{
            while(rs.next())
            {
                System.out.println(rs);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void printStuAvg()
    {
        print_view(query("Select * from v_stuavg"));
    }*/

}
