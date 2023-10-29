package com.prgrms.voucher_manage.domain.voucher.service;

import com.prgrms.voucher_manage.domain.voucher.dto.CreateVoucherDto;
import com.prgrms.voucher_manage.domain.voucher.dto.UpdateVoucherDto;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.prgrms.voucher_manage.exception.ErrorMessage.*;


@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public Voucher createVoucher(CreateVoucherDto dto) {
        return voucherRepository.save(dto.of());
    }

    public List<Voucher> getVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        if (vouchers.isEmpty()) {
            throw new RuntimeException(VOUCHER_NOT_EXISTS.getMessage());
        }
        return vouchers;
    }

    public Voucher findVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(VOUCHER_NOT_EXISTS.getMessage()));
    }

    public void updateVoucher(UpdateVoucherDto dto) {
        int update = voucherRepository.update(dto.of());
        if (update != 1) {
            throw new RuntimeException(VOUCHER_UPDATE_FAILED.getMessage());
        }
    }

    public void deleteVoucher(UUID voucherId) {
        int delete = voucherRepository.deleteById(voucherId);
        if (delete != 1) {
            throw new RuntimeException(VOUCHER_DELETE_FAILED.getMessage());
        }
    }
}
