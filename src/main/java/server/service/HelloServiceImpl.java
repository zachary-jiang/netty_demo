package server.service;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String msg) {
//        int i = 1 / 0;
        System.out.println("你好, " + msg);
        return "你好, " + msg;
    }
}