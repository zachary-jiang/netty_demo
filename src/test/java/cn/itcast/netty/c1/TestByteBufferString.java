package cn.itcast.netty.c1;

import java.nio.ByteBuffer;

/**
 * @author jiangqi
 */
public class TestByteBufferString {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put("hello".getBytes());

    }
}
