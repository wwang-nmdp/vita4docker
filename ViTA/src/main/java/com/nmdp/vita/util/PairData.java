package com.nmdp.vita.util;

/**
 * Created by wwang on 10/16/16.
 */
public class PairData {
    public String id;
    public  String seq1;
    public String cle1;

    public String seq2;
    public String cle2;

    public PairData(String id, String seq1, String cle1, String seq2, String cle2){
        this.id = id.substring(3);
        this.seq1 = seq1;
        this.seq2 = seq2;
        this.cle1 = cle1;
        this.cle2 = cle2;
    }
}
