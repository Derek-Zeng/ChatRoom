package myroom;

public class clientMain {

    public static void main(String[] args) throws Exception{
        JFlogin jFlogin=new JFlogin();
        jFlogin.showJFlogin();
//        System.out.println(jFlogin.username);
        JFclient jFclient=new JFclient(jFlogin.username);
        jFclient.showJFclient();

    }
}
