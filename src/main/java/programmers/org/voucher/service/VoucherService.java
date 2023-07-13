package programmers.org.voucher.service;

import org.springframework.stereotype.Service;
import programmers.org.voucher.domain.Voucher;
import programmers.org.voucher.dto.VoucherRequest;
import programmers.org.voucher.repository.VoucherRepository;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void create(VoucherRequest request) {
        Voucher voucher = request.toEntity();
        saveVoucher(voucher);
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.getAll();
    }

    private void saveVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }
}
