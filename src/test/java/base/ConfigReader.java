package base;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties props = new Properties();
    
    //static block
    
    static {
        try (InputStream inputstream = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) /*try(with resources) will 
        automatically close the resources after use */
        {
            if (inputstream == null) 
            {
            	throw new RuntimeException("config.properties file not found");
            }
            props.load(inputstream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(props.getProperty(key));
    }
}
