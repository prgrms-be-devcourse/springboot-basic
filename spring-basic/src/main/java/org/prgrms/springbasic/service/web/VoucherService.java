package org.prgrms.springbasic.service.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.controller.view.dto.VoucherDto;
import org.prgrms.springbasic.domain.voucher.VoucherType;
import org.prgrms.springbasic.repository.voucher.VoucherRepository;
import org.prgrms.springbasic.utils.PeriodConverter;
import org.prgrms.springbasic.utils.exception.DuplicatedDataException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.springbasic.utils.enumm.message.ErrorMessage.DUPLICATED_VOUCHER;
import static org.prgrms.springbasic.utils.enumm.message.ErrorMessage.NOT_FOUND_VOUCHER;
import static org.prgrms.springbasic.utils.validator.VoucherValidator.validateVouchers;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public List<Voucher> findVouchers() {
        return voucherRepository.findVouchers();
    }

    public Voucher findVoucherByVoucherId(UUID voucherId) throws NoSuchElementException {
        return voucherRepository.findByVoucherId(voucherId)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_VOUCHER.getMessage()));
    }

    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        var retrievedVouchers = voucherRepository.findByCustomerId(customerId);

        return validateVouchers(retrievedVouchers);
    }

    public List<Voucher> findVoucherByVoucherType(VoucherType voucherType) {
        var retrievedVouchers = voucherRepository.findByVoucherType(voucherType);

        return validateVouchers(retrievedVouchers);
    }

    public List<Voucher> findVoucherByPeriod(String from, String to) {
        var period = PeriodConverter.of(from, to);

        var retrievedVoucher = voucherRepository.findByCreatedPeriod(period.get(0), period.get(1));

        return validateVouchers(retrievedVoucher);
    }

    public Voucher addVoucher(Voucher voucher) throws DuplicatedDataException {
        validateDuplicatedVoucher(voucher.getVoucherId());

        return voucherRepository.save(voucher);
    }

    public void modifyVoucher(Voucher voucher, VoucherDto voucherDto) {
        var retrievedVoucher = voucher.update(voucherDto.getVoucherType(), voucherDto.getDiscountInfo(), voucherDto.getCustomerId());

        voucherRepository.update(retrievedVoucher);
    }

    public void assignToCustomer(Voucher voucher, UUID customerId) {
        var assignedVoucher = voucher.assignToCustomer(customerId);

        voucherRepository.update(assignedVoucher);
    }

    public boolean removeVoucherById(UUID voucherId) {
        return voucherRepository.deleteByVoucherId(voucherId);
    }

    public void removeVouchers() {
        voucherRepository.deleteVouchers();
    }

    private void validateDuplicatedVoucher(UUID voucherId) {
        Optional<Voucher> retrievedVoucher = voucherRepository.findByVoucherId(voucherId);

        if (retrievedVoucher.isPresent()) {
            log.error("Got duplicated voucher: {}", voucherId);

            throw new DuplicatedDataException(DUPLICATED_VOUCHER.getMessage());
        }
    }
}
