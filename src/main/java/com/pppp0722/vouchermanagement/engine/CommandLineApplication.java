package com.pppp0722.vouchermanagement.engine;

import static com.pppp0722.vouchermanagement.entity.voucher.VoucherType.FIXED_AMOUNT;
import static com.pppp0722.vouchermanagement.entity.voucher.VoucherType.PERCENT_DISCOUNT;

import com.pppp0722.vouchermanagement.entity.EntityType;
import com.pppp0722.vouchermanagement.io.Console;
import com.pppp0722.vouchermanagement.entity.member.Member;
import com.pppp0722.vouchermanagement.service.member.MemberServiceImpl;
import com.pppp0722.vouchermanagement.entity.voucher.Voucher;
import com.pppp0722.vouchermanagement.entity.voucher.VoucherType;
import com.pppp0722.vouchermanagement.service.voucher.VoucherServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CommandLineApplication {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    private final Console console = Console.getInstance();
    private final VoucherServiceImpl voucherService;
    private final MemberServiceImpl memberService;

    public CommandLineApplication(VoucherServiceImpl voucherService,
        MemberServiceImpl memberService) {
        this.voucherService = voucherService;
        this.memberService = memberService;
    }

    // 프로그램 실행
    public void run() {
        logger.info("Start voucher management application.");
        console.printLogo();

        boolean isExit = false;
        while (!isExit) {
            console.printMenu();
            String command = console.getCommand("Input : ");
            CommandType commandType = CommandType.getCommandType(command);
            switch (commandType) {
                case CREATE:
                    create();
                    break;
                case LIST:
                    printList();
                    break;
                case EXIT:
                    isExit = true;
                    break;
                default:
                    console.printInputError();
                    logger.error("Invalid command -> {}!", command);
            }
            console.printEmpty();
        }

        logger.info("Terminate voucher management application.");
    }

    public void create() {
        EntityType entityType;
        console.printEntityTypeInputRequest();
        entityType = EntityType.getEntityType(console.getCommand("Input : "));
        if (entityType.equals(EntityType.MEMBER)) {
            inputMember();
        } else if (entityType.equals(EntityType.VOUCHER)) {
            inputVoucher();
        } else if (entityType.equals(EntityType.WALLET)) {
            inputWallet();
        }
        else {
            logger.error("Invalid entity type!");
            console.printInputError();

            create();
        }
    }

    public void inputMember() {
        String name;
        console.printNameInputRequest();
        name = console.getCommand("Input : ");
        memberService.createMember(UUID.randomUUID(), name);
    }

    public void inputVoucher() {
        VoucherType voucherType = inputVoucherType();
        long discountAmount = inputDiscountAmount(voucherType);
        voucherService.createVoucher(voucherType, UUID.randomUUID(), discountAmount);
    }

    public VoucherType inputVoucherType() {
        VoucherType voucherType;
        console.printVoucherTypeInputRequest();
        voucherType = VoucherType.getVoucherType(console.getCommand("Input : "));

        if (voucherType.equals(VoucherType.NONE)) {
            logger.error("Invalid voucher type!");
            console.printInputError();

            return inputVoucherType();
        }

        return voucherType;
    }

    public long inputDiscountAmount(VoucherType voucherType) {
        long discountAmount;
        console.printAmountInputRequest();
        discountAmount = Long.parseLong(console.getCommand("Input : "));
        if (!isValidAmount(voucherType, discountAmount)) {
            logger.error("Invalid amount -> {}!", discountAmount);
            console.printInputError();
            inputDiscountAmount(voucherType);
        }

        return discountAmount;
    }

    public void inputWallet() {
        UUID memberId;
        console.printMemberIdInputRequest();
        memberId = UUID.fromString(console.getCommand("Input : "));

        memberService.updateWallet(memberId, UUID.randomUUID());
    }

    public boolean isValidAmount(VoucherType voucherType, long discountAmount) {
        if (voucherType.equals(FIXED_AMOUNT)) {
            if (discountAmount < 1) {
                return false;
            }
        }

        if (voucherType.equals(PERCENT_DISCOUNT)) {
            if (discountAmount < 1 && discountAmount > 100) {
                return false;
            }
        }

        return true;
    }

    public void printList() {
        EntityType entityType;
        console.printEntityTypeInputRequest();
        entityType = EntityType.getEntityType(console.getCommand("Input : "));
        if (entityType.equals(EntityType.MEMBER)) {
            printMemberList();
        } else if (entityType.equals(EntityType.VOUCHER)) {
            printVoucherList();
        } else if (entityType.equals(EntityType.WALLET)) {
            printWallet();
        } else {
            logger.error("Invalid entity type!");
            console.printInputError();
        }
    }

    public void printVoucherList() {
        List<Voucher> voucherList = voucherService.getAllVouchers();

        if (voucherList.isEmpty()) {
            logger.info("Voucher is Empty.");
            console.printVoucherEmpty();
        } else {
            console.printVoucherList(voucherList);
        }
    }

    public void printMemberList() {
        List<Member> memberList = memberService.getAllMembers();

        if (memberList.isEmpty()) {
            logger.info("Blacklist is Empty.");
            console.printMemberListEmpty();
        } else {
            console.printMemberList(memberList);
        }
    }

    public void printWallet() {
        UUID memberId;
        console.printMemberIdInputRequest();
        memberId = UUID.fromString(console.getCommand("Input : "));
    }
}