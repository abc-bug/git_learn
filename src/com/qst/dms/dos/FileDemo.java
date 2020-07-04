package com.qst.dms.dos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;

import com.qst.dms.entity.DataBase;
import com.qst.dms.entity.LogRec;
import com.qst.dms.entity.MatchedLogRec;
import com.qst.dms.entity.MatchedTransport;
import com.qst.dms.entity.Transport;
import com.qst.dms.service.LogRecService;
import com.qst.dms.service.TransportService;

public class FileDemo {
	public static void main(String[] args) {
		// ����һ����־ҵ����
		LogRecService logService = new LogRecService();
		ArrayList<MatchedLogRec> matchLogs = new ArrayList<MatchedLogRec>();  //add,same
		matchLogs.add(new MatchedLogRec(
				new LogRec(1001, new Date(), "�ൺ",DataBase.GATHER, "zhangsan", "192.168.1.1", 1),
				new LogRec(1002, new Date(), "�ൺ", DataBase.GATHER, "zhangsan",	"192.168.1.1", 0)));
		matchLogs.add(new MatchedLogRec(
				new LogRec(1003, new Date(), "����",DataBase.GATHER, "lisi", "192.168.1.6", 1), 
				new LogRec(1004, new Date(), "����", DataBase.GATHER, "lisi", "192.168.1.6", 0)));
		matchLogs.add(new MatchedLogRec(
				new LogRec(1005, new Date(), "����",DataBase.GATHER, "wangwu", "192.168.1.89", 1),
				new LogRec(1006, new Date(), "����", DataBase.GATHER,	"wangwu", "192.168.1.89", 0)));
		//����ƥ�����־��Ϣ���ļ���
		
		try {
			FileInputStream fin = new FileInputStream("MatchLogs.txt");
			try {
				if(fin.available()!=0)
				{
					ObjectInputStream ois = new ObjectInputStream(fin);
					MatchedLogRec matchLog;
					// ѭ�����ļ��еĶ���
					while ((matchLog = (MatchedLogRec) ois.readObject()) != null) {
						// ��������ӵ����ͼ�����
						matchLogs.add(matchLog);	
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
		}

		logService.saveMatchLog(matchLogs);
		//����ƥ�����־��Ϣ�����ݿ���
		//logService.saveMatchLogToDB(matchLogs);
		//���ļ��ж�ȡƥ�����־��Ϣ
		ArrayList<MatchedLogRec> list1 = logService.readMatchLog();
		logService.showMatchLog(list1);
		
		// ����һ������ҵ����
		TransportService tranService = new TransportService();
		ArrayList<MatchedTransport> matchTrans = new ArrayList<MatchedTransport>();  //add,same
		matchTrans.add(new MatchedTransport(
				new Transport(2001, new Date(), "�ൺ",DataBase.GATHER,"zhangsan","zhaokel",1),
				new Transport(2002, new Date(), "����",DataBase.GATHER,"lisi","zhaokel",2),
				new Transport(2003, new Date(), "����",DataBase.GATHER,"wangwu","zhaokel",3)));	
		matchTrans.add(new MatchedTransport(
				new Transport(2004, new Date(), "�ൺ",DataBase.GATHER,"maliu","zhaokel",1),
				new Transport(2005, new Date(), "����",DataBase.GATHER,"sunqi","zhaokel",2),
				new Transport(2006, new Date(), "����",DataBase.GATHER,"fengba","zhaokel",3)));
		//����ƥ���������Ϣ���ļ���

		try {
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
		}

		tranService.saveMatchedTransport(matchTrans);
		//����ƥ���������Ϣ�����ݿ���
		//tranService.saveMatchTransportToDB(matchTrans);
		//���ļ��ж�ȡƥ���������Ϣ
		ArrayList<MatchedTransport> list2 = tranService.readMatchedTransport();
		tranService.showMatchTransport(list2);
	}
}
