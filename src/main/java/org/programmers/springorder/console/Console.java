package org.programmers.springorder.console;

import org.programmers.springorder.consts.ErrorMessage;
import org.programmers.springorder.consts.Message;
import org.programmers.springorder.customer.dto.CustomerResponseDto;
import org.programmers.springorder.utils.MenuType;
import org.programmers.springorder.utils.Validation;
import org.programmers.springorder.voucher.dto.GiveVoucherRequestDto;
import org.programmers.springorder.voucher.dto.VoucherRequestDto;
import org.programmers.springorder.voucher.dto.VoucherResponseDto;
import org.programmers.springorder.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class Console {

    private final Logger logger = LoggerFactory.getLogger(Console.class);
    private final Input input;
    private final Output output;


    public Console() {
        this.input = new Input();
        this.output = new Output();
    }

    public void printMessage(String message) {
        output.printMessage(message);
    }

    public MenuType inputMenu() {
        printMessage(Message.MENU_SELECT_MESSAGE);
        output.printPrompt();
        try {
            String menuNum = Validation.validateString(input.getInput());
            return MenuType.selectMenu(menuNum);
        } catch (InputMismatchException e) {
            logger.error("errorMessage = {}", e.getMessage());
            printMessage(e.getMessage());
            return inputMenu(); // TODO:재귀로 호출하는 게 괜찮은 건지 확인 필요
        }
    }

    private VoucherType inputVoucherType() {
        printMessage(Message.VOUCHER_SELECT_MESSAGE);
        output.printPrompt();
        try {
            String voucherNum = Validation.validateString(input.getInput());
            return VoucherType.selectVoucherType(voucherNum);
        } catch (InputMismatchException e) {
            logger.error("errorMessage = {}", e.getMessage());
            printMessage(e.getMessage());
            return inputVoucherType();
        }
    }

    private long inputVoucherValue(VoucherType voucherType) {
        String discountValueMessage = voucherType == VoucherType.FIXED ? Message.INPUT_FIXED_DISCOUNT_VALUE_MESSAGE
                : Message.INPUT_PERCENT_DISCOUNT_VALUE_MESSAGE;
        printMessage(discountValueMessage);
        output.printPrompt();
        try {
            return Validation.validateDiscountValue(input.getInput(), voucherType);
        } catch (InputMismatchException | NumberFormatException e) {
            //TODO: Message Const 로 넣을 수 있는 방법 있는지 확인
            String message = String.format("잘못된 입력 값입니다. %d ~ %d 사이의 값을 입력해주세요.",
                    voucherType.getMinimumValue(),
                    voucherType.getMaximumValue());
            logger.error("errorMessage = {}", message);
            printMessage(message);
            return inputVoucherValue(voucherType);
        }
    }


    public VoucherRequestDto inputVoucherInfo() {
        VoucherType voucherType = inputVoucherType();
        long discountValue = inputVoucherValue(voucherType);

        return new VoucherRequestDto(discountValue, voucherType);
    }

    public void showList(List<VoucherResponseDto> allVoucher) {
        if (allVoucher.isEmpty()) {
            printMessage(ErrorMessage.VOUCHER_NOT_EXIST_MESSAGE);
        } else {
            allVoucher.forEach(System.out::println);
        }
    }

    public void showBlackList(List<CustomerResponseDto> blackList) {
        if (blackList.isEmpty()) {
            printMessage(ErrorMessage.BLACK_CONSUMER_NOT_EXIST_MESSAGE);
        } else {
            blackList.forEach(System.out::println);
        }
    }

    public GiveVoucherRequestDto giveVoucherInfo() {
        UUID customerId = getCustomerId();
        UUID voucherId = getVoucherId();
        return new GiveVoucherRequestDto(voucherId, customerId);
    }

    public UUID getCustomerId() {
        printMessage(Message.INPUT_USER_ID);
        return UUID.fromString(input.getInput());
    }

    public UUID getVoucherId() {
        printMessage(Message.INPUT_VOUCHER_ID);
        return UUID.fromString(input.getInput());
    }

    public void showCustomer(CustomerResponseDto customer) {
        printMessage(customer.toString());
    }
}
