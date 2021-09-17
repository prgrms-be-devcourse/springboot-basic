package org.prgrms.kdt.voucher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.prgrms.kdt.exception.ResourceNotFoundException;
import org.prgrms.kdt.form.VoucherForm;
import org.prgrms.kdt.form.VoucherSearchForm;
import org.prgrms.kdt.mapper.VoucherMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 10:27 오전
 */
@Service
@Transactional(readOnly = true)
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

    @Transactional
    public void addVoucher(VoucherForm voucherForm) {
        Voucher voucher = new Voucher(
                UUID.randomUUID(),
                voucherForm.getName(),
                Long.valueOf(voucherForm.getDiscount()),
                VoucherType.valueOf(voucherForm.getVoucherType()),
                LocalDateTime.now());

        voucherRepository.insert(voucher);
    }

    @Transactional
    public void addVoucher(VoucherDto voucherDto) {
        Voucher voucher = VoucherMapper.voucherDtoToVoucher(voucherDto);
        voucherRepository.insert(voucher);
    }

    @Transactional
    public void removeVoucher(String voucherId) {
        voucherRepository.deleteById(UUID.fromString(voucherId));
    }

    public List<VoucherDto> getVoucherByVoucherType(String voucherType) {
        return voucherRepository.findByVoucherType(VoucherType.valueOf(voucherType)).stream()
                .map(VoucherMapper::voucherToVoucherDto)
                .toList();
    }

    public List<VoucherDto> getVoucherByCreatedAt(String beforeDate, String afterDate) {
        List<Voucher> vouchers = voucherRepository.findByPeriodByCreatedAt(
                LocalDate.parse(beforeDate), LocalDate.parse(afterDate));

        return vouchers.stream()
                .map(VoucherMapper::voucherToVoucherDto)
                .toList();
    }

    public List<VoucherDto> getSearchVoucher(VoucherSearchForm form) {
        SearchVoucher searchVoucher = new SearchVoucher(
                form.getVoucherType() == null ? null : VoucherType.valueOf(form.getVoucherType()),
                form.getBeforeDate() == null ? null : LocalDate.parse(form.getBeforeDate()),
                form.getAfterDate() == null ? null : LocalDate.parse(form.getAfterDate()));

        List<Voucher> vouchers = voucherRepository.findBySearchData(searchVoucher);
        return vouchers.stream()
                .map(VoucherMapper::voucherToVoucherDto)
                .toList();
    }
}
