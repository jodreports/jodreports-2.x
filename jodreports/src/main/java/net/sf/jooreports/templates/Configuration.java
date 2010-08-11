package net.sf.jooreports.templates;

import java.util.Map;

public class Configuration {

	public static final String SETTING_CHECK_IMAGE_EXIST = "check_image_exist";
	public static final String SETTING_PROCESS_JOOSCRIPT_ONLY = "process_jooscript_only";

	public static Boolean getConfiguration(String name, Map configurations){
		Boolean value = null;
		Object configuration = configurations.get(name);
		if(configuration!=null & configuration instanceof Boolean){
			value = (Boolean) configuration;
		}
		return value;
	}
	
}
