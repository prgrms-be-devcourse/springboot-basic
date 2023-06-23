package kr.co.springbootweeklymission.global.view;

import kr.co.springbootweeklymission.domain.member.dto.response.MemberResDTO;
import kr.co.springbootweeklymission.domain.model.VoucherPolicy;
import kr.co.springbootweeklymission.domain.voucher.dto.response.VoucherResDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OutputView {

    public static void outputCommand(){
        System.out.println("=== Voucher Program ===");
        for(Command command : Command.values()){
            System.out.println("Command : " + command.getCommand());
        }
    }

    public static void outputVoucherPolicy(){
        System.out.println("=== Voucher Policy ===");
        for(VoucherPolicy voucherPolicy : VoucherPolicy.values()){
            System.out.println(voucherPolicy.getType() + " : " + voucherPolicy.getPolicy());
        }
    }

    public static void outputCreateVoucher(){
        System.out.println("바우처가 생성되었습니다.");
    }

    public static void outputVouchers(List<VoucherResDTO.READ> reads){
        System.out.println(reads);
    }

    public static void outputBlackMembers(List<MemberResDTO.READ> reads){
        System.out.println(reads);
    }
}
