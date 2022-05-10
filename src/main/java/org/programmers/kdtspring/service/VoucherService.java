package org.programmers.kdtspring.service;

import org.programmers.kdtspring.entity.voucher.FixedAmountVoucher;
import org.programmers.kdtspring.entity.voucher.PercentDiscountVoucher;
import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.entity.voucher.VoucherType;
import org.programmers.kdtspring.repository.user.CustomerRepository;
import org.programmers.kdtspring.repository.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    public VoucherService(VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }

    public List<Voucher> getVouchersByType(String voucherType) {
        return voucherRepository.findByType(voucherType);
    }

    public Optional<Voucher> getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public Optional<Voucher> createVoucher(String voucherType, int discount) {
        logger.info("[VoucherService] createFixedAmountVoucher(long amount) called");
        UUID uuid = UUID.randomUUID();
        if (voucherType.equals(VoucherType.FixedAmountVoucher.toString())) {
            voucherRepository.insert(new FixedAmountVoucher(uuid, discount, voucherType));
            return voucherRepository.findById(uuid);
        }

        if (voucherType.equals(VoucherType.PercentDiscountVoucher.toString())) {
            voucherRepository.insert(new PercentDiscountVoucher(uuid, discount, voucherType));
            return voucherRepository.findById(uuid);
        }
        logger.info("voucher created");
        return Optional.empty();
    }

    public void allocateVoucher(UUID voucherId, UUID customerId) {
        logger.info("[VoucherService] allocateVoucher() called");

        var voucher = voucherRepository.findById(voucherId).orElseThrow(
                NoSuchElementException::new);
        var customer = customerRepository.findById(customerId).orElseThrow(
                NoSuchElementException::new);

        voucher.belongToCustomer(customer);
        voucherRepository.updateCustomerId(voucher);
    }

    public List<Voucher> findVoucherForCustomer(UUID customerId) {
        logger.info("[VoucherService] findVoucherForCustomer() called");

        var customer = customerRepository.findById(customerId)
                .orElseThrow(IllegalArgumentException::new);
        return voucherRepository.findByCustomer(customer);
    }

    public void removeVoucher(UUID voucherId) {
        Optional<Voucher> voucher = voucherRepository.findById(voucherId);
        voucherRepository.deleteOne(voucher.get());
    }

    public void removeAllVoucher() {
        voucherRepository.deleteAll();
    }
}