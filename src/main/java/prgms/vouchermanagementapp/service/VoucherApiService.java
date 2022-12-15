package prgms.vouchermanagementapp.service;

import org.springframework.stereotype.Service;
import prgms.vouchermanagementapp.model.Voucher;
import prgms.vouchermanagementapp.model.dto.VoucherCreationDto;
import prgms.vouchermanagementapp.model.dto.VoucherResponseDto;
import prgms.vouchermanagementapp.repository.JdbcVoucherRepository;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherApiService {

    private JdbcVoucherRepository voucherRepository;

    public VoucherApiService(JdbcVoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public UUID save(VoucherCreationDto voucherCreationDto) {
        Voucher voucher = VoucherFactory.createVoucher(
                voucherCreationDto.getVoucherType(),
                voucherCreationDto.getDiscountLevel()
        );

        voucherRepository.save(voucher);

        return voucher.getVoucherId();
    }

    public VoucherResponseDto findById(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .map(VoucherResponseDto::new)
                .orElseThrow(() -> new IllegalArgumentException(
                        "[Error] Can't find Voucher for voucherId=" + voucherId
                ));
    }

    public List<VoucherResponseDto> findAll() {
        return voucherRepository.findAll()
                .stream()
                .map(VoucherResponseDto::new)
                .toList();
    }

    public void deleteById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }
}
