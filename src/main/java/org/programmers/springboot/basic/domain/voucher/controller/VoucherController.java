package org.programmers.springboot.basic.domain.voucher.controller;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.AppConstants;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherRequestDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherResponseDto;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.programmers.springboot.basic.domain.voucher.exception.DuplicateVoucherException;
import org.programmers.springboot.basic.domain.voucher.exception.IllegalDiscountException;
import org.programmers.springboot.basic.domain.voucher.exception.VoucherNotFoundException;
import org.programmers.springboot.basic.domain.voucher.mapper.VoucherEntityMapper;
import org.programmers.springboot.basic.domain.voucher.service.VoucherService;
import org.programmers.springboot.basic.util.manager.ConsoleIOManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class VoucherController {

    private final ConsoleIOManager consoleIOManager;
    private final VoucherService voucherService;

    @Autowired
    public VoucherController(ConsoleIOManager consoleIOManager, VoucherService voucherService) {
        this.consoleIOManager = consoleIOManager;
        this.voucherService = voucherService;
    }

    public void create() {

        boolean flag = true;
        int type = AppConstants.NONE;
        Long discount = null;
        VoucherType voucherType = null;

        while (flag) {

            consoleIOManager.printVoucherCreateHandler();

            try {
                type = consoleIOManager.getInteger();
            } catch (NumberFormatException e) {
                log.error(e.toString());
            }

            switch (type) {
                case AppConstants.FIXED_AMOUNT, AppConstants.PERCENT_DISCOUNT -> {
                    discount = requestDiscount();
                    flag = false;
                }
                default -> System.out.println("[System] 잘못된 모드의 접근입니다.");
            }
        }

        try {
            voucherType = VoucherType.valueOfVoucherByValue(type);
            VoucherRequestDto requestDto = VoucherEntityMapper.INSTANCE.entityToDtoWithDetails(voucherType, discount);
            this.voucherService.create(requestDto);
        } catch (IllegalDiscountException e) {
            log.error(e.toString());
        } catch (DuplicateVoucherException e) {
            log.warn("voucher of voucherType '{}' and discount '{} is already exists", voucherType, discount);
        }
    }

    private Long requestDiscount() {

        boolean flag = true;
        String input = null;
        Long discount = null;

        while (flag) {
            try {
                consoleIOManager.printRequestDiscount();
                input = consoleIOManager.getInput();
                discount = Long.parseLong(input);
                flag = false;
            } catch (NumberFormatException e) {
                log.error("Your input is Invalid Type of Long: '{}", input);
            }
        }

        return discount;
    }

    public void list() {

        List<VoucherResponseDto> responseDtos = voucherService.findAll();
        consoleIOManager.printVoucher(responseDtos);
    }

    public void find() {

        consoleIOManager.printFindHandler();

        String input = consoleIOManager.getInput();

        try {
            VoucherRequestDto requestDto = VoucherEntityMapper.INSTANCE.entityToDtoWithUUID(input);
            VoucherResponseDto responseDto = this.voucherService.findById(requestDto);
            consoleIOManager.printFoundVoucher(responseDto);
        } catch (VoucherNotFoundException e) {
            log.warn("No voucher found for voucherId: '{}'", input);
        } catch (IllegalArgumentException e) {
            log.error("Your input is Invalid UUID string: '{}'", input);
        }
    }

    public void update() {

        String input = null;
        Long discount = null;

        consoleIOManager.printUpdateHandler();

        try {
            input = consoleIOManager.getInput();
            discount = Long.parseLong(input);
            input = consoleIOManager.getInput();
            VoucherRequestDto requestDto = VoucherEntityMapper.INSTANCE.entityToDtoWithDiscount(input, discount);
            this.voucherService.updateVoucher(requestDto);
        } catch (NumberFormatException e) {
            log.error("Your input is Invalid Type of Long: '{}'", input);
        } catch (IllegalArgumentException e) {
            log.error("Your input is Invalid UUID string: '{}'", input);
        } catch (VoucherNotFoundException e) {
            log.warn("No voucher of voucherId '{}' found", input);
        }
    }

    public void delete() {

        String input = null;

        consoleIOManager.printVoucherDeleteHandler();

        try {
            input = consoleIOManager.getInput();
            VoucherRequestDto requestDto = VoucherEntityMapper.INSTANCE.entityToDtoWithUUID(input);
            this.voucherService.deleteVoucher(requestDto);
        } catch (IllegalArgumentException e) {
            log.error("Your input is Invalid UUID string: '{}'", input);
        } catch (VoucherNotFoundException e) {
            log.warn("No voucher of voucherId '{}' found", input);
        }

    }

    public void deleteAll() {

        consoleIOManager.printDeleteAllHandler();

        switch (consoleIOManager.getInput()) {
            case "Y", "y" -> this.voucherService.deleteAll();
            default -> {}
        }
    }
}
