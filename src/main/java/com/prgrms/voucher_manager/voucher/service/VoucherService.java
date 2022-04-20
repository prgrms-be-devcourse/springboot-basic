package com.prgrms.voucher_manager.voucher.service;

import com.prgrms.voucher_manager.exception.WrongVoucherValueException;
import com.prgrms.voucher_manager.voucher.Voucher;
import com.prgrms.voucher_manager.voucher.VoucherType;
import com.prgrms.voucher_manager.voucher.repository.JdbcVoucherRepository;
import com.prgrms.voucher_manager.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class VoucherService {
    private final static Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> getFindAllVoucher() {
        List<Voucher> vouchers = new ArrayList<>();
        try{
            vouchers = voucherRepository.findAll();
            AtomicInteger i = new AtomicInteger();
            vouchers.forEach(v -> {
                System.out.println(i.getAndIncrement() + " : " + v.toString());
            });
        } catch (RuntimeException e) {
            logger.info("JDBC voucher 가 비어있습니다. ");
        }
        return vouchers;
    }

    public void createVoucher(String type, Long value) {
        try{
            VoucherType voucherType = VoucherType.getVoucherType(type);
            Voucher voucher = voucherType.create(value);
            voucherRepository.insert(voucher);
        } catch (IllegalArgumentException e) {
            logger.info("Voucher Type을 잘못 입력 했습니다 type : {}", type);
        } catch (WrongVoucherValueException wrongVoucherValueException) {
            logger.info("범위가 다릅니다. {}",wrongVoucherValueException.getMessage());
        } catch (DataAccessException dataAccessException) {
            logger.info("중복된 voucher가 이미 존재합니다. type : {}, value : {}", type, value);
        }
    }


}
