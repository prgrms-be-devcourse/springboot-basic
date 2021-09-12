package org.prgrms.kdt.assignments;

import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Component
public class CreateVoucher implements FileRepository<VoucherData> {

    private static final String VoucherFileName = "voucher.txt";

    private final VoucherService voucherService;

    public CreateVoucher(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Voucher create(VoucherData voucherData){
        if(voucherData.getVoucherNumber() == 1) {
            saveFile(voucherData);
            return voucherService.createVoucher(new FixedAmountVoucher(UUID.randomUUID(), voucherData.getDiscountAmount()));
            // VoucherData 에서 유요한 바우처 번호인지는 이미 검증하기 때문에 else로만 표현
            // 추후 확장을 위해서는 switch나 else if로 표현해도 될듯
        } else {
            saveFile(voucherData);
            return voucherService.createVoucher(new PercentDiscountVoucher(UUID.randomUUID(), voucherData.getDiscountAmount()));
        }
    }

    @Override
    public void saveFile(VoucherData voucherData) {
        System.out.println(voucherData.getVoucherNumber());
        FileUtil.write("VoucherNumber : "+String.valueOf(voucherData.getVoucherNumber()) + ",  DiscountAmount : " + voucherData.getDiscountAmount(), VoucherFileName);
    }

    @Override
    public void readFile() {
        FileUtil.readText(VoucherFileName);
    }
}
