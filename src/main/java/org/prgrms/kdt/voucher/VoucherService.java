package org.prgrms.kdt.voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.prgrms.kdt.exception.ResourceNotFoundException;
import org.prgrms.kdt.form.VoucherForm;
import org.prgrms.kdt.mapper.VoucherMapper;
import org.springframework.stereotype.Service;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 10:27 오전
 */
@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherDto getVoucherById(String voucherId) {
        return voucherRepository.findById(UUID.fromString(voucherId))
                .map(VoucherMapper::voucherToVoucherDto)
                .orElseThrow(() -> new ResourceNotFoundException("not found voucherId : " + voucherId));
    }

    public List<VoucherDto> getVouchers(String customerId) {
        return voucherRepository.findVouchersByCustomerId(UUID.fromString(customerId)).stream()
                .map(VoucherMapper::voucherToVoucherDto)
                .collect(Collectors.toList());
    }

    public List<VoucherDto> getAllVouchers() {
        return voucherRepository.findAll().stream()
                .map(VoucherMapper::voucherToVoucherDto)
                .collect(Collectors.toList());
    }

    public void addVoucher(VoucherForm voucherForm) {
        Voucher voucher = new Voucher(
                UUID.randomUUID(),
                voucherForm.getName(),
                Long.valueOf(voucherForm.getDiscount()),
                VoucherType.valueOf(voucherForm.getVoucherType()),
                LocalDateTime.now());

        voucherRepository.insert(voucher);
    }

    public void removeVoucher(String voucherId) {
        voucherRepository.deleteById(UUID.fromString(voucherId));
    }
}
