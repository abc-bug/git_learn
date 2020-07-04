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
		// ����һ����־ҵ����
		LogRecService logService = new LogRecService();
		ArrayList<MatchedLogRec> matchLogs = new ArrayList<MatchedLogRec>();
		matchLogs.add(new MatchedLogRec(
				new LogRec(11, new Date(), "�ൺ",DataBase.GATHER, "zhangsan", "192.168.1.1", 1),
				new LogRec(12, new Date(), "�ൺ", DataBase.GATHER, "zhangsan",	"192.168.1.1", 0)));
		matchLogs.add(new MatchedLogRec(
				new LogRec(13, new Date(), "beijing",DataBase.GATHER, "lisi", "192.168.1.6", 1), 
				new LogRec(14, new Date(), "beijing", DataBase.GATHER, "lisi", "192.168.1.6", 0)));
		matchLogs.add(new MatchedLogRec(
				new LogRec(15, new Date(), "jinan",DataBase.GATHER, "wangwu", "192.168.1.89", 1),
				new LogRec(16, new Date(), "jinan", DataBase.GATHER,	"wangwu", "192.168.1.89", 0)));
		//����ƥ�����־��Ϣ�����ݿ���
		logService.saveMatchLogToDB(matchLogs);
		//�����ݿ��ж�ȡƥ�����־��Ϣ
		ArrayList<MatchedLogRec> logList = logService.readMatchedLogFromDB();
		logService.showMatchLog(logList);
		
		// ����һ������ҵ����
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
		//����ƥ���������Ϣ�����ݿ���
		tranService.saveMatchTransportToDB(matchTrans);
		//�����ݿ����ж�ȡƥ���������Ϣ
		ArrayList<MatchedTransport> transportList = tranService.readMatchedTransportFromDB();
		tranService.showMatchTransport(transportList);
	}
}
