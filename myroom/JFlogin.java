package myroom;

import tools.DB;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class JFlogin extends JDialog {

    //用户名
    JPanel jPanelUserName;
    JLabel jLabelUserName;
    JTextField jTextFieldUserName;
    //密码
    JPanel jPanelPassword;
    JLabel jLabelPassword;
    JPasswordField jPasswordFieldPassword;
    //按钮
    JPanel jPanelButton;
    JButton jButtonLogin;
    JButton jButtonRegister;
    //数据
    String username;
    String password;
    public void showJFlogin (){
        this.setTitle("登录界面");
        this.setSize(200,200);
        this.setModal(true);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        //用户名组件
        jPanelUserName=new JPanel();
        jLabelUserName=new JLabel("用户名");
        jTextFieldUserName=new JTextField(7);
        jPanelUserName.add(jLabelUserName);
        jPanelUserName.add(jTextFieldUserName);
        //密码组件
        jPanelPassword=new JPanel();
        jLabelPassword=new JLabel("密码");
        jPasswordFieldPassword=new JPasswordField(8);
        jPanelPassword.add(jLabelPassword);
        jPanelPassword.add(jPasswordFieldPassword);
        //按钮群组件
        jPanelButton=new JPanel();
        jButtonLogin=new JButton("登录");
        jButtonRegister=new JButton("注册");
        jPanelButton.add(jButtonLogin);
        jPanelButton.add(jButtonRegister);
        //添加组件
        this.add(jPanelUserName);
        this.add(jPanelPassword);
        this.add(jPanelButton);
        //数据库连接
        DB.getconn();
        //添加事件
        jButtonRegister.addActionListener(e->{
            JFRegister jfRegister=new JFRegister();
            jfRegister.showRegister();
        });
        jButtonLogin.addActionListener(e -> {
            this.username=jTextFieldUserName.getText();
            this.password=new String( jPasswordFieldPassword.getPassword());
            String sql="select username from login where username='"+username+"'and password='"+password+"'";
            try {
                Statement statement=DB.conns.createStatement();
                ResultSet resultSet=statement.executeQuery(sql);
                if (resultSet.next()){
                    JOptionPane.showMessageDialog(this,"登录成功");
                    this.dispose();
                }else {
                    JOptionPane.showMessageDialog(this,"该用户未注册");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        this.setVisible(true);
    }
}
