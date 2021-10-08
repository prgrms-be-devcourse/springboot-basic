package org.prgrms.orderApp.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUNT_VOUCHER_ID(451, "Voucher Id 를 찾을 수가 없습니다. "),
    FAIL_DELETE_BY_ID(452, "요청하신 삭제처리를 실패했습니다."),
    PERCENT_VOUCHER_OVER_100(453, "Percent 쿠폰은 100을 넘을 수 없습니다."),
    FAIL_SAVE_VOUCHER(454, "쿠폰 저장을 실패하였습니다.");

    private final int Status;
    private final String errorMessage;
}
