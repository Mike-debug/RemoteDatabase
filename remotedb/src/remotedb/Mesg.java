package remotedb;

import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;

//该类定义数据包格式
public class Mesg implements java.io.Serializable{
	private int SorC;//发送方是服务器还是客户端
	private int Cmd;//客户端发送的命令号
	private ArrayList<String> inst;//客户端发送的命令内容，或者，服务器发送的回复内容
	
	//客户端发送给服务器的数据包初始化
	public void ClientToServer(ClientFrame cf) {
		this.SorC = 0;
		//================
		Scanner input1 = new Scanner(System.in);
		System.out.println("请选择操作类型\n"
				+ "0--显示全表\t"
				+ "1--插入一行\t"
				+ "2--删除一行\t"
				+ "3--更新数据\t"
				+ "4--查询\t"
				+ "其他--退出");
		cf.jtaChat.append(
				"请选择操作类型\n"
						+ "0--显示全表\t"
						+ "1--插入一行\t"
						+ "2--删除一行\t"
						+ "3--更新数据\t"
						+ "4--查询\t"
						+ "其他--退出\n"
				);
		int choice = input1.nextInt();
		ArrayList<String> instruct = new ArrayList<String>();
		attr attrtmp = new attr();
		switch (choice) {
		case 0://显示全表
			instruct.clear();
			break;
		case 1://插入一行
			instruct.clear();
			for(int i = 0; i < attrtmp.getattr().size(); ++i) {
				System.out.println("Input " + attrtmp.getattr().get(i));
				cf.jtaChat.append("Input " + attrtmp.getattr().get(i) + "\n");
				instruct.add(input1.next());
			}
			break;
			
		case 2://删除数据
			instruct.clear();
			System.out.println("输入你想删除行的行号(以实际标号为准)");
			cf.jtaChat.append("输入你想删除行的行号(以实际标号为准)\n");
			instruct.add(input1.next());
			break;

		case 3://修改数据
			instruct.clear();
			System.out.println("输入你想修改行的行号(以实际标号为准)");
			cf.jtaChat.append("输入你想修改行的行号(以实际标号为准)");
			instruct.add(input1.next());
			System.out.println("输入你想修改的列号(从1号开始编号)");
			cf.jtaChat.append("输入你想修改的列号(从1号开始编号)\n");
			instruct.add(input1.next());				
			System.out.println("输入你想修改后的内容");				
			cf.jtaChat.append("输入你想修改后的内容\n");
			instruct.add(input1.next());
			break;
			
		case 4://查询数据
			instruct.clear();
			System.out.println("输入需要查询的第二列的信息");
			cf.jtaChat.append("输入需要查询的第二列的信息\n");
			System.out.println("Input the " + attrtmp.getattr().get(1) + " to search for the tupple:");
			cf.jtaChat.append("Input the " + attrtmp.getattr().get(1) + " to search for the tupple:"+"\n");
			instruct.add(input1.next());
			break;
		default:
			instruct.clear();
			this.SorC = -1;
			break;
		}
		//================
		this.Cmd = choice;
		this.inst = new ArrayList<String>(instruct);
	}
	
	//服务器发送个客户端的数据包初始化
	public void ServerToClient(ArrayList<String> con) {
		this.SorC = 1;
		this.Cmd = -1;
		this.inst = con;
	}
	
	public ArrayList<String> getinst(){
		return this.inst;
	}
	
	public void Print(ClientFrame cf) {//用于客户端打印数据包内容
		for(int i = 0; i < inst.size(); ++i) {
			System.out.println(inst.get(i));
			cf.jtaChat.append(inst.get(i) + "\n");
		}
		return;
	}
	
	public int getSorC() {
		return this.SorC;
	}
	
	public int getCmd() {
		return this.Cmd;
	}
}
