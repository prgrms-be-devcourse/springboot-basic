package org.prgrms.springorder.domain.voucher.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.domain.voucher.api.CustomerWithVoucher;
import org.prgrms.springorder.domain.voucher.api.request.VoucherCreateRequest;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
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

    @Transactional
    public Voucher createVoucher(VoucherType voucherType, long amount, UUID customerId) {
        return voucherRepository.insert(
            VoucherFactory.toVoucher(voucherType, UUID.randomUUID(), amount, customerId,
                LocalDateTime.now()));
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

    @Transactional // 특정 고객에게 존재하는 바우처 할당
    public void changeCustomerId(UUID voucherId, UUID customerId) {
        Voucher voucher = voucherRepository.findById(voucherId)
            .orElseThrow(() -> new EntityNotFoundException(Voucher.class, voucherId));

        voucher.changeCustomerId(customerId);

        try {
            voucherRepository.update(voucher);
        } catch (DataIntegrityViolationException e) {
            throw new EntityNotFoundException(Customer.class, customerId, e);
       }
    }

    // 특정 바우처를 보유한 고객을 조회 ->  바우처 서비스 역할 -> /api/vouchers/1/customers
    @Transactional(readOnly = true)
    public CustomerWithVoucher findVoucherWithCustomerByVoucherId(UUID voucherId) {
        Optional<CustomerWithVoucher> byIdWithCustomer = voucherRepository.findByIdWithCustomer(
            voucherId);

        return byIdWithCustomer.orElseThrow(
            () -> new EntityNotFoundException(Voucher.class, voucherId));
    }

    @Transactional
    public void deleteById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    @Transactional // 고객이 보유한 바우처를 제거할 수 있어야 합니다.
    public void deleteVoucherByCustomerId(UUID voucherId, UUID customerId) {
        Voucher voucher = voucherRepository.findById(voucherId)
            .orElseThrow(() -> new EntityNotFoundException(Voucher.class, voucherId));

        if (!voucher.isOwned(customerId)) {
            throw new BadAccessRequestException("해당 유저의 바우처가 아닙니다.");
        }

        voucherRepository.deleteById(voucherId);
    }

}