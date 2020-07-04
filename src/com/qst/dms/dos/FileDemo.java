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
		// 创建一个日志业务类
		LogRecService logService = new LogRecService();
		ArrayList<MatchedLogRec> matchLogs = new ArrayList<MatchedLogRec>();  //add,same
		matchLogs.add(new MatchedLogRec(
				new LogRec(1001, new Date(), "青岛",DataBase.GATHER, "zhangsan", "192.168.1.1", 1),
				new LogRec(1002, new Date(), "青岛", DataBase.GATHER, "zhangsan",	"192.168.1.1", 0)));
		matchLogs.add(new MatchedLogRec(
				new LogRec(1003, new Date(), "北京",DataBase.GATHER, "lisi", "192.168.1.6", 1), 
				new LogRec(1004, new Date(), "北京", DataBase.GATHER, "lisi", "192.168.1.6", 0)));
		matchLogs.add(new MatchedLogRec(
				new LogRec(1005, new Date(), "济南",DataBase.GATHER, "wangwu", "192.168.1.89", 1),
				new LogRec(1006, new Date(), "济南", DataBase.GATHER,	"wangwu", "192.168.1.89", 0)));
		//保存匹配的日志信息到文件中
		
		try {
			FileInputStream fin = new FileInputStream("MatchLogs.txt");
			try {
				if(fin.available()!=0)
				{
					ObjectInputStream ois = new ObjectInputStream(fin);
					MatchedLogRec matchLog;
					// 循环读文件中的对象
					while ((matchLog = (MatchedLogRec) ois.readObject()) != null) {
						// 将对象添加到泛型集合中
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
		//保存匹配的日志信息到数据库中
		//logService.saveMatchLogToDB(matchLogs);
		//从文件中读取匹配的日志信息
		ArrayList<MatchedLogRec> list1 = logService.readMatchLog();
		logService.showMatchLog(list1);
		
		// 创建一个物流业务类
		TransportService tranService = new TransportService();
		ArrayList<MatchedTransport> matchTrans = new ArrayList<MatchedTransport>();  //add,same
		matchTrans.add(new MatchedTransport(
				new Transport(2001, new Date(), "青岛",DataBase.GATHER,"zhangsan","zhaokel",1),
				new Transport(2002, new Date(), "北京",DataBase.GATHER,"lisi","zhaokel",2),
				new Transport(2003, new Date(), "北京",DataBase.GATHER,"wangwu","zhaokel",3)));	
		matchTrans.add(new MatchedTransport(
				new Transport(2004, new Date(), "青岛",DataBase.GATHER,"maliu","zhaokel",1),
				new Transport(2005, new Date(), "北京",DataBase.GATHER,"sunqi","zhaokel",2),
				new Transport(2006, new Date(), "北京",DataBase.GATHER,"fengba","zhaokel",3)));
		//保存匹配的物流信息到文件中

		try {
			FileInputStream fin = new FileInputStream("MatchedTransports.txt");
			try {
				if(fin.available()!=0)
				{
					ObjectInputStream ois = new ObjectInputStream(fin);
					MatchedTransport matchTran;
					// 循环读文件中的对象
					while ((matchTran = (MatchedTransport) ois.readObject()) != null) {
						// 将对象添加到泛型集合中
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
		//保存匹配的物流信息到数据库中
		//tranService.saveMatchTransportToDB(matchTrans);
		//从文件中读取匹配的物流信息
		ArrayList<MatchedTransport> list2 = tranService.readMatchedTransport();
		tranService.showMatchTransport(list2);
	}
}
