package com.example.springbootbasic.service.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherType;
import com.example.springbootbasic.repository.voucher.JdbcVoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JdbcVoucherService {
    private final JdbcVoucherRepository voucherRepository;

    public JdbcVoucherService(JdbcVoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher saveVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    public List<Voucher> findAllVouchers() {
        return voucherRepository.findAllVouchers();
    }

    public List<Voucher> findAllVoucherByVoucherType(VoucherType voucherType) {
        return voucherRepository.findAllVouchersByVoucherType(voucherType);
    }

    public Voucher findById(long voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public Voucher update(Voucher voucher) {
        return voucherRepository.update(voucher);
    }

    public void deleteAllVouchers() {
        voucherRepository.deleteAllVouchers();
    }

    public void deleteVouchersByVoucherType(VoucherType voucherType) {
        voucherRepository.deleteVouchersByVoucherType(voucherType);
    }

    public void deleteVoucherById(long voucherId) {
        voucherRepository.deleteVoucherById(voucherId);
    }
}
