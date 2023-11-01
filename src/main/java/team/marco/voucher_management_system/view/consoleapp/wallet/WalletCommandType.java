package team.marco.voucher_management_system.view.consoleapp.wallet;

import java.util.Arrays;

public enum WalletCommandType {
    BACK(1, "메인 페이지로 돌아가기"),
    REGISTER(2, "쿠폰 등록"),
    LIST(3, "나의 쿠폰 목록 조회"),
    REMOVE(4, "쿠폰 삭제");

    private final int num;
    private final String description;

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
