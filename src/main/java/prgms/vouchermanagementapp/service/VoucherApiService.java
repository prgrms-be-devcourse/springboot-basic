package prgms.vouchermanagementapp.service;

import org.springframework.stereotype.Service;
import prgms.vouchermanagementapp.model.Voucher;
import prgms.vouchermanagementapp.model.VoucherType;
import prgms.vouchermanagementapp.model.dto.VoucherCreationDto;
import prgms.vouchermanagementapp.model.dto.VoucherQueryDto;
import prgms.vouchermanagementapp.model.dto.VoucherResponseDto;
import prgms.vouchermanagementapp.repository.JdbcVoucherRepository;
import prgms.vouchermanagementapp.util.DateTimeConverter;

import java.time.LocalDateTime;
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

    public List<VoucherResponseDto> findAllAsDto() {
        return findAll().stream()
                .map(VoucherResponseDto::new)
                .toList();
    }

    public List<VoucherResponseDto> findAllByTypeAsDto(String voucherType) {
        return findAll().stream()
                .filter(voucher -> VoucherType.fromSpecificType(voucher.getVoucherType())
                        == VoucherType.fromSimpleType(voucherType))
                .map(VoucherResponseDto::new)
                .toList();
    }

    public List<VoucherResponseDto> findAllByCreatedAtAsDto(VoucherQueryDto.ByCreatedAt queryDto) {
        LocalDateTime start = DateTimeConverter.toStartOfDay(queryDto.getStart());
        LocalDateTime end = DateTimeConverter.toEndOfDay(queryDto.getEnd());

        return findAll().stream()
                .filter(voucher -> voucher.getCreatedDateTime().isAfter(start)
                        && voucher.getCreatedDateTime().isBefore(end))
                .map(VoucherResponseDto::new)
                .toList();
    }

    public void deleteById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    private List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
