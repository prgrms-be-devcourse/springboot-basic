package org.prgms.kdt.application.command;


import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgms.kdt.application.customer.domain.Customer;
import org.prgms.kdt.application.customer.service.CustomerService;
import org.prgms.kdt.application.io.Input;
import org.prgms.kdt.application.io.Output;
import org.prgms.kdt.application.voucher.domain.Voucher;
import org.prgms.kdt.application.voucher.domain.VoucherType;
import org.prgms.kdt.application.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommandLineApplication implements Runnable {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private boolean exit = true;

    @Override
    public void run() {
        while (exit) {
            output.commandTypeMessage();
            String commandTypeInput = input.typeOptionInput();
            try {
                CommandType commandType = CommandType.findCommandType(commandTypeInput);
                switch (commandType) {
                    case EXIT:
                        exit = false;
                        output.printExit();
                        break;
                    case CREATE_CUSTOMER:
                        customerService.join("name", "email@gmail.com");
                        break;
                    case GET_CUSTOMER_LIST:
                        List<Customer> allCustomers = customerService.getAllCustomers();
                        break;
                    case CREATE_VOUCHER:
                        String voucherTypeInput = input.typeOptionInput();
                        VoucherType voucherType = VoucherType.findVoucherType(voucherTypeInput);
                        voucherService.createVoucher(voucherType, 2000L, null);
                    case GET_VOUCHER_LIST:
                        List<Voucher> allVouchers = voucherService.getAllVouchers();
                    default:
                        break;
                }
            } catch (IllegalArgumentException e) {
                output.printError(e.getMessage());
                log.error("{}", e);
                continue;
            }
        }
    }

}
