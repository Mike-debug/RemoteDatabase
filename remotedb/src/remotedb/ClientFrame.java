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

//客户端窗口
public class ClientFrame extends JFrame
{
  //窗口宽度
  final int WIDTH = 700;
  //窗口高度
  final int HEIGHT = 700;
  
  //创建发送按钮
  JButton btnSend = new JButton("发送");
  //创建清除按钮
  JButton btnClear = new JButton("清屏");
  //创建退出按钮
  JButton btnExit = new JButton("退出");

  //创建文本输入框, 参数分别为行数和列数
  JTextArea jtaSay = new JTextArea();

  //创建控制消息框
  JTextArea jtaChat = new JTextArea();

  //创建聊天消息框的滚动窗
  JScrollPane jspChat = new JScrollPane(jtaChat);
  
  
  private int cmd = -2;
  StringBuffer cmds = new StringBuffer();
  
  public int getcmd() {
	  return this.cmd;
  }
  
  //设置默认窗口属性，连接窗口组件
  public ClientFrame()
  {
      //标题
      setTitle("显示框");
      //大小
      setSize(WIDTH, HEIGHT);
      //不可缩放
      setResizable(false);
      //设置布局:不适用默认布局，完全自定义
      setLayout(null);

      //设置按钮大小和位置
      btnSend.setBounds(20, 600, 60, 60);
      btnClear.setBounds(260, 600, 60, 60);
      btnExit.setBounds(500, 600, 60, 60);

      //设置按钮文本的字体
      btnSend.setFont(new Font("宋体", Font.BOLD, 12));
      btnClear.setFont(new Font("宋体", Font.BOLD, 12));
      btnExit.setFont(new Font("宋体", Font.BOLD, 12));

      
      //添加按钮
      this.add(btnSend);
      this.add(btnClear);
      this.add(btnExit);

      
      //设置文本输入框大小和位置
      jtaSay.setBounds(20, 460, 600, 120);
      //设置文本输入框字体
      jtaSay.setFont(new Font("楷体", Font.BOLD, 16));
      //添加文本输入框
      this.add(jtaSay);
      
      //聊天消息框自动换行
      jtaChat.setLineWrap(true);
      //聊天框不可编辑，只用来显示
      jtaChat.setEditable(false);
      //设置聊天框字体
      jtaChat.setFont(new Font("楷体", Font.BOLD, 16));

      //设置滚动窗的水平滚动条属性:不出现
      jspChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      //设置滚动窗的垂直滚动条属性:需要时自动出现
      jspChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      //设置滚动窗大小和位置
      jspChat.setBounds(20, 20, 600, 400);
      //添加聊天窗口的滚动窗
      this.add(jspChat);

      //添加发送按钮的响应事件
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
      //添加清屏按钮的响应事件
      btnClear.addActionListener
                              (
                                   new ActionListener()
                                   {
                                      @Override
                                      public void actionPerformed(ActionEvent event)
                                      {
                                          //聊天框清屏
                                          jtaChat.setText("");
                                      }
                                   }
                              );
      //添加退出按钮的响应事件
      btnExit.addActionListener
                              (
                                   new ActionListener()
                                   {
                                      @Override
                                      public void actionPerformed(ActionEvent event)
                                      {
                                          try
                                          {
                                        	  
                                              //退出
                                              System.exit(0);
                                          }
                                          catch(Exception e){}
                                      }
                                   }
                              );
      

  }
}