package org.voucherProject.voucherProject.voucher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.voucherProject.voucherProject.customer.entity.Customer;
import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.repository.VoucherRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    @Override
    public Voucher getVoucher(UUID voucherId){
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("Can't find a voucher for {0}", voucherId)));
    }

    @Override
    public List<Voucher> findByCustomer(Customer customer) {
        UUID customerId = customer.getCustomerId();
        return voucherRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Voucher> findAll(){
        return voucherRepository.findAll();
    }

    @Override
    public Voucher updateVoucher(Voucher voucher) {
        voucherRepository.update(voucher);
        return voucher;
    }

    @Override
    public Voucher save(Voucher voucher){
        voucherRepository.save(voucher);
        return voucher;
    }
}
