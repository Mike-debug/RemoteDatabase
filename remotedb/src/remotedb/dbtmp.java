package remotedb;						
							
import java.sql.*;						
import java.util.*;						
import java.net.*;						
import java.io.*;						
							
public class dbtmp						
{						
	//===================================================================================					
	//==========��Ա����������==============================================================					
	//===================================================================================					
	int	attrnum;//���������			
	//��������
	ArrayList<String > columns = new ArrayList();					

	Connection conn;				
	Statement stmt;				
							
	//���ݿ��������ݱ������ڴ�������֮ǰ�û�ѡ���ģ����ɸ���					
	static String  dBname = "studentinfo";//���ݿ���					
	static String  tablename = "info";//���ݱ���					
							
	//===================================================================================					
	//==========���²����ǹ��캯�������ݱ�ѡ�����ͳ�Ա������ֵ��������============================					
	//===================================================================================					

	/*					
	 * ���캯��					
	 * ���캯���Ĺ�����Ҫ��������ݿ������					
	 * Ĭ�Ϲ��캯��������Ĭ�����ݿ�studentinfo					
	 */					
	public dbtmp() {					
		//���ʹ��Ĭ�Ϲ��캯������Ĭ�϶�ȡstudentinfo���ݿ�				
		//�����ڲ���֮ǰװ��Ĭ�����ݿ�studentinfo	
		try {				
			Class.forName("com.mysql.cj.jdbc.Driver");//JDBC������			
			String  sourceURL = "jdbc:mysql://localhost/";//JDBC���ݿ���Դ��λ			
			String  TimeZone = "?serverTimezone=UTC";//ʱ��			
			String  USER = "root";//�û���			
			String  PASS = "root";//����			
			//conn = DriverManager.getConnection(sourceURL);			
			conn = DriverManager.getConnection(sourceURL+dBname+TimeZone, USER, PASS);			
			stmt = conn.createStatement();			
							
			//��ö�ȡ����������			
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
	//==========���²��������ݱ���ɾ�Ĳ����ʾ���������=========================================					
	//===================================================================================					
							
							
	/*					
	 * ��ʾ����disp					
	 * ��ʾ���������Ϣ					
	 * */					
	ArrayList<String> disp()					
	{
		ArrayList<String> displayArrayList = new ArrayList<String>();
		StringBuffer tmpBuffer = new StringBuffer();
		
		String sql = "select * from info order by " + columns.get(0) + ";";
		try{				
			ResultSet rs= stmt.executeQuery(sql);
			//��ȡ����
			for(int i = 0; i < columns.size(); ++i) {
				tmpBuffer.append(columns.get(i) + "\t");
			}
			displayArrayList.add(tmpBuffer.toString());
			
			//��ȡ����
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
	 * ���뺯��					
	 * ����������ʾ������Ӧ����Ϣ					
	 * ������ɺ����ʾ����ɹ�����ʾ��������ݱ�					
	 * */					
	ArrayList<String> insert(ArrayList<String> instruction)
	{
		StringBuffer sql= new StringBuffer("insert into " + tablename + " values(");//����Ԥ�����				
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
	 * �޸ĺ���					
	 * ����������ʾ������Ϣ����					
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
		case 0://��ʾȫ��
			outcome = disp();
			break;
		case 1://��������
			outcome = insert(instruction);
			break;
		case 2://ɾ������
			outcome = delete(instruction);
			break;
		case 3://�޸�����
			outcome = update(instruction);
			break;
		case 4://��ѯ����
			outcome = select(instruction);
			break;
		default:
			outcome.add("error command");
			break;
		}
		return outcome;
	}
	
	//===================================================================================					
	//==========������====================================================================					
	//===================================================================================					
	public static void main(String[] args)					
	{					
		dbtmp w = new dbtmp();
		
		ArrayList<String> teStrings = new ArrayList<String>();
		w.PrintSA(w.function(0, teStrings));
		
		teStrings.add("10");
		teStrings.add("����׿");
		teStrings.add("����ѧԺ");
		teStrings.add("��");
		teStrings.add("23");
		teStrings.add("����ѧ");
		w.PrintSA(w.function(1, teStrings));
		
		teStrings.clear();
		teStrings.add("10");
		w.PrintSA(w.function(2, teStrings));
		
		teStrings.clear();
		teStrings.add("9");
		teStrings.add("6");
		teStrings.add("���＼��");
		w.PrintSA(w.function(3, teStrings));

		teStrings.clear();
		teStrings.add("���»�");
		w.PrintSA(w.function(4, teStrings));
		
		return;
	}
	
	void PrintSA(ArrayList<String> toprint) {
		for(int i = 0; i < toprint.size(); ++i) {
			System.out.println(toprint.get(i));
		}
	}
}						
