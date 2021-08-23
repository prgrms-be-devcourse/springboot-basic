package com.org.prgrms.mission1;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    private static final long DEFAULT_PERCENT = 10L;

    private static final long DAFAULT_AMOUNT = 500l;

    public VoucherService(@Qualifier("File") VoucherRepository voucherRepository) {   // FileVoucgherRepository 객체 주입
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