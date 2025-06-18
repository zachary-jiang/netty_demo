package client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import message.RpcResponseMessage;

@Slf4j
@ChannelHandler.Sharable
public class RpcResponseMessageHandler extends
        SimpleChannelInboundHandler<RpcResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponseMessage msg)
            throws Exception {
        log.debug("返回结果{}", msg);
    }
}