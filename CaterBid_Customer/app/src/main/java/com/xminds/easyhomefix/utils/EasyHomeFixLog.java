package com.xminds.easyhomefix.utils;

/**
 * 
 * @author sujith 
 * <a> href="sujith@xminds.in">mail to </a> 
 *
 */
public class EasyHomeFixLog {

	private static int LOG_LEVEL = 1;

	public static boolean IS_VERBOSE_ENABLED = 1 >= LOG_LEVEL;

	public static boolean IS_DEBUG_ENABLED = 2 >= LOG_LEVEL;

	public static boolean IS_INFO_ENABLED = 3 >= LOG_LEVEL;

	public static boolean IS_WARN_ENABLED = 4 >= LOG_LEVEL;

	public static boolean IS_ERROR_ENABLED = 5 >= LOG_LEVEL;

}
