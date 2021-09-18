package org.prgrms.dev.voucher.service;

import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.voucher.domain.VoucherDto;
import org.prgrms.dev.voucher.domain.VoucherType;
import org.prgrms.dev.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("해당 바우처 아이디를 찾을 수 없습니다. " + voucherId));
    }

    public Voucher createVoucher(VoucherDto voucherDto) {
        Voucher voucher = VoucherType.getVoucherType(voucherDto.getVoucherType(), voucherDto.getVoucherId(), voucherDto.getDiscount(), voucherDto.getCreatedAt());
        return voucherRepository.insert(voucher);
    }

    public List<Voucher> listVoucher() {
        return voucherRepository.findAll();
    }

    public Voucher updateVoucherDiscount(VoucherDto voucherDto) {
        Voucher voucher = getVoucher(voucherDto.getVoucherId());
        Voucher updateVoucher = VoucherType.getVoucherType(voucher.getVoucherType().toString(), voucher.getVoucherId(), voucherDto.getDiscount(), voucher.getCreatedAt());
        return voucherRepository.update(updateVoucher);
    }

    public void deleteVoucher(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    public void useVoucher(Voucher voucher) {
        // TODO:
    }
}
