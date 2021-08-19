package org.prgrms.kdt.Service;

import org.prgrms.kdt.VO.Voucher;
import org.prgrms.kdt.Repository.VoucherRepository;

import java.text.MessageFormat;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final String fixedType = "fixed amount voucher";
    private final String percentType = "percent discount voucher";
    private final String guideString = "voucher type : ";



    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(()->new RuntimeException(MessageFormat.format("Cannot find a voucher{0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {
    }



    public void showAllVoucherList() {
        System.out.println("총 voucher 개수는 " + voucherRepository.numVouchers()+ "개 입니다.");
        voucherRepository
                .getVoucherList().stream()
                .forEach(System.out::println);
    }



    // voucherId는 시스템이 만들어 주는 case만 고려함.
    public void createVoucher(char voucherType, long voucherInfo) {
        switch (voucherType){
            case 'f':
                //System.out.println("voucherServcie에서 voucherDB로");
                //voucher할인율 받는 코드에서(VoucherTest.java) 숫자 유효성 검증하는 아스키코드를 잘못씀
                voucherRepository.createFixedAmountVoucher(voucherInfo, UUID.randomUUID());
                break;
            case 'p':
                voucherRepository.createPercentDiscountVoucher(voucherInfo, UUID.randomUUID());
                break;

        }
    }
}
