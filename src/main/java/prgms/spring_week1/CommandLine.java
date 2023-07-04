package prgms.spring_week1;

import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.customer.service.CustomerService;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;
import prgms.spring_week1.domain.voucher.service.VoucherService;
import prgms.spring_week1.exception.NoSuchOptionValueException;
import prgms.spring_week1.exception.NoSuchVoucherTypeException;
import prgms.spring_week1.io.Input;
import prgms.spring_week1.io.Output;
import prgms.spring_week1.io.message.ConsoleOutputMessage;
import prgms.spring_week1.menu.Menu;

import java.util.List;

@Component
public class CommandLine implements Runnable {
    private final Input input;
    private final Output output;
    private final VoucherRepository voucherRepository;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public CommandLine(Input input, Output output, VoucherRepository voucherRepository, VoucherService voucherService, CustomerService customerService) {
        this.input = input;
        this.output = output;
        this.voucherRepository = voucherRepository;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            output.outputMessage(ConsoleOutputMessage.MENU_LIST_MESSAGE);
            String selectOption = input.input();
            switch (findMenuName(selectOption)) {
                case EXIT -> isRunning = false;
                case CREATE -> selectVoucherType();
                case LIST -> printAllVoucher(voucherRepository.findAll());
                case BLACK -> output.printBlackConsumerList(customerService.getBlackConsumerList());
                default -> output.outputMessage(ConsoleOutputMessage.INVALID_MENU_MESSAGE);
            }
        }
    }

    private Menu findMenuName(String inputText) {
        try {
            Menu selectMenu = Menu.findMenuType(inputText);
            return selectMenu;
        } catch (NoSuchOptionValueException e) {
            return Menu.INVALID;
        }
    }

    private void selectVoucherType() {
        output.outputMessage(ConsoleOutputMessage.TYPE_SELECT_MESSAGE);
        try {
            String select = input.input();

            switch (VoucherType.findVoucherType(select)) {
                case FIXED -> insertFixedAmountValue();
                case PERCENT -> insertPercentDiscountValue();
            }
        } catch (NoSuchVoucherTypeException e) {
            output.outputMessage(ConsoleOutputMessage.INVALID_VOUCHER_TYPE_MESSAGE);
        }
    }

    private void insertFixedAmountValue() {
        output.outputMessage(ConsoleOutputMessage.INPUT_DISCOUNT_AMOUNT_MESSAGE);
        insertFixedAmountVoucher(Long.parseLong(input.input()));
    }

    private void insertFixedAmountVoucher(long discountAmount) {
        while (discountAmount <= 0) {
            insertFixedAmountValue();
        }
        voucherService.insertFixedAmountVoucher(discountAmount);
        output.outputMessage(ConsoleOutputMessage.COMPLETE_VOUCHER_INSERT_MESSAGE);
    }

    private void insertPercentDiscountValue() {
        output.outputMessage(ConsoleOutputMessage.INPUT_DISCOUNT_PERCENT_MESSAGE);
        insertPercentDiscountVoucher(Integer.parseInt(input.input()));
    }

    private void insertPercentDiscountVoucher(int discountPercent) {
        while (discountPercent < 0 || discountPercent > 100) {
            insertPercentDiscountValue();
        }
        voucherService.insertPercentDiscountVoucher(discountPercent);
        output.outputMessage(ConsoleOutputMessage.COMPLETE_VOUCHER_INSERT_MESSAGE);
    }

    private void printAllVoucher(List<Voucher> voucherList) {
        if (voucherList.isEmpty()) {
            output.outputMessage(ConsoleOutputMessage.NO_VOUCHER_LIST_MESSAGE);
            return;
        }

        for (Voucher voucher : voucherList) {
            switch (voucher.getVoucherType()) {
                case FIXED -> output.printDiscountFixedVoucherInfo(voucher.getDiscount());
                case PERCENT -> output.printDiscountAmountVoucherInfo(voucher.getDiscount());
            }
        }
    }
}
