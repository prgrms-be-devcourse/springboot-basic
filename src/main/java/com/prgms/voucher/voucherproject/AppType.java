package com.prgms.voucher.voucherproject;

import java.util.InputMismatchException;
import java.util.stream.Stream;

public enum AppType {
    VOUCHER,
    CUSTOMER,
    EXIT
    ;

    public static AppType getSelectedAppType(String selectedApp) {
        return Stream.of(AppType.values())
                .filter(appType -> appType.name().equalsIgnoreCase(selectedApp))
                .findAny()
                .orElseThrow(() -> new InputMismatchException("잘못된 실행 명령어입니다."));
    }

}
