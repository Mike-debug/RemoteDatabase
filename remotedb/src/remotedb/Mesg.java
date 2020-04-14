package remotedb;

import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;

//���ඨ�����ݰ���ʽ
public class Mesg implements java.io.Serializable{
	private int SorC;//���ͷ��Ƿ��������ǿͻ���
	private int Cmd;//�ͻ��˷��͵������
	private ArrayList<String> inst;//�ͻ��˷��͵��������ݣ����ߣ����������͵Ļظ�����
	
	//�ͻ��˷��͸������������ݰ���ʼ��
	public void ClientToServer(ClientFrame cf) {
		this.SorC = 0;
		//================
		Scanner input1 = new Scanner(System.in);
		System.out.println("��ѡ���������\n"
				+ "0--��ʾȫ��\t"
				+ "1--����һ��\t"
				+ "2--ɾ��һ��\t"
				+ "3--��������\t"
				+ "4--��ѯ\t"
				+ "����--�˳�");
		cf.jtaChat.append(
				"��ѡ���������\n"
						+ "0--��ʾȫ��\t"
						+ "1--����һ��\t"
						+ "2--ɾ��һ��\t"
						+ "3--��������\t"
						+ "4--��ѯ\t"
						+ "����--�˳�\n"
				);
		int choice = input1.nextInt();
		ArrayList<String> instruct = new ArrayList<String>();
		attr attrtmp = new attr();
		switch (choice) {
		case 0://��ʾȫ��
			instruct.clear();
			break;
		case 1://����һ��
			instruct.clear();
			for(int i = 0; i < attrtmp.getattr().size(); ++i) {
				System.out.println("Input " + attrtmp.getattr().get(i));
				cf.jtaChat.append("Input " + attrtmp.getattr().get(i) + "\n");
				instruct.add(input1.next());
			}
			break;
			
		case 2://ɾ������
			instruct.clear();
			System.out.println("��������ɾ���е��к�(��ʵ�ʱ��Ϊ׼)");
			cf.jtaChat.append("��������ɾ���е��к�(��ʵ�ʱ��Ϊ׼)\n");
			instruct.add(input1.next());
			break;

		case 3://�޸�����
			instruct.clear();
			System.out.println("���������޸��е��к�(��ʵ�ʱ��Ϊ׼)");
			cf.jtaChat.append("���������޸��е��к�(��ʵ�ʱ��Ϊ׼)");
			instruct.add(input1.next());
			System.out.println("���������޸ĵ��к�(��1�ſ�ʼ���)");
			cf.jtaChat.append("���������޸ĵ��к�(��1�ſ�ʼ���)\n");
			instruct.add(input1.next());				
			System.out.println("���������޸ĺ������");				
			cf.jtaChat.append("���������޸ĺ������\n");
			instruct.add(input1.next());
			break;
			
		case 4://��ѯ����
			instruct.clear();
			System.out.println("������Ҫ��ѯ�ĵڶ��е���Ϣ");
			cf.jtaChat.append("������Ҫ��ѯ�ĵڶ��е���Ϣ\n");
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
	
	//���������͸��ͻ��˵����ݰ���ʼ��
	public void ServerToClient(ArrayList<String> con) {
		this.SorC = 1;
		this.Cmd = -1;
		this.inst = con;
	}
	
	public ArrayList<String> getinst(){
		return this.inst;
	}
	
	public void Print(ClientFrame cf) {//���ڿͻ��˴�ӡ���ݰ�����
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
