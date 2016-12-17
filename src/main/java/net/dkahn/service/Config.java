package net.dkahn.service;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Config {
	public static final String URL_BASE_RESOURCES = "resources";
	
	public static final String DEFAULT_RESSOURCE_THUMB = URL_BASE_RESOURCES+"/Default/Image/";
	public static final String DEFAULT_PRIVATE_FOLDER = DEFAULT_RESSOURCE_THUMB+"repPriv.gif";
	public static final String DEFAULT_PARTAGE_FOLDER = DEFAULT_RESSOURCE_THUMB+"repPar.gif";
	public static final String DEFAULT_PUBLIC_FOLDER = DEFAULT_RESSOURCE_THUMB+"repPub.gif";

	public static final Point DEFAULT_BIGTHUMB_SIZE = new Point(1024,768);

	public static final String BIGTHUMB =  "BigThumb";
	
	// A mettre dans un fichier de configuration
	public static String PATH_SERVER = "/home/dev/DocsOnLine/resources/";
	
	public static String THUMB = "Thumb";
	public static String PATH_THUMB_DEFAULT = PATH_SERVER+"Default/"+"THUMB";
	public static Point DEFAULT_THUMB_SIZE = new Point(100,100);
	public static Map<String, String> TYPE_MINE;
	
	static{
		TYPE_MINE = new HashMap<String, String>();
		TYPE_MINE.put(".jpeg", null);
		TYPE_MINE.put(".jpg", null);
		TYPE_MINE.put(".png", null);
		TYPE_MINE.put(".bmp", null);
		TYPE_MINE.put(".wav", DEFAULT_RESSOURCE_THUMB+"music.gif");
		TYPE_MINE.put(".mp3", DEFAULT_RESSOURCE_THUMB+"music.gif");
		TYPE_MINE.put(".xls", DEFAULT_RESSOURCE_THUMB+"xls.gif");
		TYPE_MINE.put(".xlsx", DEFAULT_RESSOURCE_THUMB+"xls.gif");
		TYPE_MINE.put(".ppt", DEFAULT_RESSOURCE_THUMB+"ppt.gif");
		TYPE_MINE.put(".doc", DEFAULT_RESSOURCE_THUMB+"doc.gif");
		TYPE_MINE.put(".docx", DEFAULT_RESSOURCE_THUMB+"doc.gif");
		TYPE_MINE.put(".zip", DEFAULT_RESSOURCE_THUMB+"zip.gif");
		TYPE_MINE.put(".rar", DEFAULT_RESSOURCE_THUMB+"zip.gif");
		TYPE_MINE.put(".txt", DEFAULT_RESSOURCE_THUMB+"txt.gif");
		TYPE_MINE.put(".avi", DEFAULT_RESSOURCE_THUMB+"video.gif");
		TYPE_MINE.put("default", DEFAULT_RESSOURCE_THUMB+"default.gif");
	}
}
