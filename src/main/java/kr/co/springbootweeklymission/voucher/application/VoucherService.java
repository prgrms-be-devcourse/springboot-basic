package kr.co.springbootweeklymission.voucher.application;

import kr.co.springbootweeklymission.infrastructure.error.exception.NotFoundException;
import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;
import kr.co.springbootweeklymission.voucher.api.dto.request.VoucherReqDTO;
import kr.co.springbootweeklymission.voucher.api.dto.response.VoucherResDTO;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));
        return VoucherResDTO.READ.toVoucherReadDto(readVoucher);
    }

    public List<VoucherResDTO.READ> getVouchersAll() {
        final List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream()
                .map(VoucherResDTO.READ::toVoucherReadDto)
                .toList();
    }

    public void updateVoucherById(UUID voucherId,
                                  VoucherReqDTO.UPDATE update) {
        final Voucher updateVoucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));
        updateVoucher.updateVoucherInformation(update);
        voucherRepository.update(updateVoucher);
    }

    public void deleteVoucherById(UUID voucherId) {
        final Voucher deleteVoucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));
        voucherRepository.delete(deleteVoucher);
    }
}
