/*
 * Created on 05.05.2004
 *
 */
package utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class Messages {

	private static final String BUNDLE_NAME = "strings";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	/**
	 * empty constructor :o 
	 */
	private Messages() {
	}


	/**
	 * @param key Key-String
	 * @return Returns a (localized) string for the given key.
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
