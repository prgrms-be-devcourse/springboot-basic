package org.prgrms.voucherapplication.voucher.controller;

import org.prgrms.voucherapplication.console.CommandType;
import org.prgrms.voucherapplication.console.Input;
import org.prgrms.voucherapplication.console.Output;
import org.prgrms.voucherapplication.voucher.entity.Voucher;
import org.prgrms.voucherapplication.voucher.entity.VoucherType;
import org.prgrms.voucherapplication.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private static final String REQUEST_MESSAGE_VOUCHER_TYPE = "원하시는 바우처 이름을 입력해주세요.";
    private String blacklistString;

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public VoucherController(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    public void init(String blacklist) {
        blacklistString = blacklist;
    }

    public void start() {
        String description = "=== Voucher Program ===\n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers.\n" +
            "Type blacklist to list all customer_blacklist.";

        boolean isExited = false;

        while (!isExited) {
            output.display(description);

            String type = input.command();

            CommandType commandType = CommandType.valueOf(type);

            switch (commandType) {
                case EXIT:
                    isExited = true;
                    break;

                case CREATE:
                    create();
                    break;

                case LIST:
                    getList();
                    break;
                case BLACKLIST:
                    getBlacklist();
                    break;
            }
        }
    }

    private void getBlacklist() {
        output.display(blacklistString);
    }

    public void getList() {
        String voucherList = voucherService.getList();
        output.display(voucherList);
    }

    public void create() {
        VoucherType voucherType = getVoucherType();

        String discount;
        Voucher voucher;
        try {
            discount = input.command();
            voucher = voucherType.createVoucher(Integer.parseInt(discount));
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            discount = input.command();
            voucher = voucherType.createVoucher(Integer.parseInt(discount));
        }
        voucherService.create(voucher);
    }

    public VoucherType getVoucherType() {
        output.display(REQUEST_MESSAGE_VOUCHER_TYPE);
        String voucherNames = VoucherType.getNames();
        output.display(voucherNames);
        String voucherNameInput = input.command();
        VoucherType voucherType;

        try {
            voucherType = VoucherType.of(voucherNameInput);
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            output.display(voucherNames);
            voucherNameInput = input.command();
            voucherType = VoucherType.of(voucherNameInput);
        }

        output.display(voucherType.getDiscountGuide());
        return voucherType;
    }
}
