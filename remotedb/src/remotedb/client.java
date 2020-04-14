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
		//�����ͻ��˴��ڶ���
        ClientFrame cframe = new ClientFrame();
        //���ڹرռ���Ч������ͨ���˳����˳��ͻ����Ա��ƺ�
        cframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //��ȡ������Ļ����ֱ���
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        //��ȡ������Ļ����ֱ���
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        //����������
        cframe.setLocation((w - cframe.WIDTH)/2, (h - cframe.HEIGHT)/2);
        //���ÿͻ��˴���Ϊ�ɼ�
        cframe.setVisible(true);
        
		Scanner input = new Scanner(System.in);
		System.out.println("0--go on\t1--exit");
		cframe.jtaChat.append("0--go on\t1--exit\n");
		int cmd1 = input.nextInt();
		
		//cmd1 = cframe.jtaSay.n
		while(cmd1 == 0) {
			//===========================================================================
			//���Ӻ�Ķ���=================================================================
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
				//���쳣�Ķ���========================================================
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
						receive.Print(cframe);//�ͻ����յ�����������ϢֻҪ��ӡ������
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


