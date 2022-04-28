package com.pppp0722.vouchermanagement.engine.command;

import com.pppp0722.vouchermanagement.io.Console;
import com.pppp0722.vouchermanagement.member.service.MemberService;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
import com.pppp0722.vouchermanagement.voucher.service.VoucherService;
import java.time.LocalDateTime;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Create {

    private static final Logger logger = LoggerFactory.getLogger(Create.class);
    private final Console console = Console.getInstance();
    private final MemberService memberService;
    private final VoucherService voucherService;

    public Create(MemberService memberService, VoucherService voucherService) {
        this.memberService = memberService;
        this.voucherService = voucherService;
    }

    public void create() {
        try {
            EntityType entityType = console.inputEntityType();
            switch (entityType) {
                case MEMBER:
                    createMember();
                    break;
                case VOUCHER:
                    createVoucher();
                    break;
                default:
                    break;
            }
        } catch (IllegalArgumentException e) {
            logger.error("Invalid entity type!");
            console.printInputError();
            create();
        }
    }

    public void createMember() {
        String name = console.inputMemberName();
        try {
            memberService.createMember(UUID.randomUUID(), name);
        } catch (RuntimeException e) {
            console.printFailure();
        }
    }

    public void createVoucher() {
        try {
            UUID memberId = console.inputMemberId();
            VoucherType voucherType = console.inputVoucherType();
            long amount = console.inputVoucherAmount();
            try {
                voucherService.createVoucher(UUID.randomUUID(), voucherType, amount,
                    LocalDateTime.now(),
                    memberId);
            } catch (RuntimeException e) {
                console.printFailure();
            }
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input!", e);
            console.printInputError();
            createVoucher();
        }
    }
}
