package org.prgrms.prgrmsspring.console;

import org.prgrms.prgrmsspring.controller.VoucherController;
import org.prgrms.prgrmsspring.domain.command.Command;
import org.prgrms.prgrmsspring.domain.command.VoucherCommand;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.view.CommandLineView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class VoucherConsole implements ConsoleIOManager {

    @Autowired
    private CommandLineView commandLineView;
    @Autowired
    private VoucherController voucherController;

    public void create() {
        Voucher voucher = voucherController.create(commandLineView.createVoucher());
        commandLineView.print(voucher);
    }

    public void update() {
        Voucher updateVoucher = voucherController.update(commandLineView.updateVoucher());
        commandLineView.print(updateVoucher);
    }

    public void delete() {
        UUID deleteVoucherId = commandLineView.deleteVoucher();
        voucherController.delete(deleteVoucherId);
    }

    public void findAll() {
        List<Voucher> allVouchers = voucherController.findAll();
        commandLineView.printAll(allVouchers);
    }

    public void run(String commandName) {
        Command command = Command.from(commandName, VoucherCommand.class);
        command.run(this);
    }

    @Override
    public void printCommands() {
        List<String> modeStrings = this.getModeStrings(VoucherCommand.values());
        commandLineView.printAll(modeStrings);
    }
}
