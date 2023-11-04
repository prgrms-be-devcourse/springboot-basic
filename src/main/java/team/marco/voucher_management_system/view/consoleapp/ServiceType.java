package team.marco.voucher_management_system.view.consoleapp;

import java.util.Arrays;

import static java.text.MessageFormat.format;

public enum ServiceType {
    EXIT(1, "프로그램을 종료합니다."),
    MANAGEMENT(2, "관리자 서비스로 이동합니다."),
    WALLET(3, "회원 지갑 서비스로 이동합니다.");

    private static final String SERVICE_NOT_EXIST = "해당 서비스가 존재하지 않습니다.";

    private final int num;
    private final String description;

    ServiceType(int num, String description) {
        this.num = num;
        this.description = description;
    }

    static ServiceType get(int num) {
        return Arrays.stream(ServiceType.values())
                .filter(v -> isEqual(num, v.getNum()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(format(SERVICE_NOT_EXIST)));
    }

    private static boolean isEqual(int targetNum, int serviceNum) {
        return targetNum == serviceNum;
    }

    public int getNum() {
        return num;
    }

    public String getManual() {
        return num + ". " + description;
    }
}
