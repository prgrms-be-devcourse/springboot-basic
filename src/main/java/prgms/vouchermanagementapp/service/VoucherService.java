package prgms.vouchermanagementapp.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.domain.dto.VoucherViewDTO;
import prgms.vouchermanagementapp.domain.value.Amount;
import prgms.vouchermanagementapp.domain.value.Ratio;
import prgms.vouchermanagementapp.repository.VoucherRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(String voucherType, long discountLevel) {
        Voucher newVoucher = VoucherFactory.createVoucher(voucherType, discountLevel);
        voucherRepository.save(newVoucher);
        return newVoucher;
    }

    public void createVoucher(Amount fixedDiscountAmount) {
        Voucher fixedAmountVoucher = VoucherFactory.createVoucher(fixedDiscountAmount);
        save(fixedAmountVoucher);
    }

    public void createVoucher(Ratio fixedDiscountRatio) {
        Voucher percentDiscountVoucher = VoucherFactory.createVoucher(fixedDiscountRatio);
        save(percentDiscountVoucher);
    }

    public void save(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    public List<Voucher> findAllVouchers() {
        return voucherRepository.findAll();
    }

    public Optional<Voucher> findVoucherById(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public List<VoucherViewDTO> findAllVouchersAsViewDTO() {
        return findAllVouchers().stream()
                .map(VoucherViewDTO::new)
                .toList();
    }

    public VoucherViewDTO findVoucherByIdAsViewDTO(UUID voucherId) {
        Voucher foundVoucher = voucherRepository.findById(voucherId)
                .orElseThrow(() ->
                        new EmptyResultDataAccessException("cannot find voucher for voucherId=" + voucherId, 1)
                );
        return new VoucherViewDTO(foundVoucher);
    }

    public void updateVoucherDiscountLevel(UUID voucherId, long discountLevel) {
        voucherRepository.updateDiscountLevel(voucherId, discountLevel);
    }

    public void deleteVoucherById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }
}
