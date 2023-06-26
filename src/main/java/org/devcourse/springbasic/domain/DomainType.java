package org.devcourse.springbasic.domain;

import org.devcourse.springbasic.global.util.DigitChecker;

import java.util.Arrays;

public enum DomainType {
    CUSTOMER(1), VOUCHER(2);

    private int domainNumber;

    DomainType(int domainNumber) {
        this.domainNumber = domainNumber;
    }

    public static DomainType findDomainByInputNum(String input) {

        if (DigitChecker.isDigit(input)) {
            return Arrays.stream(DomainType.values())
                    .filter(domainType -> (domainType.domainNumber == Integer.parseInt(input)))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다."));
        } else {
            return DomainType.valueOf(input.toUpperCase());
        }
    }
}
