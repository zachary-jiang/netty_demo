package cn.itcast.netty.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static cn.itcast.netty.c1.ByteBufferUtil.debugAll;

/**
 * @author jiangqi
 */
@Slf4j
public class AIODemo {
    public static void main(String[] args) throws IOException {
        try {
            AsynchronousFileChannel s = AsynchronousFileChannel.open(Paths.get("words.txt"), StandardOpenOption.READ);
            ByteBuffer buffer = ByteBuffer.allocate(10);
            log.info("begin");
            s.read(buffer, 0, null, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    log.info("read completed ...{}",result);
                    buffer.flip();
                    debugAll(buffer);
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    log.info("read failed");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("do other things");
        System.in.read();
    }
}
