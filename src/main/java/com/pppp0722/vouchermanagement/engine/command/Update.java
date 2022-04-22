package com.pppp0722.vouchermanagement.engine.command;

import static com.pppp0722.vouchermanagement.engine.command.EntityType.MEMBER;
import static com.pppp0722.vouchermanagement.engine.command.EntityType.VOUCHER;
import static com.pppp0722.vouchermanagement.engine.command.validate.Validate.isValidAmount;

import com.pppp0722.vouchermanagement.engine.CommandLineApplication;
import com.pppp0722.vouchermanagement.io.Console;
import com.pppp0722.vouchermanagement.member.model.Member;
import com.pppp0722.vouchermanagement.member.service.MemberService;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
import com.pppp0722.vouchermanagement.voucher.service.VoucherService;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Update {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    private final Console console = Console.getInstance();
    private final MemberService memberService;
    private final VoucherService voucherService;

    public Update(MemberService memberService, VoucherService voucherService) {
        this.memberService = memberService;
        this.voucherService = voucherService;
    }

    public void start() {
        EntityType type = console.inputEntityType("member\nvoucher");
        if (type.equals(MEMBER)) {
            updateMember();
        } else if (type.equals(VOUCHER)) {
            updateVoucher();
        } else {
            logger.error("Invalid entity type!");
            console.printInputError();
            start();
        }
    }

    public void updateMember() {
        UUID memberId = console.inputMemberId();
        String name = console.inputMemberName();
        Optional<Member> member = memberService.updateMember(memberId, name);

        if (member.isPresent()) {
            console.printSuccess();
        } else {
            console.printFailure();
        }
    }

    public void updateVoucher() {
        UUID voucherId = console.inputVoucherId();
        VoucherType type = console.inputVoucherType();
        long amount = console.inputVoucherAmount();

        if (!isValidAmount(type, amount)) {
            logger.error("Invalid discount amount!");
            console.printInputError();
            updateVoucher();
        }

        Optional<Voucher> voucher = voucherService.updateVoucher(voucherId, type, amount);

        if (voucher.isPresent()) {
            console.printSuccess();
        } else {
            console.printFailure();
        }
    }
}
