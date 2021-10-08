package org.prgrms.kdt.command.service.customer;

import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.command.service.CommandService;
import org.springframework.stereotype.Service;

@Service
public class CustomerCommandService implements CommandService {
    private final CustomerCreateService createService;
    private final CustomerListService listService;
    private final CustomerVoucherListService voucherListService;
    private final BlackListCommandService blacklistCommandService;

    public CustomerCommandService(final CustomerCreateService createService,
                                  final CustomerListService listService,
                                  final CustomerVoucherListService voucherListService,
                                  final BlackListCommandService blacklistCommandService) {
        this.createService = createService;
        this.listService = listService;
        this.voucherListService = voucherListService;
        this.blacklistCommandService = blacklistCommandService;
    }

    @Override
    public void commandRun() {
        boolean customerServiceRunning = true;
        do {
            Output.customerServiceMessage();

            final String commandInput = Input.input();
            switch (commandInput) {
                case "back" -> {
                    customerServiceRunning = false;
                }
                case "create" -> createService.commandRun();
                case "list" -> listService.commandRun();
                case "my_voucher_list" -> voucherListService.commandRun();
                case "blacklist" -> blacklistCommandService.commandRun();
                default -> Output.inputTypeErrorMessage(commandInput);
            }
        } while (customerServiceRunning);
    }
}
