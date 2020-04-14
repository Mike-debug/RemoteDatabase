package remotedb;

import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;

public class server {
	public static void main(String[] args) throws IOException
    {
        // 监听8000端口
        ServerSocket server=new ServerSocket(8000);
        while(true)
        {
            //接收客户端的连接
            Socket socket=server.accept();
            //调用客户端的数据处理方法
            invoke(socket);
        }
    }
	private static void invoke(final Socket socket) throws IOException
    {
        //开启一个新线程
        new Thread(new Runnable()
        {
            public void run()
            {
                //创建输入流对象
                ObjectInputStream is=null;
                //创建输出流对象
                ObjectOutputStream os=null;
                try
                {
                    is=new ObjectInputStream(socket.getInputStream());
                    os=new ObjectOutputStream(socket.getOutputStream());
                    //读取一个对象
                    Object obj = is.readObject();
                    if(obj != null) {
                    	//将对象转换为User类型
                    	Mesg instCliMesg=(Mesg)obj;
                    
	                    //生成的数据包
	                    Mesg returnMesg = new Mesg();
	                    
	                    //接受信息并处理
	                    ArrayList<String> returnClient = new ArrayList<String>();
	                    dbtmp tmdbcon = new dbtmp();
	                    
	                    if(instCliMesg.getSorC() == 0) {
	                    	returnClient = tmdbcon.function(instCliMesg.getCmd(), instCliMesg.getinst());
	                    	/*
	                    	switch (instCliMesg.getCmd()) {
	                    	case 0:
	                    		returnClient = tmdbcon.function(0, instCliMesg.getinst());
	                    		break;
	                    	case 1:
	                    		System.out.println(instCliMesg.getinst().toString());
	                    		returnClient.add("inst1");
	                    		break;
	                    	case 2:
	                    		returnClient.add("inst2");
	                    		break;
	                    	case 3:
	                    		returnClient.add("inst3");
	                    		break;
	                    	case 4:
	                    		returnClient.add("inst4");
	                    		break;
	                    	default:break;
	                    	}
	                    	*/
	                    	returnMesg.ServerToClient(returnClient);
	                    	
	                    	os.writeObject(returnMesg);
	                        os.flush();
	                    }
                    }
                }
                catch(IOException|ClassNotFoundException ex)
                {
                    ex.printStackTrace();
                }
                finally
                {
                    try
                    {
                        //关闭输入流
                        is.close();
                        //关闭输出流
                        os.close();
                        //关闭客户端
                        socket.close();
                    }
                    catch(Exception ex){}
                }
            }
        }).start();
    }
}
