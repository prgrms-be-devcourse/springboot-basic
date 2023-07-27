package org.programmers.VoucherManagement.global.response;

public enum SuccessCode {
    /**
     * 1000번 -> 성공
     */
    SUCCESS(200, "1000", "요청에 성공하였습니다."),

    /**
     * 2000번 -> Voucher
     */
    DELETE_VOUCHER_SUCCESS(200, "V2000", "바우처 삭제를 성공하였습니다"),
    UPDATE_VOUCHER_SUCCESS(200, "V2001", "바우처 수정을 성공하였습니다");

    /**
     * 3000번 -> Member
     */

    /**
     * 3000번 -> Wallet
     */


    private final int status;
    private final String code;
    private final String message;

    private SuccessCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}
