package org.programmers.springorder.voucher.service;

import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.customer.repository.CustomerRepository;
import org.programmers.springorder.exception.ErrorCode;
import org.programmers.springorder.exception.VoucherException;
import org.programmers.springorder.voucher.dto.VoucherRequestDto;
import org.programmers.springorder.voucher.dto.VoucherResponseDto;
import org.programmers.springorder.voucher.model.Voucher;
import org.programmers.springorder.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private static final Logger log = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;


    public VoucherService(VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    public List<VoucherResponseDto> getAllVoucher() {
        return voucherRepository.findAll()
                .stream()
                .map(VoucherResponseDto::of)
                .toList();
    }

    public void saveNewVoucher(VoucherRequestDto voucherDto) {
        Voucher voucher = Voucher.of(UUID.randomUUID(), voucherDto);
        voucherRepository.save(voucher);
    }

    public void allocateVoucher(UUID voucherId, UUID customerId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherException(ErrorCode.VOUCHER_NOT_FOUND));
        Customer customer = customerRepository.findByID(customerId)
                .orElseThrow(() -> new VoucherException(ErrorCode.CUSTOMER_NOT_FOUND));
        voucherRepository.updateVoucherOwner(voucher, customer);
    }

    public List<VoucherResponseDto> getCustomerOwnedVouchers(UUID customerId) {
        Customer customer = customerRepository.findByID(customerId)
                .orElseThrow(() -> new VoucherException(ErrorCode.CUSTOMER_NOT_FOUND));
        return voucherRepository.findAllByCustomerId(customer)
                .stream()
                .map(VoucherResponseDto::of)
                .toList();
    }

    public void deleteVoucher(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherException(ErrorCode.VOUCHER_NOT_FOUND));
        voucherRepository.deleteVoucher(voucher);
    }

    public VoucherResponseDto getVoucherById(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .map(VoucherResponseDto::of)
                .orElseThrow(() -> new VoucherException(ErrorCode.VOUCHER_NOT_FOUND));
    }

    public List<VoucherResponseDto> getAllVoucherByTime(LocalDateTime startedAt, LocalDateTime endedAt) {
        return voucherRepository.findAllByTimeLimit(startedAt, endedAt)
                .stream()
                .map(VoucherResponseDto::of)
                .toList();
    }
}
