package org.prgrms.kdt.kdtspringorder.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorInfo {

    VOUCHER_NOT_FOUND("VOUCHER_NOT_FOUND", "Can not find a voucher"),
    CUSTOMER_NOT_FOUND("CUSTOMER_NOT_FOUND", "Can not find a customer"),
    VOUCHER_TYPE_NOT_FOUND("VOUCHER_TYPE_NOT_FOUND", "Can not find a voucher type");

    private String code;
    private String message;

}
