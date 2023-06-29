package kr.co.springbootweeklymission.view;

import kr.co.springbootweeklymission.member.api.dto.response.MemberResDTO;
import kr.co.springbootweeklymission.voucher.api.dto.response.VoucherResDTO;
import kr.co.springbootweeklymission.voucher.domain.model.VoucherPolicy;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OutputView {

    public static void outputCommand() {
        log.info("================= Voucher Program =================");
        for (Command command : Command.values()) {
            log.info("[Command] : {}", command.getCommand());
        }
    }

    public static void outputVoucherPolicy() {
        log.info("================= Voucher Policy =================");
        for (VoucherPolicy voucherPolicy : VoucherPolicy.values()) {
            log.info("[{}] : {}", voucherPolicy.getType(), voucherPolicy.getPolicy());
        }
    }

    public static void outputCreateVoucher() {
        log.info("바우처가 생성되었습니다.");
    }

    public static void outputVouchers(List<VoucherResDTO.READ> reads) {
        for (VoucherResDTO.READ read : reads) {
            log.info("\n[\n\tVoucher ID : {}\n\tVoucher Policy : {}\n\tVoucher Amount : {}\n]"
                    , read.getVoucherId(), read.getVoucherPolicy(), read.getAmount());
        }
    }

    public static void outputBlackMembers(List<MemberResDTO.READ> reads) {
        for (MemberResDTO.READ read : reads) {
            log.info("\n[\n\tMember ID : {}\n\tMember Status : {}\n]"
                    , read.getMemberId(), read.getMemberStatus());
        }
    }
}
