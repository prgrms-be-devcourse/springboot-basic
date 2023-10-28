package org.programmers.springboot.basic.domain.voucher.service;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherRequestDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherResponseDto;
import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.programmers.springboot.basic.domain.voucher.exception.DuplicateVoucherException;
import org.programmers.springboot.basic.domain.voucher.exception.VoucherNotFoundException;
import org.programmers.springboot.basic.domain.voucher.exception.VoucherValidatorNotFoundException;
import org.programmers.springboot.basic.domain.voucher.mapper.VoucherEntityMapper;
import org.programmers.springboot.basic.util.generator.UUIDGenerator;
import org.programmers.springboot.basic.domain.voucher.repository.VoucherRepository;
import org.programmers.springboot.basic.domain.voucher.service.validate.ValidateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final Map<VoucherType, ValidateHandler> validateHandlers;
    private final UUIDGenerator uuidGenerator;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository, Map<VoucherType, ValidateHandler> validateHandlers, UUIDGenerator uuidGenerator) {
        this.voucherRepository = voucherRepository;
        this.validateHandlers = validateHandlers;
        this.uuidGenerator = uuidGenerator;
    }

    @Transactional
    public void create(VoucherRequestDto voucherRequestDto) {

        Long discount = voucherRequestDto.getDiscount();
        VoucherType voucherType = voucherRequestDto.getVoucherType();

        ValidateHandler handler;
        Optional.ofNullable(handler = validateHandlers.get(voucherType))
                .orElseThrow(VoucherValidatorNotFoundException::new);

        handler.validate(discount);
        Voucher voucher = VoucherEntityMapper.INSTANCE.mapToVoucherWithGenerator(voucherRequestDto, uuidGenerator);

        if (voucherRepository.findByTypeNDiscount(voucherType, discount).isPresent()) {
            log.warn("voucher of voucherType '{}' and discount '{}' is already exists", voucherType, discount);
            throw new DuplicateVoucherException();
        }
        add(voucher);
    }

    public List<VoucherResponseDto> findAll() {

        List<Voucher> vouchers = getAll();
        return vouchers.stream()
                .map(VoucherEntityMapper.INSTANCE::entityToDto)
                .toList();
    }

    public VoucherResponseDto findById(VoucherRequestDto voucherRequestDto) {

        UUID voucherId = voucherRequestDto.getVoucherId();
        Voucher voucher = get(voucherId);
        return VoucherEntityMapper.INSTANCE.entityToDto(voucher);
    }

    @Transactional
    public void deleteVoucher(VoucherRequestDto voucherRequestDto) {
        UUID voucherId = voucherRequestDto.getVoucherId();
        delete(voucherId);
    }

    @Transactional
    public void updateVoucher(VoucherRequestDto voucherRequestDto) {
        Voucher voucher = VoucherEntityMapper.INSTANCE.mapToVoucher(voucherRequestDto);
        update(voucher);
    }

    private void add(Voucher voucher) {
        voucherRepository.add(voucher);
    }

    private Voucher get(UUID voucherId) {
        return voucherRepository.get(voucherId)
                .orElseThrow(() -> {
                    log.warn("No voucher found for voucherId: {}" , voucherId);
                    return new VoucherNotFoundException();
                });
    }

    private List<Voucher> getAll() {
        return voucherRepository.getAll();
    }

    private void delete(UUID voucherId) {
        voucherRepository.delete(voucherId);
    }

    private void update(Voucher voucher) {
        voucherRepository.update(voucher);
    }

    @Transactional
    public void deleteAll() {
        voucherRepository.deleteAll();
    }
}
