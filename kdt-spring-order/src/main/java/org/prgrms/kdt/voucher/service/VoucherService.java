package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.prgrms.kdt.common.util.VoucherUtil.createVoucherByType;


@Service
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

    public void createVoucher(VoucherType voucherType, long value){
        var voucher = createVoucherByType(UUID.randomUUID(), value, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), voucherType);
        voucherRepository.insert(voucher);
    }

    public List<Voucher> getVoucherList(){
        return voucherRepository.findAll();
    }

    public void deleteVoucher(UUID voucherId){
        voucherRepository.deleteById(voucherId);
    }
}
