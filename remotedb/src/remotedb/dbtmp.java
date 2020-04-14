package remotedb;						
							
import java.sql.*;						
import java.util.*;						
import java.net.*;						
import java.io.*;						
							
public class dbtmp						
{						
	//===================================================================================					
	//==========成员变量定义区==============================================================					
	//===================================================================================					
	int	attrnum;//表格属性数			
	//各个属性
	ArrayList<String > columns = new ArrayList();					

	Connection conn;				
	Statement stmt;				
							
	//数据库名和数据表名是在代码生成之前用户选定的，不可更改					
	static String  dBname = "studentinfo";//数据库名					
	static String  tablename = "info";//数据表名					
							
	//===================================================================================					
	//==========以下部分是构造函数、数据表选择函数和成员变量赋值函数部分============================					
	//===================================================================================					

	/*					
	 * 构造函数					
	 * 构造函数的功能主要是完成数据库的链接					
	 * 默认构造函数，操作默认数据库studentinfo					
	 */					
	public dbtmp() {					
		//如果使用默认构造函数，则默认读取studentinfo数据库				
		//请先在测试之前装好默认数据库studentinfo	
		try {				
			Class.forName("com.mysql.cj.jdbc.Driver");//JDBC驱动名			
			String  sourceURL = "jdbc:mysql://localhost/";//JDBC数据库资源定位			
			String  TimeZone = "?serverTimezone=UTC";//时区			
			String  USER = "root";//用户名			
			String  PASS = "root";//密码			
			//conn = DriverManager.getConnection(sourceURL);			
			conn = DriverManager.getConnection(sourceURL+dBname+TimeZone, USER, PASS);			
			stmt = conn.createStatement();			
							
			//获得读取表格的属性数			
			String  sql = "show columns from " + tablename;			
			ResultSet rs1 = stmt.executeQuery(sql);			
			while(rs1.next()) {			
				columns.add(rs1.getString ("Field"));		
			}			
			attrnum = columns.size();			
		} catch(Exception e){				
			e.printStackTrace();			
		}				
	}					
							
	//===================================================================================					
	//==========以下部分是数据表增删改查和显示的五个函数=========================================					
	//===================================================================================					
							
							
	/*					
	 * 显示函数disp					
	 * 显示表格所有信息					
	 * */					
	ArrayList<String> disp()					
	{
		ArrayList<String> displayArrayList = new ArrayList<String>();
		StringBuffer tmpBuffer = new StringBuffer();
		
		String sql = "select * from info order by " + columns.get(0) + ";";
		try{				
			ResultSet rs= stmt.executeQuery(sql);
			//获取属性
			for(int i = 0; i < columns.size(); ++i) {
				tmpBuffer.append(columns.get(i) + "\t");
			}
			displayArrayList.add(tmpBuffer.toString());
			
			//获取内容
			while(rs.next()) {
				StringBuffer tmp = new StringBuffer();
				for(int j = 0; j < columns.size(); ++j) {
					tmp.append(rs.getString(j + 1) + "\t");
				}
				displayArrayList.add(tmp.toString());
			}			
		} 
		catch(Exception e){				
			e.printStackTrace();			
		}
		return displayArrayList;
	}					
							
	/*					
	 * 插入函数					
	 * 根据输入提示输入相应的信息					
	 * 插入完成后会提示插入成功并显示插入后数据表					
	 * */					
	ArrayList<String> insert(ArrayList<String> instruction)
	{
		StringBuffer sql= new StringBuffer("insert into " + tablename + " values(");//插入预备语句				
		ArrayList<String> Toinsert = new ArrayList();				
		Scanner inputScanner = new Scanner(System.in);				
		for(int i = 0; i < attrnum; ++i) {				
			Toinsert.add(instruction.get(i));			
		}
		for(int i = 0; i < Toinsert.size(); ++i) {				
			if(i == 0) {			
				sql.append(Toinsert.get(i) + ",'");		
			}			
			else if(i < Toinsert.size() - 1) {			
				sql.append(Toinsert.get(i) + "','");		
			}			
			else {			
				sql.append(Toinsert.get(i) + "');");		
			}			
		}				
		try {				
			stmt = conn.createStatement();			
			stmt.executeUpdate(sql.toString());			
		}				
		catch (Exception e) {				
			e.printStackTrace();			
		}
		
		ArrayList<String> outcome = new ArrayList<String>();
		outcome.add("Insert Successfully");
		return outcome;
	}					
							
