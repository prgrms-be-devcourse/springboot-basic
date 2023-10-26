package org.programmers.springorder.voucher.service;

import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.customer.repository.CustomerRepository;
import org.programmers.springorder.voucher.dto.VoucherRequestDto;
import org.programmers.springorder.voucher.dto.VoucherResponseDto;
import org.programmers.springorder.voucher.model.Voucher;
import org.programmers.springorder.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    public List<VoucherResponseDto> getAllVoucher(){
        return voucherRepository.findAll()
                .stream()
                .map(VoucherResponseDto::of)
                .toList();
    }

    public void save(VoucherRequestDto voucherDto) {
        Voucher voucher = Voucher.of(UUID.randomUUID(), voucherDto);
        voucherRepository.save(voucher);
        log.info("등록된 Voucher => iD: {}, type: {}, value: {}", voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscountValue());
    }

    public String update(UUID voucherId, UUID customerID){
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow( () -> new RuntimeException("해당 바우처를 찾을 수 없습니다."));
        Customer customer = customerRepository.findByID(customerID)
                .orElseThrow( () -> new RuntimeException("해당 고객을 찾을 수 없습니다."));
        Voucher updatedVoucher = voucherRepository.updateVoucherOwner(voucher, customer);
        return updatedVoucher.getVoucherId().toString();
    }
}
