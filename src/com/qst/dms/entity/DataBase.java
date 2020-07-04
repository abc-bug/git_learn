package com.qst.dms.entity;  //����������У�������DataBaseΪ������Ȼ��ֱ���LogRec->MatchedLogRec��Transport->MatchedTransport��
                             //ʵ�����ݵ���������

import java.io.Serializable;
import java.util.Date;

//���ݻ�����

public class DataBase implements Serializable{
	// ID��ʶ
	private int id;
	// ʱ��
	private Date time;
	// �ص�
	private String address;
	// ״̬
	private int type;
	// ״̬����
	public static final int GATHER=1;//"�ɼ�"
	public static final int MATHCH=2;//"ƥ��";
	public static final int RECORD=3;//"��¼";
	public static final int SEND=4;//"����";
	public static final int RECIVE=5;//"����";
	public static final int WRITE=6;//"�鵵";
	public static final int SAVE=7;//"����";

	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}


	public Date getTime() {
		return time;
	}

	
	public void setTime(Date time) {
		this.time = time;
	}


	public String getAddress() {
		return address;
	}

	
	public void setAddress(String address) {
		this.address = address;
	}

	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}

	public DataBase() {
	}

	public DataBase(int id, Date time, String address, int type) {
		this.id = id;
		this.time = time;
		this.address = address;
		this.type = type;
	}


	public String toString() {
		return id + "," + time + "," + address + "," + type;
	}

}
