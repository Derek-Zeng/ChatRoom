package myroom;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;

public class JFclient extends JFrame {
    //显示窗口
    JTextArea jTextAreamsg;
    JScrollPane jScrollPane;
    //输入窗口
    JTextField jTextField;
    //按钮群
    JPanel jPanel;
    JButton jButtonsend;
    JButton jButtonremove;
    //套接字对象
    Socket socket;
    //用户名
    String username;
    public JFclient(String username){
        this.username=username;
    }
    {
        try {
            socket = new Socket("127.0.0.1",6666);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showJFclient(){
        this.setTitle(username+"的客户端");
        this.setSize(500,480);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        //显示框
        jTextAreamsg=new JTextArea(20,20);
        jScrollPane=new JScrollPane(jTextAreamsg);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jTextAreamsg.setEditable(false);
        //输入框
        jTextField=new JTextField(100);
        //按钮
        jButtonsend=new JButton("send");
        jButtonremove=new JButton("remove");
        //添加到组件
        jPanel=new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        jPanel.add(jButtonsend);
        jPanel.add(jButtonremove);
        //添加组件
        this.add(jScrollPane,BorderLayout.NORTH);
        this.add(jTextField,BorderLayout.CENTER);
        this.add(jPanel,BorderLayout.PAGE_END);
        //
        //添加事件
        jButtonremove.addActionListener(e -> jTextAreamsg.setText(""));
        //添加事件监听器
        clientsender clientsender=new clientsender(socket,username,jTextField,jTextAreamsg);
        try {
            clientsender.userRegist();
        } catch (IOException e) {
            e.printStackTrace();
        }
        jButtonsend.addMouseListener(clientsender);
        clientreader clientreader=new clientreader(socket,jTextAreamsg);
        clientreader.start();
        this.setVisible(true);
    }
}
