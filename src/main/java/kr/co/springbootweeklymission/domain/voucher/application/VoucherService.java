package kr.co.springbootweeklymission.domain.voucher.application;

import kr.co.springbootweeklymission.domain.voucher.dao.VoucherRepository;
import kr.co.springbootweeklymission.domain.voucher.dto.request.VoucherReqDTO;
import kr.co.springbootweeklymission.domain.voucher.dto.response.VoucherResDTO;
import kr.co.springbootweeklymission.domain.voucher.entity.Voucher;
import kr.co.springbootweeklymission.global.error.exception.EntityNotFoundException;
import kr.co.springbootweeklymission.global.error.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public void createVoucher(VoucherReqDTO.CREATE create) {
        final Voucher saveVoucher = Voucher.toVoucher(create);
        voucherRepository.save(saveVoucher);
    }

    public VoucherResDTO.READ getVoucherById(UUID voucherId) {
        final Voucher readVoucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));
        return Voucher.toVoucherReadDto(readVoucher);
    }

    public List<VoucherResDTO.READ> getVouchersAll() {
        final List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream()
                .map(Voucher::toVoucherReadDto)
                .collect(Collectors.toList());
    }
}
