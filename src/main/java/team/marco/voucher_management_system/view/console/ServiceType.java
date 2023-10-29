package team.marco.voucher_management_system.view.console;

import java.util.Arrays;

public enum ServiceType {
    MANAGEMENT(1, "관리자 서비스"),
    WALLET(2, "회원 지갑 서비스"),
    EXIT(3, "프로그램 종료");

    private int num;
    private String description;

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
