package com.pppp0722.vouchermanagement.engine.command;

import com.pppp0722.vouchermanagement.io.Console;
import com.pppp0722.vouchermanagement.member.model.Member;
import com.pppp0722.vouchermanagement.member.service.MemberService;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.service.VoucherService;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Read {

    private static final Logger logger = LoggerFactory.getLogger(Read.class);
    private final Console console = Console.getInstance();
    private final MemberService memberService;
    private final VoucherService voucherService;

    public Read(MemberService memberService, VoucherService voucherService) {
        this.memberService = memberService;
        this.voucherService = voucherService;
    }

    public void read() {
        try {
            EntityType entityType = console.inputEntityType();
            switch (entityType) {
                case MEMBER:
                    readMember();
                    break;
                case VOUCHER:
                    readVoucher();
                    break;
                default:
                    break;
            }
        } catch (IllegalArgumentException e) {
            logger.error("Invalid command!");
            console.printInputError();
            read();
        }
    }

    public void readMember() {
        String readType = console.inputMemberReadType();
        switch (readType) {
            case "1": // all
                readAllMembers();
                break;
            case "2": // by member id
                readMemberByMemberId();
                break;
            default:
                logger.error("Invalid command!");
                console.printInputError();
                readMember();
                break;
        }
    }

    public void readAllMembers() {
        List<Member> memberList = memberService.getAllMembers();
        console.printMemberList(memberList);
    }

    public void readMemberByMemberId() {
        try {
            UUID memberId = console.inputMemberId();
            Member member = memberService.getMemberById(memberId);
            console.printMember(member);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid UUID!", e);
            console.printInputError();
            readMemberByMemberId();
        }
    }

    public void readVoucher() {
        String readType = console.inputVoucherReadType();
        switch (readType) {
            case "1": // all
                readAllVouchers();
                break;
            case "2": // by voucher id
                readVoucherByVoucherId();
                break;
            case "3": // by member id
                readVoucherByMemberId();
                break;
            default:
                logger.error("Invalid command!");
                console.printInputError();
                readVoucher();
                break;
        }
    }

    public void readAllVouchers() {
        List<Voucher> voucherList = voucherService.getAllVouchers();
        console.printVoucherList(voucherList);
    }

    public void readVoucherByVoucherId() {
        try {
            UUID voucherId = console.inputVoucherId();
            Voucher voucher = voucherService.getVoucherById(voucherId);
            console.printVoucher(voucher);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid UUID!", e);
            console.printInputError();
            readVoucherByVoucherId();
        }
    }

    public void readVoucherByMemberId() {
        try {
            UUID memberId = console.inputMemberId();
            List<Voucher> voucherList = voucherService.getVouchersByMemberId(memberId);
            console.printVoucherList(voucherList);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid UUID!", e);
            console.printInputError();
            readVoucherByMemberId();
        }
    }
}
