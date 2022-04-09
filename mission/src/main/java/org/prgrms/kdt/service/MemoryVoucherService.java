package org.prgrms.kdt.service;

import org.prgrms.kdt.model.Voucher;
import org.prgrms.kdt.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemoryVoucherService implements VoucherService {

    private final VoucherRepository voucherRepository;

    public MemoryVoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId){
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("can not find a voucher for " + voucherId));
    }

    @Override
    public Voucher createVoucher(){
        return null;
    }

    @Override
    public Optional<List<Voucher>> findAllVoucher() {
        return Optional.empty();
    }
}
