package com.qst.dms.service;   //ͨ��LogRec������ʵ����entity������ϵ���������ǵ�����������Ϣ���������Ҫ����entity���еĺ���������


import java.sql.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.qst.dms.db.DBUtil;
import com.qst.dms.entity.DataBase;
import com.qst.dms.entity.LogRec;
import com.qst.dms.entity.MatchedLogRec;
import com.qst.dms.entity.MatchedTransport;


//��־ҵ����
public class LogRecService {
	// ��־���ݲɼ�
	public LogRec inputLog() { 
		LogRec log = null;
		// ����һ���Ӽ��̽������ݵ�ɨ����
		Scanner scanner = new Scanner(System.in);
		try {
			// ��ʾ�û�����ID��ʶ
			System.out.println("������ID��ʶ��");
			// ���ռ������������
			int id = scanner.nextInt();
			// ��ȡ��ǰϵͳʱ��
			Date nowDate = new Date();
			// ��ʾ�û������ַ
			System.out.println("�������ַ��");
			// ���ռ���������ַ�����Ϣ
			String address = scanner.next();
			// ����״̬�ǡ��ɼ���
			int type = DataBase.GATHER;

			// ��ʾ�û������¼�û���
			System.out.println("������ ��¼�û�����");
			// ���ռ���������ַ�����Ϣ
			String user = scanner.next();
			// ��ʾ�û���������IP
			System.out.println("������ ����IP:");
			// ���ռ���������ַ�����Ϣ
			String ip = scanner.next();
			// ��ʾ�û������¼״̬���ǳ�״̬
			System.out.println("�������¼״̬:1�ǵ�¼��0�ǵǳ�");
			int logType = scanner.nextInt();
			// ������־����
			log = new LogRec(id, nowDate, address, type, user, ip, logType);
		} catch (Exception e) {
			System.out.println("�ɼ�����־��Ϣ���Ϸ�");
		}
		// ������־����
		return log;
	}

	// ��־��Ϣ���
	public void showLog(LogRec... logRecs) {
		for (LogRec e : logRecs) {
			if (e != null) {
				System.out.println(e.toString());
			}
		}
	}

	// ƥ����־��Ϣ������ɱ����
	public void showMatchLog(MatchedLogRec... matchLogs) {
		for (MatchedLogRec e : matchLogs) {
			if (e != null) {
				System.out.println(e.toString());
			}
		}
	}

	// ƥ����־��Ϣ���,�����Ǽ���
	public void showMatchLog(ArrayList<MatchedLogRec> matchLogs) {
		for (MatchedLogRec e : matchLogs) {
			if (e != null) {
				System.out.println(e.toString());
			}
		}
	}
	
