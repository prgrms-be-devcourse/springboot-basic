package org.programmers.springbootbasic.repository;

import org.programmers.springbootbasic.domain.FixedAmountVoucher;
import org.programmers.springbootbasic.domain.PercentDiscountVoucher;
import org.programmers.springbootbasic.domain.Voucher;
import org.programmers.springbootbasic.exception.WrongTypeInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(CSVFileVoucherRepository.class);
    private static final int VOUCHER_TYPE_INDEX = 0;
    private static final int VOUCHER_UUID_INDEX = 1;
    private static final int VOUCHER_AMOUNT_INDEX = 2;
    private final File csv;

    public CSVFileVoucherRepository(@Value("${files.location.voucher}") String CSV_FILE_PATH) {
        this.csv = new File(CSV_FILE_PATH);
    }

    @Override
    public Voucher save(Voucher voucher) {
        logger.info("voucher fileRepository에 저장 voucher = {}", voucher);
        write(voucher);
        return voucher;
    }


    @Override
    public List<Voucher> findAll() {
        logger.info("voucher 전체 조회");
        return read();
    }

    private void write(Voucher voucher) {

        try (
                BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
        ) {
            bw.write(voucher + "\n");
            bw.flush();
        } catch (IOException e) {
            logger.error("해당 바우처를 파일에 저장할 수 없습니다. voucher {}", voucher);
        }
    }


    private List<Voucher> read() {
        List<Voucher> vouchers = new ArrayList<>();

        try (
                BufferedReader br = new BufferedReader(new FileReader(csv));
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] voucherInfo = line.split(", ");
                vouchers.add(assembleVoucher(voucherInfo));
            }
        } catch (FileNotFoundException e) {
            logger.error("읽어올 파일을 찾을 수 없습니다.");
        } catch (IOException e) {
            logger.error("바우처를 읽어올 수 없습니다.");
        } catch (WrongTypeInputException e) {
            logger.error("허용하지 않는 바우처 타입입니다. 허용하는 바우처 타입은 fixed, percent 입니다.");
        }
        return vouchers;
    }

    private Voucher assembleVoucher(String[] voucherInfo) throws WrongTypeInputException {
        if (FixedAmountVoucher.class.getSimpleName().equals(voucherInfo[VOUCHER_TYPE_INDEX])) {
            return new FixedAmountVoucher(UUID.fromString(voucherInfo[VOUCHER_UUID_INDEX]),
                    Long.parseLong(voucherInfo[VOUCHER_AMOUNT_INDEX]));
        } else if (PercentDiscountVoucher.class.getSimpleName().equals(voucherInfo[VOUCHER_TYPE_INDEX])) {
            return new PercentDiscountVoucher(UUID.fromString(voucherInfo[VOUCHER_UUID_INDEX]),
                    Long.parseLong(voucherInfo[VOUCHER_AMOUNT_INDEX]));
        } else throw new WrongTypeInputException();
    }
}
