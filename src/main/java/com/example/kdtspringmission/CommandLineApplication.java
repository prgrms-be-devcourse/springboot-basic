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
            return;
        }

        if (command == Command.ASSIGN_VOUCHER) {
            outputView.customerList(customerService.allCustomers());
            String customerName = inputView.getCustomerName();

            outputView.voucherList(voucherService.findAll());
            UUID voucherId = inputView.getVoucherId();

            customerService.assignVoucher(customerName, voucherId);
            return;
        }

        if (command == Command.LIST_BY_OWNER) {
            outputView.customerList(customerService.allCustomers());
            String customerName = inputView.getCustomerName();

            outputView.voucherList(customerService.getWallet(customerName));
            return;
        }

        if (command == Command.DELETE_VOUCHER) {
            outputView.customerList(customerService.allCustomers());
            String customerName = inputView.getCustomerName();

            outputView.voucherList(voucherService.findAll());
            UUID voucherId = inputView.getVoucherId();

            customerService.deleteVoucher(customerName, voucherId);
            return;
        }

    }

}
