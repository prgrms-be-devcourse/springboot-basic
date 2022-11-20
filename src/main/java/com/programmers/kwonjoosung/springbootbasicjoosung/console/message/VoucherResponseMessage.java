package com.programmers.kwonjoosung.springbootbasicjoosung.console.message;

public enum VoucherResponseMessage implements ResponseMessage {
    CREATE_VOUCHER_FAIL("Voucher 생성에 실패했습니다."),
    UPDATE_VOUCHER_SUCCESS("해당 Voucher 정보를 수정했습니다."),
    DELETE_VOUCHER_SUCCESS("해당 Voucher를 삭제했습니다."),
    NOT_FOUND_VOUCHER_ERROR("해당하는 ID를 가진 Voucher가 없습니다."),
    NOT_YET_VOUCHER_SAVE_ERROR("아직 등록된 Voucher가 없습니다."),
    NOT_FOUND_OWNED_CUSTOMER("소유한 고객을 찾지 못했습니다.");

    private final String message;

    VoucherResponseMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
