package org.prgrms.kdt.service;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherRepository;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.error.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@Service
public class SimpleVoucherService implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final SimpleCustomerService customerService;

    public SimpleVoucherService(VoucherRepository voucherRepository, SimpleCustomerService customerService) {
        this.voucherRepository = voucherRepository;
        this.customerService = customerService;
    }

    @Override
    public Optional<Voucher> findByVoucherId(Long voucherId) {
        checkNotNull(voucherId, "voucherId must be provided");
        return voucherRepository.findById(voucherId);
    }

    @Override
    public void useVoucher(Voucher voucher) {
        //TODO: 바우처 사용 api 구현해야 함
    }

    @Override
    public Voucher create(String voucherType) {
        checkNotNull(voucherType, "voucherType must be provided");
        Voucher voucher = VoucherType.valueOf(voucherType).of();
        save(voucher);
        return voucher;
    }

    private void save(Voucher voucher) {
        voucherRepository.insert(voucher);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Voucher> list() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher allocate(Long voucherId, Long customerId) {
        Voucher voucher = findByVoucherId(voucherId).orElseThrow(() -> new NotFoundException(Voucher.class, voucherId));
        Customer customer = customerService.findCustomer(customerId);
        voucherRepository.update(voucher, customer);
        return voucher;
    }
}
