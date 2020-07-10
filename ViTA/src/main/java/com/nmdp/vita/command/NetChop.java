package com.nmdp.vita.command;


import com.nmdp.vita.Configure;
import com.nmdp.vita.util.FileHelp;

import java.io.IOException;

public class NetChop {
	public void run(){
		//create command line
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s/netchop-3.1/bin/netChop -s -v 1 ", Configure.tool));
		sb.append(FileHelp.getProteinFile());
		System.out.println(sb.toString());
		try {
			CommandHelper.runAndSave(sb.toString(), FileHelp.getCleavageFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		//If female to male, cleavage y genes
//		if(Configure.getSex() == Sex.FM){
//			sb = new StringBuilder();
//			sb.append(String.format("%s/netchop-3.1/bin/netChop -s ", Configure.tool));
//			sb.append(FileHelp.getYproteinFile());
//			try {
//				CommandHelper.runAndSave(sb.toString(), FileHelp.getCleavageYFile());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println(sb.toString());
//		}
		
		
	}

}
