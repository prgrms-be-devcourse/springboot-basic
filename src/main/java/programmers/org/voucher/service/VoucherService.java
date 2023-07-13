package programmers.org.voucher.service;

import org.springframework.stereotype.Service;
import programmers.org.voucher.domain.Voucher;
import programmers.org.voucher.dto.VoucherRequest;
import programmers.org.voucher.dto.VoucherResponse;
import programmers.org.voucher.repository.VoucherRepository;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<VoucherResponse> getAllVouchers() {
        return voucherRepository.getAll()
                .stream()
                .map(VoucherResponse::new)
                .collect(Collectors.toList());
    }

    private void saveVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }
}
