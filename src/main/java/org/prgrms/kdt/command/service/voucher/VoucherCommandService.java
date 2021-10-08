package org.prgrms.kdt.command.service.voucher;

import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.command.service.CommandService;
import org.springframework.stereotype.Service;

@Service
public class VoucherCommandService implements CommandService {
    private final VoucherCreateService createService;
    private final VoucherListService listService;
    private final VoucherOfCustomerService findCustomerService;

    public VoucherCommandService(final VoucherCreateService createService,
                                 final VoucherListService listService,
                                 final VoucherOfCustomerService findCustomerService) {
        this.createService = createService;
        this.listService = listService;
        this.findCustomerService = findCustomerService;
    }

    @Override
    public void commandRun() {
        boolean voucherServiceRunning = true;
        do {
            Output.voucherServiceMessage();

            final String commandInput = Input.input();
            switch (commandInput) {
                case "back" -> {
                    voucherServiceRunning = false;
                }
                case "create" -> createService.commandRun();
                case "list" -> listService.commandRun();
                case "find_customer" -> findCustomerService.commandRun();
                default -> Output.inputTypeErrorMessage(commandInput);
            }
        } while (voucherServiceRunning);
    }
}
