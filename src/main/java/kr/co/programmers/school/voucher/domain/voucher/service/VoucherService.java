package kr.co.programmers.school.voucher.domain.voucher.service;

import kr.co.programmers.school.voucher.domain.voucher.domain.Voucher;
import kr.co.programmers.school.voucher.domain.voucher.dto.VoucherListResponse;
import kr.co.programmers.school.voucher.domain.voucher.dto.VoucherRequest;
import kr.co.programmers.school.voucher.domain.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher(VoucherRequest voucherRequest) {
        Voucher voucher = VoucherFactory.createVoucher(voucherRequest);
        voucherRepository.save(voucher);
    }

    public VoucherListResponse getAllVoucher() {
        List<Voucher> voucherList = voucherRepository.findAll();
        VoucherListResponse voucherListResponse = VoucherListResponse.from(voucherList);
        return voucherListResponse;
    }
}