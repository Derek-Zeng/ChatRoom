package myroom;

public class clientMain2 {
    public static void main(String[] args) throws Exception{
        JFlogin jFlogin=new JFlogin();
        jFlogin.showJFlogin();
        JFclient jFclient=new JFclient(jFlogin.username);
        jFclient.showJFclient();
    }
}
