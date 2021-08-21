package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.repository.MemoryVoucherRepository;
import org.prgrms.kdt.voucher.repository.VoucherRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId){
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public void createVoucher(String voucherType, long value){
        if(voucherType.equals("fixed")){
            voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), value));
        }
        else if(voucherType.equals("percent")){
            voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), value));
        }
    }

    public List<Voucher> getVoucherList(){
        return voucherRepository.getVoucherList();
    }

    public void useVoucher(Voucher voucher) {
    }
}
