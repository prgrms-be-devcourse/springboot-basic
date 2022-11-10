package org.programmers.springbootbasic.repository;

import org.programmers.springbootbasic.domain.FixedAmountVoucher;
import org.programmers.springbootbasic.domain.PercentDiscountVoucher;
import org.programmers.springbootbasic.domain.Voucher;
import org.programmers.springbootbasic.exception.WrongTypeInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("local")
public class CSVFileVoucherRepository implements VoucherRepository {


    private String CSV_FILE_PATH;
    private static final int VOUCHER_TYPE_INDEX = 0;
    private static final int VOUCHER_UUID_INDEX = 1;
    private static final int VOUCHER_AMOUNT_INDEX = 2;
    private final File csv;

    public CSVFileVoucherRepository(@Value("${files.location.voucher}") String CSV_FILE_PATH) {
        this.CSV_FILE_PATH = CSV_FILE_PATH;
        this.csv = new File(this.CSV_FILE_PATH);
    }

    @Override
    public Voucher save(Voucher voucher) {
        write(voucher);
        return voucher;
    }


    @Override
    public List<Voucher> findAll() {
        return read();
    }

    private void write(Voucher voucher) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(csv, true));
            bw.write(voucher + "\n");
            bw.flush();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private List<Voucher> read() {
        List<Voucher> vouchers = new ArrayList<>();
        BufferedReader br = null;
        try {
            String line;
            br = new BufferedReader(new FileReader(csv));
            while((line = br.readLine()) != null) {
                String[] voucherInfo = line.split(", ");
                vouchers.add(assembleVoucher(voucherInfo));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WrongTypeInputException e) {
            throw new RuntimeException(e);
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return vouchers;
    }

    private Voucher assembleVoucher(String[] voucherInfo) throws WrongTypeInputException {
        if(FixedAmountVoucher.class.getSimpleName().equals(voucherInfo[VOUCHER_TYPE_INDEX])) {
            return new FixedAmountVoucher(UUID.fromString(voucherInfo[VOUCHER_UUID_INDEX]),
                    Long.parseLong(voucherInfo[VOUCHER_AMOUNT_INDEX]));
        } else if (PercentDiscountVoucher.class.getSimpleName().equals(voucherInfo[VOUCHER_TYPE_INDEX])) {
            return new PercentDiscountVoucher(UUID.fromString(voucherInfo[VOUCHER_UUID_INDEX]),
                    Long.parseLong(voucherInfo[VOUCHER_AMOUNT_INDEX]));
        } else throw new WrongTypeInputException();
    }
}
