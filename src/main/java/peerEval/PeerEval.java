package peerEval;
import java.io.*;  
import java.util.Scanner; 

/***********************************
*	Authors: Christa Greenwood, Garrett Powell, Megan Skeen
*	Class: CS 375.01 - Spring 2022
*	Project - Peer Evaluations
*
*
*	How to compile/build:
*		mvn compile
*	
*	How to run/execute:
*		java -cp target\classes peerEval.PeerEval
*
*	How to test:
*		mvn test
*
************************************/
public class PeerEval
{
    public static String filePath;

    public void main( String[] args ) throws Exception
    {
        
        filePath = "sampleCSV.csv";
        System.out.println(this.getClass().getResource(filePath));
        //readCSV();
 
        /*
        // Parses CSV file
        Scanner csv = new Scanner(new File(filePath));  
        csv.useDelimiter(",");
        
        // Displays data from CSV file
        while (csv.hasNext())
        {  
        System.out.print(csv.next() + " ");
        }   
        // Closes scanner
        csv.close();

        */
    }
   
    /*
    public static void readCSV()
    {
         //Parses CSV file
         Scanner csv = new Scanner(new File(this.getClass().getResource(filePath)));  
         csv.useDelimiter(",");

         //Displays data from CSV file
         while (csv.hasNext())
         {  
         System.out.print(csv.next() + " ");
         }   
         //Closes scanner
         csv.close();
    }*/
}
