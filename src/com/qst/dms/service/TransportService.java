package com.qst.dms.service;

import java.sql.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.qst.dms.db.DBUtil;
import com.qst.dms.entity.DataBase;
import com.qst.dms.entity.MatchedLogRec;
import com.qst.dms.entity.MatchedTransport;
import com.qst.dms.entity.Transport;

public class TransportService {
	// �������ݲɼ�
	public Transport inputTransport() {
		Transport trans = null;

		// ����һ���Ӽ��̽������ݵ�ɨ����
		Scanner scanner = new Scanner(System.in);
		try{
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
			System.out.println("��������ﾭ���ˣ�");
			// ���ռ���������ַ�����Ϣ
			String handler = scanner.next();
			// ��ʾ�û���������IP
			System.out.println("������ �ջ���:");
			// ���ռ���������ַ�����Ϣ
			String reciver = scanner.next();
			// ��ʾ������������״̬
			System.out.println("����������״̬��1�����У�2�ͻ��У�3��ǩ��");
			// ��������״̬
			int transportType = scanner.nextInt();
			// ����������Ϣ����
			trans = new Transport(id, nowDate, address, type, handler, reciver,
					transportType);
		} catch (Exception e) {
			System.out.println("�ɼ�����־��Ϣ���Ϸ�");
		}
		// ������������
		return trans;
	}

	// ������Ϣ���
	public void showTransport(Transport... transports) {
		for (Transport e : transports) {
			if (e != null) {
				System.out.println(e.toString());
			}
		}
	}

	// ƥ���������Ϣ������ɱ����
	public void showMatchTransport(MatchedTransport... matchTrans) {
		for (MatchedTransport e : matchTrans) {
			if (e != null) {
				System.out.println(e.toString());
			}
		}
	}
	// ƥ���������Ϣ����������Ǽ���
	public void showMatchTransport(ArrayList<MatchedTransport> matchTrans) {
		for (MatchedTransport e : matchTrans) {
			if (e != null) {
				System.out.println(e.toString());
			}
		}
	}
	
