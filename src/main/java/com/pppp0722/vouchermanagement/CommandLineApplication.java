package com.pppp0722.vouchermanagement;

import com.pppp0722.vouchermanagement.io.Console;
import com.pppp0722.vouchermanagement.member.model.Member;
import com.pppp0722.vouchermanagement.member.service.MemberService;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
import com.pppp0722.vouchermanagement.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CommandLineApplication {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    private final Console console = new Console();
    private final VoucherService voucherService;
    private final MemberService memberService;

    public CommandLineApplication(VoucherService voucherService, MemberService memberService) {
        this.voucherService = voucherService;
        this.memberService = memberService;
    }

    // 프로그램 실행
    public void run() {
        logger.info("Start voucher management application");
        console.printLogo();

        boolean isExit = false;
        while(!isExit) {
            console.printMenu();
            String command = console.getCommand("Input : ");
            CommandType commandType = CommandType.getCommandType(command);
            switch(commandType) {
                case CREATE:
                    createVoucher();
                    break;
                case LIST:
                    printVoucherList();
                    break;
                case BLACK:
                    printBlackList();
                    break;
                case EXIT:
                    isExit = true;
                    break;
                default:
                    console.printInputError();
                    logger.error("Invalid command -> {}", command);
            }
            console.printEmpty();
        }

        logger.info("Terminate voucher management application");
    }

    // voucher 생성
    public void createVoucher() {
        // voucher type 예외처리
        VoucherType voucherType;
        while(true) {
            console.printVoucherTypeInputRequest();
            voucherType = VoucherType.getVoucherType(console.getCommand("Input : "));

            if(!voucherType.equals(VoucherType.FIXED_AMOUNT) &&
                    !voucherType.equals(VoucherType.PERCENT_DISCOUNT)){
                logger.error("Invalid voucher type.");
                console.printInputError();
            }
            else break;
        }

        // voucher amount 예외처리
        long discountAmount;
        while(true) {
            console.printAmountInputRequest();
            discountAmount = Long.parseLong(console.getCommand("Input : "));
            if ((voucherType.equals(VoucherType.FIXED_AMOUNT) && discountAmount < 1) ||
                    (voucherType.equals(VoucherType.PERCENT_DISCOUNT) && (discountAmount < 1 || discountAmount > 100))) {
                logger.error("Invalid amount -> {}", discountAmount);
                console.printInputError();
            } else break;
        }

        voucherService.createVoucher(voucherType, UUID.randomUUID(), discountAmount);
    }

    // voucher 조회
    public void printVoucherList() {
        Optional<List<Voucher>> voucherList = voucherService.getOptionalVoucherList();

        if(voucherList.isEmpty()) {
            logger.error("Voucher is Empty");
            console.printVoucherEmpty();
        }
        else console.printVoucherList(voucherList.get());
    }

    // blacklist 조회
    public void printBlackList() {
        Optional<List<Member>> blackList = memberService.getOptionalBlackList();

        if(blackList.isEmpty()) {
            logger.error("Blacklist is Empty");
            console.printBlackListEmpty();
        }
        else console.printBlackList(blackList.get());
    }
}