package org.prgrms.kdt.application.voucher;

import org.prgrms.kdt.application.voucher.io.Input;
import org.prgrms.kdt.application.voucher.io.Output;
import org.prgrms.kdt.domain.voucher.service.VoucherService;

public class VoucherApplication implements Runnable{
    private VoucherService voucherService;
    private Input input;
    private Output output;

    public VoucherApplication(VoucherService voucherService, Input input, Output output) {
        this.voucherService = voucherService;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        output.printProgramName();
        while(true) {
            output.printCommandList();
            switch (input.inputCommand()) {
                case EXIT -> {
                    exitCommand();
                    return;
                }
                case CREATE -> createCommand();
                case LIST -> listCommand();
                default -> output.printInputCommandError();
            }
        }
    }

    private void exitCommand() {
        output.printExit();
    }

    private void createCommand() {
        // 바우처 종류 안내 메시지 출력
        // 바우처 종류 입력
        // 바우처 종류 입력 검증
        // 바우처 금액 or 퍼센트 입력 안내 메시지
        // 바우처 금액 or 퍼센트 입력 검증
        // 바우처 생성 결과 출력
        System.out.println("생성");
    }

    private void listCommand() {
        // 바우처 목록 출력 (없을 경우는?)
        System.out.println("목록");
    }
}
