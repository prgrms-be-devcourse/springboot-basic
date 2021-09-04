package com.example.kdtspringmission;

import com.example.kdtspringmission.customer.service.CustomerService;
import com.example.kdtspringmission.view.InputView;
import com.example.kdtspringmission.view.OutputView;
import com.example.kdtspringmission.voucher.service.VoucherService;
import java.util.UUID;
import org.springframework.stereotype.Controller;

@Controller
public class CommandLineApplication {

    private final InputView inputView;
    private final OutputView outputView;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public CommandLineApplication(InputView inputView, OutputView outputView,
        VoucherService voucherService,
        CustomerService customerService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public void run() {
        while (true) {
            outputView.commandList();
            executeCommand(inputView.getCommand());
        }
    }

    private void executeCommand(Command command) {
        if (command == Command.EXIT) {
            System.exit(0);
            return;
        }

        if (command == Command.CREATE) {
            outputView.creatableVoucherList();
            voucherService.createAndPersist(inputView.nextLine());
            return;
        }

        if (command == Command.LIST) {
            outputView.voucherList(voucherService.findAll());
            return;
        }

        if (command == Command.CUSTOMERS) {
            outputView.customerList(customerService.allCustomers());
        }

        if (command == Command.ASSIGN_VOUCHER) {
            outputView.customerList(customerService.allCustomers());
            System.out.println();
            System.out.println("Type customer name: ");
            String customerName = inputView.nextLine();

            outputView.voucherList(voucherService.findAll());
            System.out.println();
            System.out.println("Type voucher ID: ");
            UUID voucherId = UUID.fromString(inputView.nextLine());

            customerService.assignVoucher(customerName, voucherId);
        }

    }

}
