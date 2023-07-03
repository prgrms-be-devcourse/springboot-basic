package org.promgrammers.springbootbasic.view;

public class Console {

    private final Input input;
    private final Output output;

    private static final String NUMBER_REGEX = "-?\\d+";
    private static final String[] COMMAND_GUIDE_MESSAGES = {
            "=== Voucher Program ===",
            "Type '1' => 프로그램 종료.",
            "Type '2' => 바우처 메뉴",
            "Type '3' => 고객 메뉴.",
            "Type '4' => 지갑 메뉴."
    };

    private static final String[] VOUCHER_TYPE_GUIDE_MESSAGES = {
            "=== 바우처 타입을 골라주세요. ===",
            "Type '1' => 'Fixed' 바우처.",
            "Type '2' => 'Percent' 바우처."
    };

    private static final String[] CUSTOMER_FIND_TYPE_MESSAGES = {
            "=== 찾을 고객 유형을 골라주세요. ===",
            "Type '1' => 고객ID.",
            "Type '2' => 고객 이름.",
            "Type '3' => 블랙 리스트."
    };

    private static final String[] CUSTOMER_DELETE_TYPE_MESSAGES = {
            "=== 삭제할 고객 유형을 골라주세요. ===",
            "Type '1' => 고객ID로 삭제.",
            "Type '2' => 고객 전체 삭제."
    };

    private static final String[] VOUCHER_DELETE_TYPE_MESSAGES = {
            "=== 삭제할 바우처 유형을 골라주세요. ===",
            "Type '1' => 바우처ID로 삭제.",
            "Type '2' => 바우처 전체 삭제."
    };

    private static final String[] VOUCHER_MENU_GUIDE_MESSAGES = {
            "=== 사용하실 서비스를 골라주세요. ===",
            "Type '1' => 바우처 생성",
            "Type '2' => 바우처 전체 조회",
            "Type '3' => 바우처 ID로 조회",
            "Type '4' => 바우처 삭제",
            "Type '5' => 내 바우처 수정"
    };

    private static final String[] CUSTOMER_MENU_GUIDE_MESSAGES = {
            "=== 사용하실 서비스를 골라주세요. ===",
            "Type '1' => 고객 생성",
            "Type '2' => 고객 전체 조회",
            "Type '3' => 타입으로 고객 조회",
            "Type '4' => 고객 삭제",
            "Type '5' => 고객 이름 수정"
    };

    private static final String[] WALLET_MENU_GUIDE_MESSAGES = {
            "=== 사용하실 서비스를 골라주세요. ===",
            "Type '1' => 지갑 생성",
            "Type '2' => 지갑 전체 조회",
            "Type '3' => 타입으로 지갑 조회",
            "Type '4' => 지갑 삭제"
    };

    private static final String[] WALLET_FIND_GUIDE_MESSAGES = {
            "=== 찾을 지갑의 유형을 골라주세요. ===",
            "Type '1' => 지갑 ID",
            "Type '2' => 바우처 ID",
            "Type '3' => 고객 ID",
    };

    private static final String[] WALLET_DELETE_GUIDE_MESSAGES = {
            "=== 찾을 지갑의 유형을 골라주세요. ===",
            "Type '1' => 지갑ID로 삭제",
            "Type '2' => 전체 삭제",
    };

    public Console(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public void print(String message) {
        output.print(message);
    }

    public String input() {
        return input.read();
    }

    public String askForVoucherType() {
        output.print(VOUCHER_TYPE_GUIDE_MESSAGES);
        return input();
    }

    public long askForDiscountAmount() {
        output.print("할인 정책에 맞는 할인 금액을 입력해 주세요. : ");
        String inputString = input();
        if (!inputString.matches(NUMBER_REGEX)) {
            throw new IllegalArgumentException("입력값은 숫자여야 합니다. => " + inputString);
        }
        return Long.parseLong(inputString);
    }

    public String displayCommandGuide() {
        output.print(COMMAND_GUIDE_MESSAGES);
        return input();
    }

    public String voucherCommandGuide() {
        output.print(VOUCHER_MENU_GUIDE_MESSAGES);
        return input();
    }

    public String customerCommandGuide() {
        output.print(CUSTOMER_MENU_GUIDE_MESSAGES);
        return input();
    }

    public String walletCommandGuide() {
        output.print(WALLET_MENU_GUIDE_MESSAGES);
        return input();
    }

    public String askForCustomerFindType() {
        output.print(CUSTOMER_FIND_TYPE_MESSAGES);
        return input();
    }

    public String askForCustomerDeleteType() {
        output.print(CUSTOMER_DELETE_TYPE_MESSAGES);
        return input();
    }

    public String askForWalletFindType() {
        output.print(WALLET_FIND_GUIDE_MESSAGES);
        return input();
    }

    public String askForWalletDeleteType() {
        output.print(WALLET_DELETE_GUIDE_MESSAGES);
        return input();
    }

    public String askForVoucherDeleteType() {
        output.print(VOUCHER_DELETE_TYPE_MESSAGES);
        return input();
    }

    public String askForVoucherId() {
        output.print("바우처 ID를 입력해 주세요.");
        return input();
    }

    public String askForCustomerId() {
        output.print("고객ID를 입력해 주세요.");
        return input();
    }
}