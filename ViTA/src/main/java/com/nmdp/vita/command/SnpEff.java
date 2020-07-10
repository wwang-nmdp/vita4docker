package com.nmdp.vita.command;


import com.nmdp.vita.Configure;
import com.nmdp.vita.util.FileHelp;

import java.io.IOException;

public class SnpEff {
	public void run() throws Exception{
		System.out.println("vcf annotation starting");
		//create command line
		StringBuilder sb = new StringBuilder();
		//create annotated output fp
		sb.append(String.format("java -jar %s/snpEff/snpEff.jar GRCh38.86 ", Configure.tool));
		// Switch reference version
		// sb.append(String.format("java -jar %s/snpEff/snpEff.jar GRCh37.75 ", Configure.tool));
		sb.append(FileHelp.getInputFile().getAbsolutePath());
		sb.append(" -t -canon -onlyProtein ");
		//sSystem.out.println(sb.toString());
		
		try {
			CommandHelper.runAndSave(sb.toString(), FileHelp.getAnnotateOutput());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}

}
