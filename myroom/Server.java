package myroom;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server implements Runnable{
    private static Map<String, Socket> map=new ConcurrentHashMap<>();
    private Socket socket;
    public Server(Socket socket){
        this.socket=socket;
    }
    @Override
    public void run() {
        try {
            Scanner scanner=new Scanner(socket.getInputStream());
            String msg=null;
            while(true){
                if(scanner.hasNextLine()){
                    //0.处理客户端输入的字符串
                    msg=scanner.nextLine();
                    Pattern pattern=Pattern.compile("\r");
                    Matcher matcher=pattern.matcher(msg);
                    msg=matcher.replaceAll("");
                    //1.注册用户流程,注册用户的格式为:userName:用户名
                    if(msg.startsWith("userName:")){
                        //将用户名保存在userName中
                        String userName=msg.split("\\:")[1];
                        //注册该用户
                        userRegist(userName,socket);
                        continue;
                    }
                    //2.群聊信息流程,群聊的格式为:G:群聊信息
                    else if(msg.startsWith("G:")){
                        //保存群聊信息
                        String str=msg.split("\\:")[1];
                        //发送群聊信息
                        groupChat(socket,str);
                        continue;
                    }
                    //3.私聊信息流程,私聊的格式为:P:userName-私聊信息
                    else if(msg.contains("-")){
                        //保存需要私聊的用户名
                        String userName=msg.split("-")[0];
                        //保存私聊的信息
                        String str=msg.split("-")[1];
                        //发送私聊信息
                        privateChat(socket,userName,str);
                        continue;
                    }
                    //4.用户退出流程,用户退出格式为:包含exit
                    else if(msg.contains("exit")){
                        userExit(socket);
                        continue;
                    }
                    //其他输入格式均错误
                    else{
                        PrintStream printStream=new PrintStream(socket.getOutputStream());
                        printStream.println("输入格式错误!请按照以下格式输入!");
                        printStream.println("群聊格式:[G:群聊信息]");
                        printStream.println("私聊格式:[userName-私聊信息]");
                        printStream.println("用户退出格式[包含exit即可]");
                        continue;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //注册
    private void userRegist(String userName,Socket socket){
        map.put(userName,socket);
        System.out.println("[用户名为"+userName+"][客户端为"+socket+"]上线了!");
        System.out.println("当前在线人数为:"+map.size()+"人");
    }

    //群聊
    private void groupChat(Socket socket,String msg) throws IOException {
        //1.将Map集合转换为Set集合
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        Set<Map.Entry<String,Socket>> set=map.entrySet();
        //2.遍历Set集合找到发起群聊信息的用户
        String userName=null;
        for(Map.Entry<String,Socket> entry:set){
            if(entry.getValue().equals(socket)){
                userName=entry.getKey();
                break;
            }
        }
        //3.遍历Set集合将群聊信息发给每一个客户端
        for(Map.Entry<String,Socket> entry:set){
            //取得客户端的Socket对象
            Socket client=entry.getValue();
            if (entry.getKey().equals(userName)) continue;
            //取得client客户端的输出流
            PrintStream printStream=new PrintStream(client.getOutputStream());
            printStream.println(df.format(new Date())+"\n"+userName+"说:"+msg);
        }
    }
    //私聊
    private void privateChat(Socket socket,String userName,String msg) throws IOException {
        //1.取得当前客户端的用户名
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        String curUser=null;
        Set<Map.Entry<String,Socket>> set=map.entrySet();
        for(Map.Entry<String,Socket> entry:set){
            if(entry.getValue().equals(socket)){
                curUser=entry.getKey();
                break;
            }
        }
        //2.取得私聊用户名对应的客户端
        Socket client=map.get(userName);
        //3.获取私聊客户端的输出流,将私聊信息发送到指定客户端
        PrintStream printStream=new PrintStream(client.getOutputStream());
        printStream.println(df.format(new Date())+"\n"+curUser+"说:"+msg);
    }

    //用户退出
    private void userExit(Socket socket){
        //1.利用socket取得对应的Key值
        String userName=null;
        for(String key:map.keySet()){
            if(map.get(key).equals(socket)){
                userName=key;
                break;
            }
        }
        //2.将userName,Socket元素从map集合中删除
        map.remove(userName,socket);
        //3.提醒服务器该客户端已下线
        System.out.println("用户:"+userName+"已下线!");
    }
}
