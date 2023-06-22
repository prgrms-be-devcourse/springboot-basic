package kr.co.springbootweeklymission.global.error.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResponseStatus {

    //fail Voucher
    FAIL_WRONG_DISCOUNT("할인전 가격보다 할인 가격이 더 클 수 없습니다."),
    FAIL_NOT_FOUND_VOUCHER("해당 바우처를 찾을 수 없습니다."),
    ;

    private String message;
}
