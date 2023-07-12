package kr.co.programmers.springbootbasic.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.UUID;

public class ApplicationUtils {
    private static final String NO_VALID_NUMBER_INPUT_LOG = """
            사용자가 숫자가 아닌 {}를 입력했습니다.
                        
            """;

    private static final Logger logger = LoggerFactory.getLogger(ApplicationUtils.class);

    private ApplicationUtils() {
    }

    public static int parseStringToInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            logger.warn(NO_VALID_NUMBER_INPUT_LOG, input);
            throw new NumberFormatException("올바른 숫자입력이 아닙니다.\n\n");
        }
    }

    public static UUID createUUID() {
        return UUID.randomUUID();
    }

    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);

        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
