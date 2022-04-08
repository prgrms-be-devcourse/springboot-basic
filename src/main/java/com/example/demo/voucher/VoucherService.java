package com.example.demo.voucher;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void addVoucher(String type, Float amount) {
        voucherRepository.add(new Voucher(type, amount));
    }

    public Optional<Voucher> getVoucher(String id) {
        return voucherRepository.get(UUID.fromString(id));
    }

    public Optional<ArrayList<Voucher>> getVouchers() {
        return voucherRepository.getAll();
    }
}
