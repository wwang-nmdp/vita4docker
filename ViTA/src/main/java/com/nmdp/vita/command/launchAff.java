package com.nmdp.vita.command;

import com.nmdp.vita.Configure;
import com.nmdp.vita.ProcessAff;
import com.nmdp.vita.util.FileHelp;

import java.io.File;


public class launchAff {
	public void run(){
		ProcessAff pa = new ProcessAff();
		for(String hla : Configure.getHLA()){
			pa.run(new File(FileHelp.getChopMetaData()), new File(FileHelp.getAffFile(hla)), new File(FileHelp.getPredictFile(hla)));
		}
	}
}
