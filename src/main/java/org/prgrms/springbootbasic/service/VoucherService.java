package org.prgrms.springbootbasic.service;

import static org.prgrms.springbootbasic.exception.ServiceExceptionMessage.ALREADY_ASSIGNED_VOUCHER_EXP_MSG;
import static org.prgrms.springbootbasic.exception.ServiceExceptionMessage.INVALID_CUSTOMER_ID_EXP_MSG;
import static org.prgrms.springbootbasic.exception.ServiceExceptionMessage.INVALID_VOUCHER_ID_EXP_MSG;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.voucher.Voucher;
import org.prgrms.springbootbasic.exception.AlreadyAssignedVoucherException;
import org.prgrms.springbootbasic.exception.InvalidCustomerIdException;
import org.prgrms.springbootbasic.exception.InvalidVoucherIdException;
import org.prgrms.springbootbasic.repository.customer.CustomerRepository;
import org.prgrms.springbootbasic.repository.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class VoucherService {

    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    public VoucherService(VoucherRepository voucherRepository,
        CustomerRepository customerRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public UUID createVoucher(VoucherType voucherType, int amount, int percent) {
        logger.info("createVoucher() called");

        if (voucherType.isFixed()) {
            var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
            voucherRepository.insert(fixedAmountVoucher);
            return fixedAmountVoucher.getVoucherId();
        }

        var percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), percent);
        voucherRepository.insert(percentDiscountVoucher);
        return percentDiscountVoucher.getVoucherId();
    }

    public List<Voucher> findAll() {
        logger.info("findAll() called");

        return voucherRepository.findAll();
    }

    @Transactional
    public void assignVoucherToCustomer(UUID voucherId, UUID customerId) {
        logger.info("assignVoucherToCustomer() called");

        var voucher = voucherRepository.findById(voucherId)
            .orElseThrow(() -> new InvalidVoucherIdException(INVALID_VOUCHER_ID_EXP_MSG));

        validateAssignedVoucher(voucher);

        var customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new InvalidCustomerIdException(INVALID_CUSTOMER_ID_EXP_MSG));

        voucher.assignCustomer(customer);
        voucherRepository.updateCustomerId(voucher);
    }

    public List<Voucher> findCustomerVoucher(UUID customerId) {
        logger.info("findCustomerVoucher() called");

        var customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new InvalidCustomerIdException(INVALID_CUSTOMER_ID_EXP_MSG));
        return voucherRepository.findByCustomer(customer);
    }

    public Voucher findVoucher(UUID voucherId) {
        logger.info("findVoucher() called");

        return voucherRepository.findById(voucherId)
            .orElseThrow(() -> new InvalidVoucherIdException(INVALID_VOUCHER_ID_EXP_MSG));
    }

    @Transactional
    public UUID deleteVoucher(UUID voucherId) {
        logger.info("deleteVoucher() called");

        var voucher = voucherRepository.findById(voucherId)
            .orElseThrow(() -> new InvalidVoucherIdException(INVALID_VOUCHER_ID_EXP_MSG));

        voucherRepository.deleteVoucher(voucher);
        return voucher.getVoucherId();
    }

    private void validateAssignedVoucher(Voucher voucher) {
        if (voucher.getCustomerId() != null) {
            throw new AlreadyAssignedVoucherException(ALREADY_ASSIGNED_VOUCHER_EXP_MSG);
        }
    }

    public List<Voucher> findVoucherUsingType(VoucherType voucherType) {
        return voucherRepository.findByType(voucherType);
    }

    public List<Voucher> findVoucherUsingCreatedAt(LocalDateTime startTime, LocalDateTime endTime) {
        return voucherRepository.findByCreatedAt(startTime, endTime);
    }
}
