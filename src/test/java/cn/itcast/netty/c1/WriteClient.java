package cn.itcast.netty.c1;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author jiangqi
 */
public class WriteClient {
    public static void main(String[] args) throws Exception {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        int count = 0;
        while (true){
            ByteBuffer allocate = ByteBuffer.allocate(1024 * 1024);
            count += sc.read(allocate);
            System.out.println(count);
            allocate.clear();
        }
    }
}
