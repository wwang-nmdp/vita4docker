package com.nmdp.vita;

import com.nmdp.vita.util.FileHelp;
import com.nmdp.vita.util.PepChop;

import java.io.File;
import java.io.FileNotFoundException;

public class debugLuncher {
    public static void main(String[] args) throws Exception {
        PepChop cl = new PepChop();
        ProcessAff pf = new ProcessAff();
        try {
            pf.run(new File(args[0]), new File(args[1]), new File("output.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
