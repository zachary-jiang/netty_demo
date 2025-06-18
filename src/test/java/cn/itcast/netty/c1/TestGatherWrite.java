package cn.itcast.netty.c1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static cn.itcast.netty.c1.ByteBufferUtil.debugAll;

/**
 * @author jiangqi
 */
public class TestGatherWrite {
    public static void main(String[] args) {
        try (FileChannel channel = new RandomAccessFile("wordWrite.txt","rw").getChannel()) {
            ByteBuffer d = ByteBuffer.allocate(4);
            ByteBuffer e = ByteBuffer.allocate(4);
            d.put(new byte[]{'f', 'o', 'u', 'r'});
            e.put(new byte[]{'f', 'i', 'v', 'e'});
            d.flip();
            e.flip();
            channel.write(new ByteBuffer[]{d, e});

            debugAll(d);
            debugAll(e);
        } catch (IOException e) {
        }
    }
}
