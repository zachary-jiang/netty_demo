package cn.itcast.netty.c1;

import java.nio.ByteBuffer;

import static cn.itcast.netty.c1.ByteBufferUtil.debugAll;

/**
 * @author jiangqi
 */
public class TestResumeData {
    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);
        //                     11            24
        source.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(source);

        source.put("w are you?\nhaha!\n".getBytes());
        split(source);
    }

    //    private static void split(ByteBuffer source) {
//        source.flip();
//        int oldLimit = source.limit();
//        for (int i = 0; i < oldLimit; i++) {
//            if (source.get(i) == '\n') {
//                System.out.println(i);
//                ByteBuffer target = ByteBuffer.allocate(i + 1 - source.position());
//                // 0 ~ limit
//                source.limit(i + 1);
//                target.put(source); // 从source 读，向 target 写
//                debugAll(target);
//                source.limit(oldLimit);
//            }
//        }
//        source.compact();
//    }
    private static void split(ByteBuffer buffer) {
        buffer.flip();
        int oldLimit = buffer.limit();
        for (int i = 0; i < buffer.limit(); i++) {
            if (buffer.get(i) == '\n') {
                ByteBuffer newBuffer = ByteBuffer.allocate(i + 1 - buffer.position());
                buffer.limit(i + 1);
                newBuffer.put(buffer);
                buffer.limit(oldLimit);
                debugAll(newBuffer);
            }
        }
        buffer.compact();
    }
}
