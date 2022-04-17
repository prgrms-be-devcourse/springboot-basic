package com.programmers.part1.ui;

import com.programmers.part1.io.Console;
import com.programmers.part1.io.Message;
import com.programmers.part1.member.MemberController;
import com.programmers.part1.order.voucher.VoucherController;
import com.programmers.part1.domain.VoucherType;
import lombok.Builder;

import java.io.IOException;


/**
 * Author : Jung
 * 1. 사용자로부터 입출력을 받는 UI 계층으로 지정하였습니다.
 * 2. VoucherController, MemberController 외에도 추가적으로 매핑 받을 수 있게 하였으나,
 * 제가 handler 처리에 미숙해서 리팩터에 실패하였습니다. -> 조언을 받고 싶습니다.
 * 3. Controller 및 도메인에서 에러를 던져서 받았는데 exception 처리 책임이 잘 나뉘어졌는지 궁금합니다.
 */
public class Client implements Runnable {

    private final Console console;
    private final MemberController memberController;
    private final VoucherController voucherController;

    @Builder
    public Client(Console console, MemberController memberController, VoucherController voucherController) {
        this.console = console;
        this.memberController = memberController;
        this.voucherController = voucherController;
    }

    @Override
    public void run() {

        boolean isWorking = true;

        while (isWorking) {
            console.write(Message.START_PROMPT);

            try {
                // 요청 받기
                String request = console.readLine();
                CommandType requestCommandType = CommandType.getCommandType(request);

                switch (requestCommandType) {
                    case CREATE -> createVoucher(voucherController);
                    case LIST -> getAllVoucher(voucherController);
                    case BLACKLIST -> getAllBlackMembers(memberController);
                    case EXIT -> {
                        isWorking = false;
                        exitApp();
                    }
                }
            } catch (RuntimeException e) {
                console.write(e.getMessage());
            }
        }
    }

    private void exitApp() {
        console.write(Message.EXIT);
    }


    private void createVoucher(VoucherController voucherController) {
        // 1. voucher type 입력
        console.write(Message.VOUCHER_PROMPT);
        VoucherType requestVoucherType = VoucherType.getVoucherType(console.readLine());

        console.write(Message.AMOUNT_PROMPT);
        long amount = Long.parseLong(console.readLine());

        voucherController.create(requestVoucherType, amount);
        console.write(Message.CREATE_SUCCESS);
    }

    private void getAllVoucher(VoucherController voucherController) {
        voucherController.list()
                .iterator()
                .forEachRemaining(System.out::println);
    }

    private void getAllBlackMembers(MemberController memberController){
        try {
            memberController.list()
                    .iterator()
                    .forEachRemaining(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
