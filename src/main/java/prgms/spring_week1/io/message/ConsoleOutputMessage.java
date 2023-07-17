package prgms.spring_week1.io.message;

public final class ConsoleOutputMessage {
    private ConsoleOutputMessage() {
    }

    public static final String MENU_LIST_MESSAGE =
            """
                    === Voucher Program ===
                    Type exit to exit the program.
                    Type voucher to manage voucher.
                    Type customer to manage customer.                
                    """;

    public static final String VOUCHER_MENU_LIST_MESSAGE =
            """
                    === Voucher Program ===
                    Type INSERT to create new voucher.
                    Type FIND_ALL to find all voucher.
                    Type FIND_BY_TYPE to find a voucher by type.
                    Type DELETE_ALL to delete all voucher.                
                    """;

    public static final String CUSTOMER_MENU_LIST_MESSAGE =
            """
                    === Voucher Program ===
                    Type insert to create new customer.
                    Type findAll to find all customer.
                    Type findbyemail to find a voucher by type.
                    Type update to update customer information.
                    Type deleteall to delete all voucher.                
                    """;


    public static final String TYPE_SELECT_MESSAGE =
            """
                    === Voucher Select ===
                    Fixed Amount Voucher 을 생성하려면 띄어쓰기 없이 입력하세요. -> Fixed       
                    Percent Discount Voucher 을 생성하려면 띄어쓰기 없이 입력하세요. -> Percent
                    """;

    public static final String FIND_TYPE_SELECT_MESSAGE =
            """
                    === Voucher Select ===
                    Fixed Amount Voucher 를 바우처를 찾으려면 띄어쓰기 없이 입력하세요. -> Fixed       
                    Percent Discount Voucher 를 바우처를 찾으려면 띄어쓰기 없이 입력하세요. -> Percent
                    """;

    public static final String NO_VOUCHER_LIST_MESSAGE = "조회된 바우처 리스트가 없습니다.";

    public static final String INVALID_MENU_MESSAGE = "해당 메뉴가 존재하지 않습니다.";

    public static final String INPUT_DISCOUNT_AMOUNT_MESSAGE = """
                    할인 정보를 입력하세요
                    고정 금액 할인(Fixed) 바우처는 0보다 큰 수만 입력할 수 있으며,
                    할인율(Percent) 바우처는 1 ~ 100 까지의 수만 입력할 수 있습니다.
                    """;

    public static final String INVALID_INPUT_DISCOUNT_MESSAGE = "올바르지 않은 입력값입니다.";

    public static final String COMPLETE_VOUCHER_INSERT_MESSAGE = "상품권 등록이 완료되었습니다.";

    public static final String EMPTY_BLACK_LIST_MESSAGE = "블랙리스트 목록이 없습니다.";

    public static final String INPUT_EMAIL_MESSAGE = "이메일을 입력해주세요.";

    public static final String INPUT_NAME_AND_EMAIL_MESSAGE = "이름과 이메일을 한칸 띄고 입력해주세요";

    public static final String INPUT_BEFORE_EMAIL_AND_AFTER_EMAIL_MESSAGE = "현재 이메일과 변경할 이메일을 한 칸 띄고 입력해주세요";
}
