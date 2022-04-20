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
import java.sql.*;

import java.io.File; 
import java.util.*;
import java.util.Scanner;
import java.io.*;

import java.io.FileWriter;
import java.io.BufferedWriter;

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
    private static String url = "jdbc:postgresql://localhost:5432/cs375v1";
    private static String user = "mrblee";
    private static String password = "purplewhite";
    public static Connection c;
    public static PeerEval pe = new PeerEval();

    public static void main(final String[] args) throws IOException 
    {
        pe.logon();
        
     }

     public void logon()
     {       
        Scanner sin = new Scanner(System.in);
        boolean correctLogOn = false;
        //list of program options that will be outputted and then switched on based on user input
        String [] options = 
        {
        "1. Load CSV",
        "2. Print Report"
        };

        while(!correctLogOn)
        {
            System.out.print("Please enter your credentials.\nUsername: ");
            user = sin.nextLine();
            System.out.print("\nPlease enter your password: ");
            password = sin.nextLine();

            try{
                connect(url, user, password);
                if(c != null)
                    correctLogOn = true;
                else
                {
                    System.out.println("*************************************************");
                    System.out.println("Incorrect Username or Password. Please try again.");
                    System.out.println("*************************************************");
                }
            }
            catch (Exception e)
            {
                System.out.println("*************************************************");
                System.out.println("Incorrect Username or Password. Please try again.");
                System.out.println("*************************************************");
            }
        }

        adminMenu();
     }

     public void adminMenu()
     {
        Scanner sin = new Scanner(System.in);
        //list of program options that will be outputted and then switched on based on user input
        String [] options = 
        {
        "1. Load CSV",
        "2. Print Report",
        "3. Exit"
        };


        //begin menu
        System.out.println("\n\nWelcome to Peer Eval\nWhat would you like to do? (Please select a number from the list)");
        
        //print all program options
        for(String var : options)
        {
            System.out.println(var);
        }

        String userResponse = "";
        System.out.print("\nSelection Number: ");
        userResponse = sin.nextLine();

        //Switch on what option user inputted 
        //Currently accounting for single number or number. options ex (1 or 1.)
        System.out.print("\nOption selected: ");
        switch(userResponse)
        {
            case "1.": 
            case "1": 
                System.out.println("(Load CSV)");
                pe.loadFileMenu();
                break;
            case "2.":
            case "2":
                System.out.println("(Print Report)");
                pe.printReportMenu();
                break;

            case "3.":
            case "3":
                System.out.println("(Exit)");
                break;
            default: 
                System.out.println("Option not found");
                adminMenu();
                break;
        }
     }

     //Contains text prompts for user in command line terminal 
     //Calls other functions but does not do anything to files or database
     public void loadFileMenu()
     {
        Scanner sin = new Scanner(System.in);
        System.out.println("Make sure the file you are wanting to input has been placed in the resources file of this project. \nThis is located at filepath: src/main/resources\n");
       

        boolean correctFileName = false;
        String fileName = "";
         String tableName = "";

        //keep looping till valid file name is given
        while(!correctFileName)
        {
            System.out.print("What is the exact name of the csv file you are wanting to read from? (Do not include .csv)\nFile name:");

            fileName = sin.nextLine();
        
            //check to make sure file is there before moving on
            try{

                loadFile(fileName);
                correctFileName = true;
            }
            catch(Exception e)
            {
                System.out.println("************************************");
                System.out.println("A file by that name was not found.\n");
                System.out.println("************************************");
            
            }
        }
    
        tableName = "response";

        //try loading file into table
        try
        {
            loadData(fileName, tableName);
        }
        catch(Exception e)
        {
            System.out.println("************************************");
            System.out.println("A table by that name was not found.\n");
            System.out.println("************************************");
                
        }   
     }


    //attempts to load data from given file into given table
    public static void loadData(String fileName, String tableName)
    {
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
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println(fileName + " loaded successfully into " + tableName);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            
            //System.out.println("Is there anything else you would like to do?");
            //pe.adminMenu();
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
            c = null;
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

    //querys and accounts for a return value (select)
    public ResultSet query(String inQuery) throws Exception {
	    //	System.out.println("query: [" + inQuery + "]");
	    ResultSet rs = null;

	    PreparedStatement pstmt = c.prepareStatement(inQuery);
	    rs = pstmt.executeQuery();

	    return rs;
    }

    //querys for query with no return value (insert, delete, etc)
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


    //displays menu when print report is selected 
    public void printReportMenu()
    {
        Scanner sin = new Scanner(System.in);
        String [] options = 
        {
        "1. Student Report (By Student ID)",
        "2. Class Report (By Eval ID)",
        "3. Go Back",
        "4. Exit"
        };


        System.out.println("What report would you like to print?");
        
        //print all program options
        for(String var : options)
        {
            System.out.println(var);
        }
        System.out.print("\nOption selected: ");

        String userResponse = "";
        System.out.print("\nSelection Number: ");
        userResponse = sin.nextLine();

        //Switch on what option user inputted 
        //Currently accounting for single number or number. options ex (1 or 1.)
        System.out.print("\nOption selected: ");
        switch(userResponse)
        {
            case "1.": 
            case "1": 
                System.out.println("(Student Report)");
                printStudentMenu();
                break;
            case "2.":
            case "2":
                System.out.println("(Class Report)");
                break;
            case "3.":
            case "3":
                System.out.println("(Go Back)");
                adminMenu();
                break;
            case "4.":
            case "4":
                System.out.println("(Exit)");
                break;

            default: 
                System.out.println("Option not found");
                printReportMenu();
                break;
        }     
    }

    //displays menu for when student is selected in the print report screen
    public void printStudentMenu()
    {
        Scanner sin = new Scanner(System.in);
        String [] options = 
        {
        "1. Single Student Report (Student ID and EvalID)",
        "2. All Student's Reports (Student ID)",
        "3. All Student Reports with overall Stats (Student ID)",
        "4. Go Back",
        "5. Exit"
        };


        System.out.println("What report would you like to print?");
        //print all program options
        for(String var : options)
        {
            System.out.println(var);
        }
        System.out.print("\nOption selected: ");

        String userResponse = "";
        System.out.print("\nSelection Number: ");
        userResponse = sin.nextLine();
        
        //Switch on what option user inputted 
        //Currently accounting for single number or number. options ex (1 or 1.)
        System.out.print("\nOption selected: ");
        switch(userResponse)
        {
            case "1.": 
            case "1": 
                System.out.println("(Single Student Report)");
                printSingleStudent();               
                break;

            case "2.":
            case "2":
                System.out.println("(All Student's Reports)");
                createHTML();
                break;

            case "3.":
            case "3":
                System.out.println("(All Student's Reports with overall Stats)");
                break;

            case "4.":
            case "4":
                System.out.println("(Go Back)");
                printReportMenu();
                break;

            case "5.":
            case"5":
                System.out.println("(Exit)");
                break;

            default: 
                System.out.println("Option not found");
                printStudentMenu();
                break;
        } 
    }


    //prints a single student for a single evalid
    public void printSingleStudent()
    {
     
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

        Scanner sin = new Scanner(System.in);
        System.out.print("\nWhat is the student ID of the student?\nStudent ID: ");
        String studentID = sin.nextLine();
        System.out.print("\nWhat is the eval ID of the student you want to print?\nEval ID:");
        String evalID = sin.nextLine();

        try{
            String queryString = "Select * from v_response where student2 = '" + studentID + "' and evalID = '" + evalID + "';";

            //
            //System.out.println("Select * from v_response where student2 = '2' AND evalid = '999';");
            printResultSet(query(queryString));
            
            }catch(Exception e){
                System.out.print("Failed printSingleStudent");
            }

    }

    //prints a given result set entirely
    public void printResultSet(ResultSet rs)
    {
        try{
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) 
            {
                for (int i = 1; i <= columnsNumber; i++) 
                {
                    if (i > 1) System.out.print(",  ");
                        String columnValue = rs.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }
        }
        catch(Exception e)
        {
            System.out.println("Print View failed");
        }
    }

    public void v_response_print(ResultSet rs) {
	System.out.println("evalid\tstudent1\tstudent2\tcategory\tvalue");
	    try {
	        while(!rs.isLast()) {
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

    public void createHTML()
    {
        System.out.println("About to create the HTML file...");

        String html = "<div>Try writing it the html...</div>";
        //File f = new File("C:\\Users\\Chicky Nuggy\\Documents\\ACU\\Software Engineering II\\SE2PeerEval\\test.html");
        File f = new File("src/main/html/test.html");
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(html);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Created the HTML file.");
    }
}
