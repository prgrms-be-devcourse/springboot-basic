package org.devcourse.springbasic.io;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.devcourse.springbasic.validator.Validator;
import org.devcourse.springbasic.validator.VoucherMenuValidator;
import org.devcourse.springbasic.menu.MenuType;
import org.devcourse.springbasic.validator.MenuValidator;
import org.devcourse.springbasic.voucher.Voucher;
import org.devcourse.springbasic.voucher.VoucherType;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

@Component
public class ConsoleDevice implements IODevice{

    private final TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public MenuType inputMenu() {

        String input = textIO.newStringInputReader()
                .read("메뉴를 입력하세요: ");

        Validator<String> menuValidator = new MenuValidator<>();
        boolean isValid = menuValidator.validate(input);

        if (isValid) {
            return MenuType.findSolveStateByInput(input);
        } else {
            throw new IllegalArgumentException("잘못된 메뉴 입력입니다.");
        }
    }

    @Override
    public VoucherType inputVoucherMenu() {
        String input = textIO.newStringInputReader()
                .read("바우처를 선택하세요. (번호 입력)");

        Validator<String> voucherMenuValidator = new VoucherMenuValidator<>();
        boolean isValid = voucherMenuValidator.validate(input);

        if (isValid) {
            return VoucherType.findByMenuNum(Integer.parseInt(input));
        } else {
            throw new IllegalArgumentException("잘못된 메뉴 입력입니다.");
        }

    }

    @Override
    public void outputVoucher(Voucher voucher) {
        System.out.println(MessageFormat.format("생성된 바우처 정보입니다 => {0}", voucher));
    }

    @Override
    public void outputVouchers(List<Voucher> vouchers) {
        System.out.println(MessageFormat.format("바우처 목록입니다\n {0}", vouchers));
    }
}
