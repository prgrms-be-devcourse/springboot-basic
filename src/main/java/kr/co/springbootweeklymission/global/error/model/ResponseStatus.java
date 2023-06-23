package kr.co.springbootweeklymission.global.error.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResponseStatus {
    //fail command
    FAIL_NOT_FOUND_COMMAND("해당 명령어를 찾을 수 없습니다."),

    //fail Voucher
    FAIL_WRONG_DISCOUNT("할인전 가격보다 할인 가격이 더 클 수 없습니다."),
    FAIL_NOT_FOUND_VOUCHER("해당 바우처를 찾을 수 없습니다."),
    FAIL_NOT_FOUND_VOUCHER_POLICY("해당 할인 정책을 찾을 수 없습니다."),
    FAIL_IO_NOT_FOUND_VOUCHER("I/O 문제로 바우처가 저장되지 않았습니다."),
    FAIL_IO_NOT_SAVE_VOUCHER("I/O 문제로 바우처가 조회되지 않았습니다."),
    ;

    private String message;
}
