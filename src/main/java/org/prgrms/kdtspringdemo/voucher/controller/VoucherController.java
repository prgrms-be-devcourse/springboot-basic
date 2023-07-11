package org.prgrms.kdtspringdemo.voucher.controller;

import org.prgrms.kdtspringdemo.view.console.VoucherConsole;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherCommandType;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherDto;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherController {
    private static final String INIT_MESSAGE = """
            === Voucher Program ===
            CREATE -> 바우처 생성
            LIST -> 특정 바우처 조회
            LIST_ALL -> 바우처 전체 조회
            UPDATE -> 특정 바우처 업데이트
            DELETE -> 특정 바우처 삭제
            """;
    private static final String CHOICE_VOUCHER_TYPE_MESSAGE = "바우처 타입을 입력하세요.(ex : FIXED or PERCENT)\n";
    private static final String AMOUNT_VOUCHER_MESSAGE = "할인 금액을 입력하세요.\n";
    private static final String SUCCESS_CREATED_VOUCHER = """
            type : %s
            discount amount : %s
            """;

    private final VoucherConsole voucherConsole = new VoucherConsole();
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void run() {
        VoucherCommandType userCommand = voucherConsole.inputVoucherCommand(INIT_MESSAGE);

        switch (userCommand) {
            case CREATE -> createVoucher();
            case LIST_ALL -> getAllVoucher();
        }
    }

    private void createVoucher() {
        VoucherType userVoucherType = voucherConsole.chooseVoucherType(CHOICE_VOUCHER_TYPE_MESSAGE);
        Long amount = voucherConsole.inputAmountByVoucher(AMOUNT_VOUCHER_MESSAGE);

        VoucherDto voucherDto = voucherService.create(userVoucherType, amount);
        voucherConsole.printCreatedVoucher(SUCCESS_CREATED_VOUCHER, voucherDto.getVoucherType(), voucherDto.getAmount());
    }

    private void getAllVoucher() {
        List<VoucherDto> vouchers = voucherService.getAllVoucher();
        for (VoucherDto voucherDto : vouchers) {
            voucherConsole.printCreatedVoucher(SUCCESS_CREATED_VOUCHER, voucherDto.getVoucherType(), voucherDto.getAmount());
        }
    }
}
