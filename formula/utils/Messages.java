/*
 * Created on 05.05.2004
 *
 */
package utils;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @since 05.05.2004
 *
 */
public class Messages {

	private static final String BUNDLE_NAME = "strings"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	/**
	 * 
	 */
	private Messages() {

		// TODO Auto-generated constructor stub
	}
	/**
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		// TODO Auto-generated method stub
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
