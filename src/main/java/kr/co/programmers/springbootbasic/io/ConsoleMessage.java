package kr.co.programmers.springbootbasic.io;

public final class ConsoleMessage {
    public static final String VOUCHER_PROGRAM_MENU = """
            === Voucher Program ===
            Type 1 to exit the program.
            Type 2 to create a new voucher.
            Type 3 to list all vouchers.
            Type 4 to list all black customers.
                        
            Type here :\s""";
    public static final String VOUCHER_CREATION_MENU = """
                        
            === Select Voucher Type to create ===
            Type 1 to create FixedAmountVoucher.
            Type 2 to create PercentAmountVoucher.
                        
            Type here :\s""";
    public static final String PERCENT_AMOUNT_ENTER_MESSAGE = """
            할인률을 입력해주세요 :\s""";
    public static final String FIXED_AMOUNT_ENTER_MESSAGE = """
            할인 가격을 입력해주세요 :\s""";
    public static final String EXIT_MESSAGE = """
                        
            프로그램을 종료합니다.
            """;
    public static final String EMPTY_LIST_MESSAGE = """
            리스트가 비어있습니다.
            """;

    private ConsoleMessage() {
    }
}
