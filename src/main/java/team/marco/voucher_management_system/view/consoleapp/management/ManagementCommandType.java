package team.marco.voucher_management_system.view.consoleapp.management;

import java.util.Arrays;

import static java.text.MessageFormat.format;

public enum ManagementCommandType {
    BACK(1, "메인 페이지로 돌아가기"),
    CREATE_VOUCHER(2, "쿠폰 생성"),
    VOUCHER_LIST(3, "쿠폰 목록 조회"),
    SEARCH_VOUCHER(4, "쿠폰 검색"),
    CUSTOMER_LIST(5, "전체 회원 조회"),
    BLACKLIST(6, "블랙 리스트 회원 조회");

    private final Integer num;
    private final String description;

    ManagementCommandType(Integer num, String description) {
        this.num = num;
        this.description = description;
    }

    public static ManagementCommandType get(Integer num) {
        return Arrays.stream(ManagementCommandType.values())
                .filter(v -> isEqual(num, v.getNum()))
                .findAny()
                .orElseThrow();
    }

    public int getNum() {
        return num;
    }

    public String getManual() {
        return format("{0}. {1}", num, description);
    }

    private static Boolean isEqual(int targetNum, int serviceNum) {
        return targetNum == serviceNum;
    }
}
