package peerEval;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

    public static void main(final String[] args) throws IOException 
    {
        
    }

    public InputStream getFileAsIOStream(final String fileName) 
    {
        InputStream ioStream = this.getClass()
            .getClassLoader()
            .getResourceAsStream(fileName);
        
        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }

    public void printFileContent(InputStream is)
    {
        try (InputStreamReader isr = new InputStreamReader(is); 
                BufferedReader br = new BufferedReader(isr);) 
        {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            is.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
