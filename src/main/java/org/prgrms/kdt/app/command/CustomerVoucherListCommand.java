package org.prgrms.kdt.app.command;

import java.util.UUID;
import org.prgrms.kdt.app.io.Console;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.stereotype.Component;


@Component
public class CustomerVoucherListCommand implements CommandOperator {

    private final CommandType commandType;
    private final VoucherService voucherService;
    private final CustomerListCommand customerListCommand;
    private final Console console;

    public CustomerVoucherListCommand(VoucherService voucherService,
        CustomerListCommand customerListCommand, Console console) {
        this.voucherService = voucherService;
        this.customerListCommand = customerListCommand;
        this.console = console;
        this.commandType = CommandType.CUSTOMER_VOUCHER_LIST;
    }

    @Override
    public void execute() {
        customerListCommand.execute();
        var customerId = UUID.fromString(console.input("조회할 고객의 아이디를 입력해주세요~"));
        var customers = voucherService.getVouchersByCustomerId(customerId);
        if (customers.isEmpty()) {
            console.printMessage("해당 고객(%s)에게 할당된 바우처가 없습니다.".formatted(customerId));
        } else {
            console.printMessage("=== 해당 고객(%s)의 바우처 리스트 ===".formatted(customerId));
            customers.forEach(v -> console.printMessage(v.toString()));
        }
    }

    public void execute(UUID customerId) {
        var vouchers = voucherService.getVouchersByCustomerId(customerId);
        console.printMessage("=== 해당 고객(%s)의 바우처 리스트 ===".formatted(customerId));
        if (vouchers.isEmpty()) {
            console.printMessage("해당 고객(%s)에게 할당된 바우처가 없습니다.".formatted(customerId));
        } else {
            vouchers.forEach(v -> console.printMessage(v.toString()));
        }
    }

    @Override
    public CommandType getCommandType() {
        return commandType;
    }
}
