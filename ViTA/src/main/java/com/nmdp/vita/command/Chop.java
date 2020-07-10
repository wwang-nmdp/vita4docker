package com.nmdp.vita.command;


import com.nmdp.vita.util.PepChop;
import com.nmdp.vita.util.FileHelp;

import java.io.File;
import java.io.FileNotFoundException;

public class Chop {
	public void run(){
		  PepChop cl = new PepChop();
	        try {
	            cl.run(new File(FileHelp.getMetaData()), new File(FileHelp.getCleavageFile()));
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
		
	}
}
