package com.pppp0722.vouchermanagement.engine.command;

import com.pppp0722.vouchermanagement.io.Console;
import com.pppp0722.vouchermanagement.member.service.MemberService;
import com.pppp0722.vouchermanagement.voucher.service.VoucherService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Delete {

    private static final Logger logger = LoggerFactory.getLogger(Delete.class);
    private final Console console = Console.getInstance();
    private final MemberService memberService;
    private final VoucherService voucherService;

    public Delete(MemberService memberService, VoucherService voucherService) {
        this.memberService = memberService;
        this.voucherService = voucherService;
    }

    public void delete() {
        try {
            EntityType entityType = console.inputEntityType();
            switch (entityType) {
                case MEMBER:
                    deleteMemberByMemberId();
                    break;
                case VOUCHER:
                    deleteVoucherByVoucherId();
                    break;
                default:
                    break;
            }
        } catch (IllegalArgumentException e) {
            logger.error("Invalid command!");
            console.printInputError();
            delete();
        }
    }

    public void deleteMemberByMemberId() {
        try {
            UUID memberId = console.inputMemberId();
            memberService.deleteMember(memberId);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid UUID!", e);
            console.printInputError();
            deleteMemberByMemberId();
        }
    }

    public void deleteVoucherByVoucherId() {
        try {
            UUID voucherId = console.inputVoucherId();
            voucherService.deleteVoucher(voucherId);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid UUID!", e);
            console.printInputError();
            deleteVoucherByVoucherId();
        }
    }
}
