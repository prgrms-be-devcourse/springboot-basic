package org.prgrms.kdt.commadLineApp;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.customer.CustomerService;
import org.prgrms.kdt.voucher.VoucherService;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class CommandLineController {

    private final VoucherService voucherService;
    private final CustomerService customerService;

    public CommandLineController(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public void startProgram(File blackList) throws IOException {
        while (true) {
            InPutView.startProgram();
            doCommand(chooseCommand(), blackList);
        }
    }

    public CommandType chooseCommand() throws IOException {
        return CommandLineInput.inputCommand();
    }

    public void doCommand(CommandType command, File blackList) throws IOException {
        if (command == CommandType.CREATE) {
            createCommand();
            return;
        }

        if (command == CommandType.LIST){
            listCommand();
            return;
        }

        if (command == CommandType.EXIT) {
            exitCommand();
        }

        if (command == CommandType.BLACKLIST) {
            blackListCommand(blackList);
            return;
        }

        if (command == CommandType.JOIN){
            joinNewCustomer();
            return;
        }

        if (command == CommandType.CUSTOMERLIST){
            showCustomerList();
            return;
        }
    }

    public void showCustomerList() {
        List<Customer> customerList = customerService.getAllCustomers();
        OutPutView.showCustomerList(customerList);
    }

    public void joinNewCustomer() throws IOException {
        InPutView.inputCustomerName();
        String name = CommandLineInput.inputCustomerName();
        InPutView.inputCustomerEmail();
        String email = CommandLineInput.inputCustomerEmail();
        if (customerService.checkEmail(email)) {
            customerService.createCustomer(email, name);
        }
        else{
            OutPutView.wrongEmail(email);
        }
    }

    public void blackListCommand(File blackList) throws IOException {
        OutPutView.showBlackList(blackList);
    }

    public void createCommand() throws IOException {
        InPutView.chooseType();
        VoucherType type = CommandLineInput.inputType();
        String amount =  CommandLineInput.inputAmount(type);
        voucherService.createVoucher(type, amount);
    }

    public void exitCommand() throws IOException {
        InPutView.exit();
        CommandLineInput.closeReader();
        System.exit(0);
    }

    public void listCommand(){
        if(voucherService.getVoucherList().isEmpty())
            InPutView.listIsEmpty();
        else{
            Map<UUID, Voucher> voucherList = voucherService.getVoucherList();
            OutPutView.showList(voucherList);
        }
    }
}
