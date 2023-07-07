package kr.co.springbootweeklymission.voucher.application;

import kr.co.springbootweeklymission.voucher.api.dto.request.VoucherReqDTO;
import kr.co.springbootweeklymission.voucher.api.dto.response.VoucherResDTO;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public void createVoucher(VoucherReqDTO.CREATE create) {
        final Voucher saveVoucher = Voucher.toVoucher(create);
        voucherRepository.save(saveVoucher);
    }

    public List<VoucherResDTO.READ> getVouchersAll() {
        final List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream()
                .map(Voucher::toVoucherReadDto)
                .toList();
    }
}
