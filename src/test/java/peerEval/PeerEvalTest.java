package peerEval;
import java.io.*;  

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class PeerEvalTest 
{
    @Test
    public void shouldAnswerWithTrue()
    {
        PeerEval test = new PeerEval();

        InputStream is = test.loadFile("sampleCSV.csv");
        test.printFile(is);
    }
}
