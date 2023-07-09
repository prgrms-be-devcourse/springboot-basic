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
                case CREATE -> createVoucher();
                case LIST -> output.printAllVoucher(voucherService.findAll());
                case BLACK -> output.printBlackConsumerList(customerService.getBlackConsumerList());
            }
        }
    }

    private void insertDiscountValue() {
        VoucherType voucherType = input.selectVoucherType();
        int discountValue = input.insertDiscountValue();
        voucherService.insertNewVoucher(voucherType, discountValue);
        output.outputMessage(ConsoleOutputMessage.COMPLETE_VOUCHER_INSERT_MESSAGE);
    }

    private void createVoucher(){
        try {
            insertDiscountValue();
        }catch (RuntimeException e){
            logger.warn(e.getMessage());
            output.outputWarnMessage(ConsoleOutputMessage.INVALID_INPUT_DISCOUNT_MESSAGE);
        }

    }
}
