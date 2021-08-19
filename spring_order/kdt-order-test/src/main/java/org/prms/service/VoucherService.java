package org.prms.service;

import org.prms.domain.FixedAmountVoucher;
import org.prms.domain.PercentDiscountVoucher;
import org.prms.domain.Voucher;
import org.prms.repository.VoucherRepository;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherID) {
        // voucherID를 찾지 못하는 경우에는 Throw로 날려서 Exception 처리
        return voucherRepository
                .findById(voucherID)
                .orElseThrow(()->new RuntimeException(MessageFormat.format("Can not find a Voucher for {0}",voucherID)));

    }

    public void createVoucher(String voucherType,Long val) {
        if (voucherType.equals("fixed")) {
            voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(),val));
        }
        else {
            voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(),val));
        }
    }

    public ArrayList<Voucher> getVoucherList() {
        return voucherRepository.getList();
    }



    //To do
    public void useVoucher(Voucher voucher) {

    }


}
