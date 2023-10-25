package org.prgrms.vouchermanager.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.repository.voucher.VoucherRepository;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;
    public List<Voucher> getAllVoucher(){
        return voucherRepository.findAll();
    }
    public Voucher createVoucher(Voucher voucher){
        return voucherRepository.save(voucher);
    }
    public Optional<Voucher> findById(Voucher voucher){
        return voucherRepository.findByID(voucher.getVoucherId());
    }
}
