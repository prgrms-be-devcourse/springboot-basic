package com.pppp0722.vouchermanagement.engine.command;

import static com.pppp0722.vouchermanagement.engine.command.EntityType.MEMBER;
import static com.pppp0722.vouchermanagement.engine.command.EntityType.VOUCHER;

import com.pppp0722.vouchermanagement.engine.CommandLineApplication;
import com.pppp0722.vouchermanagement.io.Console;
import com.pppp0722.vouchermanagement.member.model.Member;
import com.pppp0722.vouchermanagement.member.service.MemberService;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.service.VoucherService;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Delete {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    private final Console console = Console.getInstance();
    private final MemberService memberService;
    private final VoucherService voucherService;

    public Delete(MemberService memberService, VoucherService voucherService) {
        this.memberService = memberService;
        this.voucherService = voucherService;
    }

    public void start() {
        EntityType type = console.inputEntityType("member\nvoucher");
        if (type.equals(MEMBER)) {
            deleteMember();
        } else if (type.equals(VOUCHER)) {
            deleteVoucher();
        } else {
            logger.error("Invalid entity type!");
            console.printInputError();
            start();
        }
    }

    public void deleteMember() {
        UUID memberId = console.inputMemberId();
        Optional<Member> member = memberService.deleteMember(memberId);

        if (member.isPresent()) {
            console.printSuccess();
        } else {
            console.printFailure();
        }
    }

    public void deleteVoucher() {
        UUID voucherId = console.inputVoucherId();
        Optional<Voucher> voucher = voucherService.deleteVoucher(voucherId);

        if (voucher.isPresent()) {
            console.printSuccess();
        } else {
            console.printFailure();
        }
    }
}
