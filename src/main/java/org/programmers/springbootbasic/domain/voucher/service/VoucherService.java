package org.programmers.springbootbasic.domain.voucher.service;

import lombok.AllArgsConstructor;
import org.programmers.springbootbasic.domain.voucher.model.Voucher;
import org.programmers.springbootbasic.domain.voucher.VoucherFactory;
import org.programmers.springbootbasic.domain.voucher.dto.VoucherInputDto;
import org.programmers.springbootbasic.exception.WrongTypeInputException;
import org.programmers.springbootbasic.domain.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

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

    public List<Voucher> collectVouchers() {
        logger.info("Voucher 전체 출력");
        return voucherRepository.findAll();
    }
}
