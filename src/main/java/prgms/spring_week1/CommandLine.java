package prgms.spring_week1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(CommandLine.class);
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
        boolean IS_RUNNING = true;
        while (IS_RUNNING) {
            output.outputMessage(ConsoleOutputMessage.MENU_LIST_MESSAGE);
            String selectOption = input.input();
            switch (findMenuName(selectOption)) {
                case EXIT -> IS_RUNNING = false;
                case CREATE -> selectVoucherType();
                case LIST -> printAllVoucher(voucherRepository.findAll());
                case BLACK -> output.printBlackConsumerList(customerService.blackConsumerList());
                default -> output.outputMessage(ConsoleOutputMessage.INVALID_MENU_MESSAGE);
            }
        }
    }

    private void insertFixedAmountVoucher() {
        output.outputMessage(ConsoleOutputMessage.INPUT_DISCOUNT_AMOUNT_MESSAGE);
        long discountAmount = Long.parseLong(input.input());
        while (discountAmount <= 0) {
            output.outputMessage(ConsoleOutputMessage.INPUT_DISCOUNT_AMOUNT_MESSAGE);
            discountAmount = Long.parseLong(input.input());
        }
        Voucher newVoucher = voucherService.insertFixedAmountVoucher(discountAmount);
        output.outputMessage(ConsoleOutputMessage.COMPLETE_VOUCHER_INSERT_MESSAGE);
    }

    private void insertPercentDiscountVoucher() {
        output.outputMessage(ConsoleOutputMessage.INPUT_DISCOUNT_PERCENT_MESSAGE);
        int discountPercent = Integer.parseInt(input.input());
        while (discountPercent < 0 || discountPercent > 100) {
            output.outputMessage(ConsoleOutputMessage.INPUT_DISCOUNT_PERCENT_MESSAGE);
            discountPercent = Integer.parseInt(input.input());
        }
        Voucher newVoucher = voucherService.insertPercentDiscountVoucher(discountPercent);
        output.outputMessage(ConsoleOutputMessage.COMPLETE_VOUCHER_INSERT_MESSAGE);
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

            switch (VoucherType.makeVoucherType(select)) {
                case FIXED -> insertFixedAmountVoucher();
                case PERCENT -> insertPercentDiscountVoucher();
            }
        } catch (NoSuchVoucherTypeException e) {
            output.outputMessage(ConsoleOutputMessage.INVALID_VOUCHER_TYPE_MESSAGE);
        }
    }

    private void printAllVoucher(List<Voucher> voucherList) {
        if (voucherList.isEmpty()) {
            output.outputMessage(ConsoleOutputMessage.NO_VOUCHER_LIST_MESSAGE);
            return;
        }

        for (Voucher voucher : voucherList) {
            switch (voucher.getVoucherType()) {
                case FIXED -> output.printDiscountFixedVoucherInfo(voucher);
                case PERCENT -> output.printDiscountAmountVoucherInfo(voucher);
            }
        }
    }
}
