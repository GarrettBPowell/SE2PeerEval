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
        Scanner sin = new Scanner(System.in);
        PeerEval peerEval = new PeerEval();
        //list of program options that will be outputted and then switched on based on user input
        String [] options = 
        {
        "1. Load CSV",
        "2. Print Report"
        };

        //begin menu
        System.out.println("Welcome to Peer Eval\nWhat would you like to do? (Please select a number from the list)");
        
        //print all program options
        for(String var : options)
        {
            System.out.println(var);
        }

        String userResponse = "";
        System.out.print("\nSelection: ");
        userResponse = sin.nextLine();

        //Switch on what option user inputted 
        //Currently accounting for single number or number. options ex (1 or 1.)
        System.out.print("\nOption selected: ");
        switch(userResponse)
        {
            case "1.": 
            case "1": 
                System.out.println("(Load CSV)");
                peerEval.loadFileMenu();
                break;
            case "2.":
            case "2":
                 System.out.println("(Print Report)");
                 break;
             default: 
                System.out.println("Option not found");
                break;
        }
     }


     //Contains text prompts for user in command line terminal 
     //Calls other functions but does not do anything to files or database
     public void loadFileMenu()
     {
        Scanner sin = new Scanner(System.in);
        System.out.println("Make sure the file you are wanting to input has been placed in the resources file of this project. \nThis is located at filepath: src/main/resources\n");
        System.out.print("What is the exact name of the csv file you are wanting to read from? (Do not include .csv)\nFile name:");

        String fileName = sin.nextLine();
        
        //check to make sure file is there before moving on
        try{

            loadFile(fileName);
        }
        catch(Exception e)
        {
            System.out.println("************************************");
            System.out.println("A file by that name was not found.\n");
            System.out.println("************************************");
            loadFileMenu();
        }


        System.out.print("What is the name of the table this file is being input into?\nTable name:");
        String tableName = "";
        tableName = sin.nextLine();

        //try loading file into table
        try{
        //
        //Potentially try to connect to table before attempting to load
        //
            loadData(fileName, tableName);
        }
        catch(Exception e)
        {
            System.out.println("************************************");
            System.out.println("A table by that name was not found.\n");
            System.out.println("************************************");
            loadFileMenu();
        }
     }


    //attempts to load data from given file into given table
    public static void loadData(String fileName, String tableName)
    {
        PeerEval pe = new PeerEval();
        Scanner s;
        String columnNames = "";
        c = null;

        try{
            Class.forName("org.postgresql.Driver");
            c = pe.connect("jdbc:postgresql://localhost:5432/cs375v1", "mrblee", "purplewhite");
        } catch(Exception e)
        {
            System.out.println("Failed to connect to database when loading data");
            e.printStackTrace();
        }
        
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


    //connect to database
    public static Connection connect(String url, String userN, String pass) 
    {
       try 
       {
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


    //loads a file given the filename
    public Scanner loadFile(final String fileName) 
    {
        //scanner for reading file
        Scanner s;
        try{
            //
            File fullFile = new File("src/main/resources/" + fileName + ".csv");

            s = new Scanner(fullFile);
        } 
        catch (IOException e)
        {
            e.printStackTrace();
            throw new IllegalArgumentException(fileName + " is not found");
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


    public void nonquery(String inQuery)  
    {
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
}
