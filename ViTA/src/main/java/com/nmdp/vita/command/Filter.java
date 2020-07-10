package com.nmdp.vita.command;

import com.nmdp.vita.util.FileHelp;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


/**
 * The class to extract the missense variant from annotated vcf.
 *
 */
public class Filter {
	public void run(){
		filter(FileHelp.getAnnotateOutput(), FileHelp.getMissense());
	}
	
	private void filter(String input, String output){
		try{
			Scanner sn = new Scanner(new File(input));
			File outputFile = new File(output);
			PrintWriter pw = new PrintWriter(outputFile);
			while(sn.hasNext()){
				String line = sn.nextLine();
				String[] lineData = line.split("ANN=");
				if(lineData.length == 2 && lineData[1].contains("missense_variant")){
					pw.println(line);
				}
			}
			sn.close();
			pw.close();
		}catch(IOException e){
			
		}
	}
}
