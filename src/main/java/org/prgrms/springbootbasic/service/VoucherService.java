package org.prgrms.springbootbasic.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.dto.VoucherDTO;
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
    public void createVoucher(VoucherType voucherType, int amount, int percent) {
        logger.info("createVoucher() called");

        if (voucherType.isFixed()) {
            voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), amount));
        }
        if (voucherType.isPercent()) {
            voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), percent));
        }
    }

    public List<Voucher> findAll() {
        logger.info("findAll() called");

        return voucherRepository.findAll();
    }

    @Transactional
    public void assignVoucherToCustomer(UUID voucherId, UUID customerId) {
        logger.info("assignVoucherToCustomer() called");

        var voucher = voucherRepository.findById(voucherId)
            .orElseThrow(InvalidVoucherIdException::new);

        validateAssignedVoucher(voucher);

        var customer = customerRepository.findById(customerId)
            .orElseThrow(InvalidCustomerIdException::new);

        voucher.assignCustomer(customer);
        voucherRepository.updateCustomerId(voucher);
    }

    public List<VoucherDTO> findCustomerVoucher(UUID customerId) {
        logger.info("findCustomerVoucher() called");

        var customer = customerRepository.findById(customerId)
            .orElseThrow(InvalidCustomerIdException::new);
        return voucherRepository.findByCustomer(customer).stream()
            .map(VoucherDTO::new)
            .collect(Collectors.toList());
    }

    public VoucherDTO findVoucher(UUID voucherId) {
        var voucher = voucherRepository.findById(voucherId)
            .orElseThrow(InvalidVoucherIdException::new);
        return new VoucherDTO(voucher);
    }

    @Transactional
    public UUID deleteVoucher(UUID voucherId) {
        logger.info("deleteVoucher() called");

        var voucher = voucherRepository.findById(voucherId)
            .orElseThrow(InvalidVoucherIdException::new);

        voucherRepository.deleteVoucher(voucher);
        return voucher.getVoucherId();
    }

    private void validateAssignedVoucher(Voucher voucher) {
        if (voucher.getCustomerId().isPresent()) {
            throw new AlreadyAssignedVoucherException();
        }
    }

    public List<Voucher> findVoucherUsingType(VoucherType voucherType) {
        return voucherRepository.findByType(voucherType);
    }

    public List<Voucher> findVoucherUsingCreatedAt(LocalDateTime startTime, LocalDateTime endTime) {
        return voucherRepository.findByCreatedAt(startTime, endTime);
    }
}
