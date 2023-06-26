package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.model.Command;
import org.prgrms.kdt.voucher.model.Menu;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.prgrms.kdt.voucher.view.Input;
import org.prgrms.kdt.voucher.view.Output;

import java.util.stream.Stream;

public class Controller {
    // VoucherRepository로부터 Controller로 DTO로 받는다..?
    private final Input input;
    private final Output output;
    private final VoucherRepository voucherRepository;
    private final Stream<Menu> menuStream = Menu.getMenusToStream();


    public Controller(Input input, Output output, VoucherRepository voucherRepository) {
        this.input = input;
        this.output = output;
        this.voucherRepository = voucherRepository;
    }

    public void run() {
        while(true) {
            try {
                output.showMenu(Menu.values());
                Command command = new Command(input.getCommandInput());
                switch (Menu.of(command.getCommand())) {
                    case EXIT-> {
                        return;
                    }
                    case CREATE -> {
                        input.getVoucherInput();
                        break;
                    }
                    // 여기서 DTO로 만들어야 됨.
                    case LIST -> {
                        output.showVoucherList(VoucherService
                                .toVoucherDTOList(voucherRepository.findAll()));
                    }
                }
            } catch(IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}
