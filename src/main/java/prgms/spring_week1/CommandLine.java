package prgms.spring_week1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.customer.service.CustomerService;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.service.VoucherService;
import prgms.spring_week1.exception.NoSuchOptionValueException;
import prgms.spring_week1.exception.NoSuchVoucherTypeException;
import prgms.spring_week1.io.Input;
import prgms.spring_week1.io.Output;
import prgms.spring_week1.io.message.ConsoleOutputMessage;
import prgms.spring_week1.menu.Menu;

import java.util.List;

import static prgms.spring_week1.domain.voucher.model.type.VoucherType.FIXED;
import static prgms.spring_week1.domain.voucher.model.type.VoucherType.PERCENT;

@Component
public class CommandLine implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(CommandLine.class);

    private final Input input = new Input();
    private final Output output = new Output();
    private final VoucherService voucherService;
    private final CustomerService customerService;

    private CommandLine(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean isRunning = true;
        while (isRunning) {
            Menu menuName = getMenu();
            switch (menuName) {
                case EXIT -> isRunning = false;
                case CREATE -> selectVoucherTypeBeforeCreate();
                case LIST -> printAllVoucher(voucherService.findAll());
                case BLACK -> output.printBlackConsumerList(customerService.getBlackConsumerList());
            }
        }
    }

    private Menu getMenu() {
        output.outputMessage(ConsoleOutputMessage.MENU_LIST_MESSAGE);
        Menu menu = input.selectMenu();
        while (menu == null) {
            output.outputMessage(ConsoleOutputMessage.INVALID_MENU_MESSAGE);
            menu = input.selectMenu();
        }
        return menu;
    }

    private void selectVoucherTypeBeforeCreate() {
        output.outputMessage(ConsoleOutputMessage.TYPE_SELECT_MESSAGE);
        String select = null;

        try {
            select = input.input();
        } catch (NoSuchVoucherTypeException e) {
            logger.error(e.getMessage());
            output.outputMessage(ConsoleOutputMessage.INVALID_VOUCHER_TYPE_MESSAGE);
        }

        switch (VoucherType.findVoucherType(select)) {
            case FIXED -> insertNewVoucher(FIXED);
            case PERCENT -> insertNewVoucher(PERCENT);
        }
    }

    private void insertNewVoucher(VoucherType voucherType) {
        Long discountValue = switch (voucherType) {
            case FIXED -> {
                output.outputMessage(ConsoleOutputMessage.INPUT_DISCOUNT_AMOUNT_MESSAGE);
                Long inputAmountValue = input.insertFixedDiscountValue();
                while (VoucherType.validateAmountInputValue(inputAmountValue) == null) {
                    output.outputMessage(ConsoleOutputMessage.INVALID_INPUT_DISCOUNT_MESSAGE);
                    inputAmountValue = VoucherType.validateAmountInputValue(input.insertFixedDiscountValue());
                }

                yield inputAmountValue;
            }
            case PERCENT -> {
                output.outputMessage(ConsoleOutputMessage.INPUT_DISCOUNT_PERCENT_MESSAGE);
                Long inputPercentValue = input.insertPercentDiscountValue();
                while (VoucherType.validatePercentInputValue(inputPercentValue) == null) {
                    output.outputMessage(ConsoleOutputMessage.INVALID_INPUT_DISCOUNT_MESSAGE);
                    inputPercentValue = VoucherType.validatePercentInputValue(input.insertPercentDiscountValue());
                }

                yield inputPercentValue;
            }
        };

        voucherService.insertNewVoucher(voucherType, discountValue);
        output.outputMessage(ConsoleOutputMessage.COMPLETE_VOUCHER_INSERT_MESSAGE);
    }

    private void printAllVoucher(List<Voucher> voucherList) {
        if (voucherList.isEmpty()) {
            output.outputMessage(ConsoleOutputMessage.NO_VOUCHER_LIST_MESSAGE);
            return;
        }

        for (Voucher voucher : voucherList) {
            output.classifyVoucherInfo(voucher.getVoucherType(), voucher.getDiscount());
        }
    }
}
