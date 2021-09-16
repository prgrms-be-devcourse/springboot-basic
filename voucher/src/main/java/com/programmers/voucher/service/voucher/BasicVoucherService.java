package com.programmers.voucher.service.voucher;

import com.programmers.voucher.entity.voucher.DiscountPolicy;
import com.programmers.voucher.entity.voucher.DiscountType;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.repository.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasicVoucherService implements VoucherService {

    private static final Logger log = LoggerFactory.getLogger(BasicVoucherService.class);

    private final VoucherRepository jdbcVoucherRepository;

    public BasicVoucherService(VoucherRepository jdbcVoucherRepository) {
        this.jdbcVoucherRepository = jdbcVoucherRepository;
    }

    @Override
    public void openStorage() {
        jdbcVoucherRepository.loadVouchers();
    }

    @Override
    public void closeStorage() {
        jdbcVoucherRepository.persistVouchers();
    }

    @Override
    public Voucher create(String name, DiscountType type, int value, long customerId) {
        Voucher voucher = new Voucher(name, new DiscountPolicy(value, type), customerId);
        voucher = jdbcVoucherRepository.save(voucher);
        log.debug("Persisted voucher {} to repository", voucher);
        return voucher;
    }

    @Override
    public List<Voucher> listAll() {
        return jdbcVoucherRepository.listAll();
    }

    @Override
    public Optional<Voucher> findById(long id) {
        return jdbcVoucherRepository.findById(id);
    }

    @Override
    public Voucher update(Voucher voucher) {
        jdbcVoucherRepository.update(voucher);
        return voucher;
    }

    @Override
    public void delete(long voucherId) {
        jdbcVoucherRepository.findById(voucherId).ifPresentOrElse(
                voucher -> jdbcVoucherRepository.deleteById(voucherId),
                () -> {
                    throw new IllegalArgumentException("Voucher with given id not found.");
                }
        );
    }

}
