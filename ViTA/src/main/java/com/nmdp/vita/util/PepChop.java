package com.nmdp.vita.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Chop the protein to peptide by using the predicted cleavage sites. The .pep file then used for downstream HLA affinity prediction. Others info about the peptide were stored in metadata sets.
 */
public class PepChop {
    ArrayList<Integer> location = new ArrayList<>();
    ArrayList<String>  headerData = new ArrayList<>();
    ArrayList<cleavagedData> dataList = new ArrayList<>();
    Scanner sn;
    PrintWriter pw;
    PrintWriter chopMetaWriter;
    public void run(File meta, File input) throws FileNotFoundException {
        String [] list = input.getName().split("\\.");
        String fileName = list[0];
        if(fileName.isEmpty()){
            return;
        }
        File chopFile = new File(FileHelp.getChopFile());
        pw = new PrintWriter(chopFile);
        
        File metaData = new File(FileHelp.getChopMetaData());
        chopMetaWriter = new PrintWriter(metaData);
       
        loadMetaData(meta);

        sn = new Scanner(input);
        findData();
        generateOutput();

        sn.close();
        pw.close();
        chopMetaWriter.close();

        dataList.clear();
        location.clear();
        headerData.clear();
    }

    private void generateOutput() {
        if(location.size()== dataList.size()){
            for(int i = 0; i < location.size(); i++){
                processOneLocation(location.get(i), dataList.get(i), i);
            }
        }
    }

    private void processOneLocation(Integer index, cleavagedData data, int i) {

        List<String>  result = clevage(index, data.seq, data.cle);

        for(String fragment : result){
            pw.println(fragment);
            chopMetaWriter.println(headerData.get(i));
        }
    }


    private List<String> clevage(Integer index, String seq1, String cle1) {
        List<String> result = new ArrayList<>();
        List<Integer> maker = new ArrayList<>();
        for(int i = 0 ; i < cle1.length(); i++){
            if(cle1.charAt(i) == 'S'){
                maker.add(i);
            }
        }
        for(int i = 0; i< maker.size(); i++){
            for(int j = i+1; j< maker.size(); j++){
                int size = maker.get(j) - maker.get(i) + 1;
                if(size >= 8 && size <= 11){
                    if(index >= maker.get(i) && index <= maker.get(j)){
                        result.add(seq1.substring(maker.get(i), maker.get(j)+1));
                    }
                }else {
                    if(size > 11){
                        break;
                    }
                }
            }
        }
        return result;
    }

    private void findData() {
        String seq = null;
        String cle = null;
        while (sn.hasNext()){
            String line = sn.nextLine();
            if(line.length() == 0){
                continue;
            }
            char digit = line.charAt(0);
            if(Character.isDigit(digit) && line.contains("chr")){

                seq = sn.nextLine();
                cle = sn.nextLine();
                    dataList.add(new cleavagedData(seq, cle));
                }
            }

    }

    private void loadMetaData(File meta) {
        Scanner sn = null;
        try {
            sn = new Scanner(meta);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(sn != null){
            while(sn.hasNext()){
                String line = sn.nextLine();
                headerData.add(line);
                line = sn.nextLine();
                location.add(Integer.parseInt(line));
            }
            sn.close();
        }

    }
}
