package com.prgrms.vouchermanagement.core.voucher.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    private static final Logger logger = LoggerFactory.getLogger("PropertiesUtil.class");
    public static final String FILE_REPOSITORY_KEY = "voucher.repository.path";
    private static final String PROPERTIES_FILE_PATH = "application.properties";
    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
