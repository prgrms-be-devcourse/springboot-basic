package org.prgrms.kdt.service;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherRepository;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

import static org.prgrms.kdt.exception.Message.CANNOT_FIND_VOUCHER;

@Slf4j
@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final CustomerService customerService;

    public VoucherService(VoucherRepository voucherRepository, CustomerService customerService) {
        this.voucherRepository = voucherRepository;
        this.customerService = customerService;
    }

    public Voucher findVoucher(Long voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format(CANNOT_FIND_VOUCHER + "{0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {
        //TODO
    }

    public Voucher create(String voucherType) {
        Voucher voucher = VoucherType.valueOf(voucherType).of();
        save(voucher);
        return voucher;
    }

    private void save(Voucher voucher) {
        voucherRepository.insert(voucher);
    }

    public List<Voucher> list() {
        return voucherRepository.findAll();
    }

    public Voucher allocate(Long voucherId, Long customerId) {
        Voucher voucher = findVoucher(voucherId);
        Customer customer = customerService.findCustomer(customerId);
        voucherRepository.update(voucher, customer);
        return voucher;
    }
}
