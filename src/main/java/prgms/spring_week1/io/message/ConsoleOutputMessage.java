package prgms.spring_week1.io.message;

public final class ConsoleOutputMessage {
    private ConsoleOutputMessage() {
    }

    public static final String MENU_LIST_MESSAGE =
            """
                    === Voucher Program ===
                    Type exit to exit the program.
                    Type create to create a new voucher.
                    Type list to list all vouchers.
                    Type black to list all blackList
                    """;

    public static final String TYPE_SELECT_MESSAGE =
            """
                    === Voucher Select ===
                    Fixed Amount Voucher 을 생성하려면 띄어쓰기 없이 입력하세요. -> Fixed       
                    Percent Discount Voucher 을 생성하려면 띄어쓰기 없이 입력하세요. -> Percent
                    """;

    public static final String NO_VOUCHER_LIST_MESSAGE = "조회된 바우처 리스트가 없습니다.";

    public static final String INVALID_MENU_MESSAGE = "해당 메뉴가 존재하지 않습니다.";

    public static final String INPUT_DISCOUNT_AMOUNT_MESSAGE = "할인 정보를 입력하세요 : ";

    public static final String INVALID_INPUT_DISCOUNT_MESSAGE = "올바르지 않은 입력값입니다.";

    public static final String COMPLETE_VOUCHER_INSERT_MESSAGE = "상품권 등록이 완료되었습니다.";

    public static final String EMPTY_BLACK_LIST_MESSAGE = "블랙리스트 목록이 없습니다.";
}
