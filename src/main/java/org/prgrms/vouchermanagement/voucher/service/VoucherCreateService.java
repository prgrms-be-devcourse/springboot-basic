package org.prgrms.vouchermanagement.voucher.service;

import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.customer.repository.CustomerRepository;
import org.prgrms.vouchermanagement.exception.customer.CustomerNotFoundException;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.domain.VoucherType;
import org.prgrms.vouchermanagement.voucher.domain.dto.VoucherVO;
import org.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherCreateService {

    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    public VoucherCreateService(VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    public Voucher createVoucher(String voucherTypeInput, int discountValue, String customerEmail) {

        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(CustomerNotFoundException::new);

        Voucher voucher = VoucherType.createVoucher(
                VoucherVO.of(UUID.randomUUID(), voucherTypeInput, discountValue, customer.getCustomerId()));

        voucherRepository.save(voucher);

        return voucher;
    }
}
