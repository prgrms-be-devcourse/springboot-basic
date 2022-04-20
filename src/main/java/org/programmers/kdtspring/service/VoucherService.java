package org.programmers.kdtspring.service;

import org.programmers.kdtspring.entity.user.Customer;
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

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;
    private Long sequence = 1L;

    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    public VoucherService(VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    public void createFixedAmountVoucher(Optional<Customer> customer, int amount) {
        logger.info("[VoucherService] createFixedAmountVoucher(long amount) called");

        Voucher FixedAmountVoucher = new FixedAmountVoucher(sequence++, customer.get().getCustomerId(), amount, VoucherType.FixedAmountVoucher.name());
        voucherRepository.save(FixedAmountVoucher);

        logger.info("{} saved", FixedAmountVoucher);
    }

    public void createPercentDiscountVoucher(int percent) {
        logger.info("[VoucherService] createPercentDiscountVoucher(long amount) called");

        Voucher PercentDiscountVoucher = new PercentDiscountVoucher(sequence++, percent, VoucherType.PercentDiscountVoucher.name());
        voucherRepository.save(PercentDiscountVoucher);

        logger.info("{} saved", PercentDiscountVoucher);
    }

    public List<Voucher> findAll() {
        logger.info("[VoucherService] findAll() called");

        return voucherRepository.findAll();
    }

    public void VoucherBelongToCustomer(Long voucherId, Long customerId) {
        logger.info("[VoucherService] VoucherBelongToCustomer() called()");

        var voucher = voucherRepository.findById(voucherId).orElseThrow(NoSuchElementException::new);
        var customer = customerRepository.findById(customerId)
                .orElseThrow(NoSuchElementException::new);
        voucher.belongToCustomer(customer);
        voucherRepository.updateCustomerId(voucher);
    }

    public List<Voucher> findVoucherForCustomer(Long customerId) {
        logger.info("[VoucherService] findVoucherForCustomer() called");
        Customer customer = customerRepository.findById(customerId).orElseThrow(NoSuchElementException::new);
        return voucherRepository.findByCustomer(customer);
    }

}
