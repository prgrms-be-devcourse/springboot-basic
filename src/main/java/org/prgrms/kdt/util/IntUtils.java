package org.prgrms.kdt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntUtils {
    private final static Logger logger = LoggerFactory.getLogger(IntUtils.class);

    private IntUtils() {
    }

    public static boolean isNumber(String inputString) {
        return inputString.chars()
                .filter(Character::isDigit)
                .count() == inputString.length();
    }

    public static int toInt(String inputString) {
        if (!isNumber(inputString)) {
            logger.error("inputString -> {}", inputString);
            throw new IllegalArgumentException("WRONG : Please input right Number");
        }
        return Integer.parseInt(inputString);
    }
}
