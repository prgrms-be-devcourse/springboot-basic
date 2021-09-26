package org.prgrms.kdt.voucher.service;

import java.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.prgrms.kdt.customer.controller.CustomerDto;
import org.prgrms.kdt.customer.model.CustomerType;
import org.prgrms.kdt.exception.BadRequestException;
import org.prgrms.kdt.exception.NotFoundException;
import org.prgrms.kdt.utils.EnumUtils;
import org.prgrms.kdt.utils.MappingUtils;
import org.prgrms.kdt.voucher.controller.VoucherDto;
import org.prgrms.kdt.voucher.controller.VoucherSearch;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;


@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final ModelMapper modelMapper;

    public VoucherService(VoucherRepository voucherRepository,
        ModelMapper modelMapper) {
        this.voucherRepository = voucherRepository;
        this.modelMapper = modelMapper;
    }

    public VoucherDto getVoucher(UUID voucherId) {
        var voucher = voucherRepository
            .findById(voucherId)
            .orElseThrow(
                () -> new NotFoundException("Can not find a voucher %s".formatted(voucherId)));
        return modelMapper.map(voucher, VoucherDto.class);
    }

    public List<VoucherDto> getAllVouchers() {
        return MappingUtils
            .mapList(voucherRepository.findAllVoucher(), VoucherDto.class, modelMapper);
    }

    public List<VoucherDto> getFilteredVouchers(VoucherSearch voucherSearch) {
        return MappingUtils
            .mapList(voucherRepository.findFilteredVouchers(voucherSearch), VoucherDto.class, modelMapper);
    }

    @Transactional
    public VoucherDto createVoucher(VoucherDto voucherDto) {
        var voucher = voucherRepository.insert(
            new Voucher(
                UUID.randomUUID(),
                voucherDto.getDiscount(),
                LocalDateTime.now(),
                EnumUtils.getVoucherTypeByName(voucherDto.getVoucherType())
                    .orElseThrow(() -> new BadRequestException("invalid voucher type")))
        );
        return modelMapper.map(voucher, VoucherDto.class);
    }

    @Transactional
    public VoucherDto updateVoucher(UUID voucherId, VoucherDto voucherDto) {
        var voucher = voucherRepository
            .findById(voucherId)
            .orElseThrow(
                () -> new NotFoundException("Can not find customer %s".formatted(voucherId)));
        voucher.changeVoucherType(
            EnumUtils.getVoucherTypeByName(voucherDto.getVoucherType())
                .orElseThrow(() -> new BadRequestException("invalid voucher type")),
            voucherDto.getDiscount()
        );

        var updatedVoucher = voucherRepository.updateType(voucher);
        return modelMapper.map(updatedVoucher, VoucherDto.class);
    }

    public void deleteAllVouchers() {
        voucherRepository.deleteAll();
    }

    public void deleteById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    public List<VoucherDto> getVouchersByCustomerId(UUID customerId) {
        return MappingUtils.mapList(
            voucherRepository.findByCustomerId(customerId),
            VoucherDto.class,
            modelMapper);
    }

}
