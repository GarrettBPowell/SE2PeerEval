package peerEval;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    private static String url = "jdbc:postgresql://localhost/SE375v1";
    private static String user = "mrblee";
    private static String password = "purplewhite";

    public static void main(final String[] args) throws IOException 
    {
        PeerEval peer = new PeerEval();
        peer.connect();   
    }

    public static Connection connect() {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public InputStream loadFile(final String fileName) 
    {
        InputStream ioStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        
        //catch if name is empty
        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }

    public void printFile(InputStream is)
    {
        try (InputStreamReader isr = new InputStreamReader(is); BufferedReader br = new BufferedReader(isr);) 
        {
            String line;

            while ((line = br.readLine()) != null) 
            {
                System.out.println(line);
            }

            //close the file
            is.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public BufferedReader getBuffer(InputStream is)
    {
        InputStreamReader isr = new InputStreamReader(is); 
        BufferedReader br = new BufferedReader(isr);
        
            return br;
    }

}
