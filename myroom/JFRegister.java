package myroom;

import tools.DB;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Statement;

public class JFRegister extends JDialog {
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
    JButton jButtonRegister;
    //数据
    String username;
    String password;
    public void showRegister(){
        this.setTitle("注册界面");
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
        jButtonRegister=new JButton("注册");
        jPanelButton.add(jButtonRegister);
        //添加组件
        this.add(jPanelUserName);
        this.add(jPanelPassword);
        this.add(jPanelButton);
        //数据库连接
        DB.getconn();
        //添加事件
        jButtonRegister.addActionListener(e->{
            this.username=jTextFieldUserName.getText();
            this.password=new String( jPasswordFieldPassword.getPassword());
            String sql="insert into login values('"+username+"','"+password+"')" ;
            try {
                Statement statement=DB.conns.createStatement();
                int count=statement.executeUpdate(sql);
                if (count>0){
                    JOptionPane.showMessageDialog(this,"插入成功");
                    this.dispose();
                }else {
                    JOptionPane.showMessageDialog(this,"插入失败，请重试");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        this.setVisible(true);
    }
}
