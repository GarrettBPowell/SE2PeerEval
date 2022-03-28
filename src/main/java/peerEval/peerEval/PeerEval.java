package peerEval;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


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
    private static String url = "jdbc:postgresql://localhost:5432/SE375v1";
    private static String user = "mrblee";
    private static String password = "purplewhite";
    public static Connection c;

    public static void main(final String[] args) throws IOException 
    {
        PeerEval pe = new PeerEval();
        c = null;
        c = pe.connect("jdbc:postgresql://localhost:5432/cs375v1", "mrblee", "purplewhite");
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

}
