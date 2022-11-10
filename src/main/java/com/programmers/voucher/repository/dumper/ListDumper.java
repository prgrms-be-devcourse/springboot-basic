package com.programmers.voucher.repository.dumper;

import com.programmers.voucher.repository.FileVoucherRepository;
import com.programmers.voucher.voucher.Voucher;
import org.ini4j.Wini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.repository.FileVoucherRepository.VOUCHER_TYPE;
import static com.programmers.voucher.repository.FileVoucherRepository.VOUCHER_VALUE;
import static org.slf4j.LoggerFactory.*;

@Component
public class ListDumper implements Dumper {
    private final Logger logger = getLogger(FileVoucherRepository.class);
    private final Wini wini;

    @Autowired
    public ListDumper(Wini wini) {
        this.wini = wini;
    }

    @Override
    public void dump(List<Voucher> dumper) {
        for (Voucher voucher : dumper) {
            UUID voucherId = voucher.getVoucherId();
            String voucherClassName = voucher.getClass()
                    .getSimpleName()
                    .replaceAll("Voucher", "");

            long voucherValue = voucher.getValue();

            wini.put(voucherId.toString(), VOUCHER_TYPE, voucherClassName);
            wini.put(voucherId.toString(), VOUCHER_VALUE, voucherValue);
        }

        try {
            logger.info("파일 동기화 시작");
            wini.store();
            logger.info("파일 동기화 완료");
        } catch (IOException e) {
            logger.error("파일 동기화 중 에러 발생", e);
        }
    }
}
