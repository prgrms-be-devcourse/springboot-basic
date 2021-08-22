package org.prgrms.kdt.engine.io;

public interface Output {
    String HELP = """
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.""";

    String INPUT = "명령어를 입력하세요.";

    String INPUT_ERROR = "정상적인 입력이 아닙니다";

    String INPUT_VOUCHER_NUM = """
            원하는 종류의 voucher 번호를 입력하세요.
            1. FixedAmountVoucher
            2. PercentDiscountVoucher""";

    String INPUT_DISCOUNT = "원하는 할인금액 또는 할인율을 입력해주세요.";

    String CREATE_VOUCHER_ERROR = "voucher를 정상적으로 생성하지 못했습니다.";

    String NO_VOUCHER = "voucher가 없습니다.";

    void printConsole(String output);
}
