package remotedb;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Date;
import java.sql.*;
import java.net.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.nio.charset.*;
import java.text.*;

public class client {
	public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
		//创建客户端窗口对象
        ClientFrame cframe = new ClientFrame();
        //窗口关闭键无效，必须通过退出键退出客户端以便善后
        cframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //获取本机屏幕横向分辨率
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        //获取本机屏幕纵向分辨率
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        //将窗口置中
        cframe.setLocation((w - cframe.WIDTH)/2, (h - cframe.HEIGHT)/2);
        //设置客户端窗口为可见
        cframe.setVisible(true);
        
		Scanner input = new Scanner(System.in);
		System.out.println("0--go on\t1--exit");
		cframe.jtaChat.append("0--go on\t1--exit\n");
		int cmd1 = input.nextInt();
		
		//cmd1 = cframe.jtaSay.n
		while(cmd1 == 0) {
			//===========================================================================
			//连接后的动作=================================================================
			//===========================================================================
			Socket socket=null;
			try {
				socket=new Socket("localhost",8000);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			ObjectOutputStream os=null;
			ObjectInputStream is=null;
			try {
				//==================================================================
				//无异常的动作========================================================
				//==================================================================
				
				os=new ObjectOutputStream(socket.getOutputStream());
				Mesg Sending = new Mesg();
				
				Sending.ClientToServer(cframe);
				if(Sending.getSorC() == -1) {
					System.out.println("Good Bye");
					cframe.jtaChat.append("Good Bye\n");
					try {
						socket.close();
						os.close();
						is.close();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					return;
				}
				else {
					os.writeObject(Sending);
					os.flush();
					
					is = new ObjectInputStream(socket.getInputStream());
					Object obj = is.readObject();
					
					if(obj != null) {
						Mesg receive = (Mesg)obj;
						receive.Print(cframe);//客户端收到服务器的消息只要打印就行了
					}
				}
				//==================================================================
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					socket.close();
					os.close();
					is.close();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			//===========================================================================
			System.out.println("0--go on\t1--exit");
			cframe.jtaChat.append("0--go on\t1--exit\n");
			cmd1 = input.nextInt();
		}
		System.out.println("Good Bye");
		cframe.jtaChat.append("Good Bye\n");
		return;
	}
	
	
}


