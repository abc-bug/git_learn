package com.qst.dms.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class AppendObjectOutputStream extends ObjectOutputStream  {
	public static  File file = null;   
    public AppendObjectOutputStream(File file) throws IOException {
    super(new FileOutputStream(file,true));
    }
    public void writeStreamHeader() throws IOException {

    if (file != null) {
       if (file.length() == 0) {
       super.writeStreamHeader();
       } else { }
    } else {
    super.writeStreamHeader();
    }
    }
}
