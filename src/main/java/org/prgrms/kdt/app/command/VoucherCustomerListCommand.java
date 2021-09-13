package org.prgrms.kdt.app.command;

import java.util.UUID;
import org.prgrms.kdt.app.io.Console;
import org.prgrms.kdt.service.CustomerService;
import org.springframework.stereotype.Component;


@Component
public class VoucherCustomerListCommand implements CommandOperator{

    private final CommandType commandType;
    private final CustomerService customerService;
    private final VoucherListCommand voucherListCommand;
    private final Console console;

    public VoucherCustomerListCommand(CustomerService customerService,
        VoucherListCommand voucherListCommand, Console console) {
        this.customerService = customerService;
        this.voucherListCommand = voucherListCommand;
        this.console = console;
        this.commandType = CommandType.VOUCHER_CUSTOMER_LIST;
    }

    @Override
    public void execute() {
        voucherListCommand.execute();
        var voucherId = UUID.fromString(console.input("조회할 바우처의 아이디를 입력해주세요~"));
        var customers = customerService.getCustomersByVoucherId(voucherId);
        if (customers.isEmpty()) {
            console.printMessage("해당 바우처(%s)를 가지고 있는 고객이 없습니다.".formatted(voucherId));
        } else {
            console.printMessage("=== 해당 고객(%s)의 바우처 리스트 ===".formatted(voucherId));
            customers.forEach(v -> console.printMessage(v.toString()));
        }

    }

    @Override
    public CommandType getCommandType() {
        return commandType;
    }
}
