package team.marco.voucher_management_system.view.consoleapp.wallet;

import java.util.Arrays;

public enum WalletCommandType {
    REGISTER(1, "쿠폰 등록"),
    LIST(2, "나의 쿠폰 목록 조회"),
    REMOVE(3, "쿠폰 삭제"),
    BACK(4, "메인 페이지로 돌아가기");

    private int num;
    private String description;

    WalletCommandType(int num, String description) {
        this.num = num;
        this.description = description;
    }

    public static WalletCommandType get(int num) {
        return Arrays.stream(WalletCommandType.values())
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
