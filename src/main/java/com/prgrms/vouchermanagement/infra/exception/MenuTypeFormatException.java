package com.prgrms.vouchermanagement.infra.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuTypeFormatException extends IllegalArgumentException {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MenuTypeFormatException() {
        super("[exit, create, list] 중 하나를 입력해야합니다.");
        String msg = "[exit, create, list] 중 하나를 입력해야합니다.";
        logger.warn(msg);
    }
}
