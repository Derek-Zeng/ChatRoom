package myroom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class servermain {
    public static void main(String[] args){
        try {
            //1.创建服务器端的ServerSocket对象,等待客户端连接
            ServerSocket serverSocket=new ServerSocket(6666);
            System.out.println("欢迎来到我的聊天室......");
            //2.创建线程池
            ExecutorService executorService= Executors.newFixedThreadPool(20);
            for(int i=0;i<20;i++){
                //3.侦听客户端
                Socket socket=serverSocket.accept();
                //4.启动线程
                executorService.execute(new Server(socket));
            }
            //5.关闭线程池
            executorService.shutdown();
            //6.关闭服务器
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
