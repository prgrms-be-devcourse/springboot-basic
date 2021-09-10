package org.prgrms.kdt.kdtspringorder.common.exception;

import lombok.Getter;
import org.prgrms.kdt.kdtspringorder.common.enums.ErrorInfo;

@Getter
public class BusinessException extends RuntimeException{

    private ErrorInfo errorInfo;

    public BusinessException(ErrorInfo errorInfo) {
        super(errorInfo.getMessage());
        this.errorInfo = errorInfo;
    }

}
