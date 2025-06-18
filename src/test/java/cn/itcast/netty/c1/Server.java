package cn.itcast.netty.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static cn.itcast.netty.c1.ByteBufferUtil.debugAll;
import static cn.itcast.netty.c1.ByteBufferUtil.debugRead;

/**
 * @author jiangqi
 */
@Slf4j
public class Server {

    public static void main(String[] args) throws IOException {
        // 使用 nio 来理解非阻塞模式, 单线程
// 0. ByteBuffer
// 1. 创建了服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false); // 非阻塞模式
        Selector selector = Selector.open();
        SelectionKey selectionKey = ssc.register(selector, 0, null);
        log.info("selection key:{}", selectionKey);
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);

// 2. 绑定监听端口
        ssc.bind(new InetSocketAddress(8080));
// 3. 连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            selector.select();
            for (SelectionKey selectedKey : selector.selectedKeys()) {
                if (selectedKey.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) selectedKey.channel();
                    SocketChannel connChannel = channel.accept();
                    selector.selectedKeys().remove(selectedKey);
                    connChannel.configureBlocking(false);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(16);
                    connChannel.register(selector, SelectionKey.OP_READ, byteBuffer);
                    log.info("连接已经建立:{}", connChannel);
                } else if (selectedKey.isReadable()) {
                    SocketChannel sc = (SocketChannel) selectedKey.channel();
                    ByteBuffer buffer = (ByteBuffer) selectedKey.attachment();
                    try {
                        int read = 0;
                        read = sc.read(buffer);
                        buffer.flip();
                        String string = StandardCharsets.UTF_8.decode(buffer).toString();
                        log.info("read:{},string:{}", read, string);
                        if (read == -1) {
                            selectedKey.cancel();
                            sc.close();
                        } else {
                            split(buffer);
                            if(buffer.position() == buffer.limit()){
                                buffer.flip();
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.limit() << 1);
                                newBuffer.put(buffer);
                                selectedKey.attach(newBuffer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        selectedKey.cancel();
                    }
                }
            }
        }
    }

    private static void split(ByteBuffer buffer) {
        buffer.flip();
        int oldLimit = buffer.limit();
        for (int i = 0; i < buffer.limit(); i++) {
            if(buffer.get(i) == '\n'){
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
