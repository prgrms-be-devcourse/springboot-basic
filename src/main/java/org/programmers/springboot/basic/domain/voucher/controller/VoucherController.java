package org.programmers.springboot.basic.domain.voucher.controller;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.AppConstants;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherRequestDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherResponseDto;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.programmers.springboot.basic.domain.voucher.exception.IllegalDiscountException;
import org.programmers.springboot.basic.domain.voucher.exception.VoucherNotFoundException;
import org.programmers.springboot.basic.domain.voucher.service.VoucherService;
import org.programmers.springboot.basic.util.manager.ConsoleIOManager;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class VoucherController {

    private final ConsoleIOManager consoleIOManager;
    private final VoucherService voucherService;

    public VoucherController(ConsoleIOManager consoleIOManager, VoucherService voucherService) {
        this.consoleIOManager = consoleIOManager;
        this.voucherService = voucherService;
    }

    public void create() {

        boolean flag = true;
        int type = AppConstants.NONE;
        Long discount = null;

        while (flag) {

            consoleIOManager.printCreateHandler();

            try {
                type = consoleIOManager.getInteger();
            } catch (NumberFormatException e) {
                log.error(e.toString());
            }

            switch (type) {
                case AppConstants.FIXED_AMOUNT, AppConstants.PERCENT_DISCOUNT -> {
                    try {
                        consoleIOManager.printDiscount();
                        discount = consoleIOManager.getLong();
                    } catch (NumberFormatException e) {
                        log.error(e.toString());
                    }
                    flag = false;
                }
                default -> System.out.println("[System] 잘못된 모드의 접근입니다.");
            }
        }

        try {
            VoucherType voucherType = VoucherType.valueOfVoucherByType(type);
            VoucherRequestDto requestDto = new VoucherRequestDto(voucherType, discount);
            this.voucherService.create(requestDto); // <-- IllegalDiscountException
        } catch (IllegalDiscountException e) {
            log.error(e.toString());
        }
    }

    public void list() {

        consoleIOManager.printListHandler();

        try {
            List<VoucherResponseDto> responseDtos = voucherService.findAll();
            consoleIOManager.printVoucher(responseDtos);
        } catch (VoucherNotFoundException e) {
            log.error(e.toString());
        }

    }
}
