package com.nmdp.vita;


import com.nmdp.vita.database.DatabaseUtil;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by wwang on 10/23/16.
 */
public class ProcessAff {
    ArrayList<String> header = new ArrayList<>();
    Scanner sn;
    PrintWriter pw;
    public void run(File meta, File aff, File output){
        try
        {
            Thread.sleep(10000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

    	DatabaseUtil.connectDatabase();
        try {
            sn = new Scanner(aff);
            System.out.println("The aff ile path is "+ aff.getAbsolutePath());
            pw = new PrintWriter(output);
        } catch (FileNotFoundException e) {
            System.out.println("Can't find aff file ");
            e.printStackTrace();
            return;
        }
        loadMetaData(meta);
        skipHeader();
        for(int i = 0; i< header.size(); i++){
            pw.print(header.get(i));
            if(!sn.hasNext()){
                System.out.println("There is no data in aff file.");
                break;
            }else {
            	String[] data = sn.nextLine().split(" +");
            	//data[0] is a space
                //data[1] is position
                //They are not needed in the output file
            	for(int k = 2; k< data.length; k++){
            		pw.print(data[k]);
            		if(k != data.length-1){
            			pw.print(",");
            		}
            	}
                pw.println();
            }
        }
        sn.close();
        pw.close();
        header.clear();
        DatabaseUtil.cleanUp();
    }

    private String getFileName(File aff){
        String [] names = aff.getName().split("\\.");
        return names[0];

    }

    private void skipHeader() {
        while(sn.hasNext()){
            String line = sn.nextLine();
            if(line.startsWith("  Pos          HLA")){
                break;
            }else {
                continue;
            }
        }
        if(sn.hasNext()){
            //jump the dash line
            sn.nextLine();
        }
    }

    private void loadMetaData(File meta) {
        Scanner sn = null;
        try {
            sn = new Scanner(meta);
        } catch (FileNotFoundException e) {
            System.out.println("Can't find meta file");
            e.printStackTrace();
            return;
        }
        if(sn != null){
            while(sn.hasNext()){
                header.add(addFreqIntoHead(sn.nextLine()));
            }
            sn.close();
        }
        System.out.println("The header size of process aff is "+ header.size());
    }
    
    private String addFreqIntoHead(String head){
    	String[] data = head.split(",");
    	StringBuilder sb = new StringBuilder();
    	String chrome = data[0];
    	String pos = data[1];
    	String change1 = data[2];
    	String change2 = data[3];

    	if(sb.length() == 0){
    		sb.append(chrome);
			sb.append(",");
			sb.append(pos);
			sb.append(",");
			sb.append(change1);
			sb.append(",");
			sb.append(change2);
			sb.append(",");
			sb.append(data[4]);
			sb.append(",");
			sb.append(data[5]);
			sb.append(",");
			if(data.length > 7){
				//print donor or recipient
				sb.append(data[7]);
			}
    		
    	}
    	return sb.toString();
    }
}
