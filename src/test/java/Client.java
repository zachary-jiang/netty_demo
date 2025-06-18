import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangqi
 */
public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
//        sc.write(StandardCharsets.UTF_8.encode("中国"));
        sc.write(StandardCharsets.UTF_8.encode("0123456789abceffgh"));
        sc.write(StandardCharsets.UTF_8.encode("aaa\n0123456789abcef\n"));
        System.out.println("waiting...");
        sc.close();
    }
}
