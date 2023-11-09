package org.programmers.springorder.consts;

public class Message {

    public static final String CHOOSING_MEDIA = """
            어떤 방식으로 구동하시겠습니까?
            1. web 방식
            2. console 방식
            """;

    private Message(){}
    public static final String MENU_SELECT_MESSAGE = """
            === Voucher Program ===
            1. 프로그램을 종료합니다.
            2. 바우처를 생성합니다.
            3. 모든 바우처 리스트를 반환합니다.
            4. blacklist를 반환합니다.
            5. 바우처를 특정 고객에 등록합니다.
            6. 특정 회원이 가진 바우처 리스트를 조회합니다.
            7. 바우처를 삭제합니다.
            8. 선택하신 바우처를 가진 고객을 찾습니다.
            """;

    public static final String VOUCHER_SELECT_MESSAGE = """
            Q. 원하는 바우처 타입을 입력해주세요.
            1. Fixed Amount Voucher
            2. Percent Discount Voucher
            """;

    public static final String INPUT_FIXED_DISCOUNT_VALUE_MESSAGE = "Q. 할인 금액(₩)을 입력해주세요.";
    public static final String INPUT_PERCENT_DISCOUNT_VALUE_MESSAGE = "Q. 할인율(%)을 입력해주세요.";
    public static final String VOUCHER_REGISTERED = "바우처가 등록되었습니다.";
    public static final String EXIT_PROGRAM_MESSAGE = "프로그램이 종료되었습니다.";
    public static final String INPUT_USER_ID = "사용자 아이디를 입력해주세요";
    public static final String INPUT_VOUCHER_ID = "등록하실 voucher id를 입력해주세요";
    public static final String VOUCHER_ALLOCATED = "성공적으로 voucher가 해당 유저에 등록되었습니다.";
    public static final String VOUCHER_DELETE_SUCCESS = "성공적으로 voucher를 삭제했습니다.";
}
