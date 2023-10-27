package org.prgrms.prgrmsspring.controller;

import org.prgrms.prgrmsspring.domain.command.Command;
import org.prgrms.prgrmsspring.domain.command.VoucherCommand;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.service.VoucherService;
import org.prgrms.prgrmsspring.view.CommandLineView;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController implements ApplicationController {

    private final CommandLineView commandLineView;
    private final VoucherService voucherService;

    public VoucherController(CommandLineView commandLineView, VoucherService voucherService) {
        this.commandLineView = commandLineView;
        this.voucherService = voucherService;
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
        List<Voucher> vouchers = voucherService.findAll();
        commandLineView.printAll(vouchers);
    }

    @Override
    public void run(String commandName) {
        Command command = Command.from(commandName, VoucherCommand.class);
        command.run(this);
    }

    @Override
    public void listMode() {
        commandLineView.printAll(ApplicationController.getModeStrings(VoucherCommand.values()));
    }
}
