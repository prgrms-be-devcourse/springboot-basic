package com.programmers.part1.order.voucher;

import com.programmers.part1.order.voucher.entity.Voucher;
import com.programmers.part1.order.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository<UUID, Voucher> voucherRepository;

    public VoucherService(VoucherRepository<UUID, Voucher> voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void saveVoucher(Voucher voucher){
        voucherRepository.save(voucher);
    }

    public List<Voucher> getAllVoucher(){
        return voucherRepository.findAll();
    }
}
