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
		//通过使用不同的端口区分接受不同数据，6666端口是日志6668端口是物流
        //开启监听6666端口的线程，接受日志信息
		new AcceptLogThread(6661).start();
		//开启监听6668端口的线程，接收物流数据
		new AcceptTranThread(6662).start();
		System.out.println("网络服务器已开启.........");
	}
	
	//接受日志数据的线程类
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
	//重写run方法，将日志数据保存到数据库中
	public void run() {
		while(this.isAlive()) {
			try {
				//接收客户端发送过来的套换字
				socket=serverSocket.accept();
				if(socket!=null) {
					ois=new ObjectInputStream(socket.getInputStream());
					//反序列化，得到匹配的日志列表
					ArrayList<MatchedLogRec> matchedLogs=(ArrayList<MatchedLogRec>)ois
							.readObject();
					//将客户端发送来的匹配日志信息保存到数据库
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
	
	//接受物流数据的线程类
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
			    	
			    	//重写run方法，将物流数据保存到数据库中
					public void run() {
						while(this.isAlive()) {
							try {
								//接收客户端发送过来的套换字
								socket=serverSocket.accept();
								if(socket!=null) {
									ois=new ObjectInputStream(socket.getInputStream());
									//反序列化，得到匹配的日志列表
									ArrayList<MatchedTransport> matchedTrans=(ArrayList<MatchedTransport>)ois
											.readObject();
									//将客户端发送来的匹配日志信息保存到数据库
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
		//主程序
		public static void main(String[]args) {
			new DmsNetServer();
		}
		
}

