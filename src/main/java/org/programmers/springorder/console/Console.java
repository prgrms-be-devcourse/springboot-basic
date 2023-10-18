package org.programmers.springorder.console;

import org.programmers.springorder.consts.ErrorMessage;
import org.programmers.springorder.consts.Message;
import org.programmers.springorder.dto.VoucherRequestDto;
import org.programmers.springorder.dto.VoucherResponseDto;
import org.programmers.springorder.model.VoucherType;
import org.programmers.springorder.utils.MenuType;
import org.programmers.springorder.utils.Validation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Console {

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

        String menuNum = Validation.validateString(input.getInput());
        return MenuType.selectMenu(menuNum); // TODO: Console Validation
    }

    private VoucherType inputVoucherType() {
        printMessage(Message.VOUCHER_SELECT_MESSAGE);
        output.printPrompt();

        int voucherNum = Validation.validateInt(input.getInput());
        return VoucherType.selectVoucherType(voucherNum);
    }

    private long inputVoucherValue(VoucherType voucherType) {
        String discountValueMessage = voucherType == VoucherType.FIXED ? Message.INPUT_FIXED_DISCOUNT_VALUE_MESSAGE : Message.INPUT_PERCENT_DISCOUNT_VALUE_MESSAGE;
        printMessage(discountValueMessage);
        output.printPrompt();

        return Validation.validateLong(input.getInput());
    }


    public VoucherRequestDto inputVoucherInfo() {
        VoucherType voucherType = inputVoucherType();
        long discountValue = inputVoucherValue(voucherType);

        return new VoucherRequestDto(discountValue, voucherType);
    }

    public void showList(List<VoucherResponseDto> allVoucher) {
        if(allVoucher.size() == 0) {
            printMessage(ErrorMessage.VOUCHER_NOT_EXIST_MESSAGE);
        } else {
            allVoucher.forEach(System.out::println);
        }
    }
}
