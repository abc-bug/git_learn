package com.qst.dms.exception;  //这个包是单纯的判断是否出错的

public class DataAnalyseException extends Exception {
	public DataAnalyseException() {

	}

	public DataAnalyseException(String msg) {
		super(msg);
	}
}
