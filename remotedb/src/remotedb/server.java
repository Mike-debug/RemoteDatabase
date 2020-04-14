package remotedb;

import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;

public class server {
	public static void main(String[] args) throws IOException
    {
        // ����8000�˿�
        ServerSocket server=new ServerSocket(8000);
        while(true)
        {
            //���տͻ��˵�����
            Socket socket=server.accept();
            //���ÿͻ��˵����ݴ�����
            invoke(socket);
        }
    }
	private static void invoke(final Socket socket) throws IOException
    {
        //����һ�����߳�
        new Thread(new Runnable()
        {
            public void run()
            {
                //��������������
                ObjectInputStream is=null;
                //�������������
                ObjectOutputStream os=null;
                try
                {
                    is=new ObjectInputStream(socket.getInputStream());
                    os=new ObjectOutputStream(socket.getOutputStream());
                    //��ȡһ������
                    Object obj = is.readObject();
                    if(obj != null) {
                    	//������ת��ΪUser����
                    	Mesg instCliMesg=(Mesg)obj;
                    
	                    //���ɵ����ݰ�
	                    Mesg returnMesg = new Mesg();
	                    
	                    //������Ϣ������
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
                        //�ر�������
                        is.close();
                        //�ر������
                        os.close();
                        //�رտͻ���
                        socket.close();
                    }
                    catch(Exception ex){}
                }
            }
        }).start();
    }
}
