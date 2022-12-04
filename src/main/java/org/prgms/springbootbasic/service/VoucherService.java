package org.prgms.springbootbasic.service;

import org.prgms.springbootbasic.domain.voucher.*;
import org.prgms.springbootbasic.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherCreateDTO voucherCreateDTO)  {

        switch (voucherCreateDTO.voucherType()) {
            case PERCENT -> {
                return voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(),
                            voucherCreateDTO.voucherType(),
                            voucherCreateDTO.amount()));
            }

            case FIXED -> {
                return voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(),
                                voucherCreateDTO.voucherType(),
                                voucherCreateDTO.amount()));
            }
            default ->
                throw new IllegalArgumentException("invalid voucher option");
        }

    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public List<Voucher> findVouchers(UUID customerId) {
        return voucherRepository.findVouchersByCustomerId(customerId);
    }

    public Optional<Voucher> findVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }
    public UUID deleteVouchers(UUID customerId) {
        return voucherRepository.deleteByCustomerId(customerId);
    }

    public Voucher allocateVoucher(Voucher voucher) {
        return voucherRepository.updateByCustomerId(voucher);
    }

    public UUID allocateVoucher(UUID customerId, UUID voucherId) {
        return voucherRepository.updateByCustomerId(customerId, voucherId);
    }
}
