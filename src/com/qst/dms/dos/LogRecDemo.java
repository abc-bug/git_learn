package com.qst.dms.dos;

import com.qst.dms.entity.LogRec;
import com.qst.dms.service.LogRecService;

public class LogRecDemo {
	public static void main(String[] args) {
		// ����һ����־ҵ����
		LogRecService logService = new LogRecService();
		// ����һ����־�������飬���ڴ�Ųɼ���������־��Ϣ
		LogRec[] logs = new LogRec[3];
		for (int i = 0; i < logs.length; i++) {
			System.out.println("��" + (i + 1) + "����־���ݲɼ���");
			logs[i] = logService.inputLog();
		}
		// ��ʾ��־��Ϣ
		logService.showLog(logs);
	}

}
