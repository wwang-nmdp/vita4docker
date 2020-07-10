package com.nmdp.vita;




import com.nmdp.vita.command.*;
import com.nmdp.vita.util.FileHelp;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Launcher {
    private static final String HLA ="HLA";
    private static final String HELP = "HELP";
    private static final String WINDOW = "WINDOWS_SLIDER";
    private static final String POS = "-pos";
    private static final String PARAMETER_DIVIDER = "=";
    private static final String HLA_DIVIDER = ",";
    private static final String SHOW_VERSION = "SHOW_VERSION";
    private static final String VERSION = "1.0";

    private static boolean showHelp;
    private static boolean showVersion;
    private static String helpInfo = "Program: ViTA (variant to antigen)\n" +
            "is a Java-based workflow for predicting the immunogenicity of HLA-epitopes\n" +
            "by simulating gene-edited peptide(s) and their binding to patient HLA(s)\n" +
            "Before running this program, make sure you have CDS.db downloaded on your working directory\n" +
            "Usage: java -jar vita.jar <command> [option]\n" +
            "A typical invovation would be:" +
            "   java -jar vita.jar -i /path/to/myVariants.vcf -hla A02:01,B07:02,C04:02 -w -t /path/to/workingDirectory/Tools -o /path/to/output\n" +
            "Commands:\n" +
            "   -i,     Multiple or single variant input file\n" +
            "           input file should be a tab-delimited format, regardless of the file extensions.\n\n" +
            "   -pos,   Position of variants at chromosome coordinate\n" +
            "           simply input the chromosome coordinate. e.g. -pos chr11:5227002\n" +
            "           Be cautious, the position format have to be 1-based\n" +
            "           Please follow the instruction of UCSC genome browser for details of coordinate system: \n" +
            "               http://genome.ucsc.edu/blog/the-ucsc-genome-browser-coordinate-counting-systems/\n" +
            "           It can only take either -i or -pos as input for each process\n\n" +
            "   -hla,   List of HLA alleles you wish to access immunogenicities\n" +
            "           HLA allele should be 4-digit resolution with one upper-case allele type followed by 4 numbers.\n" +
            "               e.g. A01:01\n" +
            "           Multiple allele input are allowed by comma-delimited format\n" +
            "               e.g. A0101,A,2001,A0301,B0702\n" +
            "           Depends on the number of CPUs and disk writing speed, multiple allele input could significantly reduce the predictions\n" +
            "   -t,     Tell the program where are the dependency tools\n" +
            "           Set the path to netChop and netMHCpan\n" +
            "           You might also need to set the PATH for the dependencies:\n" +
            "               export TMPDIR=/path/to/Tools/netMHCpan-3.0/tmp\n" +
            "               export TMPDIR=/path/to/Tools/netchop-3.1/tmp\n" +
            "               export NETCHOP=/path/to/Tools/netchop-3.1/\n" +
            "               export NETMHCpan=/path/to/Tools/netMHCpan-3.0\n\n" +
            "   -w,    \n" +
            "           To activate the slidingWindow function for chopping out all possible variant-containing 8- to 11- mer\n" +
            "           Otherwise the program goes netChop as default\n" +
            "   -o,     Set a directory to store the output\n" +
            "              e.g. /path/to/test/output\n" +
            "           You don't have to make an exact directory for the program unless you need the output be somewhere specifically\n" +
            "           Otherwise it will automatically generate one for you\n\n" +
            "Miscellaneous:\n" +
            "   -v,     Print current version information and exit\n" +
            "   -h,     Print this help and exit\n\n" +
            "Please check out latest update from our Github repo:\n" +
            "https://github.com/wwang-nmdp/ViTA\n\n" +
            "PROBLEMS:\n" +
            "\n" +
            "Leave the comments or report the issues via Github.\n" +
            "   https://github.com/wwang-nmdp/ViTA/issues\n" +
            "Contact wwang@nmdp.org in case of problems.\n" +
            "\n";
    
   
    private static HashMap<String, String> paramMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        
        try{
            getParameters(args);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }
        //check if show help information or not
        if(showHelp){
        	System.out.println(helpInfo);
        	return;
        }

        if(showVersion){
            System.out.println("The version is "+ VERSION);
        }

        try{

            File inputFile = FileHelp.getInputFile();
            System.out.println("Process input file: "+ inputFile.getAbsolutePath());
            Configure.setWorkerId(inputFile.getName().replaceFirst("[.][^.]+$", ""));

            checkIllegalState(paramMap.containsKey(HLA), "The HLA is not set");
            Configure.setHLA(paramMap.get(HLA).split(HLA_DIVIDER));

            FileHelp.makeFolders();
            new SnpEff().run();
            new Filter().run();
            new TranslateToProtein().run();
            if(Configure.windowSlide){
                new WindowSlider().run();
            }else{
                new NetChop().run();
                new Chop().run();
            }
            new NetMHCpan().run();
            new launchAff().run();
        }catch (Exception exp){
            System.out.println(exp.getMessage());
        }

       
    }


    private static void checkIllegalState(boolean goodState, String message){
        if(!goodState){
            throw new IllegalStateException(message);
        }
    }

    private static void getParameters(String[] args) throws Exception {
        Scanner configure = new Scanner(new File(FileHelp.getConfigFile()));
        //Check help
        String helpLine = configure.nextLine();
        System.out.println(helpLine);
        String[] helpData = helpLine.split(PARAMETER_DIVIDER);
        if(helpData[0].equals(HELP)){
            showHelp = Boolean.valueOf(helpData[1]);
        }else {
           throw new Exception("In valid config format, there is no help setting");
        }
        //Check hla
        String hlaLine = configure.nextLine();
        System.out.println(hlaLine);
        String[] hlaData = hlaLine.split(PARAMETER_DIVIDER);
        if(hlaData[0].equals(HLA) && hlaData.length == 2){
            paramMap.put(HLA, hlaData[1]);

        }else {
            throw new Exception("In valid config format, there is no hla setting");
        }
        //Check window slider
        String windowLine = configure.nextLine();
        System.out.println(windowLine);
        String[] windowData = windowLine.split(PARAMETER_DIVIDER);
        if(windowData[0].equals(WINDOW)){
            boolean setWindowSlider = Boolean.valueOf(windowData[1]);
            if(setWindowSlider){
                Configure.setWindowSlide();
            }
        }else {
            throw new Exception("In valid config format, there is no window slider setting");
        }

        //Check version
        String versionLine = configure.nextLine();
        System.out.println(versionLine);
        String[] versionData = versionLine.split(PARAMETER_DIVIDER);
        if(versionData[0].equals(SHOW_VERSION)){
            showVersion = Boolean.valueOf(versionData[1]);
        }else {
            throw new Exception("In valid config format, there is no version setting");
        }

    }

}
