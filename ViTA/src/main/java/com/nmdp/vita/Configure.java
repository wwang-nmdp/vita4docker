package com.nmdp.vita;

public class Configure {
	public static String input = null;
	private static String[] hla;
	//public static String tool = "/app/Tools/";
	public static String tool = "/home/wwang/vita/Tools";
	public static boolean windowSlide = false;
	public static String chrome = null;
	public static String location = null;
	private static String workerId;
    
    public static String removeComma(String s){
    	return s.replace(":", "");
    }


    
    public static void setWindowSlide(){
    	windowSlide = true;
    }

	public static String getWorkID() {
    	return workerId;
	}

	public static void setWorkerId(String id){
    	workerId = id;
	}

	public static void setHLA(String[] hlaList){
    	hla = hlaList;
	}

	public static String[] getHLA(){
    	return hla;
	}
}
