package cn.itcast.netty.c1;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

import static cn.itcast.netty.c1.ByteBufferUtil.debugAll;

/**
 * @author jiangqi
 */
@Slf4j
public class MultiThreadServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("Boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        ssc.register(boss, SelectionKey.OP_ACCEPT);
        Worker worker = new Worker("worker-0");
        worker.register(null);

        while (true){
            boss.select();
            Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                if(key.isAcceptable()){
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    log.info("conn from :{}",sc.getRemoteAddress());
                    worker.register(sc);
                }
            }
        }
    }
}

@Slf4j
class Worker implements Runnable{
    private Thread thread;
    public Selector selector;
    private String name;
    private volatile boolean started = false;
    private ConcurrentLinkedDeque <Runnable> concurrentLinkedDeque = new ConcurrentLinkedDeque<>();
    public Worker(String name) {
        this.name = name;
    }

    public void register(SocketChannel sc) throws IOException {
        if(!started) {
            thread = new Thread(this, name);
            selector = Selector.open();
            thread.start();
            started = true;
        }
        //想让线程自己去执行
        if(sc != null) {
            concurrentLinkedDeque.add(() -> {
                try {
                    sc.register(selector, SelectionKey.OP_READ);
                    log.info("after register ...{}",sc.getRemoteAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            selector.wakeup();
        }


    }

    @SneakyThrows
    @Override
    public void run() {
        while (true){
                selector.select();
                Runnable task = concurrentLinkedDeque.poll();
                if(task != null){
                    task.run();
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if(key.isReadable()){
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        SocketChannel channel = (SocketChannel) key.channel();
                        int read = channel.read(buffer);
                        if( read == -1){
                            key.cancel();
                        }else {
                            buffer.flip();
                            debugAll(buffer);
                        }
                    }

                }
            }
        }
}
