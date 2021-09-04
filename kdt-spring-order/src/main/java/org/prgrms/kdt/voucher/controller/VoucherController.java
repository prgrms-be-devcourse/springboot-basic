package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.voucher.service.VoucherService;

import java.util.UUID;

public class VoucherController {
    private final VoucherService voucherService;
    private final Console console = new Console();

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
        // 초기 문구 출력
        console.printInitText();
    }

    public void run() {
        // 프로그램 시작
        while (true) {
            String command = console.inputCommand();
            switch (command) {
                // voucher 생성
                case "create" -> {
                    // voucher 타입 선택
                    String inputType = console.inputVoucherType();
                    VoucherType voucherType = VoucherType.convert(inputType);
                    switch (voucherType){
                        case FIXED, PERCENT -> {
                            long voucherValue = console.inputVoucherValue();
                            voucherService.createVoucher(UUID.randomUUID(), voucherType, voucherValue);
                            console.printSuccess();
                        }
                        case UNDEFINED -> {
                            console.printCommandError(inputType);
                        }
                    }
                }
                // voucher 레포 리스트 출력
                case "list" -> {
                    console.printVoucherList(voucherService.getVoucherList());
                }
                // 프로그램 종료
                case "exit" -> {
                    console.printExitText();
                    return;
                }
                // 커맨드 오류시 메시지
                default -> console.printCommandError(command);
            }
        }
    }

}
