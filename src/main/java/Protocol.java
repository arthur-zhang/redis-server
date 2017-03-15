import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created By Arthur Zhang at 15/03/2017
 */
public class Protocol {
    public static final char CR = '\r';
    public static final char LF = '\n';
    
    public static long readLong(InputStream in) throws IOException {
        long number = 0;
        
        int read;
        
        do {
            read = in.read();
            if (read == '\r') {
                read = in.read();
                if (read == '\n') {
                    return number;
                }
            } else {
                number = (number * 10) + (read - '0');
            }
        } while (true);
    }
    
    
    public static byte[] readBytes(InputStream in) throws IOException {
        
        int size = (int) readLong(in);
        
        byte[] bytes = new byte[size];
        
        int readTotal = 0;
        
        int read = 0;
        while (read != -1 && readTotal < size) {
            read = in.read(bytes, readTotal, size - readTotal);
            readTotal += read;
        }
        
        int cr = in.read();
        int lf = in.read();
        if (cr != CR || lf != LF) throw new IOException("Illegal line ending: " + cr + ", " + lf);
        return bytes;
    }
    
    public static void writeResponsePong(OutputStream out) throws IOException {
        out.write('+');
        out.write("pong".getBytes());
        out.write(CR);
        out.write(LF);
        out.flush();
    }
}
