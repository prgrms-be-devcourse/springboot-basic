package team.marco.voucher_management_system.view.console.management;

import java.util.Arrays;

public enum ManagementCommandType {
    CREATE_VOUCHER(1, "쿠폰 생성"),
    VOUCHER_LIST(2, "쿠폰 목록 조회"),
    SEARCH_VOUCHER(3, "쿠폰 검색"),
    CUSTOMER_LIST(4, "전체 회원 조회"),
    BLACKLIST(5, "블랙 리스트 회원 조회"),
    BACK(6, "메인 페이지로 돌아가기");

    private int num;
    private String description;

    ManagementCommandType(int num, String description) {
        this.num = num;
        this.description = description;
    }

    public static ManagementCommandType get(int num) {
        return Arrays.stream(ManagementCommandType.values())
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
