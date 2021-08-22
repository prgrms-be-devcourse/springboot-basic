package com.prgms.kdtspringorder.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import com.prgms.kdtspringorder.adapter.exception.InvalidDiscountException;
import com.prgms.kdtspringorder.application.VoucherService;
import com.prgms.kdtspringorder.config.AppConfig;
import com.prgms.kdtspringorder.domain.model.voucher.Voucher;
import com.prgms.kdtspringorder.domain.model.voucher.VoucherType;
import com.prgms.kdtspringorder.ui.Command;
import com.prgms.kdtspringorder.ui.Printer;
import com.prgms.kdtspringorder.ui.Receiver;

@Controller
public class VoucherController {
    private final Receiver receiver = new Receiver();
    private final Printer printer = new Printer();

    public void manageVouchers() {
        List<Voucher> vouchers = new ArrayList<>();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        VoucherService voucherService = context.getBean(VoucherService.class);

        printer.printCommandList();

        while (true) {
            String command = receiver.enterCommand().toUpperCase();

            if (command.equals(Command.EXIT.name())) {
                break;
            }
            if (command.equals(Command.CREATE.name())) {
                createVoucher(vouchers, voucherService);
                continue;
            }
            if (command.equals(Command.LIST.name())) {
                printer.listVouchers(vouchers);
                continue;
            }
            if (command.equals(Command.HELP.name())) {
                printer.printCommandList();
                continue;
            }

            printer.printInvalidCommandMessage();
        }
    }

    private void createVoucher(List<Voucher> vouchers, VoucherService voucherService) {
        try {
            VoucherType voucherType = VoucherType.valueOf(receiver.enterVoucherType().toUpperCase());
            long discount = receiver.enterDiscountAmount();

            Voucher voucher = voucherService.createVoucher(UUID.randomUUID(), voucherType, discount);
            vouchers.add(voucher);
        } catch (InvalidDiscountException e) {
            printer.printInvalidDiscount(e.getMessage());   // 할인률이 100% 초과일 경우
        } catch (IllegalArgumentException e) {
            printer.printInvalidVoucherType(e.getMessage());    // 잘못된 voucher type을 입력했을 경우
        }
    }
}
