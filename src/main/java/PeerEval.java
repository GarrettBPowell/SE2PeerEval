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
           System.out.println("Credentials did not validate");
            c = null;
        }
        return c;
    }


    //loads a csv file given the filename
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

    //loads an html file given the filename
    public Scanner loadFileHTML(final String fileName) 
    {
        //scanner for reading file
        Scanner s;
        try{
            //
            File fullFile = new File("src/main/html/" + fileName + ".html");

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
        "1. Student View",
        "2. Teacher View",
        "3. Admin View",
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
                System.out.println("(Student View)");
                printStudentMenu();
                break;
            case "2.":
            case "2":
                System.out.println("(Teacher View)");
                printClassReport();
                break;
            case "3.":
            case "3":
                System.out.println("(Admin View)");
                printAdminView();
                break;

            case "4.":
            case "4":
                System.out.println("(Go Back)");
                adminMenu();
                break;
            
            case "5.":
            case "5":
                System.out.println("(Exit)");
                break;

            default: 
                System.out.println("Option not found");
                printReportMenu();
                break;
        }     
    }

    //HTML PRINJT
    //Print the raw not anonymized data that spans previous classes
    //
    public void printAdminView()
    {
        String columnNames = "";
        c = null;

        try{
            Class.forName("org.postgresql.Driver");
            c = pe.connect("jdbc:postgresql://localhost:5432/cs375v1", "mrblee", "purplewhite");
        } catch(Exception e)
        {
            System.out.println("Failed to connect to database when printing admin");
            e.printStackTrace();
        }

        Scanner sin = new Scanner(System.in);
        System.out.print("\nAdmin View:");

        try{
        Double alltimeAvg = 0.0;
            System.out.println("Printed Admin");
            ResultSet rs = null;
            String queryString = "Select avg(value) from response";
            rs = query(queryString);
            String columnValue = "";
            if(rs.next())
            {
                columnValue = rs.getString("avg");
                alltimeAvg = Double.parseDouble(columnValue);
            }

            String[] stats = new String[1];
            stats[0] = alltimeAvg.toString();
           
            //String queryString = "Select * from v_response where student2 = '" + classID;

            //
            //System.out.println("Select * from v_response where student2 = '2' AND evalid = '999';");
            //printResultSet(query(queryString));
            //createHTMLResult(query(queryString));
            
        }catch(Exception e){
                System.out.print("Failed printAdminView");
            }
    }

    //HTML PRINT
    //Print the view with anonymized data to show to class
    //Teacher view with raw not naonymized data
    public void printClassReport()
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
        System.out.print("\nWhat is the section ID?\nSection ID: ");
        String sectionID = sin.nextLine();
        System.out.print("\nWould you like the data anonymized?\nY/N: ");
        String anonymized = sin.nextLine();
        System.out.print("\nWhat should the output file be named?\nFilename:");
        String htmlFilename = sin.nextLine();

        //backhere


        
        try
        {
            Double classAvg = 0.0;
            String queryString = "Select avg(value) from response join eval_section on response.evalid = eval_section.eval_id where section_id = '" + sectionID + "';" ;
            ResultSet rs = null;
            rs = query(queryString);
            String columnValue = "";
            if(rs.next())
            {
                columnValue = rs.getString("avg");
                classAvg = Double.parseDouble(columnValue);
            }

            String[] stats = new String[1];
            stats[0] = classAvg.toString();
        }
        catch(Exception e)
        {
            System.out.print("Failed class print");
        }
        try{
            String queryString = "Select * from v_response;";
            
            //if ((anonymized == "Y") || (anonymized == "y")){
            if (anonymized.equals("Y") || anonymized.equals("y")){
                //createHTMLResult(query(queryString), htmlFilename, 1, true);
            }
            else{
                //createHTMLResult(query(queryString), htmlFilename, 1, false);
            }
            
        }catch(Exception e){
                System.out.print("Failed printAllStudentResponsesStats");
            }
    }



    //HTML PRINT
    //Specific student with anonymized data
    //Student Lifetime view with anonymized data
    //Student view for team statistics
    public void printStudentMenu()
    {
        Scanner sin = new Scanner(System.in);
        String [] options = 
        {
        "1. Single Student Report (Student ID and EvalID)",
        "2. Student Team Statistics (Student ID)",
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
                System.out.println("(Student Team Statistics)");
                teamStats(true);
                //createHTML();
                break;

            case "3.":
            case "3":
                System.out.println("(All Student's Reports with overall Stats)");
                printAllStudentResponsesStats();
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


    //HTML PRINT
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
        System.out.print("\nWhat should the output file be named?\nFilename:");
        String htmlFilename = sin.nextLine();

        try{
            String queryString = "Select * from v_response where student2 = '" + studentID + "' and evalID = '" + evalID + "';";

            //
            //System.out.println("Select * from v_response where student2 = '2' AND evalid = '999';");
            //printResultSet(query(queryString));
            //createHTMLResult(query(queryString), htmlFilename, 1, false);
            
            }catch(Exception e){
                System.out.print("Failed printSingleStudent");
            }

    }

    //HTML PRINT
    //Prints the stats of a specifc student for a specifc team/evalid
    public void teamStats(boolean anonymized)
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

        System.out.print("\nWhat is the team ID for the stats you want to calculate?\nTeam ID: ");
        String teamID = sin.nextLine();

        System.out.print("\nWhat is the eval ID for the stats you want to calculate?\nEval ID: ");
        String evalID = sin.nextLine();

        System.out.print("\nWhat should the output file be named?\nFilename:");
        String htmlFilename = sin.nextLine();

        try{

        //calcs stats and returns all of the flags
            String flagsForStudent [];
            flagsForStudent = calcStats(studentID, teamID, evalID);
            String queryString = "Select * from v_response where student2 = '" + studentID + "' and evalid = '" + evalID + "';";

            System.out.println(flagsForStudent[0] + " " + flagsForStudent[1] + " " + flagsForStudent[2] + " " + flagsForStudent[3]);
            //
            //System.out.println("Select * from v_response where student2 = '2' AND evalid = '999';");
            //printResultSet(query(queryString));
            createHTMLResult(query(queryString), htmlFilename, 1, anonymized, flagsForStudent);
            
            }catch(Exception e){
                System.out.print("Failed printAllStudentResponses");
            }
    }

    public String[] calcStats(String stuID, String teamID, String evalID)
    {
        String queryString = "";
        ResultSet rs = null;

        //team values
        Double teamAvg = 0.0;;

        //count of ratings from other students
        Double teamRatingOfStu = 0.0;

        //students rating of themselves
        Double stuRating = 0.0;

        //student's rating of rest of team
        Double ratedStudentOfTeam = 0.0;

        Double stdOfTeam = 0.0;

       

        //queryString = "Select student1, student2, value from response join team On response.student1 = team.student where team.evalID = '" + evalID + "' and team.teamid = '" + teamID + "' order by category, student2, student1;";
        
        try 
        {
        //get query
            //printResultSet(query(queryString));
            //student's self rating
            
            queryString = "Select avg(value) from response join team On response.student1 = team.student where student1 = '" + stuID + "' and student2 = '" + stuID + "' and team.evalid = '" + evalID + "' and teamID = '" + teamID + "';";
            
            rs = query(queryString);
            String columnValue = "";
            if(rs.next())
            {
                columnValue = rs.getString("avg");
                stuRating = Double.parseDouble(columnValue);
            }

            //other students rating of the student
            queryString = "Select avg(value) from response join team On response.student1 = team.student where student1 <> '" + stuID + "' and student2 = '" + stuID + "' and teamid = '" + teamID + "' and team.evalid = '" + evalID + "';";
            rs = query(queryString);
            if(rs.next())
            {
                columnValue = rs.getString("avg");
                teamRatingOfStu = Double.parseDouble(columnValue);
            }

            //average of team - teams rating of student
            queryString = "Select avg(value) from response join team On response.student1 = team.student where student2 <> '" + stuID + "' and teamid = '" + teamID + "' and team.evalid = '" + evalID + "';";
            rs = query(queryString);
            if(rs.next())
            {
                columnValue = rs.getString("avg");
                teamAvg = Double.parseDouble(columnValue);
            }

            //average of team rated by this student
            queryString = "Select avg(value) from response join team On response.student1 = team.student where student1 = '" + stuID + "' and student2 <> '" + stuID + "' and teamid = '" + teamID + "' and team.evalid = '" + evalID + "';";
            rs = query(queryString);
            if(rs.next())
            {
                columnValue = rs.getString("avg");
                ratedStudentOfTeam = Double.parseDouble(columnValue);
            }

            //team std
            queryString = "Select stddev(value) from response join team On response.student1 = team.student where teamid = '" + teamID + "' and team.evalid = '" + evalID + "';";
            rs = query(queryString);
            if(rs.next())
            {
                columnValue = rs.getString("stddev");
                stdOfTeam = Double.parseDouble(columnValue);
            }


            //begin assigning tags to student
            String verdict = "";
            String [] options = 
            {
            "Low Performer", //0
            "Overconfident", //1
            "High Performer", //2
            "Underconfident", //3
            "Manipulator", //4
            "Conflict", //5
            "Cliques" //6
            };

            
            //Check low Performer
            if(teamRatingOfStu <= 2.5)
            {
                verdict += options[0];
            }

            //Check Overconfident
            if(teamRatingOfStu <= 2.5 && stuRating >= 3)
            {
                if(!verdict.equals(""))
                    verdict = verdict + ", " + options[1];
                else
                    verdict += options[1];
            }

            //Check High Performer
            if(teamRatingOfStu >= 3.5 && (teamRatingOfStu - teamAvg) >= 0.5)
            { 
                if(!verdict.equals(""))
                    verdict = verdict + ", " + options[2];
                else
                    verdict += options[2];
            }

            //Check Underconfident
            if(teamRatingOfStu >= 3.0 && (teamRatingOfStu - stuRating) >= 1)
            {
                 if(!verdict.equals(""))
                    verdict = verdict + ", " + options[3];
                 else
                    verdict += options[3];
            }

            //Check Manipulator
            if(stuRating >= 4 && (stuRating - ratedStudentOfTeam) >= 2)
            {
                if(!verdict.equals(""))
                        verdict = verdict + ", " + options[4];
                else
                    verdict += options[4];
            }

            //std over 2 might indicate cliques
            if(stdOfTeam >= 2)
            {
                if(!verdict.equals(""))
                    verdict = verdict + ", " + options[6];
                else
                    verdict += options[6];
            }

            //Check Conflict
            if(verdict.contains("Low Performer") && verdict.contains("Overconfident") && verdict.contains("Manipulator"))
            {
                if(!verdict.equals(""))
                    verdict = verdict + ", " + options[5];
                else
                    verdict += options[5];
            }
        

            //return verdict;
             System.out.println("Team Rating Average | Team Rating Average of Student | Student Rating | Verdict");

            //students rating of themselves
            System.out.println("Student's Self-rating: " + stuRating);
            //rating of the rest of the team minus stu
            System.out.println("Team Average: "+ teamAvg);
            //teams rating of stu
            System.out.println("Team Rating of Student: "+ teamRatingOfStu);
            //all exceptional condition tags
            System.out.println("Verdict: " + verdict);

            String[] stats = new String[4];
            stats[0] = "Student's Self-rating: " + stuRating.toString();
            stats[1] = "Team Average: "+ teamAvg.toString();
            stats[2] = "Team Rating of Student: "+ teamRatingOfStu.toString();
            stats[3] = "Verdict: " + verdict;
            return stats;

        }
        catch(Exception e)
        {
            System.out.println("Load query failed");
        }
        String[] stats = new String[]{"0", "0", "0", "None"};
        return stats;
    }



    //HTML PRINT
    //prints a single student for a single evalid
    public void printAllStudentResponsesStats()
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
        System.out.print("\nWhat should the output file be named?\nFilename:");
        String htmlFilename = sin.nextLine();

        try{
            String queryString = "Select * from v_response where student2 = '" + studentID + "';";

            //
            //System.out.println("Select * from v_response where student2 = '2' AND evalid = '999';");
            //printResultSet(query(queryString));
            //createHTMLResult(query(queryString), htmlFilename, 1, false);
            
        }catch(Exception e){
                System.out.print("Failed printAllStudentResponsesStats");
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

    public void createHTMLResult(ResultSet rs, String htmlFilename, int reportType, boolean anonymized, String[] stats)
    {
        String htmlHeader = "";
        switch (reportType){
            case 1:
                htmlHeader = "Student View";
                break;
        }

        String html = "<!DOCTYPE html><html><head><style>body { background-color: #D2CAE6;" +
        "font-family: Arial, Helvetica, sans-serif;}h1 { text-align: center;text-decoration-line: underline;" +
        "text-decoration-thickness: 2px;text-transform: uppercase;" +
        "font-family: " + "Lucida Console" + ", " + "Courier New" + ", monospace;color: #56476C;}table {" +
        "border-collapse: collapse;width: 100%;}th, td {padding: 8px;text-align: left;" +
        "border-bottom: 1px solid #E0D5F5;}tr:hover {background-color: #8569BD;}</style>" +
        "</head><body><h1>" + htmlHeader + "</h1><table><tr>";

        try{
            if (!anonymized){
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();

                // puts table headers for each column in html String
                for (int i = 1; i <= columnsNumber; i++) 
                    {
                        if (i > 1) System.out.print(" ");
                            String columnName = rsmd.getColumnName(i);
                        html += "<th>" + columnName + "</th>";
                    }
                    html += "</tr><tr>";

                // puts all data in html String
                while (rs.next()) 
                {
                    for (int i = 1; i <= columnsNumber; i++) 
                    {
                        if (i > 1) System.out.print(" ");
                            String columnValue = rs.getString(i);
                        html += "<td>" + columnValue + "</td>";
                    }
                    System.out.println("");
                    html += "</tr><tr>";
                }
            }

            html += "</tr></table>";

            for( int i = 0; i < stats.length; i++)
            {
                html += "<p>" + stats[i] + "</p>";
                if (stats[i].contains("Low Performer")){
                    html += "<p>The members of your team indicated that your contributions to the team " +
                    "were below expectations. This report gives you details about how the members of your team " +
                    "perceived your team contributions in five key areas. Please use this information to " +
                    "identify problem areas in order to contribute effectively in future teamwork situations. " +
                    "Please contact your course instructor if you need assistance or if you believe that your " +
                    "ratings were inappropriate.<p>";
                }
                if (stats[i].contains("Overconfident")){
                    html += "<p>Your self-ratings were significantly higher than your teammates. ratings of your " +
                    "contributions to the team. The members of your team indicated that your contributions to the " +
                    "team were below expectations. This report gives you details about how the members of your team " +
                    "perceived your team contributions in five key areas. Please use this information to identify " +
                    "problem areas in order to contribute effectively in future teamwork situations. Please contact your " +
                    "course instructor if you need assistance or if you believe that your ratings were inappropriate.<p>";
                }
                if (stats[i].contains("High Performer")){
                    html += "<p>Congratulations! The members of your team have indicated that you were a highly effective " +
                    "team member. Keep up the good work!<p>";
                }
                if (stats[i].contains("Underconfident")){
                    html += "<p>Your self-ratings were significantly lower than your teammates. ratings of your " +
                    "contributions to the team. The members of your team have indicated that you were a highly effective " +
                    "team member. Please try not to minimize the value of your contributions to the team.<p>";
                }
                if (stats[i].contains("Manipulator")){
                    html += "<p>Your self-evaluation indicates you made the primary contribution to the project with little " +
                    "value added by your teammates. The ratings from your teammates did not concur with your assessment. " +
                    "Your instructor may require additional information to clarify what happened in your team.<p>";
                }
                if (stats[i].contains("Conflict")){
                    html += "<p>Your evaluation indicates that someone contributed very little to the project. This is not " +
                    "consistent with the assessment of the rest of the team. Your instructor may require additional " +
                    "information to clarify what happened in your team.<p>";
                }
                if (stats[i].contains("Cliques")){
                    html += "<p>There was considerable disagreement among your teammates as to which team members were most " +
                    "effective during team assignments. Your instructor may require additional information to clarify what " +
                    "happened in your team.<p>";
                }
            }
            
            html += "</body></html>";
        }
        catch(Exception e)
        {
            System.out.println("Print View failed");
        }

        // creates HTML file
        File f = new File("src/main/html/" + htmlFilename + ".html");
        
        try {
            // writes data in html String to HTML file
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(html);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
