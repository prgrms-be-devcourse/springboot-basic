package org.programmers.springbootbasic.console;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SimpleErrorMessageMapper {

    private static final Map<Class<? extends Exception>, ErrorData> simpleErrorMessage = new ConcurrentHashMap<>();

    private static final ErrorData COMMON_ERROR_MESSAGE =
            new ErrorData("시스템 내부 오류", "최대한 빨리 복구하겠습니다.");

    @PostConstruct
    void initErrorData() {
        simpleErrorMessage.put(IllegalArgumentException.class,
                new ErrorData("잘못된 입력값", "올바른 입력값으로 다시 시도해 주세요."));
    }

    public ErrorData toErrorData(Exception e) {
        return simpleErrorMessage.getOrDefault(e.getClass(), COMMON_ERROR_MESSAGE);
    }

    public record ErrorData(String name, String message) {
    }
}
