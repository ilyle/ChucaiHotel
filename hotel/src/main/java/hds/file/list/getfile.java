package hds.file.list;

public class getfile {
	
	static 
	{
		try{
		//	System.loadLibrary("HZRecogJNI");
		//	System.loadLibrary("songtool");
			System.loadLibrary("filetool");
			
		}
		catch(Exception e){
			System.out.println("Load apkfile failed");
		}
		
	}
	public static final native void init();
	public static final native int[] fileinfo(String path,String filter);  
	public static final native String get(int recid);
	public static final native boolean isdir(int recid);
	public static final native int lengh(); 
	public static final native int txttoktvlist(String path,String filter,String savemn ); 
	public static final native void songorder(String spath);
	
//	public static final native int[] mpgaudioget(String path ,int count);
	public static final native void usbclear();
	public static final native int usbfile(String filter);
	public static final native String usbget(int recid);
	public static final native String xxxtomfile(String xxfile);
	public static final native int othsongtoktvlist(String othsongmn,String  saveph);
	public static final native void filetoktvlist(String path,String savepath );
	public static final native void setloadmaxbat(int stypeid);
	
	public static final native void songindex(String spath);
	public static final native int moveiesoktvlist(String path,String filter,String savemn ); 
	public static final native void gpiotrl(int gpio_id,int setvalu);
	
//	public static final native void savendxfile(String tmpname,String ndxname);
	//public static final native void songpathchk(String spath);

}
