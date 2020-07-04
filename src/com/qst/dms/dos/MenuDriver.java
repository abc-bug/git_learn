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
		// 建立一个从键盘接收数据的扫描器
		Scanner scanner = new Scanner(System.in);

		// 创建一个泛型ArrayList集合存储日志数据，即有了entity包中除了Matched的所有内容
		ArrayList<LogRec> logRecList = new ArrayList<LogRec>(); //自己添加了<>里面的LogRec
		// 创建一个泛型ArrrayList集合存储物流数据，即有了entity包中除了Matched的所有内容
		ArrayList<Transport> transportList = new ArrayList<Transport>();//自己添加了<>里面的Transport

		// 创建一个日志业务类，即输入和匹配输出功能,用了service包
		LogRecService logService = new LogRecService();
		// 创建一个物流业务类，即输入和匹配输出功能,用了service包
		TransportService tranService = new TransportService();

		// 日志数据匹配集合，"登录登出对" 类型，这里只是调用了entity包中的MatchedLogRec
		ArrayList<MatchedLogRec> matchedLogs = null;
		// 物流数据匹配集合，"登录登出对" 类型，这里只是调用了entity包中的MatchedTransport
		ArrayList<MatchedTransport> matchedTrans = null;

		try {
			while (true) {
				// 输出菜单界面，待补充 
				System.out.println("******************************");
				System.out.println("*欢迎进入日志物流信息数据系统！*");
				System.out.println("1.数据采集                2.数据匹配");
				System.out.println("3.数据记录                4.数据显示");
				System.out.println("5.数据发送                0.退出应用");
				System.out.println("******************************");
				// 提示用户输入要操作的菜单项
				System.out.println("请输入菜单项（0~5）：");
				// 接收键盘输入的选项
				int choice = scanner.nextInt();

				switch (choice) {
				case 1: {
					System.out.println("请输入采集数据类型：1.日志    2.物流");
					// 接收键盘输入的选项
					int type = scanner.nextInt();
					if (type == 1) {
						System.out.println("正在采集日志数据，请输入正确信息，确保数据的正常采集！");
						// 采集日志数据，待补充，通过service包中的方法来实现输入
						LogRec log=logService.inputLog();
						// 将采集的日志数据添加到logRecList集合中，待补充，赋值到entity包中去
						logRecList.add(log);
					} else if (type == 2) {
						System.out.println("正在采集物流数据，请输入正确信息，确保数据的正常采集！");
						// 采集物流数据
						Transport tran = tranService.inputTransport();
						// 将采集的物流数据添加到transportList集合中
						transportList.add(tran);
					}
				}
					break;
				case 2: {
					System.out.println("请输入匹配数据类型：1.日志    2.物流");
					// 接收键盘输入的选项
					int type = scanner.nextInt();
					if (type == 1) {
						System.out.println("正在日志数据过滤匹配...");
						// 创建日志数据分析对象，待补充，即把logRecList中的内容给了LogRecAnalyse，即包gather
						LogRecAnalyse logSe=new LogRecAnalyse(logRecList);
						// 日志数据过滤，待补充,即进行分析是登录还是登出，然后添加到对应的entity包中LogRec集合中
						logSe.doFilter();
						// 日志数据分析，待补充，通过gather包中LogRecAnallyse中的matchData函数来判断是否用户名和IP在登录和登出的时候是否一致来实现匹配
						matchedLogs=logSe.matchData();
						System.out.println("日志数据过滤匹配完成！");
						
					} else if (type == 2) {
						System.out.println("正在物流数据过滤匹配...");
						// 创建物流数据分析对象
						TransportAnalyse transAn = new TransportAnalyse(
								transportList);
						// 物流数据过滤
						transAn.doFilter();
						// 物流数据分析
						matchedTrans = transAn.matchData();
						System.out.println("物流数据过滤匹配完成！");
					}
				}
					break;
				case 3:{
					System.out.println("请输入记录数据类型：1.日志    2.物流");
					// 接收键盘输入的选项
					int type = scanner.nextInt();
					System.out.println("数据记录 中...");
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
					System.out.println("数据记录成功！");
				}
					break;
				case 4: {
					System.out.println("显示匹配的数据：");
					 //从日志匹配集合中判断匹配的日志记录，并输出匹配的日志信息，待补充
					ArrayList<MatchedLogRec>matchlogs=new ArrayList<MatchedLogRec>(); //add,same
					ArrayList<MatchedLogRec>matchLogs=new ArrayList<MatchedLogRec>();
					ArrayList<MatchedTransport>matchTrans=new ArrayList<MatchedTransport>(); //add,same
					ArrayList<MatchedTransport>matchtrans=new ArrayList<MatchedTransport>();
					
					matchLogs=logService.readMatchLog();
					matchtrans=tranService.readMatchedTransport();
					matchlogs = logService.readMatchedLogFromDB();
					matchTrans = tranService.readMatchedTransportFromDB();
					if (matchlogs == null || matchlogs.size() == 0) {
						System.out.println("匹配的日志记录是0条！");
					} else {
						// 输出匹配的日志信息
						//logService.showMatchLog(matchedLogs);
					    //matchlogs=logService.readMatchLog();
						System.out.println("日志信息文件输出：");
						logService.showMatchLog(matchLogs);
						System.out.println("日志信息数据库输出：");
					    logService.showMatchLog(matchlogs);
					}
					
					//从物流匹配集合中判断匹配的物流记录
					if (matchTrans == null || matchTrans.size() == 0) {
						System.out.println("匹配的物流记录是0条！");
					} else {
						// 输出匹配的物流信息
						//tranService.showMatchTransport(matchedTrans);
						
					//matchTrans=tranService.readMatchedTransport();
						System.out.println("物流信息文件输出：");
						tranService.showMatchTransport(matchtrans);
						System.out.println("物流信息数据库输出：");
					    tranService.showMatchTransport(matchTrans);
					}
				}
					break;
				case 5:
					System.out.println("数据发送 中...");
					break;
				case 0:
					// 应用程序退出
					System.exit(0);
				default:
					System.out.println("请输入正确的菜单项（0~5）！");
				}

			}
		} catch (Exception e) {
			System.out.println("输入的数据不合法！");
		}
	}
}
