package com.nmdp.vita.command;


import com.nmdp.vita.Configure;
import com.nmdp.vita.util.FileHelp;

import java.io.*;

public class NetMHCpan {
	public void run(){
		if(Configure.windowSlide){
			executeFile(FileHelp.getProteinSlice());
		}else{
			executeFile(FileHelp.getChopFile());
		}
	}
	
	private void executeFile(String input){
		//create the linux command line to parallel process netMHCpan
		StringBuilder sb = new StringBuilder();
		for(String hla : Configure.getHLA()){
			sb.append("#!/bin/bash\n");
			sb.append(String.format("%s/netMHCpan-4.0/bin/netMHCpan -p -a ", Configure.tool));
			sb.append(hla);
			sb.append(" ");
			sb.append(input);
			sb.append(" > ");
			sb.append(FileHelp.getAffFile(hla));
			sb.append(" &\n");
		}
		if(Configure.getHLA().length == 1){
			int index = sb.length();
			sb.deleteCharAt(index - 2);
		}
		
		//System.out.println(sb.toString());
		
		try {
			 File tempScript = File.createTempFile("script", null);
	         Writer streamWriter;
			streamWriter = new OutputStreamWriter(new FileOutputStream(
			            tempScript));
			PrintWriter printWriter = new PrintWriter(streamWriter);
			printWriter.print(sb.toString());
			printWriter.close();
			CommandHelper.runBash(tempScript);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
