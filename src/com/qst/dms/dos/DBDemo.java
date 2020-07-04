package com.qst.dms.dos;

import java.util.ArrayList;
import java.util.Date;

import com.qst.dms.entity.DataBase;
import com.qst.dms.entity.LogRec;
import com.qst.dms.entity.MatchedLogRec;
import com.qst.dms.entity.MatchedTransport;
import com.qst.dms.entity.Transport;
import com.qst.dms.service.LogRecService;
import com.qst.dms.service.TransportService;

public class DBDemo {
	public static void main(String[] args) {
		// 创建一个日志业务类
		LogRecService logService = new LogRecService();
		ArrayList<MatchedLogRec> matchLogs = new ArrayList<MatchedLogRec>();
		matchLogs.add(new MatchedLogRec(
				new LogRec(11, new Date(), "青岛",DataBase.GATHER, "zhangsan", "192.168.1.1", 1),
				new LogRec(12, new Date(), "青岛", DataBase.GATHER, "zhangsan",	"192.168.1.1", 0)));
		matchLogs.add(new MatchedLogRec(
				new LogRec(13, new Date(), "beijing",DataBase.GATHER, "lisi", "192.168.1.6", 1), 
				new LogRec(14, new Date(), "beijing", DataBase.GATHER, "lisi", "192.168.1.6", 0)));
		matchLogs.add(new MatchedLogRec(
				new LogRec(15, new Date(), "jinan",DataBase.GATHER, "wangwu", "192.168.1.89", 1),
				new LogRec(16, new Date(), "jinan", DataBase.GATHER,	"wangwu", "192.168.1.89", 0)));
		//保存匹配的日志信息到数据库中
		logService.saveMatchLogToDB(matchLogs);
		//从数据库中读取匹配的日志信息
		ArrayList<MatchedLogRec> logList = logService.readMatchedLogFromDB();
		logService.showMatchLog(logList);
		
		// 创建一个物流业务类
		TransportService tranService = new TransportService();
		ArrayList<MatchedTransport> matchTrans = new ArrayList<MatchedTransport>();
		matchTrans.add(new MatchedTransport(
				new Transport(21, new Date(), "qingdao",DataBase.GATHER,"zhangsan","zhaokel",1),
				new Transport(22, new Date(), "beijing",DataBase.GATHER,"lisi","zhaokel",2),
				new Transport(23, new Date(), "beijing",DataBase.GATHER,"wangwu","zhaokel",3)));	
		matchTrans.add(new MatchedTransport(
				new Transport(24, new Date(), "qingdao",DataBase.GATHER,"maliu","w",1),
				new Transport(25, new Date(), "beijing",DataBase.GATHER,"sunqi","w",2),
				new Transport(26, new Date(), "beijing",DataBase.GATHER,"fengba","w",3)));
		//保存匹配的物流信息到数据库中
		tranService.saveMatchTransportToDB(matchTrans);
		//从数据库中中读取匹配的物流信息
		ArrayList<MatchedTransport> transportList = tranService.readMatchedTransportFromDB();
		tranService.showMatchTransport(transportList);
	}
}