	// ƥ����־��Ϣ���棬�����Ǽ���
		public void saveMatchLog(ArrayList<MatchedLogRec> matchLogs) {
			// ����һ��ObjectOutputStream������������������ļ������
			// �Կ�׷�ӵķ�ʽ�����ļ�����������ݱ��浽MatchLogs.txt�ļ���
			
			ObjectOutputStream obs = null;
			try {
				obs = new ObjectOutputStream(
						new FileOutputStream("MatchLogs.txt"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
			try{
				// ѭ�������������
				for (MatchedLogRec e : matchLogs) {
					if (e != null) {
						// �Ѷ���д�뵽�ļ���
						obs.writeObject(e);
						obs.flush();
					}
				}
				// �ļ�ĩβ����һ��null���󣬴����ļ�����
				obs.writeObject(null);
				obs.flush();
			    }catch (Exception ex) {
				ex.printStackTrace();
			}
		}

     // ��ƥ����־��Ϣ���棬�����Ǽ���
		public ArrayList<MatchedLogRec> readMatchLog() {
			ArrayList<MatchedLogRec> matchLogs = new ArrayList<MatchedLogRec>();   //add,same
			// ����һ��ObjectInputStream�������������������ļ�����������MatchLogs.txt�ļ���
		   //�貹��÷���
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(new FileInputStream(
						"MatchLogs.txt"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				MatchedLogRec matchLog;
				// ѭ�����ļ��еĶ���
				while ((matchLog= (MatchedLogRec) ois.readObject()) != null) {
					// ��������ӵ����ͼ�����
					matchLogs.add(matchLog);
				}
			}catch (Exception ex) {
				ex.printStackTrace();}
			return matchLogs;
		}
		
		
		public void saveMatchLogToDB(ArrayList<MatchedLogRec> matchLogs){

		    //String sql = "Update table1 set user=?,password=?,age=? where id=?";
	        DBUtil LogRecDB = new DBUtil();
	        try {
	            LogRecDB.getConnection();
	            for(MatchedLogRec matchedLogRec : matchLogs){
	                LogRec login = matchedLogRec.getLogin();
	                LogRec logout = matchedLogRec.getLogout();
	                String preparedsql = "INSERT INTO gather_logrec(id,time,address,type,username,ip,logtype) VALUES(?,?,?,?,?,?,?)";
	                Object[] param = new Object[]{login.getId(),new Timestamp(login.getTime().getTime()),
	                                                login.getAddress(),login.getType(),login.getUser(),
	                                                login.getIp(),login.getLogType()};
	                LogRecDB.executeUpdate(preparedsql,param);
	                param = new Object[]{logout.getId(),new Timestamp(logout.getTime().getTime()),
	                        logout.getAddress(),logout.getType(),logout.getUser(),
	                        logout.getIp(),logout.getLogType()};
	                LogRecDB.executeUpdate(preparedsql,param);
	                preparedsql = "INSERT INTO matched_logrec(loginid,logoutid) VALUES(?,?)";
	                param = new Object[]{login.getId(),logout.getId()};
	                LogRecDB.executeUpdate(preparedsql,param);
	            }
	            LogRecDB.closeAll();

	        }catch (Exception e){
	            e.printStackTrace();      
	        }

	    }
	
		
		public ArrayList<MatchedLogRec> readMatchedLogFromDB()  {
	        ArrayList<MatchedLogRec> matchLogRecs = new ArrayList<MatchedLogRec>();
	        DBUtil LogRecDB = new DBUtil();
	        try{
	            LogRecDB.getConnection();
	            String sql = "SELECT i.id,i.time,i.address,i.type,i.username,i.ip,i.logtype,"+
	                    "o.id,o.time,o.address,o.type,o.username,o.ip,o.logtype "+
	                    "FROM matched_logrec m,gather_logrec i,gather_logrec o "+
	                    "WHERE m.loginid=i.id AND m.logoutid=o.id";
	            ResultSet rs = LogRecDB.executeQuery(sql,null);
	            while (rs.next()){

	                LogRec login = new LogRec(rs.getInt(1),rs.getDate(2),
	                        rs.getString(3),rs.getInt(4),rs.getString(5),
	                        rs.getString(6),rs.getInt(7));
	                LogRec logout = new LogRec(rs.getInt(8),rs.getDate(9),
	                        rs.getString(10),rs.getInt(11),rs.getString(12),
	                        rs.getString(13),rs.getInt(14));
	                MatchedLogRec matchedLog = new MatchedLogRec(login,logout);
	                matchLogRecs.add(matchedLog);
	            }
	            LogRecDB.closeAll();
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	        return matchLogRecs;
	    }
		
		//��ȡ���ݿ��е�����ƥ�����־��Ϣ������һ��ResultSet
		public ResultSet readLogResult() {		
			DBUtil db = new DBUtil();
			ResultSet rs=null;
			try {
				// ��ȡ���ݿ�����
				Connection conn=db.getConnection();
				// ��ѯƥ����־������ResultSet����ʹ�ó���next()֮��ķ������������
				Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				
				String sql = "SELECT * from gather_logrec";
				rs = st.executeQuery(sql);
			}catch (Exception e) {
				e.printStackTrace();
			}
			return rs;
		}
		
}
