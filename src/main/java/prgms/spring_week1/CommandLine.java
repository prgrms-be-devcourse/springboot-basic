package prgms.spring_week1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.customer.service.CustomerService;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.dto.VoucherOutputDto;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.service.VoucherService;
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
                case LIST -> output.printAllVoucher(voucherService.findAll());
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

        try {
            switch (input.selectVoucherType()) {
                case FIXED -> insertNewVoucher(FIXED);
                case PERCENT -> insertNewVoucher(PERCENT);
            }
        } catch (NoSuchVoucherTypeException e) {
            logger.error(e.getMessage());
            output.outputMessage(ConsoleOutputMessage.INVALID_VOUCHER_TYPE_MESSAGE);
        }
    }

    private void insertNewVoucher(VoucherType voucherType) {
        int discountValue = switch (voucherType) {
            case FIXED -> {
                output.outputMessage(ConsoleOutputMessage.INPUT_DISCOUNT_AMOUNT_MESSAGE);
                Integer inputAmountValue = input.insertDiscountValue();
                while (VoucherType.validateAmountInputValue(inputAmountValue) == null) {
                    output.outputMessage(ConsoleOutputMessage.INVALID_INPUT_DISCOUNT_MESSAGE);
                    inputAmountValue = VoucherType.validateAmountInputValue(input.insertDiscountValue());
                }

                yield inputAmountValue;
            }
            case PERCENT -> {
                output.outputMessage(ConsoleOutputMessage.INPUT_DISCOUNT_PERCENT_MESSAGE);
                Integer inputPercentValue = input.insertDiscountValue();
                while (VoucherType.validatePercentInputValue(inputPercentValue) == null) {
                    output.outputMessage(ConsoleOutputMessage.INVALID_INPUT_DISCOUNT_MESSAGE);
                    inputPercentValue = VoucherType.validatePercentInputValue(input.insertDiscountValue());
                }

                yield inputPercentValue;
            }
        };

        voucherService.insertNewVoucher(voucherType, discountValue);
        output.outputMessage(ConsoleOutputMessage.COMPLETE_VOUCHER_INSERT_MESSAGE);
    }


}
