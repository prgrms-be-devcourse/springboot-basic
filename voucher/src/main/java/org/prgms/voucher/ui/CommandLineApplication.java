package org.prgms.voucher.ui;

import org.prgms.voucher.voucher.controller.VoucherController;
import org.prgms.voucher.voucher.domain.AmountVoucher;
import org.prgms.voucher.voucher.domain.VoucherOptionType;
import org.prgms.voucher.voucher.dto.VoucherPrintDto;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class CommandLineApplication implements Output, Input {
    private static final String SELECT_PROMOTION_TYPE = "프로모션 종류를 선택하세요. (번호 또는 종류 이름 입력)";
    private static final String SELECT_COMMANDS_TYPE = "명령어를 선택하세요. (번호 또는 명령어 이름 입력)";
    private static final String SELECT_OPTION_TYPE = "옵션 종류를 선택하세요. (번호 또는 종류 이름 입력)";
    private static final String SELECT_INITIAL_MONEY = "원가를 입력하세요. (0보다 크고 100원 단위만 가능)";
    private static final String INPUT_AMOUNT = "값을 입력하세요. (0보다 큰 값)";
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final String VOUCHER_TYPE = "바우처 종류";
    private static final String AFTER_DISCOUNT_VALUE = "할인가";
    private static final String PUBLISH_DATE = "발행일";
    private static final String EXPIRATION_DATE = "만료일";
    private static final String SEPARATOR = ": ";
    private final VoucherController voucherController;

    public CommandLineApplication(VoucherController voucherController) {
        this.voucherController = voucherController;
    }

    @Override
    public void printPromotionType(PromotionType[] promotionTypes) {
        StringBuilder sb = new StringBuilder();

        sb.append(SELECT_PROMOTION_TYPE).append("\n");

        for (PromotionType promotionType : promotionTypes) {
            sb.append(promotionType.getChoiceNumber()).append(".");
            sb.append(promotionType.getPromotionType()).append(" ");
        }

        System.out.println(sb);
    }

    @Override
    public void printCommandType(CommandType[] commandTypes) {
        StringBuilder sb = new StringBuilder();

        sb.append(SELECT_COMMANDS_TYPE).append("\n");

        for (CommandType commandType : commandTypes) {
            sb.append(commandType.getChoiceNumber()).append(".");
            sb.append(commandType.getCommandType()).append(" ");
        }

        System.out.println(sb);
    }

    @Override
    public void printOptionType(VoucherOptionType[] voucherOptionTypes) {
        StringBuilder sb = new StringBuilder();

        sb.append("\n").append(SELECT_OPTION_TYPE).append("\n");

        for (VoucherOptionType voucherOptionType : voucherOptionTypes) {
            sb.append(voucherOptionType.getChoiceNumber()).append(".");
            sb.append(voucherOptionType.getTypeName()).append("(");
            sb.append(voucherOptionType.getShortTypeName()).append(") ");
        }

        System.out.println(sb);
    }

    @Override
    public void printInitialMoney() {
        System.out.println("\n" + SELECT_INITIAL_MONEY);
    }


    @Override
    public void printAmount() {
        System.out.println("\n" + INPUT_AMOUNT);
    }

    @Override
    public void printCreateVoucher(AmountVoucher voucher) {
        String sb = "\n" +
                VOUCHER_TYPE + SEPARATOR + voucher.getOptionTypeName() + "\n" +
                AFTER_DISCOUNT_VALUE + SEPARATOR + voucher.discount(voucher.getInitialMoney()) + "\n" +
                PUBLISH_DATE + SEPARATOR + voucher.getPublishDate() + "\n" +
                EXPIRATION_DATE + SEPARATOR + voucher.getExpirationDate() + "\n";

        System.out.println(sb);
    }

    @Override
    public void printList() {
        List<VoucherPrintDto> voucherPrintDtos = voucherController.listVoucher();

        StringBuilder sb = new StringBuilder();

        for (VoucherPrintDto voucherPrintDto : voucherPrintDtos) {
            sb.append("\n");
            sb.append(VOUCHER_TYPE).append(SEPARATOR).append(voucherPrintDto.getOptionType()).append("\n");
            sb.append(AFTER_DISCOUNT_VALUE).append(SEPARATOR).append(voucherPrintDto.getAfterDiscountValue()).append("\n");
            sb.append(PUBLISH_DATE).append(SEPARATOR).append(voucherPrintDto.getPublishDate()).append("\n");
            sb.append(EXPIRATION_DATE).append(SEPARATOR).append(voucherPrintDto.getExpirationDate()).append("\n");
        }

        System.out.println(sb);
    }


    @Override
    public PromotionType getPromotionType() throws IOException {
        String input = br.readLine();

        for (PromotionType promotionType : PromotionType.values()) {
            if (promotionType.getPromotionType().equalsIgnoreCase(input) || String.valueOf(promotionType.getChoiceNumber()).equals(input)) {
                return promotionType;
            }
        }

        throw new IllegalArgumentException("유효한 프로모션 타입이 아닙니다.");
    }

    @Override
    public CommandType getCommandType() throws IOException {
        String input = br.readLine();

        for (CommandType commandType : CommandType.values()) {
            if (commandType.getCommandType().equalsIgnoreCase(input) || String.valueOf(commandType.getChoiceNumber()).equals(input)) {
                return commandType;
            }
        }

        throw new IllegalArgumentException("유효한 커맨드 타입이 아닙니다.");
    }

    @Override
    public VoucherOptionType getOptionType() throws IOException {
        String input = br.readLine();

        for (VoucherOptionType voucherOptionType : VoucherOptionType.values()) {
            if (voucherOptionType.getTypeName().equalsIgnoreCase(input) || voucherOptionType.getShortTypeName().equalsIgnoreCase(input) || String.valueOf(voucherOptionType.getChoiceNumber()).equals(input)) {
                return voucherOptionType;
            }
        }

        throw new IllegalArgumentException("유효한 바우처 옵션 타입이 아닙니다.");
    }

    @Override
    public int getInitialMoney() throws IOException {
        int initialMoney = greaterThanZero(validateInt(br.readLine()));

        if (initialMoney % 100 != 0) {
            throw new IllegalArgumentException("유효한 단위가 아닙니다. (100원 단위로 입력)");
        }

        return initialMoney;
    }


    @Override
    public int getAmount() throws IOException {
        return greaterThanZero(validateInt(br.readLine()));
    }

    private int validateInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (Exception e) {
            throw new NumberFormatException("유효한 숫자 형식이 아니거나 최대 값을 초과합니다.");
        }
    }

    private int greaterThanZero(int number) {
        if (number <= 0) {
            throw new NumberFormatException("0보다 작거나 같은 값입니다.");
        }

        return number;
    }
}
