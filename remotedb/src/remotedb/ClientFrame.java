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

//�ͻ��˴���
public class ClientFrame extends JFrame
{
  //���ڿ��
  final int WIDTH = 700;
  //���ڸ߶�
  final int HEIGHT = 700;
  
  //�������Ͱ�ť
  JButton btnSend = new JButton("����");
  //���������ť
  JButton btnClear = new JButton("����");
  //�����˳���ť
  JButton btnExit = new JButton("�˳�");

  //�����ı������, �����ֱ�Ϊ����������
  JTextArea jtaSay = new JTextArea();

  //����������Ϣ��
  JTextArea jtaChat = new JTextArea();

  //����������Ϣ��Ĺ�����
  JScrollPane jspChat = new JScrollPane(jtaChat);
  
  
  private int cmd = -2;
  StringBuffer cmds = new StringBuffer();
  
  public int getcmd() {
	  return this.cmd;
  }
  
  //����Ĭ�ϴ������ԣ����Ӵ������
  public ClientFrame()
  {
      //����
      setTitle("��ʾ��");
      //��С
      setSize(WIDTH, HEIGHT);
      //��������
      setResizable(false);
      //���ò���:������Ĭ�ϲ��֣���ȫ�Զ���
      setLayout(null);

      //���ð�ť��С��λ��
      btnSend.setBounds(20, 600, 60, 60);
      btnClear.setBounds(260, 600, 60, 60);
      btnExit.setBounds(500, 600, 60, 60);

      //���ð�ť�ı�������
      btnSend.setFont(new Font("����", Font.BOLD, 12));
      btnClear.setFont(new Font("����", Font.BOLD, 12));
      btnExit.setFont(new Font("����", Font.BOLD, 12));

      
      //��Ӱ�ť
      this.add(btnSend);
      this.add(btnClear);
      this.add(btnExit);

      
      //�����ı�������С��λ��
      jtaSay.setBounds(20, 460, 600, 120);
      //�����ı����������
      jtaSay.setFont(new Font("����", Font.BOLD, 16));
      //����ı������
      this.add(jtaSay);
      
      //������Ϣ���Զ�����
      jtaChat.setLineWrap(true);
      //����򲻿ɱ༭��ֻ������ʾ
      jtaChat.setEditable(false);
      //�������������
      jtaChat.setFont(new Font("����", Font.BOLD, 16));

      //���ù�������ˮƽ����������:������
      jspChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      //���ù������Ĵ�ֱ����������:��Ҫʱ�Զ�����
      jspChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      //���ù�������С��λ��
      jspChat.setBounds(20, 20, 600, 400);
      //������촰�ڵĹ�����
      this.add(jspChat);

      //��ӷ��Ͱ�ť����Ӧ�¼�
      btnSend.addActionListener
                              (
                                   new ActionListener()
                                   {
                                      @Override
                                      public void actionPerformed(ActionEvent event)
                                      {
                                    	  
                                    	  jtaSay.setText("");
                                    	  
                                      }
                                   }
                              );
      //���������ť����Ӧ�¼�
      btnClear.addActionListener
                              (
                                   new ActionListener()
                                   {
                                      @Override
                                      public void actionPerformed(ActionEvent event)
                                      {
                                          //���������
                                          jtaChat.setText("");
                                      }
                                   }
                              );
      //����˳���ť����Ӧ�¼�
      btnExit.addActionListener
                              (
                                   new ActionListener()
                                   {
                                      @Override
                                      public void actionPerformed(ActionEvent event)
                                      {
                                          try
                                          {
                                        	  
                                              //�˳�
                                              System.exit(0);
                                          }
                                          catch(Exception e){}
                                      }
                                   }
                              );
      

  }
}