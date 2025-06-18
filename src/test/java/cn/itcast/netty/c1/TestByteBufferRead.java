package cn.itcast.netty.c1;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static cn.itcast.netty.c1.ByteBufferUtil.debugAll;

/**
 * @author jiangqi
 */
public class TestByteBufferRead {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd', 'e'});
        buffer.flip();

        buffer.get(new byte[4]);
        debugAll(buffer);

        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello world");
        debugAll(buffer2);

        ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes(StandardCharsets.UTF_8));
        debugAll(buffer3);

        String string = StandardCharsets.UTF_8.decode(buffer2).toString();
        System.out.println(string);




    }
}
