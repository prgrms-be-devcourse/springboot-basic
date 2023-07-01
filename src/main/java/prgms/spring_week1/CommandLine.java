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
import prgms.spring_week1.menu.Menu;

@Component
public class CommandLine implements Runnable {
    private final Input input;
    private final Output output;
    private final VoucherRepository voucherRepository;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private boolean IS_RUNNING = true;
    private static final Logger log = LoggerFactory.getLogger(CommandLine.class);

    public CommandLine(Input input, Output output, VoucherRepository voucherRepository, VoucherService voucherService, CustomerService customerService) {
        this.input = input;
        this.output = output;
        this.voucherRepository = voucherRepository;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run() {
        while (IS_RUNNING) {
            output.printMenuList();
            String selectOption = input.inputTextOption();
            switch (findMenuName(selectOption)) {
                case EXIT -> IS_RUNNING = false;
                case CREATE -> selectVoucherType();
                case LIST -> printAllVoucher();
                case BLACK -> output.printBlackConsumerList(customerService.blackConsumerList());
                default -> output.printWrongMenuMessage();
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
        output.printTypeSelectMessage();
        try {
            String select = input.inputVoucherType();
            VoucherType voucherType = VoucherType.matchVoucherType(select);
            switch (voucherType) {
                case FIXED -> {
                    output.printInsertFixedVoucherMessage();
                    long discountAmount = input.insertDiscountAmountVoucher();
                    while (discountAmount <= 0) {
                        output.printInsertFixedVoucherMessage();
                        discountAmount = input.insertDiscountAmountVoucher();
                    }
                    Voucher newVoucher = voucherService.insertFixedAmountVoucher(discountAmount);
                    output.printInsertVoucherInfo(newVoucher);
                }
                case PERCENT -> {
                    output.printInsertPercentVoucherMessage();
                    int discountPercent = input.insertDiscountPercentVoucher();
                    while (discountPercent < 0 || discountPercent > 100) {
                        output.printInsertPercentVoucherMessage();
                        discountPercent = input.insertDiscountPercentVoucher();
                    }
                    Voucher newVoucher = voucherService.insertPercentDiscountVoucher(discountPercent);
                    output.printInsertVoucherInfo(newVoucher);
                }
            }
        } catch (NoSuchVoucherTypeException e) {
            output.printNoSuchVoucherType();
        }
    }

    private void printAllVoucher() {
        output.printAllVoucher(voucherRepository.findAll());
    }
}