	/*					
	 * 修改函数					
	 * 根据输入提示输入信息即可					
	 * */					
	ArrayList<String> update(ArrayList<String> instruction)					
	{								
		String sql = "update "+tablename+" set " + columns.get(Integer.valueOf(instruction.get(1)) - 1) + " = '" + instruction.get(2) + "' where stuid = "+ Integer.valueOf(instruction.get(0));
		
		try {				
			stmt = conn.createStatement();			
			stmt.executeUpdate(sql);			
		}				
		catch (Exception e) {				
			e.printStackTrace();			
		}				
		
		ArrayList<String> outcome = new ArrayList<String>();
		outcome.add("update Successfully");
		return outcome;
	}					

	ArrayList<String> delete(ArrayList<String> instruction)					
	{						
		String sql="delete from " + tablename + " where " + columns.get(0) + " = " + Integer.valueOf(instruction.get(0)) + ";";			
		try {				
			stmt = conn.createStatement();			
			stmt.executeUpdate(sql);			
		}				
		catch (Exception e) {				
			e.printStackTrace();			
		}				
		ArrayList<String> outcome = new ArrayList<String>();
		outcome.add("delete Successfully");
		return outcome;
	}
	
	
	ArrayList<String> select(ArrayList<String> instruction)					
	{					
		ArrayList<String> out = new ArrayList<String>();
		StringBuffer tmBuffer = new StringBuffer();
		String sql = "select * from " + tablename + " where " + columns.get(1) + " = '" + instruction.get(0) + "';";								
		try {				
			stmt = conn.createStatement();			
			ResultSet rs = stmt.executeQuery(sql);			
			for(int i = 0; i < columns.size(); ++i) {			
				tmBuffer.append(columns.get(i) + "\t");		
			}
			out.add(tmBuffer.toString());
			tmBuffer.delete(0, tmBuffer.length() - 1);
			
			boolean emptyflag = false;
			while(rs.next()) {			
				for(int i = 1; i <= columns.size(); ++i) {		
					tmBuffer.append(rs.getString(i)+"\t");	
					emptyflag = true;	
				}		
			}	
			if(emptyflag == false) {			
				tmBuffer.append("Not Found");		
			}
			
			out.add(tmBuffer.toString());
		}				
		catch (Exception e) {				
			e.printStackTrace();			
		}
		
		return out;
	}					
	
	ArrayList<String> function(int cmd, ArrayList<String> instruction){
		ArrayList<String> outcome = new ArrayList<String>();
		switch(cmd) {
		case 0://显示全表
			outcome = disp();
			break;
		case 1://插入数据
			outcome = insert(instruction);
			break;
		case 2://删除数据
			outcome = delete(instruction);
			break;
		case 3://修改数据
			outcome = update(instruction);
			break;
		case 4://查询数据
			outcome = select(instruction);
			break;
		default:
			outcome.add("error command");
			break;
		}
		return outcome;
	}
	
	//===================================================================================					
	//==========主函数====================================================================					
	//===================================================================================					
	public static void main(String[] args)					
	{					
		dbtmp w = new dbtmp();
		
		ArrayList<String> teStrings = new ArrayList<String>();
		w.PrintSA(w.function(0, teStrings));
		
		teStrings.add("10");
		teStrings.add("赵文卓");
		teStrings.add("经济学院");
		teStrings.add("男");
		teStrings.add("23");
		teStrings.add("金融学");
		w.PrintSA(w.function(1, teStrings));
		
		teStrings.clear();
		teStrings.add("10");
		w.PrintSA(w.function(2, teStrings));
		
		teStrings.clear();
		teStrings.add("9");
		teStrings.add("6");
		teStrings.add("生物技术");
		w.PrintSA(w.function(3, teStrings));

		teStrings.clear();
		teStrings.add("刘德华");
		w.PrintSA(w.function(4, teStrings));
		
		return;
	}
	
	void PrintSA(ArrayList<String> toprint) {
		for(int i = 0; i < toprint.size(); ++i) {
			System.out.println(toprint.get(i));
		}
	}
}						
