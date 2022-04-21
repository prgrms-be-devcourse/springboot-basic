package org.prgms.validator;

import java.util.Arrays;
import java.util.Objects;

public class DomainValidators {
    // 정적 메서드만 이용함!
    private DomainValidators() {
    }

    ;

    public static void notNullAndEmptyCheck(Object... args) {
        Arrays.stream(args).forEach(obj -> {
            Objects.requireNonNull(obj);
            if (obj instanceof String && ((String) obj).isBlank()) {
                throw new IllegalArgumentException("문자열이 비어있을 수 없습니다.");
            }
        });
    }
}
