package com.pppp0722.vouchermanagement.engine.command;

import static com.pppp0722.vouchermanagement.engine.command.EntityType.MEMBER;
import static com.pppp0722.vouchermanagement.engine.command.EntityType.VOUCHER;
import static com.pppp0722.vouchermanagement.engine.command.EntityType.WALLET;

import com.pppp0722.vouchermanagement.engine.CommandLineApplication;
import com.pppp0722.vouchermanagement.io.Console;
import com.pppp0722.vouchermanagement.member.model.Member;
import com.pppp0722.vouchermanagement.member.service.MemberService;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.service.VoucherService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Read {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    private final Console console = Console.getInstance();
    private final MemberService memberService;
    private final VoucherService voucherService;

    public Read(MemberService memberService, VoucherService voucherService) {
        this.memberService = memberService;
        this.voucherService = voucherService;
    }

    // member -> readMember(), voucher -> readVoucher(), wallet -> readWallet()
    public void start() {
        EntityType type = console.inputEntityType("member\nvoucher\nwallet");
        if (type.equals(MEMBER)) {
            readMember();
        } else if (type.equals(VOUCHER)) {
            readVoucher();
        } else if (type.equals(WALLET)) {
            readWallet();
        } else {
            logger.error("Invalid entity type!");
            console.printInputError();
            start();
        }
    }

    // all -> printAllMembers(), one -> printMember()
    public void readMember() {
        String count = console.inputCount();
        if (count.equals("all")) {
            printAllMembers();
        } else if (count.equals("one")) {
            printMember();
        } else {
            logger.error("Invalid command!");
            console.printInputError();
            readMember();
        }
    }

    // MemberService.getAllMember() -> Console.printMemberList()
    public void printAllMembers() {
        List<Member> memberList = memberService.getAllMembers();
        if (memberList.isEmpty()) {
            logger.info("Member is Empty.");
            console.printEmpty();
        } else {
            console.printMemberList(memberList);
        }
    }

    // input memberId -> MemberService.getMemberById() -> Console.printMemberList()
    public void printMember() {
        UUID memberId = null;
        try {
            memberId = UUID.fromString(console.inputMemberId());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid UUID!", e);
            console.printInputError();
            printMember();
        }

        Optional<Member> member = memberService.getMemberByMemberId(memberId);
        if (member.isEmpty()) {
            logger.info("Member does not exist.");
            console.printEmpty();
        } else {
            console.printMemberList(new ArrayList<>() {{
                add(member.get());
            }});
        }
    }

    // all -> printAllVouchers, one -> printVoucher
    public void readVoucher() {
        String count = console.inputCount();
        if (count.equals("all")) {
            printAllVouchers();
        } else if (count.equals("one")) {
            printVoucher();
        } else {
            logger.error("Invalid command!");
            console.printInputError();
            readVoucher();
        }
    }

    // VoucherService.getAllVouchers() -> Console.printVoucherList()
    public void printAllVouchers() {
        List<Voucher> voucherList = voucherService.getAllVouchers();
        if (voucherList.isEmpty()) {
            logger.info("Voucher is Empty.");
            console.printEmpty();
        } else {
            console.printVoucherList(voucherList);
        }
    }

    // input voucherId -> VoucherService.getVoucherByVoucherId() -> Console.printVoucherList()
    public void printVoucher() {
        UUID voucherId = null;
        try {
            voucherId = UUID.fromString(console.inputVoucherId());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid UUID!", e);
            console.printInputError();
            printVoucher();
        }

        Optional<Voucher> voucher = voucherService.getVoucherByVoucherId(voucherId);
        if (voucher.isEmpty()) {
            logger.info("Voucher does not exist.");
            console.printEmpty();
        } else {
            console.printVoucherList(new ArrayList<>() {{
                add(voucher.get());
            }});
        }
    }

    // input memberId -> VoucherService.getVouchersByMemberId() -> Console.printVoucherList()
    public void readWallet() {
        UUID memberId = null;
        try {
            memberId = UUID.fromString(console.inputMemberId());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid UUID!", e);
            console.printInputError();
            readWallet();
        }

        List<Voucher> voucherList = voucherService.getVouchersByMemberId(memberId);
        if (voucherList.isEmpty()) {
            logger.info("Wallet is Empty.");
            console.printEmpty();
        } else {
            console.printVoucherList(voucherList);
        }
    }
}
