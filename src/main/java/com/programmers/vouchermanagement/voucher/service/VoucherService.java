package com.programmers.vouchermanagement.voucher.service;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherDto;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.programmers.vouchermanagement.constant.Message.NOT_FOUND_VOUCHER;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherDto create(CreateVoucherRequest createVoucherRequest) {
        //TODO: now? DB vs Application
        Voucher voucher = new Voucher(UUID.randomUUID(), createVoucherRequest.discountValue(), createVoucherRequest.voucherType(), LocalDateTime.now());
        voucherRepository.save(voucher);
        return VoucherDto.from(voucher);
    }

    public List<VoucherDto> readAll() {
        List<Voucher> vouchers = voucherRepository.findAll();

        if (vouchers.isEmpty()) {
            return Collections.emptyList();
        }

        return vouchers.stream()
                .map(VoucherDto::from)
                .toList();
    }

    public List<VoucherDto> readAllByCreatedAt(LocalDateTime from, LocalDateTime to) {
        List<Voucher> vouchers = voucherRepository.findAllByCreatedAt(from, to);

        if (vouchers.isEmpty()) {
            return Collections.emptyList();
        }

        return vouchers.stream()
                .map(VoucherDto::from)
                .toList();
    }

    public VoucherDto readById(UUID voucherId) {
        Voucher voucher = voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_VOUCHER));
        return VoucherDto.from(voucher);
    }

    public void delete(UUID voucherId) {
        voucherRepository.delete(voucherId);
    }

    public void deleteAll() {
        voucherRepository.deleteAll();
    }

    public void update(UUID voucherId, CreateVoucherRequest createVoucherRequest) {
        // TODO: modify code format
        VoucherDto voucherDto = readById(voucherId);
        Voucher voucher = new Voucher(voucherId, createVoucherRequest.discountValue(), createVoucherRequest.voucherType(), voucherDto.createdAt());
        voucherRepository.update(voucher);
    }

    public List<VoucherDto> readAllByType(VoucherType type) {
        return voucherRepository.findAllByType(type).stream().map(VoucherDto::from).toList();
    }
}
