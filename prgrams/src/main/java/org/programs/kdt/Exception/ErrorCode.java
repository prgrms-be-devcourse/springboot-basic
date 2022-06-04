package org.programs.kdt.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    //UUID
    INVALID_UUID(400,"COM-001", "잘못된 UUID입니다."),
    //ENUM
    INVALID_ENUM_TYPE(400, "ENUM-001", "지원하지 않는 Enum타입 입니다."),
    INVALID_COMMAND_TYPE(400,"ENUM-002", "지원하지 않는 명령어 입니다."),
    //CUSTOMER
    NOT_FOUND_EMAIL(400,"CUSTOMER-001", "없는 Email입니다."),
    NOT_FOUND_CUSTOMER_ID(400, "CUSTOMER-002", "없는 유저 ID입니다."),
    INVALID_CUSTOMER_NAME(400, "CUSTOMER-003" , "유효하지 않은 유저명입니다."),
    INVALID_CUSTOMER_EMAIL(400, "CUSTOMER-004", "유효하지 않은 유저 이메일입니다"),

    DUPLICATION_CUSTOMER_ID(400, "CUSTOMER-003", "이미 존재하는 유저 ID입니다."),
    DUPLICATION_CUSTOMER_EMAIL(400, "CUSTOMER-004", "이미 존재하는 유저 EMAIL입니다."),
    //VOUCHER
    NOT_FOUND_VOUCHER_ID(400, "VOUCHER-001", "없는 바우처 ID입니다."),
    INVALID_VOUCHER_VALUE(400,"VOUCHER-002", "유효하지 않은 Vucher Value입니다."),
    DUPLICATION_VOUCHER_ID(400, "VOUCHER-003", "중복된 VOUCHER ID 입니다."),

    //WALLET
    DUPLICATION_WALLET_ID(400,"WALLET-001" ,"중복된 ID입니다" );

    private final int status;
    private final String codeName;
    private final String message;

}
