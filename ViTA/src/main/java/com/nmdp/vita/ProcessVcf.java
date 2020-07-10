package com.nmdp.vita;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wwang on 9/5/16.
 */
public class ProcessVcf {
    public List<Transcript> geneList = new ArrayList<>();
    public List<String> changedYgeneList = new ArrayList<>();
  

    public void run(File input){
        Scanner sn = null;
        try {
            sn = new Scanner(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (sn.hasNext()){
            processLine(sn.nextLine());
        }
    }

    private void processLine(String s) {
        String[] trans = s.split(";ANN");
        //process the first transcript
        String[] detail = trans[1].split("\\|");
        //Process the chrome information
        String[] basic = trans[0].split("\\t");
        String chromeNumber = basic[0];
        int chrome = basic[0].charAt(0) - '0';
        if(chrome == 40){
            //convert X to 24
            chrome = 24;
        } else if(chrome ==41) {
            //convert Y to 25
            chrome = 25;
        }else if (chrome == 29){
        	//convert M to 26
        	chrome = 26;
        }else {
            chrome = Integer.parseInt(chromeNumber);
        }

        int pos = Integer.parseInt(basic[1]);
        String transcript = detail[6];
        Transcript gene;
        gene = new Transcript(transcript);
        gene.setChrome(chrome);
        gene.setChromePos(pos);
        geneList.add(gene);
        //gene.setNT(basic[3], basic[4]);
        gene.setGeneID(detail[3]);


        int DNAchange = 0;
        try{
            DNAchange = getNumber(detail[9]);
        }catch (Exception e){
            System.out.println("data is not number "+ detail[9]);
        }
        gene.addPos(DNAchange, gene.getAlt());
        
        Pattern pattern = Pattern.compile("ENST(.)*p.");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
        	processTrans(matcher.group(), gene);
        	//Only process the first ENST
        	break;
        }

    }

    private void processTrans(String tran, Transcript gene) {
        if(tran.length()<4 || !tran.startsWith("ENST")){
            return;
        }
        String[] detail = tran.split("\\|");
            String transcript = detail[0];
            int DNAchange = 0;
            try{
                DNAchange = getNumber(detail[3]);
            }catch (Exception e){
                System.out.println("data is not number "+ detail[3]);
            }

            if(transcript.equals(gene.transcriptID)){
                gene.addPos(DNAchange, gene.getAlt());
            }else{
                //create new transcript
                Transcript newGene = new Transcript(transcript);
                newGene.setInfo(gene);
                newGene.addPos(DNAchange, gene.getAlt());
                geneList.add(newGene);
            }

    }

    private int getNumber(String s){
        int start = 0;
        while(!Character.isDigit(s.charAt(start))){
            start++;
        }
        int end = start;
        while(Character.isDigit(s.charAt(end))){
            end ++;
        }
        return Integer.parseInt(s.substring(start, end));
    }
}
