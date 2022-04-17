package com.programmers.part1.order.voucher;

import com.programmers.part1.domain.Voucher;
import com.programmers.part1.order.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 추후 로직이 추가 될 것을 고려해
 * Controller와 Service 계층을 나누어 진행하였습니다.
 * **/

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
