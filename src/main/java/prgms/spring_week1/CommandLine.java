package prgms.spring_week1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.customer.service.CustomerService;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.service.VoucherService;
import prgms.spring_week1.io.Input;
import prgms.spring_week1.io.Output;
import prgms.spring_week1.io.message.ConsoleOutputMessage;
import prgms.spring_week1.menu.Menu;
import prgms.spring_week1.menu.VoucherMenu;

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
            Menu menuName = input.getMenu();
            switch (menuName) {
                case EXIT -> isRunning = false;
                case VOUCHER -> selectVoucherMenu();
                case CUSTOMER -> selectCustomerMenu();
            }
        }
    }

    private void selectVoucherMenu(){
        VoucherMenu menuName = input.getVoucherMenu();
        switch (menuName){
            case INSERT -> createVoucher();
            case FIND_ALL -> voucherService.findAll();
            case FIND_BY_TYPE -> output.printAllVoucher(voucherService.findByType(input.inputVoucherType()));
            case DELETE -> voucherService.deleteAll(VoucherType.PERCENT);
        }
    }

    private void selectCustomerMenu(){
//        Menu menuName = input.getMenu();
//        switch (menuName){
//            case EXIT -> ;
//            case CREATE -> ;
//            case LIST -> ;
//            case BLACK -> ;
//        }
    }

    private void createVoucher() {
        try {
            insertDiscountValue();
        } catch (RuntimeException e) {
            logger.warn(e.getMessage());
            input.printConsoleMessage(ConsoleOutputMessage.INVALID_INPUT_DISCOUNT_MESSAGE);
        }

    }

    private void insertDiscountValue() {
        VoucherType voucherType = input.selectVoucherType();
        int discountValue = input.insertDiscountValue();
        voucherService.insertNewVoucher(voucherType, discountValue);
        input.printConsoleMessage(ConsoleOutputMessage.COMPLETE_VOUCHER_INSERT_MESSAGE);
    }

    private void inputVoucherType(){
        voucherService.findByType(input.inputVoucherType());
    }
}
