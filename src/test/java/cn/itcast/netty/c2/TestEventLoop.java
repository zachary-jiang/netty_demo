package cn.itcast.netty.c2;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author jiangqi
 */
@Slf4j
public class TestEventLoop {
    public static void main(String[] args) {
        System.out.println(NettyRuntime.availableProcessors());
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup(2);
        System.out.println(eventExecutors.next());
        System.out.println(eventExecutors.next());
        System.out.println(eventExecutors.next());
        System.out.println(eventExecutors.next());
//        eventExecutors.next().submit(() ->{
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            log.info("hello");
//        });
        eventExecutors.next().scheduleAtFixedRate(() -> log.info("ok"), 0, 1, TimeUnit.SECONDS);
        log.info("main");
    }
}
