package peerEval;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;
import org.junit.*;

import java.io.File; 
import java.io.IOException;
import java.util.*;
import java.io.*;

/**
 * Unit test for simple App.
 */
public class PeerEvalTest 
{
    @Test
    public void ReadCSV()
    {
        PeerEval test = new PeerEval();
        InputStream is = test.loadFile("sampleCSV.csv");
        try{  
            assertNotEquals(is, null);
                }
        catch(Exception e){
            System.out.println("Read CSV failed");
        }
    }
}
