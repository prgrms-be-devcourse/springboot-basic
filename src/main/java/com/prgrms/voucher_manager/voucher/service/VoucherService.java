package com.prgrms.voucher_manager.voucher.service;

import com.prgrms.voucher_manager.voucher.Voucher;
import com.prgrms.voucher_manager.voucher.VoucherType;
import com.prgrms.voucher_manager.voucher.controller.VoucherDto;
import com.prgrms.voucher_manager.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
public class VoucherService {
    private final static Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<VoucherDto> getFindAllVoucher() {
        List<VoucherDto> voucherDtos = new ArrayList<>();
        List<Voucher> vouchers = voucherRepository.findAll();
        voucherDtos = vouchers.stream()
                .map(VoucherDto::of)
                .collect(Collectors.toList());
        try{
            AtomicInteger i = new AtomicInteger();
            vouchers.forEach(v -> {
                logger.info(i.getAndIncrement() + " : " + v.toString());
            });
        } catch (RuntimeException e) {
            logger.info("JDBC voucher 가 비어있습니다. ");
        }
        return voucherDtos;
    }

    public VoucherDto createVoucher(String type, Long value) {
        VoucherType voucherType = VoucherType.getVoucherType(type);
        Voucher voucher = voucherType.create(value);
        voucherRepository.insert(voucher);
        return VoucherDto.of(voucher);
    }
    public List<VoucherDto> findByType(String type) {
        List<Voucher> vouchers = voucherRepository.findByType(type);
        return getVoucherDtoList(vouchers);
    }

    public List<VoucherDto> findByDate(LocalDate start, LocalDate end) {
        List<Voucher> vouchers = voucherRepository.findByDate(start, end);
        return getVoucherDtoList(vouchers);
    }

    public List<VoucherDto> findByDateAndType(LocalDate start, LocalDate end, String type) {
        List<Voucher> vouchers = voucherRepository.findByDateAndType(start, end, type);
        return getVoucherDtoList(vouchers);
    }


    public VoucherDto deleteVoucher(UUID voucherId) {
        Voucher deleteVoucher = voucherRepository.findById(voucherId)
                .orElseThrow(RuntimeException::new);
        voucherRepository.delete(deleteVoucher);
        return VoucherDto.of(deleteVoucher);
    }

    public void updateVoucher(UUID voucherId, String type, long value) {
        Voucher updateVoucher = voucherRepository.findById(voucherId)
                .orElseThrow(RuntimeException::new);
        updateVoucher.changeValue(value);

        voucherRepository.update(updateVoucher);
    }

    public VoucherDto findById(UUID voucherId) {
        Voucher voucher = voucherRepository
                .findById(voucherId)
                .orElseThrow(RuntimeException::new);
        return VoucherDto.of(voucher);
    }

    private List<VoucherDto> getVoucherDtoList(List<Voucher> vouchers) {
        List<VoucherDto> voucherDtos = new ArrayList<>();
        voucherDtos = vouchers.stream()
                .map(VoucherDto::of)
                .collect(Collectors.toList());
        return voucherDtos;
    }
}
