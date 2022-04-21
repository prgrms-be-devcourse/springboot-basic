package org.prgms.validator;

import org.springframework.dao.DataIntegrityViolationException;

import java.text.MessageFormat;

public class RepositoryValidator {
    // 정적 메서드만을 사용함
    private RepositoryValidator() {
    }

    ;

    public static void affectedRowMustBeOne(int affectedRow) {
        if (affectedRow != 1) {
            throw new DataIntegrityViolationException(MessageFormat.format("데이터 삽입 실패, 유효 row 갯수 : {0}", affectedRow));
        }
    }
}
