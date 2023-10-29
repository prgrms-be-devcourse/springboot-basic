package com.pgms.part1.domain.voucher.controller;

import com.pgms.part1.domain.voucher.dto.VoucherCreateRequestDto;
import com.pgms.part1.domain.voucher.dto.VoucherResponseDto;
import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;
import com.pgms.part1.domain.voucher.service.VoucherService;
import com.pgms.part1.view.VoucherConsoleView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController {
    private final Logger log = LoggerFactory.getLogger(VoucherController.class);
    private final VoucherConsoleView voucherConsoleView;
    private final VoucherService voucherService;
    private static final int FIXED_VOUCHER_CREATE = 1;
    private static final int PERCENT_VOUCHER_CREATE = 2;
    private static final int FIXED_VOUCHER_DISCOUNT_MAX = 100000;

    public VoucherController(VoucherService voucherService, VoucherConsoleView voucherConsoleView) {
        this.voucherService = voucherService;
        this.voucherConsoleView = voucherConsoleView;
    }

    public void getMenu() {
        String command = voucherConsoleView.getMenu();

        switch (command) {
            case "create" -> createVoucher();
            case "list" -> listVoucher();
            case "delete" -> deleteVoucher();
            case "exit" -> {return;}
            default -> {
                voucherConsoleView.error(new RuntimeException("Please Enter Again!!"));
                log.warn("Invalid Menu Command Input");
            }
        }

        getMenu();
    }

    private void deleteVoucher() {
        Long id = voucherConsoleView.deleteVoucher();
        voucherService.deleteVoucher(id);
    }

    public void createVoucher() {
        VoucherCreateRequestDto voucherCreateRequestDto = voucherConsoleView.createVoucher();

        if (!isDiscountAmount(voucherCreateRequestDto)) {
            voucherConsoleView.error(new RuntimeException("Please Enter Again!!"));
            log.warn("Invalid Create Input");
            createVoucher();
        }

        switch (voucherCreateRequestDto.command()) {
            case FIXED_VOUCHER_CREATE -> voucherService.createVoucher(voucherCreateRequestDto, VoucherDiscountType.FIXED_AMOUNT_DISCOUNT);
            case PERCENT_VOUCHER_CREATE -> voucherService.createVoucher(voucherCreateRequestDto, VoucherDiscountType.PERCENT_DISCOUNT);
        }
    }

    private boolean isDiscountAmount(VoucherCreateRequestDto voucherCreateRequestDto) {
        switch (voucherCreateRequestDto.command()) {
            case FIXED_VOUCHER_CREATE -> {
                return voucherCreateRequestDto.discount() >= 0 && voucherCreateRequestDto.discount() <= FIXED_VOUCHER_DISCOUNT_MAX;
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
        List<VoucherResponseDto> voucherResponseDtos = voucherService.listVoucher();
        voucherConsoleView.listVoucher(voucherResponseDtos);
    }
}
