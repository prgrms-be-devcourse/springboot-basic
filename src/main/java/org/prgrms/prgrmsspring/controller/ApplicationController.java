package org.prgrms.prgrmsspring.controller;

import org.prgrms.prgrmsspring.domain.Command;
import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.service.CustomerService;
import org.prgrms.prgrmsspring.service.VoucherService;
import org.prgrms.prgrmsspring.view.CommandLineView;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class ApplicationController {

    private final VoucherService voucherService;
    private final CommandLineView commandLineView;
    private final CustomerService customerService;

    public ApplicationController(VoucherService voucherService, CommandLineView commandLineView, CustomerService customerService) {
        this.voucherService = voucherService;
        this.commandLineView = commandLineView;
        this.customerService = customerService;
    }

    public void start(Command command) {
        command.run(this);
    }

    public void exit() {
        voucherService.exit();
    }

    public void create() {
        Voucher voucher = commandLineView.createVoucher();
        voucherService.create(voucher);
    }

    public void update() {
        Voucher updateVoucher = commandLineView.updateVoucher();
        voucherService.update(updateVoucher);
    }

    public void delete() {
        UUID deleteVoucherId = commandLineView.deleteVoucher();
        voucherService.delete(deleteVoucherId);
    }

    public void list() {
        List<Voucher> vouchers = voucherService.list();
        commandLineView.printAll(vouchers);
    }

    public void showBlackList() {
        List<Customer> blackCustomers = customerService.findBlackAll();
        commandLineView.printAll(blackCustomers);
    }

    public Command getCommand() {
        return commandLineView.getCommand();
    }
}
