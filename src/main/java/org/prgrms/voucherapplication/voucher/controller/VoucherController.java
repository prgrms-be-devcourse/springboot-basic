package org.prgrms.voucherapplication.voucher.controller;

import org.prgrms.voucherapplication.console.CommandType;
import org.prgrms.voucherapplication.console.Input;
import org.prgrms.voucherapplication.console.Output;
import org.prgrms.voucherapplication.customer.service.CustomerService;
import org.prgrms.voucherapplication.customer.dto.ResponseBlacklist;
import org.prgrms.voucherapplication.voucher.entity.Voucher;
import org.prgrms.voucherapplication.voucher.entity.VoucherConstructorException;
import org.prgrms.voucherapplication.voucher.entity.VoucherType;
import org.prgrms.voucherapplication.voucher.entity.VoucherTypeOfException;
import org.prgrms.voucherapplication.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {

    private final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private static final String REQUEST_MESSAGE_VOUCHER_TYPE = "원하시는 바우처 이름을 입력해주세요.";

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public VoucherController(Input input, Output output, VoucherService voucherService, CustomerService customerService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.customerService = customerService;
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
                    findAll();
                    break;
                case BLACKLIST:
                    getBlacklist();
                    break;
            }
        }
    }

    public void getBlacklist() {
        List<ResponseBlacklist> blacklist = customerService.findBlacklist();
        blacklist.forEach(blackCustomer -> output.display(blackCustomer.toString()));
    }

    public void findAll() {
        List<Voucher> voucherList = voucherService.findAll();
        voucherList.forEach(voucher -> output.display(voucher.toString()));
    }

    public void create() {
        VoucherType voucherType = getVoucherType();

        boolean isSuccess = false;
        while (!isSuccess) {
            String discount;
            Voucher voucher;

            discount = input.command();
            try {
                voucher = voucherType.createVoucher(UUID.randomUUID(), Integer.parseInt(discount), LocalDateTime.now());
            } catch (VoucherConstructorException e) {
                continue;
            }

            voucherService.create(voucher);
            isSuccess = true;
        }
    }

    public VoucherType getVoucherType() {
        VoucherType voucherType = null;

        boolean isSuccess = false;
        while (!isSuccess) {
            output.display(REQUEST_MESSAGE_VOUCHER_TYPE);
            String voucherNames = VoucherType.getNames();
            output.display(voucherNames);
            String voucherNameInput = input.command();

            try {
                voucherType = VoucherType.of(voucherNameInput);
            } catch (VoucherTypeOfException e) {
                continue;
            }

            output.display(voucherType.getDiscountGuide());
            isSuccess = true;
        }
        return voucherType;
    }
}
