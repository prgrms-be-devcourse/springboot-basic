package org.prgrms.springorder.domain.voucher.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.domain.voucher.api.CustomerWithVoucher;
import org.prgrms.springorder.domain.voucher.api.request.VoucherCreateRequest;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.repository.VoucherRepository;
import org.prgrms.springorder.global.exception.BadAccessRequestException;
import org.prgrms.springorder.global.exception.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public Voucher createVoucher(VoucherCreateRequest request) {
        Voucher voucher = VoucherFactory.create(request);
        return voucherRepository.insert(voucher);
    }

    @Transactional(readOnly = true)
    public List<Voucher> findAll() {
        List<Voucher> vouchers = voucherRepository.findAll();

        if (vouchers.isEmpty()) {
            throw new EntityNotFoundException(Voucher.class, "all");
        }

        return vouchers;
    }

    @Transactional(readOnly = true)
    public List<String> findAllConvertedToString() {
        return findAll().stream()
            .map(Object::toString)
            .collect(Collectors.toList());
    }

    @Transactional
    public void changeCustomerId(UUID voucherId, UUID customerId) {
        Voucher voucher = findById(voucherId);

        voucher.changeCustomerId(customerId);

        try {
            voucherRepository.update(voucher);
        } catch (DataIntegrityViolationException e) {
            throw new EntityNotFoundException(Customer.class, customerId, e);
       }
    }

    @Transactional(readOnly = true)
    public CustomerWithVoucher findVoucherWithCustomerByVoucherId(UUID voucherId) {
        Optional<CustomerWithVoucher> byIdWithCustomer = voucherRepository.findByIdWithCustomer(
            voucherId);

        return byIdWithCustomer.orElseThrow(
            () -> new EntityNotFoundException(Voucher.class, voucherId));
    }

    @Transactional
    public void deleteVoucherByCustomerId(UUID voucherId, UUID customerId) {
        Voucher voucher = findById(voucherId);

        if (!voucher.isOwned(customerId)) {
            throw new BadAccessRequestException("해당 유저의 바우처가 아닙니다.");
        }

        voucherRepository.deleteById(voucherId);
    }

    @Transactional
    public void deleteVoucherById(UUID voucherId) {
        Voucher findVoucher = findById(voucherId);

        voucherRepository.deleteById(voucherId);
    }

    @Transactional
    public Voucher findById(UUID voucherId) {
        return voucherRepository.findById(voucherId)
            .orElseThrow(() -> new EntityNotFoundException(Voucher.class, voucherId));
    }

}