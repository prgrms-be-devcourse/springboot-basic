package org.programmers.applicationcontext.execute;

import org.programmers.applicationcontext.*;
import org.programmers.applicationcontext.voucher.Voucher;
import org.programmers.applicationcontext.voucher.VoucherService;
import org.programmers.applicationcontext.VoucherType;
import org.programmers.applicationcontext.voucher.volume.FixedVoucherVolume;
import org.programmers.applicationcontext.voucher.volume.PercentVoucherVolume;
import org.programmers.applicationcontext.voucher.volume.VoucherVolume;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class CreateExecute implements Execute{


    @Override
    public boolean execute(List<Voucher> voucherList,
                       AnnotationConfigApplicationContext commandLineContext,
                       BufferedReader br, OutPutView outPutView) throws IOException {
        var voucherId = UUID.randomUUID();
        var voucherService = commandLineContext.getBean(VoucherService.class);
        VoucherVolume fixedVoucherVolume = new FixedVoucherVolume();
        VoucherVolume percentVoucherVolume = new PercentVoucherVolume();

        VoucherType voucherType;
        boolean loop = true;

        while (loop) {
            outPutView.selectVoucherType();
            String userVoucherType = br.readLine();
            voucherType = VoucherType.of(userVoucherType);
            if(voucherType==null){
            if (voucherType == null) {
                System.out.println("해당 명령어는 올바르지 않습니다 다시 입력해주세요\n");
                continue;
            }

            switch (voucherType) {
                case FIXED_VOUCHER:
                    outPutView.inputFixedAmountVoucher();
                    loop = fixedVoucherVolume.change(voucherList, br, voucherId, voucherService);
                    break;

                case PERCENT_VOUCHER:
                    outPutView.inputPercentAmountVoucher();
                    loop = percentVoucherVolume.change(voucherList, br, voucherId, voucherService);
                    break;

                default:
                    outPutView.selectError();
                    break;
            }
        }
        return true;
    }
}
