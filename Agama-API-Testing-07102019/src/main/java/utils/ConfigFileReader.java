package utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigFileReader {

	public static Properties configPropertyFileReader(String filename) throws Exception {

		filename = filename.trim();

		InputStream reader = new FileInputStream(filename);

		Properties properties = new Properties();

		properties.load(reader);

		return properties;
	}
}
