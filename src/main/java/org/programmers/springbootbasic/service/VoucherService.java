package org.programmers.springbootbasic.service;

import lombok.AllArgsConstructor;
import org.programmers.springbootbasic.domain.Voucher;
import org.programmers.springbootbasic.domain.VoucherFactory;
import org.programmers.springbootbasic.dto.VoucherDto;
import org.programmers.springbootbasic.exception.WrongTypeInputException;
import org.programmers.springbootbasic.repository.VoucherRepository;
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

    public Voucher createVoucher(VoucherDto voucherDto) throws WrongTypeInputException {
        logger.info(MessageFormat.format("VoucherFactory에 {0}Voucher 생성 요청", voucherDto.getType()));
        Voucher voucher = voucherFactory.getVoucher(voucherDto.getType(), voucherDto.getAmount());
        return voucherRepository.save(voucher);
    }



    public List<Voucher> lookupVoucher() {
        logger.info("Voucher 전체 출력");
        return voucherRepository.findAll();
    }

}
