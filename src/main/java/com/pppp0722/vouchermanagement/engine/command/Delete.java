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

    // member -> deleteMember(), voucher -> deleteVoucher()
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

    // input memberId -> MemberService.deleteMember()
    public void deleteMember() {
        UUID memberId = null;
        try {
            memberId = UUID.fromString(console.inputMemberId());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid UUID!", e);
            console.printInputError();
            deleteMember();
        }

        Optional<Member> member = memberService.deleteMember(memberId);
        if (member.isPresent()) {
            console.printSuccess();
        } else {
            logger.error("Delete member failed!");
            console.printFailure();
        }
    }

    // input voucherId -> VoucherService.deleteVoucher()
    public void deleteVoucher() {
        UUID voucherId = null;
        try {
            voucherId = UUID.fromString(console.inputVoucherId());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid UUID!", e);
            console.printInputError();
            deleteVoucher();
        }

        Optional<Voucher> voucher = voucherService.deleteVoucher(voucherId);
        if (voucher.isPresent()) {
            console.printSuccess();
        } else {
            logger.error("Create voucher failed!");
            console.printFailure();
        }
    }
}
