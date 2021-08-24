package com.org.prgrms.mission.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.org.prgrms.mission.model.VoucherType;
import com.org.prgrms.mission.model.FixedAmoutVoucher;
import com.org.prgrms.mission.model.PercentDiscountVoucher;
import com.org.prgrms.mission.model.Voucher;
import com.org.prgrms.mission.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    private static final long DEFAULT_PERCENT = 10L;

    private static final long DAFAULT_AMOUNT = 500l;

    public VoucherService(VoucherRepository voucherRepository) {   // FileVoucgherRepository 객체 주입
        this.voucherRepository = voucherRepository;
    }


    public Voucher createVoucher(VoucherType voucherType) {  //Voucher 객체 생성

        Voucher voucher;

        if(voucherType == voucherType.FIXED_AMOUNT) {
            voucher = new FixedAmoutVoucher(VoucherType.FIXED_AMOUNT,UUID.randomUUID(), DAFAULT_AMOUNT);
        }
        else if (voucherType == voucherType.PERCENT_DISCOUNT) {
            voucher = new PercentDiscountVoucher(VoucherType.PERCENT_DISCOUNT,UUID.randomUUID(), DEFAULT_PERCENT);
        }
        else
            voucher = new FixedAmoutVoucher(VoucherType.FIXED_AMOUNT,UUID.randomUUID(), DAFAULT_AMOUNT);

            return voucher;
    }

    public Voucher save(Voucher voucher) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        voucherRepository.insert(voucher);
        return voucher;
    }



    public  List<Voucher> getAllVoucher() throws IOException, CsvException {
        return voucherRepository.findAll();
    }

    }