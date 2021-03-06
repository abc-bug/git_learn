package com.qst.dms.service;   //通过LogRec类型来实现与entity包的联系，即这里是单纯的输入信息和输出（需要调用entity包中的函数）功能


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


//日志业务类
public class LogRecService {
	// 日志数据采集
	public LogRec inputLog() { 
		LogRec log = null;
		// 建立一个从键盘接收数据的扫描器
		Scanner scanner = new Scanner(System.in);
		try {
			// 提示用户输入ID标识
			System.out.println("请输入ID标识：");
			// 接收键盘输入的整数
			int id = scanner.nextInt();
			// 获取当前系统时间
			Date nowDate = new Date();
			// 提示用户输入地址
			System.out.println("请输入地址：");
			// 接收键盘输入的字符串信息
			String address = scanner.next();
			// 数据状态是“采集”
			int type = DataBase.GATHER;

			// 提示用户输入登录用户名
			System.out.println("请输入 登录用户名：");
			// 接收键盘输入的字符串信息
			String user = scanner.next();
			// 提示用户输入主机IP
			System.out.println("请输入 主机IP:");
			// 接收键盘输入的字符串信息
			String ip = scanner.next();
			// 提示用户输入登录状态、登出状态
			System.out.println("请输入登录状态:1是登录，0是登出");
			int logType = scanner.nextInt();
			// 创建日志对象
			log = new LogRec(id, nowDate, address, type, user, ip, logType);
		} catch (Exception e) {
			System.out.println("采集的日志信息不合法");
		}
		// 返回日志对象
		return log;
	}

	// 日志信息输出
	public void showLog(LogRec... logRecs) {
		for (LogRec e : logRecs) {
			if (e != null) {
				System.out.println(e.toString());
			}
		}
	}

	// 匹配日志信息输出，可变参数
	public void showMatchLog(MatchedLogRec... matchLogs) {
		for (MatchedLogRec e : matchLogs) {
			if (e != null) {
				System.out.println(e.toString());
			}
		}
	}

	// 匹配日志信息输出,参数是集合
	public void showMatchLog(ArrayList<MatchedLogRec> matchLogs) {
		for (MatchedLogRec e : matchLogs) {
			if (e != null) {
				System.out.println(e.toString());
			}
		}
	}
	
	// 匹配日志信息保存，参数是集合
		public void saveMatchLog(ArrayList<MatchedLogRec> matchLogs) {
			// 创建一个ObjectOutputStream对象输出流，并连接文件输出流
			// 以可追加的方式创建文件输出流，数据保存到MatchLogs.txt文件中
			
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
				// 循环保存对象数据
				for (MatchedLogRec e : matchLogs) {
					if (e != null) {
						// 把对象写入到文件中
						obs.writeObject(e);
						obs.flush();
					}
				}
				// 文件末尾保存一个null对象，代表文件结束
				obs.writeObject(null);
				obs.flush();
			    }catch (Exception ex) {
				ex.printStackTrace();
			}
		}

     // 读匹配日志信息保存，参数是集合
		public ArrayList<MatchedLogRec> readMatchLog() {
			ArrayList<MatchedLogRec> matchLogs = new ArrayList<MatchedLogRec>();   //add,same
			// 创建一个ObjectInputStream对象输入流，并连接文件输入流，读MatchLogs.txt文件中
		   //需补充该方法
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
				// 循环读文件中的对象
				while ((matchLog= (MatchedLogRec) ois.readObject()) != null) {
					// 将对象添加到泛型集合中
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
		
		//获取数据库中的所有匹配的日志信息，返回一个ResultSet
		public ResultSet readLogResult() {		
			DBUtil db = new DBUtil();
			ResultSet rs=null;
			try {
				// 获取数据库链接
				Connection conn=db.getConnection();
				// 查询匹配日志，设置ResultSet可以使用除了next()之外的方法操作结果集
				Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				
				String sql = "SELECT * from gather_logrec";
				rs = st.executeQuery(sql);
			}catch (Exception e) {
				e.printStackTrace();
			}
			return rs;
		}
		
}
