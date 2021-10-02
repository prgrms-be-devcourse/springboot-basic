package org.prgrms.dev.voucher.service;

import org.prgrms.dev.exception.NotFoundException;
import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.voucher.domain.VoucherType;
import org.prgrms.dev.voucher.domain.dto.InsertVoucherDto;
import org.prgrms.dev.voucher.domain.dto.UpdateVoucherDto;
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
                .orElseThrow(() -> new NotFoundException("해당 바우처를 찾을 수 없습니다. " + voucherId));
    }

    public List<Voucher> listVoucher() {
        return voucherRepository.findAll();
    }

    public List<Voucher> listVoucherByType(String voucherType) {
        return voucherRepository.findByVoucherType(voucherType);
    }

    public List<Voucher> listVoucherByPeriod() {
        return voucherRepository.findByCreatedAt();
    }

    public Voucher createVoucher(InsertVoucherDto insertVoucherDto) {
        Voucher voucher = VoucherType.getVoucherType(insertVoucherDto.getVoucherType(), insertVoucherDto.getVoucherId(), insertVoucherDto.getDiscount(), insertVoucherDto.getCreatedAt());
        return voucherRepository.insert(voucher);
    }

    public Voucher updateVoucherDiscount(UpdateVoucherDto voucherDto) {
        Voucher voucher = getVoucher(voucherDto.getVoucherId());
        Voucher updateVoucher = VoucherType.getVoucherType(voucher.getVoucherType().toString(), voucher.getVoucherId(), voucherDto.getDiscount(), voucher.getCreatedAt());
        return voucherRepository.update(updateVoucher);
    }

    public void deleteVoucher(UUID voucherId) {
        getVoucher(voucherId);
        voucherRepository.deleteById(voucherId);
    }

    public void useVoucher(Voucher voucher) {
        // TODO:
    }
}
