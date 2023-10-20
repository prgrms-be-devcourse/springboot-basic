package org.prgrms.prgrmsspring.controller;

import org.prgrms.prgrmsspring.domain.Command;
import org.prgrms.prgrmsspring.entity.user.User;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.service.UserService;
import org.prgrms.prgrmsspring.service.VoucherService;
import org.prgrms.prgrmsspring.view.CommandLineView;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ApplicationController {

    private final VoucherService voucherService;
    private final CommandLineView commandLineView;
    private final UserService userService;

    public ApplicationController(VoucherService voucherService, CommandLineView commandLineView, UserService userService) {
        this.voucherService = voucherService;
        this.commandLineView = commandLineView;
        this.userService = userService;
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

    public void list() {
        List<Voucher> vouchers = voucherService.list();
        commandLineView.printAll(vouchers);
    }

    public void showBlackList() {
        List<User> blackUsers = userService.findBlackAll();
        commandLineView.printAll(blackUsers);
    }

    public Command getCommand() {
        return commandLineView.getCommand();
    }
}
