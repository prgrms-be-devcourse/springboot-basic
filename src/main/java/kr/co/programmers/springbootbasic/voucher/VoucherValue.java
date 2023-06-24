package kr.co.programmers.springbootbasic.voucher;

public final class VoucherValue {
    public static final String NO_VALID_FIXED_AMOUNT = """
            고정 금액 바우처의 생성 금액이 잘못 됐습니다.
                                
            """;
    public static final String NO_VALID_PERCENT_AMOUNT = """
            고정 할인률 바우처의 생성 할인률이 잘못 됐습니다.
                                
            """;
    public static final String NO_VALID_DISCOUNT_PRICE = """
            할인 적용 금액이 {0}원으로 잘못 됐습니다.

            """;
    public static final String NO_SAVED_VOUCHER = """
            생성된 바우처가 없습니다.
                        
            """;
    public static final String NO_VALID_VOUCHER = """
            올바르지 않은 바우처 선택입니다.
                                        
            """;
    public static final long ZERO = 0;
    public static final long ONE_HUNDRED = 100;

    private VoucherValue() {
    }
}
