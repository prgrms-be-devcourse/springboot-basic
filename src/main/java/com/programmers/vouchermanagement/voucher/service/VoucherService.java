package com.programmers.vouchermanagement.voucher.service;

import com.programmers.vouchermanagement.voucher.controller.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.controller.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.vouchertype.VoucherType;
import com.programmers.vouchermanagement.voucher.domain.vouchertype.VoucherTypeManager;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static com.programmers.vouchermanagement.util.Message.NOT_FOUND_VOUCHER;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public VoucherResponse create(CreateVoucherRequest createVoucherRequest) {
        Voucher voucher = new Voucher(createVoucherRequest.typeName(), createVoucherRequest.discountValue());
        voucherRepository.insert(voucher);
        return VoucherResponse.from(voucher);
    }

    @Transactional(readOnly = true)
    public List<VoucherResponse> readAll() {
        List<Voucher> vouchers = voucherRepository.findAll();

        return vouchers.stream()
                .map(VoucherResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<VoucherResponse> readAllByCreatedAt(LocalDateTime from, LocalDateTime to) {
        List<Voucher> vouchers = voucherRepository.findAllByCreatedAt(from, to);

        return vouchers.stream()
                .map(VoucherResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public VoucherResponse readById(UUID voucherId) {
        Optional<Voucher> voucherOptional = voucherRepository
                .findById(voucherId);
        Voucher voucher = voucherOptional.orElseThrow(() -> new NoSuchElementException(NOT_FOUND_VOUCHER));
        return VoucherResponse.from(voucher);
    }

    @Transactional
    public VoucherResponse delete(UUID voucherId) {
        VoucherResponse voucher = readById(voucherId);
        voucherRepository.delete(voucherId);
        return voucher;
    }

    @Transactional
    public void deleteAll() {
        voucherRepository.deleteAll();
    }

    @Transactional
    public VoucherResponse update(UUID voucherId, CreateVoucherRequest createVoucherRequest) {
        // TODO: modify code format
        VoucherResponse voucherResponse = readById(voucherId);
        Voucher voucher = new Voucher(voucherId, voucherResponse.createdAt(), createVoucherRequest.typeName(), createVoucherRequest.discountValue());
        voucherRepository.update(voucher);
        return VoucherResponse.from(voucher);
    }

    @Transactional(readOnly = true)
    public List<VoucherResponse> readAllByType(String typeName) {
        VoucherType voucherType = VoucherTypeManager.get(typeName);
        return voucherRepository.findAllByType(voucherType).stream().map(VoucherResponse::from).toList();
    }
}
