/*
 * Created on 05.05.2004
 *
 */
package utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Tool class for all message strings. There has to be a strings_xx.properties 
 * in the main directory with all strings (see strings.properties).
 * xx is the language code, e.g. de or jp.
 * 
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public final class Messages {

	private static final String BUNDLE_NAME = "strings";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	/**
	 * Hidden constructor for tool class.
	 */
	private Messages() { }


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
