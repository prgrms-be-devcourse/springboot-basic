package me.programmers.springboot.basic.springbootbasic.voucher.service;

import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import me.programmers.springboot.basic.springbootbasic.voucher.repository.JdbcTemplateVoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JdbcVoucherService {

    private final JdbcTemplateVoucherRepository voucherRepository;

    public JdbcVoucherService(JdbcTemplateVoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public List<Voucher> getAllFixVouchers() {
        return voucherRepository.findAllFixVouchers();
    }

    public List<Voucher> getAllPercentVouchers() {
        return voucherRepository.findAllPercentVouchers();
    }

    public Voucher getVoucherById(UUID voucherId) {
        Optional<Voucher> voucher = voucherRepository.findById(voucherId);
        return voucher.orElse(null);
    }

    public Voucher save(Voucher voucher) {
        voucherRepository.save(voucher);
        return voucher;
    }

    public void update(Voucher voucher) {
        voucherRepository.update(voucher);
    }

    public void delete() {
        voucherRepository.deleteAll();
    }

    public void deleteById(UUID uuid) {
        voucherRepository.deleteById(uuid);
    }
}
