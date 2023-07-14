package com.example.voucher.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.example.voucher.constant.VoucherType;
import com.example.voucher.domain.FixedAmountVoucher;
import com.example.voucher.domain.PercentDiscountVoucher;
import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.VoucherCreator;
import com.example.voucher.domain.dto.VoucherDTO;
import com.example.voucher.repository.VoucherRepository;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherDTO createVoucher(VoucherType voucherType, long discountValue) {
        Voucher createdVoucher = switch (voucherType) {
            case FIXED_AMOUNT_DISCOUNT -> new FixedAmountVoucher(discountValue);
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(discountValue);
        };

        Voucher savedVoucher = voucherRepository.save(createdVoucher);

        return toDTO(savedVoucher);
    }

    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }

    public void deleteVouchers() {
        voucherRepository.deleteAll();
    }

    public VoucherDTO searchById(UUID voucherId) {
        Voucher selectedVoucher = voucherRepository.findById(voucherId);

        return toDTO(selectedVoucher);
    }

    public VoucherDTO update(UUID voucherId, VoucherType voucherType, long discountValue) {
        Voucher voucher = VoucherCreator.getVoucher(voucherId, voucherType, discountValue);
        Voucher updatedVoucher = voucherRepository.update(voucher);

        return toDTO(updatedVoucher);
    }

    private VoucherDTO toDTO(Voucher voucher) {
        VoucherType voucherType = voucher.getVoucherType();
        long discountValue = voucher.getValue();

        return new VoucherDTO(discountValue, voucherType);
    }

}
