package org.programmers.springbootbasic.domain.voucher.service;

import lombok.AllArgsConstructor;
import org.programmers.springbootbasic.data.VoucherType;
import org.programmers.springbootbasic.domain.voucher.model.Voucher;
import org.programmers.springbootbasic.domain.voucher.VoucherFactory;
import org.programmers.springbootbasic.domain.voucher.dto.VoucherInputDto;
import org.programmers.springbootbasic.exception.NotFoundException;
import org.programmers.springbootbasic.exception.WrongTypeInputException;
import org.programmers.springbootbasic.domain.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    public Voucher createVoucher(VoucherInputDto voucherInputDto) throws WrongTypeInputException {
        logger.info(MessageFormat.format("VoucherFactory에 {0}Voucher 생성 요청", voucherInputDto.getVoucherType().name()));
        Voucher voucher = voucherFactory.getVoucher(voucherInputDto.getVoucherType(), voucherInputDto.getAmount());
        return voucherRepository.save(voucher);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public Voucher findOneById(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new NotFoundException("엔티티를 찾을 수 없습니다."));
    }

    public List<Voucher> findByType(String voucherType) {
        return voucherRepository.findByType(voucherType);
    }

    public void deleteById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }
}
