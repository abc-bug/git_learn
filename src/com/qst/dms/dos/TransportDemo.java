package com.qst.dms.dos;

import com.qst.dms.entity.Transport;
import com.qst.dms.service.TransportService;

public class TransportDemo {

	public static void main(String[] args) {
		// ����һ������ҵ����
		TransportService tranService = new TransportService();
		// ����һ�������������飬���ڴ�Ųɼ����ĸ�������Ϣ
		Transport[] transports = new Transport[4];
		for (int i = 0; i < transports.length; i++) {
			System.out.println("��" + (i + 1) + "���������ݲɼ���");
			transports[i] = tranService.inputTransport();
		}
		// ��ʾ������Ϣ
		tranService.showTransport(transports);

	}

}
