package tools;
import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
    public static String driver="com.mysql.jdbc.Driver";
    public static String url="jdbc:mysql://localhost:3306/zpdb";
    public static String name="root";
    public static String pwd="123456";
    public static Connection conns=null;
    public static void getconn(){
        try {
            Class.forName(driver);
            conns=DriverManager.getConnection(url,name,pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
