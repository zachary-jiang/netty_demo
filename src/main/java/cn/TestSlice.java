package cn;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.java.Log;

import static cn.TestByteBuff.log;

@Log
public class TestSlice {
    public static void main(String[] args) {
        ByteBuf buf= ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[]{'a','b','c','d','e','f','g','h'});
        log(buf);
        ByteBuf slice = buf.slice(0, 5);
        ByteBuf slice1 = buf.slice(5, 5);
        log(slice);
        log(slice1);
        slice.setByte(0,'A');
        log(slice);
        log(buf);
        slice1.writeBytes("D".getBytes());

    }
}
