package com.nmdp.vita.command;

import com.nmdp.vita.Configure;
import com.nmdp.vita.ProcessVcf;
import com.nmdp.vita.VCFToProtein;
import com.nmdp.vita.database.DatabaseUtil;
import com.nmdp.vita.util.FileHelp;

import java.io.File;

public class TranslateToProtein {
    public void run(){
        DatabaseUtil.connectDatabase();
        ProcessVcf pv = new ProcessVcf();
        pv.run(new File(FileHelp.getMissense()));
        System.out.println("process variants finished");
        VCFToProtein go = new VCFToProtein();
        go.run(pv.geneList, Configure.getWorkID());
        System.out.println("generate output finished");
        DatabaseUtil.cleanUp();
    }
}
