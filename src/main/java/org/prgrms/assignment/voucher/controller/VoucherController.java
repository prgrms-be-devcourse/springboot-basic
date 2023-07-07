package org.prgrms.assignment.voucher.controller;

import org.prgrms.assignment.voucher.model.Menu;
import org.prgrms.assignment.voucher.model.Voucher;
import org.prgrms.assignment.voucher.model.VoucherType;
import org.prgrms.assignment.voucher.service.VoucherService;
import org.prgrms.assignment.voucher.service.VoucherServiceImpl;
import org.prgrms.assignment.voucher.view.Input;
import org.prgrms.assignment.voucher.view.Output;
import org.springframework.stereotype.Controller;

import java.nio.ByteBuffer;
import java.util.InputMismatchException;
import java.util.UUID;

@Controller
public class VoucherController {

    private static final String SELECT_VOUCHER_MESSAGE = "TYPE YOUR VOUCHER";
    private static final String TYPE_DURATION_MESSAGE = "TYPE YOUR VOUCHER'S DURATION";

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public VoucherController(Input input, Output output, VoucherServiceImpl voucherService) {
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

                        output.printMessage(TYPE_DURATION_MESSAGE);
                        Long durationDate = input.getDurationInput();

                        voucherService.createVoucher(voucherType, benefit, durationDate);
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
