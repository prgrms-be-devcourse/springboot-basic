package kr.co.springbootweeklymission.voucher.application;

import kr.co.springbootweeklymission.common.error.exception.NotFoundException;
import kr.co.springbootweeklymission.common.response.ResponseStatus;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.repository.VoucherRepository;
import kr.co.springbootweeklymission.voucher.presentation.dto.request.VoucherReqDTO;
import kr.co.springbootweeklymission.voucher.presentation.dto.response.VoucherResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;

    @Transactional
    public void createVoucher(VoucherReqDTO.CREATE create) {
        final Voucher saveVoucher = Voucher.toVoucher(create);
        voucherRepository.save(saveVoucher);
    }

    public VoucherResDTO.READ getVoucherById(UUID voucherId) {
        final Voucher readVoucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));
        return VoucherResDTO.READ.toVoucherReadDto(readVoucher);
    }

    public List<VoucherResDTO.READ> getVouchersAll() {
        final List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream()
                .map(VoucherResDTO.READ::toVoucherReadDto)
                .toList();
    }

    @Transactional
    public void updateVoucherById(UUID voucherId,
                                  VoucherReqDTO.UPDATE update) {
        final Voucher updateVoucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));
        updateVoucher.updateVoucherInformation(update);
        voucherRepository.update(updateVoucher);
    }

    @Transactional
    public void deleteVoucherById(UUID voucherId) {
        final Voucher deleteVoucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));
        voucherRepository.delete(deleteVoucher);
    }
}
