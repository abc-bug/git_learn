package com.qst.dms.dos;

import java.util.ArrayList;
import java.util.Scanner;

import com.qst.dms.entity.LogRec;
import com.qst.dms.entity.MatchedLogRec;
import com.qst.dms.entity.MatchedTransport;
import com.qst.dms.entity.Transport;
import com.qst.dms.gather.LogRecAnalyse;
import com.qst.dms.gather.TransportAnalyse;
import com.qst.dms.service.LogRecService;
import com.qst.dms.service.TransportService;

public class MenuDriver {
	public static void main(String[] args) {
		// ����һ���Ӽ��̽������ݵ�ɨ����
		Scanner scanner = new Scanner(System.in);

		// ����һ������ArrayList���ϴ洢��־���ݣ�������entity���г���Matched����������
		ArrayList<LogRec> logRecList = new ArrayList<LogRec>(); //�Լ������<>�����LogRec
		// ����һ������ArrrayList���ϴ洢�������ݣ�������entity���г���Matched����������
		ArrayList<Transport> transportList = new ArrayList<Transport>();//�Լ������<>�����Transport

		// ����һ����־ҵ���࣬�������ƥ���������,����service��
		LogRecService logService = new LogRecService();
		// ����һ������ҵ���࣬�������ƥ���������,����service��
		TransportService tranService = new TransportService();

		// ��־����ƥ�伯�ϣ�"��¼�ǳ���" ���ͣ�����ֻ�ǵ�����entity���е�MatchedLogRec
		ArrayList<MatchedLogRec> matchedLogs = null;
		// ��������ƥ�伯�ϣ�"��¼�ǳ���" ���ͣ�����ֻ�ǵ�����entity���е�MatchedTransport
		ArrayList<MatchedTransport> matchedTrans = null;

		try {
			while (true) {
				// ����˵����棬������ 
				System.out.println("******************************");
				System.out.println("*��ӭ������־������Ϣ����ϵͳ��*");
				System.out.println("1.���ݲɼ�                2.����ƥ��");
				System.out.println("3.���ݼ�¼                4.������ʾ");
				System.out.println("5.���ݷ���                0.�˳�Ӧ��");
				System.out.println("******************************");
				// ��ʾ�û�����Ҫ�����Ĳ˵���
				System.out.println("������˵��0~5����");
				// ���ռ��������ѡ��
				int choice = scanner.nextInt();

				switch (choice) {
				case 1: {
					System.out.println("������ɼ��������ͣ�1.��־    2.����");
					// ���ռ��������ѡ��
					int type = scanner.nextInt();
					if (type == 1) {
						System.out.println("���ڲɼ���־���ݣ���������ȷ��Ϣ��ȷ�����ݵ������ɼ���");
						// �ɼ���־���ݣ������䣬ͨ��service���еķ�����ʵ������
						LogRec log=logService.inputLog();
						// ���ɼ�����־������ӵ�logRecList�����У������䣬��ֵ��entity����ȥ
						logRecList.add(log);
					} else if (type == 2) {
						System.out.println("���ڲɼ��������ݣ���������ȷ��Ϣ��ȷ�����ݵ������ɼ���");
						// �ɼ���������
						Transport tran = tranService.inputTransport();
						// ���ɼ�������������ӵ�transportList������
						transportList.add(tran);
					}
				}
					break;
				case 2: {
					System.out.println("������ƥ���������ͣ�1.��־    2.����");
					// ���ռ��������ѡ��
					int type = scanner.nextInt();
					if (type == 1) {
						System.out.println("������־���ݹ���ƥ��...");
						// ������־���ݷ������󣬴����䣬����logRecList�е����ݸ���LogRecAnalyse������gather
						LogRecAnalyse logSe=new LogRecAnalyse(logRecList);
						// ��־���ݹ��ˣ�������,�����з����ǵ�¼���ǵǳ���Ȼ����ӵ���Ӧ��entity����LogRec������
						logSe.doFilter();
						// ��־���ݷ����������䣬ͨ��gather����LogRecAnallyse�е�matchData�������ж��Ƿ��û�����IP�ڵ�¼�͵ǳ���ʱ���Ƿ�һ����ʵ��ƥ��
						matchedLogs=logSe.matchData();
						System.out.println("��־���ݹ���ƥ����ɣ�");
						
					} else if (type == 2) {
						System.out.println("�����������ݹ���ƥ��...");
						// �����������ݷ�������
						TransportAnalyse transAn = new TransportAnalyse(
								transportList);
						// �������ݹ���
						transAn.doFilter();
						// �������ݷ���
						matchedTrans = transAn.matchData();
						System.out.println("�������ݹ���ƥ����ɣ�");
					}
				}
					break;
				case 3:{
					System.out.println("�������¼�������ͣ�1.��־    2.����");
					// ���ռ��������ѡ��
					int type = scanner.nextInt();
					System.out.println("���ݼ�¼ ��...");
					if (type == 1){
					   logService.saveMatchLog(matchedLogs);
					   logService.saveMatchLogToDB(matchedLogs);
					   matchedLogs=null;
					}
					 else if (type == 2){
					         tranService.saveMatchedTransport(matchedTrans);
					         tranService.saveMatchTransportToDB(matchedTrans);
					         matchedTrans=null;
					}
					System.out.println("���ݼ�¼�ɹ���");
				}
					break;
				case 4: {
					System.out.println("��ʾƥ������ݣ�");
					 //����־ƥ�伯�����ж�ƥ�����־��¼�������ƥ�����־��Ϣ��������
					ArrayList<MatchedLogRec>matchlogs=new ArrayList<MatchedLogRec>(); //add,same
					ArrayList<MatchedLogRec>matchLogs=new ArrayList<MatchedLogRec>();
					ArrayList<MatchedTransport>matchTrans=new ArrayList<MatchedTransport>(); //add,same
					ArrayList<MatchedTransport>matchtrans=new ArrayList<MatchedTransport>();
					
					matchLogs=logService.readMatchLog();
					matchtrans=tranService.readMatchedTransport();
					matchlogs = logService.readMatchedLogFromDB();
					matchTrans = tranService.readMatchedTransportFromDB();
					if (matchlogs == null || matchlogs.size() == 0) {
						System.out.println("ƥ�����־��¼��0����");
					} else {
						// ���ƥ�����־��Ϣ
						//logService.showMatchLog(matchedLogs);
					    //matchlogs=logService.readMatchLog();
						System.out.println("��־��Ϣ�ļ������");
						logService.showMatchLog(matchLogs);
						System.out.println("��־��Ϣ���ݿ������");
					    logService.showMatchLog(matchlogs);
					}
					
					//������ƥ�伯�����ж�ƥ���������¼
					if (matchTrans == null || matchTrans.size() == 0) {
						System.out.println("ƥ���������¼��0����");
					} else {
						// ���ƥ���������Ϣ
						//tranService.showMatchTransport(matchedTrans);
						
					//matchTrans=tranService.readMatchedTransport();
						System.out.println("������Ϣ�ļ������");
						tranService.showMatchTransport(matchtrans);
						System.out.println("������Ϣ���ݿ������");
					    tranService.showMatchTransport(matchTrans);
					}
				}
					break;
				case 5:
					System.out.println("���ݷ��� ��...");
					break;
				case 0:
					// Ӧ�ó����˳�
					System.exit(0);
				default:
					System.out.println("��������ȷ�Ĳ˵��0~5����");
				}

			}
		} catch (Exception e) {
			System.out.println("��������ݲ��Ϸ���");
		}
	}
}
