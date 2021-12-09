package myroom;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class clientreader extends Thread{
    private Socket socket;
    private JTextArea jTextArea;
    public clientreader(Socket socket, JTextArea jTextArea){
        this.socket=socket;
        this.jTextArea=jTextArea;
    }
    @Override
    public void run() {
        //1.获取服务器端输入流
        while (true){
            try {
                Scanner scanner=new Scanner(socket.getInputStream());
                while(scanner.hasNext()){
//                    System.out.println(scanner.next());
                    jTextArea.append(scanner.next()+"\n");
                }
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
