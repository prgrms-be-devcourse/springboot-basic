package com.pppp0722.vouchermanagement.engine.command;

import com.pppp0722.vouchermanagement.io.Console;
import com.pppp0722.vouchermanagement.member.service.MemberService;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
import com.pppp0722.vouchermanagement.voucher.service.VoucherService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Update {

    private static final Logger logger = LoggerFactory.getLogger(Update.class);
    private final Console console = Console.getInstance();
    private final MemberService memberService;
    private final VoucherService voucherService;

    public Update(MemberService memberService, VoucherService voucherService) {
        this.memberService = memberService;
        this.voucherService = voucherService;
    }

    public void update() {
        try {
            EntityType entityType = console.inputEntityType();
            switch (entityType) {
                case MEMBER:
                    updateMember();
                    break;
                case VOUCHER:
                    updateVoucher();
                    break;
                default:
                    break;
            }
        } catch (IllegalArgumentException e) {
            logger.error("Invalid command!");
            console.printInputError();
            update();
        }
    }

    public void updateMember() {
        try {
            UUID memberId = console.inputMemberId();
            String name = console.inputMemberName();
            memberService.updateMember(memberId, name);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid UUID!", e);
            console.printInputError();
            updateMember();
        }
    }

    public void updateVoucher() {
        try {
            UUID voucherId = console.inputVoucherId();
            VoucherType type = console.inputVoucherType();
            long amount = console.inputVoucherAmount();
            voucherService.updateVoucher(voucherId, type, amount);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input!", e);
            console.printInputError();
            updateVoucher();
        }
    }
}
