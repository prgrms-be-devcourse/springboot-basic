package org.prgrms.kdt.service;

import lombok.AllArgsConstructor;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.enumType.VoucherStatus;
import org.prgrms.kdt.factory.VoucherFactory;
import org.prgrms.kdt.repository.MemoryVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VoucherService {

    @Autowired
    private final MemoryVoucherRepository memoryVoucherRepository;

    @Autowired
    private VoucherFactory voucherFactory;

    public Voucher createVoucher(VoucherStatus voucherStatus){
        Voucher v = voucherFactory.getDiscounterVoucher(voucherStatus);
        return v;
    }

    public Voucher getVoucher(UUID voucherId) {
        return memoryVoucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(String.format("Can not find a voucher %s", voucherId)));
    }

    public List<Voucher> findAll() {
        return memoryVoucherRepository.findAll();
    }

    //사용안함
    public void useVoucher(Voucher voucher){
    }

}