	// ƥ��������Ϣ���棬�����Ǽ���
		public void saveMatchedTransport(ArrayList<MatchedTransport> matchTrans) {
			// ����һ��ObjectOutputStream������������������ļ������
			// �Կ�׷�ӵķ�ʽ�����ļ�����������ݱ��浽MatchedTransports.txt�ļ���
			/*try {
				FileInputStream fin = new FileInputStream("MatchedTransports.txt");
				try {
					if(fin.available()!=0)
					{
						ObjectInputStream ois = new ObjectInputStream(fin);
						MatchedTransport matchTran;
						// ѭ�����ļ��еĶ���
						while ((matchTran = (MatchedTransport) ois.readObject()) != null) {
							// ��������ӵ����ͼ�����
							matchTrans.add(matchTran);	
					}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			
			
			ObjectOutputStream obs = null;
			try {
				obs = new ObjectOutputStream(
						new FileOutputStream("MatchedTransports.txt"));  //delete true
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try{

				// ѭ�������������
				for (MatchedTransport e : matchTrans) {
					if (e != null) {
						// �Ѷ���д�뵽�ļ���
						obs.writeObject(e);
						obs.flush();
					}
				}
				// �ļ�ĩβ����һ��null���󣬴����ļ�����
				obs.writeObject(null);
				obs.flush();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		// ��ƥ��������Ϣ���棬�����Ǽ���
		public ArrayList<MatchedTransport> readMatchedTransport() {
			ArrayList<MatchedTransport> matchTrans = new ArrayList<MatchedTransport>();  //add,same
			// ����һ��ObjectInputStream�������������������ļ�����������MatchedTransports.txt�ļ���
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(new FileInputStream(
						"MatchedTransports.txt"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					try{
				MatchedTransport matchTran;
				// ѭ�����ļ��еĶ���
				while ((matchTran = (MatchedTransport) ois.readObject()) != null) {
					// ��������ӵ����ͼ�����
					byte[]buf=new byte[4];
					ois.read(buf);
					matchTrans.add(matchTran);
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return matchTrans;
		}
		
		
		public void saveMatchTransportToDB(ArrayList<MatchedTransport> matchTrans){
			DBUtil db = new DBUtil();
			try{
			    db.getConnection();
			    for(MatchedTransport matchedTransport: matchTrans){
			        Transport send = matchedTransport.getSend();
			        Transport trans = matchedTransport.getTrans();
			        Transport receive = matchedTransport.getReceive();
			        String sql = "INSERT INTO gather_transport(id,time,address,type,handler,receiver,transporttype) VALUES(?,?,?,?,?,?,?)";
			        Object[] param = new Object[]{
			                send.getId(),new Timestamp(send.getTime().getTime()),send.getAddress(),
	                        send.getType(),send.getHandler(),send.getReciver(),send.getTransportType()
	                };
			        db.executeUpdate(sql,param);
			        param = new Object[]{
	                        trans.getId(),new Timestamp(trans.getTime().getTime()),trans.getAddress(),
	                        trans.getType(),trans.getHandler(),trans.getReciver(),trans.getTransportType()
	                };
			        db.executeUpdate(sql,param);
			        param = new Object[]{
	                        receive.getId(),new Timestamp(receive.getTime().getTime()),receive.getAddress(),
	                        receive.getType(),receive.getHandler(),receive.getReciver(),receive.getTransportType()
	                };
			        db.executeUpdate(sql,param);
			        sql = "INSERT INTO matched_transport(sendid,transid,receiveid) VALUES(?,?,?)";
			        param = new Object[]{
			                send.getId(),trans.getId(),receive.getId()
	                };
			        db.executeUpdate(sql,param);

	            }
	            db.closeAll();
	        }catch (Exception e)
	        {
	            e.printStackTrace();
	        }
		}
		
		public ArrayList<MatchedTransport> readMatchedTransportFromDB(){
		    ArrayList<MatchedTransport> matchedTransports=new ArrayList<MatchedTransport>();
		    DBUtil db = new DBUtil();
		    try{
		        db.getConnection();
		        String sql = "SELECT s.id,s.time,s.address,s.type,s.handler,s.receiver,s.transporttype,"+
	                    "t.id,t.time,t.address,t.type,t.handler,t.receiver,t.transporttype,"+
	                    "r.id,r.time,r.address,r.type,r.handler,r.receiver,r.transporttype "+
	                    "FROM matched_transport m,gather_transport s,gather_transport t,gather_transport r "+
	                    "WHERE m.sendid=s.id AND m.transid=t.id AND m.receiveid=r.id";
	            ResultSet rs = db.executeQuery(sql,null);
	            while(rs.next()){
	                Transport send = new Transport(rs.getInt(1),rs.getDate(2),
	                        rs.getString(3),rs.getInt(4),rs.getString(5),
	                        rs.getString(6),rs.getInt(7));
	                Transport trans = new Transport(rs.getInt(8),rs.getDate(9),
	                        rs.getString(10),rs.getInt(11),rs.getString(12),
	                        rs.getString(13),rs.getInt(14));
	                Transport receive = new Transport(rs.getInt(15),rs.getDate(16),
	                        rs.getString(17),rs.getInt(18),rs.getString(19),
	                        rs.getString(20),rs.getInt(21));
	                MatchedTransport matchedTrans = new MatchedTransport(send,trans,receive);
	                matchedTransports.add(matchedTrans);
	            }
	            db.closeAll();
	        }catch (Exception e){
		        e.printStackTrace();
	        }
		    return matchedTransports;

		}
		
		//��ȡ���ݿ��е�����ƥ���������Ϣ������һ��ResultSet
		public ResultSet readTransResult() {		
			DBUtil db = new DBUtil();
			ResultSet rs=null;
			try {
				// ��ȡ���ݿ�����
				Connection conn=db.getConnection();
				// // ��ѯƥ������������ResultSet����ʹ�ó���next()֮��ķ������������
				Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				
				String sql = "SELECT * from gather_transport";
				rs = st.executeQuery(sql);
			}catch (Exception e) {
				e.printStackTrace();
			}
			return rs;
		}
}
	

