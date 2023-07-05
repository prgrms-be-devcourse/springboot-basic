package org.prgrms.assignment.voucher.controller;

import org.prgrms.assignment.voucher.model.Menu;
import org.prgrms.assignment.voucher.model.Voucher;
import org.prgrms.assignment.voucher.model.VoucherType;
import org.prgrms.assignment.voucher.service.VoucherService;
import org.prgrms.assignment.voucher.view.Input;
import org.prgrms.assignment.voucher.view.Output;
import org.springframework.stereotype.Controller;

import java.nio.ByteBuffer;
import java.util.InputMismatchException;
import java.util.UUID;

import static org.prgrms.kdt.voucher.view.ConsoleOutput.SELECT_VOUCHER_MESSAGE;

@Controller
public class VoucherController {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public VoucherController(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    public void run() {
        while(true) {
            try {
                output.showMenu(Menu.values());
                String command = input.getCommandInput();

                switch (Menu.of(command)) {
                    case EXIT-> {
                        return;
                    }
                    case CREATE -> {
                        output.printMessage(SELECT_VOUCHER_MESSAGE);
                        output.showVoucherTypes(VoucherType.values());

                        String voucherTypeName = input.getVoucherInput();
                        VoucherType voucherType = VoucherType.of(voucherTypeName);

                        output.printMessage(voucherType.getBenefitMessage());
                        Long benefit = input.getBenefit();
                        voucherService.createVoucher(voucherType, benefit);
                    }
                    case LIST -> {
                        output.showVoucherList(voucherService
                                .convertToVoucherDTOs());
                    }
                }
            } catch(IllegalArgumentException | InputMismatchException e) {
                e.printStackTrace();
            }
        }
    }

    public static UUID convertBytesToUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        return new UUID(high, low);
    }
}
