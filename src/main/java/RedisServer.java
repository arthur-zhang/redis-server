import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created By Arthur Zhang at 15/03/2017
 */
public class RedisServer {
    private static final String PING = "ping";
    
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6397);
        
        while (true) {
            System.out.println("process incoming call");
            Socket socket = serverSocket.accept();
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            
            int read = in.read();
            if (read == '*') {
                long numberArgs = Protocol.readLong(in);
                byte[][] bytesArray = new byte[(int) numberArgs][];
                for (int i = 0; i < numberArgs; i++) {
                    if (in.read() == '$') {
                        bytesArray[i] = Protocol.readBytes(in);
                    }
                }
                
                if (Arrays.equals(PING.getBytes(), bytesArray[0])) {
                    System.out.println("is ping cmd");
                    Protocol.writeResponsePong(out);
                } else {
                    socket.close();
                }
            }
        }
    }
}
