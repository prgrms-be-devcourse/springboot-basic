package org.programmers.springboot.basic.domain.voucher.service;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherRequestDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherResponseDto;
import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.programmers.springboot.basic.domain.voucher.exception.VoucherNotFoundException;
import org.programmers.springboot.basic.domain.voucher.exception.VoucherValidatorNotFoundException;
import org.programmers.springboot.basic.domain.voucher.mapper.VoucherMapper;
import org.programmers.springboot.basic.domain.voucher.repository.VoucherRepository;
import org.programmers.springboot.basic.domain.voucher.service.validate.ValidateHandler;
import org.programmers.springboot.basic.util.exception.CSVFileIOFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VoucherService {

    private final Map<VoucherType, ValidateHandler> validateHandlers;
    private final VoucherMapper voucherMapper;
    private final VoucherRepository voucherRepository;

    public VoucherService(Map<VoucherType, ValidateHandler> validateHandlers, VoucherMapper voucherMapper, VoucherRepository voucherRepository) {
        this.validateHandlers = validateHandlers;
        this.voucherMapper = voucherMapper;
        this.voucherRepository = voucherRepository;
    }

    public void create(VoucherRequestDto voucherRequestDto) {

        Long discount = voucherRequestDto.discount();
        VoucherType voucherType = voucherRequestDto.voucherType();

        ValidateHandler handler = validateHandlers.get(voucherType);

        if (handler == null) {
            throw new VoucherValidatorNotFoundException();
        }

        handler.validate(discount);
        Voucher voucher = voucherMapper.mapToVoucher(voucherRequestDto);
        this.voucherRepository.save(voucher);
    }

    public List<VoucherResponseDto> findAll() {

        List<Voucher> vouchers;

        try {
            vouchers = voucherRepository.findAll();
        } catch (CSVFileIOFailureException e) {
            log.error(e.toString());
            throw new VoucherNotFoundException();
        }

        if (vouchers.isEmpty()) {
            throw new VoucherNotFoundException();
        }

        return vouchers.stream()
                .map(voucherMapper::mapToVoucherDto)
                .collect(Collectors.toList());
    }
}
