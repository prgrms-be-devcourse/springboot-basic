package kr.co.programmers.springbootbasic.io;

public final class ConsoleMessage {
    public static final String VOUCHER_PROGRAM_MENU = """
            === Voucher Program ===
            Type 1 to exit the program.
            Type 2 to use voucher service.
            Type 3 to use customer service.
            Type 4 to use wallet service.
                        
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
    public static final String CUSTOMER_FIND_MENU = """
                        
            === Select Customer Find Service ===
            Type 1 to find by customer ID.
            Type 2 to find by voucher ID.
            Type 3 to find all customers.
            Type 4 to find all black customers.
                        
            Type here :\s""";

    public static final String NO_RESULT_MESSAGE = """
            결과가 없습니다.
                        
            """;
    public static final String CUSTOMER_STATUS_MESSAGE = """
                        
            === Select Customer Status ===
            Type 1 to set White Customer.
            Type 2 to set Black Customer.
                        
            Type here :\s""";
    public static final String CUSTOMER_UUID_MESSAGE = """
                        
            === Type Customer UUID ===
            Type here :\s""";
    public static final String WALLET_SERVICE_MENU = """
                                    
            === Select wallet service ===
            Type 1 to save voucher in wallet.
            Type 2 to find all vouchers in wallet.
                        
            Type here :\s""";
    public static final String VOUCHER_UUID_MESSAGE = """
                        
            === Type voucher UUID ===
            Type here :\s""";
    public static final String WALLET_UUID_MESSAGE = """
                        
            === Type wallet UUID ===
            Type here :\s""";

    public static final String CUSTOMER_SERVICE_MENU = """
                        
            === Select customer service ===
            Type 1 to create customer.
            Type 2 to find customer.
            Type 3 to update customer.
            Type 4 to delete customer.
                        
            Type here :\s""";
    public static final String VOUCHER_SERVICE_MENU = """
                        
            === Select voucher service ===
            Type 1 to create voucher.
            Type 2 to find all vouchers.
                        
            Type here :\s""";
    public static final String CUSTOMER_NAME_MESSAGE = """
                        
            === Type customer name ===
            Type here :\s""";

    private ConsoleMessage() {
    }
}
