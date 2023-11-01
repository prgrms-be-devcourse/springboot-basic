package team.marco.voucher_management_system.view.consoleapp;

import java.util.Arrays;

public enum ServiceType {
    EXIT(1, "프로그램 종료"),
    MANAGEMENT(2, "관리자 서비스"),
    WALLET(3, "회원 지갑 서비스");

    private final int num;
    private final String description;

    ServiceType(int num, String description) {
        this.num = num;
        this.description = description;
    }

    static ServiceType get(int num) {
        return Arrays.stream(ServiceType.values())
                .filter(v -> v.getNum() == num)
                .findAny()
                .orElseThrow();
    }

    public int getNum() {
        return num;
    }

    public String getInfo() {
        return num + ". " + description;
    }
}
