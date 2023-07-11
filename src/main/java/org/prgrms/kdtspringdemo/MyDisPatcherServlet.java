package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.view.constant.MainCommandType;
import org.prgrms.kdtspringdemo.view.console.VoucherConsole;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherCommandType;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherResponseDto;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class MyDisPatcherServlet implements CommandLineRunner {
    private static final String MAIN_PROGRAM_INIT_MESSAGE = """
            === Voucher Program ===
            EXIT -> 프로그램 종료
            VOUCHER -> 바우처 관련 서비스
            CUSTOMER -> 소비자 관련 서비스
            """;
    private static final String SYSTEM_SHUTDOWN_MESSAGE = "시스템을 종료합니다.\n";
    private static final String VOUCHER_SERVICE_INIT_MESSAGE = """
            === Voucher Program ===
            CREATE -> 바우처 생성
            LIST -> 특정 바우처 조회
            LIST_ALL -> 바우처 전체 조회
            UPDATE -> 특정 바우처 업데이트
            DELETE -> 특정 바우처 삭제
            """;
    private static final String CHOICE_VOUCHER_TYPE_MESSAGE = "바우처 타입을 입력하세요.(ex : FIXED or PERCENT)\n";
    private static final String AMOUNT_VOUCHER_MESSAGE = "할인 금액을 입력하세요.\n";
    private static final String PRINT_VOUCHER_INFO_MESSAGE = """
            id : %s
            type : %s
            discount amount : %s
            """;
    private static final String VOUCHER_ID_MESSAGE = "바우처 Id를 입력하세요.\n";

    private final VoucherConsole voucherConsole = new VoucherConsole();
    private final VoucherService voucherService;

    public MyDisPatcherServlet(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void run(String... args) {
        MainCommandType userCommand = voucherConsole.inputMainCommand(MAIN_PROGRAM_INIT_MESSAGE);

        while (userCommand.isRunning()) {
            executeCommand(userCommand);
            userCommand = voucherConsole.inputMainCommand(MAIN_PROGRAM_INIT_MESSAGE);
        }
    }

    private void executeCommand(MainCommandType commandtype) {
        switch (commandtype) {
            case EXIT -> voucherConsole.printMessage(SYSTEM_SHUTDOWN_MESSAGE);
            case VOUCHER -> runVoucherService();
        }
    }

    private void runVoucherService() {
        VoucherCommandType userCommand = voucherConsole.inputVoucherCommand(VOUCHER_SERVICE_INIT_MESSAGE);

        switch (userCommand) {
            case CREATE -> createVoucher();
            case LIST -> getVoucher();
            case LIST_ALL -> getAllVoucher();
            case UPDATE -> updateVoucher();
        }
    }

    private void createVoucher() {
        VoucherType userVoucherType = voucherConsole.chooseVoucherType(CHOICE_VOUCHER_TYPE_MESSAGE);
        Long userAmount = voucherConsole.inputAmountByVoucher(AMOUNT_VOUCHER_MESSAGE);

        VoucherResponseDto voucherResponseDto = voucherService.create(userVoucherType, userAmount);
        voucherConsole.printVoucher(PRINT_VOUCHER_INFO_MESSAGE, voucherResponseDto.getVoucherId(), voucherResponseDto.getVoucherType(), voucherResponseDto.getAmount());
    }

    private void getVoucher() {
        UUID userVoucherId = voucherConsole.inputVoucherId(VOUCHER_ID_MESSAGE);

        VoucherResponseDto voucherResponseDto = voucherService.getVoucher(userVoucherId);
        voucherConsole.printVoucher(PRINT_VOUCHER_INFO_MESSAGE, voucherResponseDto.getVoucherId(), voucherResponseDto.getVoucherType(), voucherResponseDto.getAmount());
    }

    private void getAllVoucher() {
        List<VoucherResponseDto> vouchers = voucherService.getAllVoucher();
        for (VoucherResponseDto voucherResponseDto : vouchers) {
            voucherConsole.printVoucher(PRINT_VOUCHER_INFO_MESSAGE, voucherResponseDto.getVoucherId(), voucherResponseDto.getVoucherType(), voucherResponseDto.getAmount());
        }
    }

    private void updateVoucher() {
        UUID userVoucherId = voucherConsole.inputVoucherId(VOUCHER_ID_MESSAGE);
        VoucherType userVoucherType = voucherConsole.chooseVoucherType(CHOICE_VOUCHER_TYPE_MESSAGE);
        Long userAmount = voucherConsole.inputAmountByVoucher(AMOUNT_VOUCHER_MESSAGE);

        VoucherResponseDto voucherResponseDto = voucherService.updateVoucher(userVoucherId, userVoucherType, userAmount);
        voucherConsole.printVoucher(PRINT_VOUCHER_INFO_MESSAGE, voucherResponseDto.getVoucherId(), voucherResponseDto.getVoucherType(), voucherResponseDto.getAmount());
    }
}
