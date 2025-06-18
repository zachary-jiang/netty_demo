package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.RpcRequestMessage;
import message.RpcResponseMessage;
import server.service.ServicesFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * @author jiangqi
 */
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage rpcRequestMessage) throws Exception {
        RpcResponseMessage response = new RpcResponseMessage();
        response.setSequenceId(rpcRequestMessage.getSequenceId());

        try {
            Class<?> aClass = Class.forName(rpcRequestMessage.getInterfaceName());
            Object service = ServicesFactory.getService(aClass);
            Object invoke = service.getClass()
                    .getDeclaredMethod(rpcRequestMessage.getMethodName(), rpcRequestMessage.getParameterTypes())
                    .invoke(service, rpcRequestMessage.getParameterValue());
                     response.setReturnValue(invoke);
        } catch (Exception e) {
            response.setExceptionValue(e);
        }
        ctx.writeAndFlush(response);
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        RpcRequestMessage rpcRequestMessage = new RpcRequestMessage(1,
                "server.service.HelloService",
                "sayHello",
                String.class,
                new Class[]{String.class},
                new Object[]{"张三"});

        Class<?> aClass = Class.forName(rpcRequestMessage.getInterfaceName());
        Object service = ServicesFactory.getService(aClass);
        Object invoke = service.getClass()
                .getDeclaredMethod(rpcRequestMessage.getMethodName(), rpcRequestMessage.getParameterTypes())
                .invoke(service, rpcRequestMessage.getParameterValue());
        System.out.println(invoke);


    }
}
