package com.kdt.commandLineApp.voucher;

import com.kdt.commandLineApp.exception.WrongVoucherParamsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private VoucherRepository voucherRepository;
    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void addVoucher(String type, Float amount) throws WrongVoucherParamsException {
        try {
            voucherRepository.add(new Voucher(type, amount));
        }
        catch (Exception e) {
            throw new WrongVoucherParamsException();
        }
    }

    public Optional<Voucher> getVoucher(String id) {
        return voucherRepository.get(UUID.fromString(id));
    }

    public List<Voucher> getVouchers() {
        return voucherRepository.getAll();
    }

    public void showVouchers() {
        getVouchers().stream().forEach(System.out::println);
    }
}
