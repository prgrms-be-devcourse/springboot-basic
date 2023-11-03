package org.prgrms.vouchermanager.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.exception.NotExistEmailException;
import org.prgrms.vouchermanager.exception.NotExistVoucherException;
import org.prgrms.vouchermanager.repository.voucher.VoucherRepository;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;
    public List<Voucher> findAllVoucher(){
        return voucherRepository.findAll();
    }
    public Voucher createVoucher(Voucher voucher){
        return voucherRepository.save(voucher);
    }
    public Voucher findById(UUID voucherId){
        Voucher voucher = voucherRepository.findByID(voucherId).orElseThrow(NotExistVoucherException::new);
        return voucher;
    }
}
