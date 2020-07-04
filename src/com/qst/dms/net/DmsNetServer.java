package com.qst.dms.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.qst.dms.entity.MatchedLogRec;
import com.qst.dms.entity.MatchedTransport;
import com.qst.dms.service.LogRecService;
import com.qst.dms.service.TransportService;
public class DmsNetServer {
	public DmsNetServer() {
		//ͨ��ʹ�ò�ͬ�Ķ˿����ֽ��ܲ�ͬ���ݣ�6666�˿�����־6668�˿�������
        //��������6666�˿ڵ��̣߳�������־��Ϣ
		new AcceptLogThread(6661).start();
		//��������6668�˿ڵ��̣߳�������������
		new AcceptTranThread(6662).start();
		System.out.println("����������ѿ���.........");
	}
	
	//������־���ݵ��߳���
	private class AcceptLogThread extends Thread{
		    	private ServerSocket serverSocket;
		    	private Socket socket;
		    	private LogRecService logRecService;
		    	private ObjectInputStream ois;
		    	
		    	public  AcceptLogThread(int port) {
		    		logRecService=new LogRecService();
		    		try{
		    			serverSocket =new ServerSocket(port);
		    		}catch(IOException e) {
		    			e.printStackTrace();
		    		}
		    	}
	//��дrun����������־���ݱ��浽���ݿ���
	public void run() {
		while(this.isAlive()) {
			try {
				//���տͻ��˷��͹������׻���
				socket=serverSocket.accept();
				if(socket!=null) {
					ois=new ObjectInputStream(socket.getInputStream());
					//�����л����õ�ƥ�����־�б�
					ArrayList<MatchedLogRec> matchedLogs=(ArrayList<MatchedLogRec>)ois
							.readObject();
					//���ͻ��˷�������ƥ����־��Ϣ���浽���ݿ�
					logRecService.saveMatchLogToDB(matchedLogs);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		try {
			ois.close();
			socket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
	
	//�����������ݵ��߳���
		private class AcceptTranThread extends Thread{
			    	private ServerSocket serverSocket;
			    	private Socket socket;
			    	private TransportService transportService;
			    	private ObjectInputStream ois;
			    	
			    	public  AcceptTranThread(int port) {
			    		transportService=new TransportService();
			    		try{
			    			serverSocket =new ServerSocket(port);
			    		}catch(IOException e) {
			    			e.printStackTrace();
			    		}
			    	}
			    	
			    	//��дrun���������������ݱ��浽���ݿ���
					public void run() {
						while(this.isAlive()) {
							try {
								//���տͻ��˷��͹������׻���
								socket=serverSocket.accept();
								if(socket!=null) {
									ois=new ObjectInputStream(socket.getInputStream());
									//�����л����õ�ƥ�����־�б�
									ArrayList<MatchedTransport> matchedTrans=(ArrayList<MatchedTransport>)ois
											.readObject();
									//���ͻ��˷�������ƥ����־��Ϣ���浽���ݿ�
									transportService.saveMatchTransportToDB(matchedTrans);
								}
							}catch(Exception e) {
								e.printStackTrace();
							}
						}
						try {
							ois.close();
							socket.close();
						}catch(IOException e) {
							e.printStackTrace();
						}
					}
			    }
		//������
		public static void main(String[]args) {
			new DmsNetServer();
		}
		
}

