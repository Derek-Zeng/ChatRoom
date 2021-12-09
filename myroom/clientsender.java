package myroom;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class clientsender extends MouseAdapter {
    private Socket socket;
    private JTextField jTextField;
    private JTextArea jTextArea;
    private String username;
    public clientsender(Socket socket, String username,JTextField jTextField,JTextArea jTextArea){
        this.socket=socket;
        this.jTextField=jTextField;
        this.jTextArea=jTextArea;
        this.username=username;
    }
    public void userRegist() throws IOException {
        PrintStream printStream=new PrintStream(socket.getOutputStream());
        printStream.println("userName:"+this.username);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            //1.获取服务器端的输出流
            PrintStream printStream=new PrintStream(socket.getOutputStream());
            //2.从键盘中输入信息
            String msg=null;
            msg=jTextField.getText();
            if (msg.startsWith("G:")){
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
                String str=msg.split("\\:")[1];
                jTextArea.append(df.format(new Date())+"\n"+"我说："+"\n"+str+"\n");
            }
            else if(msg.contains("-")){
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
                String str=msg.split("-")[1];
                jTextArea.append(df.format(new Date())+"\n"+"我说："+"\n"+str+"\n");
            }else {
                jTextArea.append(msg+"\n");
            }
            jTextField.setText("");
            printStream.println(msg);
            if(msg.equals("exit")){
                printStream.close();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
