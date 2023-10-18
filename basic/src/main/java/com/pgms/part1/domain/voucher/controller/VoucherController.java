package com.pgms.part1.domain.voucher.controller;

import com.pgms.part1.domain.view.ConsoleView;
import com.pgms.part1.domain.voucher.dto.VoucherCreateRequestDto;
import com.pgms.part1.domain.voucher.dto.VoucherMenuRequestDto;
import com.pgms.part1.domain.voucher.dto.VoucherResponseDto;
import com.pgms.part1.domain.voucher.entity.Voucher;
import com.pgms.part1.domain.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class VoucherController {
    private final Logger log = LoggerFactory.getLogger(VoucherController.class);
    private final ConsoleView consoleView;
    private final VoucherService voucherService;

    private final int FIXED_VOUCHER_CREATE = 1;
    private final int PERCENT_VOUCHER_CREATE = 2;

    public VoucherController(ConsoleView consoleView, VoucherService voucherService) {
        this.consoleView = consoleView;
        this.voucherService = voucherService;
    }

    public void init() {
        VoucherMenuRequestDto voucherMenuRequestDto = consoleView.init();

        switch (voucherMenuRequestDto.command()) {
            case "create" -> createVoucher();
            case "list" -> listVoucher();
            case "exit" -> exitVoucher();
            default -> {
                consoleView.error(new RuntimeException("Please Enter Again!!"));
                log.error("Invalid Menu Command Input");
                init();
            }
        }
    }

    public void createVoucher(){
        VoucherCreateRequestDto voucherCreateRequestDto = consoleView.createVoucher();

        if(!validateDiscountAmount(voucherCreateRequestDto)){
            consoleView.error(new RuntimeException("Please Enter Again!!"));
            log.error("Invalid Create Input");
            createVoucher();
        }

        switch (voucherCreateRequestDto.command()) {
            case FIXED_VOUCHER_CREATE -> voucherService.createFixedAmountVoucher(voucherCreateRequestDto);
            case PERCENT_VOUCHER_CREATE -> voucherService.createPercentDiscountVoucher(voucherCreateRequestDto);
        }

        init();
    }

    private boolean validateDiscountAmount(VoucherCreateRequestDto voucherCreateRequestDto) {
        switch (voucherCreateRequestDto.command()) {
            case FIXED_VOUCHER_CREATE -> {
                return voucherCreateRequestDto.discount() >= 0;
            }
            case PERCENT_VOUCHER_CREATE -> {
                return voucherCreateRequestDto.discount() >= 0 && voucherCreateRequestDto.discount() <= 100;
            }
            default -> {
                return false;
            }
        }
    }

    public void listVoucher(){
        List<Voucher> vouchers = voucherService.listVoucher();
        List<VoucherResponseDto> voucherResponseDtos =
                vouchers.stream().map(v -> new VoucherResponseDto(v.getId(), v.getDiscount(), v.getVoucherDiscountType()))
                        .collect(Collectors.toList());

        consoleView.listVoucher(voucherResponseDtos);
        init();
    }

    public void exitVoucher(){
        System.exit(0);
    }
}
