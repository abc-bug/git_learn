package com.qst.dms.util;

import java.io.FileInputStream;
import java.util.Properties;

//������
public class Config {
	private static Properties p = null;
	static {
		try {
			p = new Properties();
			//����������
			p.load(new FileInputStream("C:\\Users\\����\\Desktop\\java���߳�ʵ��\\config\\config.propertise"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//��ȡ������Ĳ���
	public static String getValue(String key) {
		return p.get(key).toString();
	}
}
