package net.sf.jooreports.templates;

import java.util.Map;

public class Configuration {

	public static final String SETTING_CHECK_IMAGE_EXIST = "check_image_exist";
	public static final String SETTING_PROCESS_JOOSCRIPT_ONLY = "process_jooscript_only";

	public static boolean getConfiguration(String name, Map configurations){
		boolean value = true;
		Object configuration = configurations.get(name);
		if(configuration!=null & configuration instanceof Boolean){
			value = ((Boolean) configuration).booleanValue();
		}
		return value;
	}
	
}
