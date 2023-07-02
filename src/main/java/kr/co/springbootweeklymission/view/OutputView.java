package kr.co.springbootweeklymission.view;

import kr.co.springbootweeklymission.member.api.dto.response.MemberResDTO;
import kr.co.springbootweeklymission.member.domain.model.MemberStatus;
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
            log.info("[Command] : {} : {}", command.getNumber(), command.getCommand());
        }
    }

    public static void outputVoucherPolicy() {
        log.info("================= Voucher Policy =================");
        for (VoucherPolicy voucherPolicy : VoucherPolicy.values()) {
            log.info("[{}] : {}", voucherPolicy.getType(), voucherPolicy.getPolicy());
        }
    }

    public static void outputMemberStatus() {
        log.info("================= Member Status =================");
        for (MemberStatus memberStatus : MemberStatus.values()) {
            log.info("[{}]", memberStatus.toString());
        }
    }

    public static void outputCreateVoucher() {
        log.info("================= 바우처 생성 페이지 =================");
    }

    public static void outputUpdateVoucher() {
        log.info("================= 바우처 수정 페이지 =================");
    }

    public static void outputDeleteVoucher() {
        log.info("================= 바우처 삭제 페이지 =================");
    }

    public static void outputVoucher(VoucherResDTO.READ read) {
        log.info("\n[\n\tVoucher ID : {}\n\tVoucher Policy : {}\n\tVoucher Amount : {}\n]"
                , read.getVoucherId(), read.getVoucherPolicy(), read.getAmount());
    }

    public static void outputVouchers(List<VoucherResDTO.READ> reads) {
        for (VoucherResDTO.READ read : reads) {
            outputVoucher(read);
        }
    }

    public static void outputCreateMember() {
        log.info("================= 회원 생성 페이지 =================");
    }

    public static void outputUpdateMember() {
        log.info("================= 회원 수정 페이지 =================");
    }

    public static void outputDeleteMember() {
        log.info("================= 회원 삭제 페이지 =================");
    }

    public static void outputMember(MemberResDTO.READ read) {
        log.info("\n[\n\tMember ID : {}\n\tVoucher Amount : {}\n]"
                , read.getMemberId(), read.getMemberStatus());
    }

    public static void outputBlackMembers(List<MemberResDTO.READ> reads) {
        for (MemberResDTO.READ read : reads) {
            log.info("\n[\n\tMember ID : {}\n\tMember Status : {}\n]"
                    , read.getMemberId(), read.getMemberStatus());
        }
    }

    public static void outputMembers(List<MemberResDTO.READ> reads) {
        for (MemberResDTO.READ read : reads) {
            outputMember(read);
        }
    }

    public static void outputCreateVoucherMember() {
        log.info("================= 바우처 고객 할당 페이지 =================");
    }

    public static void outputDeleteVoucherMember() {
        log.info("================= 고객의 바우처 삭제 페이지 =================");
    }
}
