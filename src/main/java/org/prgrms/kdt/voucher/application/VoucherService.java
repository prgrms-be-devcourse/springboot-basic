package org.prgrms.kdt.voucher.application;

import org.prgrms.kdt.customer.domain.Customer;
import org.prgrms.kdt.customer.domain.vo.Email;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.vo.Type;
import org.prgrms.kdt.voucher.dto.VoucherDto;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public Voucher createVoucher(VoucherDto voucher) {
        return voucherRepository.insert(voucher);
    }

    @Transactional(readOnly = true)
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Voucher> findByEmail(Email email) {
        return voucherRepository.findByEmail(email);
    }

    @Transactional
    public void deleteById(UUID id) {
        voucherRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Voucher> findByType(Type type) {
        return voucherRepository.findByType(type);
    }

    @Transactional(readOnly = true)
    public List<Voucher> findByCreatedDate(LocalDateTime time) {
        return voucherRepository.findByCreatedDate(time);
    }

    @Transactional(readOnly = true)
    public Voucher findById(UUID id) {
        return voucherRepository.findById(id);
    }
}