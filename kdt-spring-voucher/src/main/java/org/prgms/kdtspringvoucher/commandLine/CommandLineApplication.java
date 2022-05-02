package org.prgms.kdtspringvoucher.commandLine;

import org.prgms.kdtspringvoucher.customer.domain.Customer;
import org.prgms.kdtspringvoucher.customer.domain.CustomerType;
import org.prgms.kdtspringvoucher.customer.service.CustomerService;
import org.prgms.kdtspringvoucher.io.Input;
import org.prgms.kdtspringvoucher.io.Output;
import org.prgms.kdtspringvoucher.voucher.domain.Voucher;
import org.prgms.kdtspringvoucher.voucher.domain.VoucherType;
import org.prgms.kdtspringvoucher.voucher.service.VoucherService;
import org.prgms.kdtspringvoucher.wallet.WalletCommandType;
import org.prgms.kdtspringvoucher.wallet.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication{

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final WalletService walletService;
    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    public CommandLineApplication(Input input, Output output, VoucherService voucherService, CustomerService customerService, WalletService walletService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.walletService = walletService;
    }

    public void run() {
        while (true) {
            output.printCommandTypeInputPrompt();
            CommandType command = input.inputCommandType();
            if (command == null) continue;

            switch (command) {
                case EXIT -> System.exit(0);

                case LIST -> voucherService.showAllVoucher();

                case BLACK -> customerService.showBlackList(CustomerType.BLACKLIST);

                case CREATE -> runCreateVoucherForCommandLine();

                case CUSTOMER -> customerService.createCustomer(input.inputCustomerName(), input.inputCustomerEmail(), input.inputCustomerType());

                case WALLET -> runWalletService();
            }
        }
    }

    private void runCreateVoucherForCommandLine() {
        output.printVoucherTypeInputPrompt();
        VoucherType voucherType = input.inputVoucherType();
        if (voucherType == null) return;

        output.outputAmountOrPercentPrompt(voucherType);
        Long amountOrPercent = input.inputDiscountAmountOrPercent();
        voucherService.createVoucher(voucherType, amountOrPercent);
    }

    private void runWalletService() {
        output.printWalletCommandTypeInputPrompt();
        WalletCommandType walletCommandType = input.inputWalletCommandType();
        if (walletCommandType == null) return;

        switch (walletCommandType) {
            case ASSIGN -> walletService.assignVoucherToCustomer(chooseCustomer(),chooseVoucher());

            case CUSTOMER -> walletService.showVouchersAssignedToCustomer(chooseCustomer());

            case DELETE -> walletService.deleteVoucherAssignedToCustomer(chooseCustomer().getCustomerId());

            case VOUCHER -> walletService.showCustomerByVoucherId(chooseVoucher());
        }
    }

    private Customer chooseCustomer() {
        return customerService.showAllCustomer().get(input.inputCustomerNumber() - 1);
    }

    private Voucher chooseVoucher(){
        return voucherService.showAllVoucher().get(input.inputVoucherNumber() - 1);
    }
}
