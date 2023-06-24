package kr.co.springbootweeklymission.domain.voucher.application;

import kr.co.springbootweeklymission.domain.voucher.dao.VoucherRepository;
import kr.co.springbootweeklymission.domain.voucher.api.request.VoucherReqDTO;
import kr.co.springbootweeklymission.domain.voucher.api.response.VoucherResDTO;
import kr.co.springbootweeklymission.domain.voucher.entity.Voucher;
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
