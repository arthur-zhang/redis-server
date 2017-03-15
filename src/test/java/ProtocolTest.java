import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created By Arthur Zhang at 15/03/2017
 */
public class ProtocolTest {
    @Test
    public void testReadLong() {
        InputStream in = new ByteArrayInputStream("*1\r\n$4\r\nping\r\n".getBytes());
        try {
            int read = in.read();
            if (read == '*') {
                long numberArgs = Protocol.readLong(in);
                byte[][] bytesArray = new byte[(int) numberArgs][];
                for (int i = 0; i < numberArgs; i++) {
                    if (in.read() == '$') {
                        bytesArray[i] = Protocol.readBytes(in);
                        System.out.println("....");
                    }
                }
    
                System.out.println(new String(bytesArray[0]));
                if ("ping".getBytes().equals(bytesArray[0])) {
                    System.out.println("is ping cmd");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
