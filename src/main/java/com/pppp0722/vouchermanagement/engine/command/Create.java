package com.pppp0722.vouchermanagement.engine.command;

import static com.pppp0722.vouchermanagement.engine.command.EntityType.MEMBER;
import static com.pppp0722.vouchermanagement.engine.command.EntityType.VOUCHER;
import static com.pppp0722.vouchermanagement.engine.command.validate.Validate.isValidAmount;

import com.pppp0722.vouchermanagement.engine.CommandLineApplication;
import com.pppp0722.vouchermanagement.engine.command.validate.Validate;
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

public class Create {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    private final Console console = Console.getInstance();
    private final MemberService memberService;
    private final VoucherService voucherService;

    public Create(MemberService memberService, VoucherService voucherService) {
        this.memberService = memberService;
        this.voucherService = voucherService;
    }

    // member -> createdMember(), voucher -> createVoucher()
    public void start() {
        EntityType type = console.inputEntityType("member\nvoucher");
        if (type.equals(MEMBER)) {
            createMember();
        } else if (type.equals(VOUCHER)) {
            createVoucher();
        } else {
            logger.error("Invalid entity type!");
            console.printInputError();
            start();
        }
    }

    // input memberName -> MemberService.createMember()
    public void createMember() {
        String name = console.inputMemberName();
        if (!Validate.isValidName(name)) {
            logger.error("Invalid name!");
            console.printInputError();
            createMember();
        }

        Optional<Member> member = memberService.createMember(UUID.randomUUID(), name);
        if (member.isPresent()) {
            console.printSuccess();
        } else {
            logger.error("Create member failed!");
            console.printFailure();
        }
    }

    // input memberId, type, amount -> VoucherService.createVoucher()
    public void createVoucher() {
        UUID memberId = null;
        try {
            memberId = UUID.fromString(console.inputMemberId());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid UUID!", e);
            console.printInputError();
            createVoucher();
        }

        VoucherType type = console.inputVoucherType();
        long amount = console.inputVoucherAmount();
        if (!isValidAmount(type, amount)) {
            logger.error("Invalid discount amount!");
            console.printInputError();
            createVoucher();
        }

        Optional<Voucher> voucher = voucherService.createVoucher(UUID.randomUUID(), type, amount,
            memberId);
        if (voucher.isPresent()) {
            console.printSuccess();
        } else {
            logger.error("Create voucher failed!");
            console.printFailure();
        }
    }
}
