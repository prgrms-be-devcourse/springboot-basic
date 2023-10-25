package org.prgms.kdtspringweek1.voucher.repository;

import jakarta.annotation.PostConstruct;
import org.prgms.kdtspringweek1.exception.FileException;
import org.prgms.kdtspringweek1.exception.FileExceptionCode;
import org.prgms.kdtspringweek1.voucher.entity.FixedAmountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.PercentDiscountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.Voucher;
import org.prgms.kdtspringweek1.voucher.entity.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("default")
public class FileVoucherRepository implements VoucherRepository {
    private Map<UUID, Voucher> vouchers;
    private File voucherInfoCsv;
    @Value("${spring.file.voucher.path}")
    private String vouchInfoCsvPath;
    private final static Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    @PostConstruct
    private void init() {
        voucherInfoCsv = new File(vouchInfoCsvPath);
        prepareCsv();
        try {
            vouchers = getAllVouchersFromCSV();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
    }

    @Override
    public Voucher save(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
        updateVouchersInfoOnCsv();
        logger.info("Success to create {} -> {} {} discount",
                voucher.getVoucherType().getName(),
                voucher.getDiscountValue(),
                voucher.getVoucherType().getUnit());
        return voucher;
    }

    @Override
    public List<Voucher> findAllVouchers() {
        List<Voucher> allVouchers = new ArrayList<>(vouchers.values());
        logger.info("Success to findAllVouchers");
        return allVouchers;
    }

    private void prepareCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(voucherInfoCsv, true))) {
            if (voucherInfoCsv.length() == 0) {
                bw.write("type, id, discount value");
                bw.newLine();
            }
        } catch (IOException ioException) {
            System.out.println("파일 쓰기 에러가 발생했습니다.");
            logger.error("Fail to write file when prepareCsv");
        }
    }

    private Map<UUID, Voucher> getAllVouchersFromCSV() {
        Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();

        // try 코드 블록이 끝나면 자동으로 자원을 종료해주도록 하기 위해 try-with-resources 사용
        try (BufferedReader br = new BufferedReader(new FileReader(voucherInfoCsv))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] voucherInfo = line.split(", ");
                String voucherType = voucherInfo[0];
                UUID voucherId = UUID.fromString(voucherInfo[1]);
                long discountValue = Long.parseLong(voucherInfo[2]);

                if (voucherType.equals(VoucherType.FIXED_AMOUNT.getName())) {
                    vouchers.put(voucherId, FixedAmountVoucher.createWithIdAndAmount(voucherId, discountValue));
                } else if (voucherType.equals(VoucherType.PERCENT_DISCOUNT.getName())) {
                    vouchers.put(voucherId, PercentDiscountVoucher.createWithIdAndPercent(voucherId, discountValue));
                }
            }
        } catch (IOException e) {
            logger.error("Fail to read file when getAllVouchersFromCSV");
            throw new FileException(FileExceptionCode.FAIL_TO_READ_DATA_FROM_CSV);
        }

        return vouchers;
    }

    private void updateVouchersInfoOnCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(voucherInfoCsv))) {
            bw.write("type, id, discount value");
            bw.newLine();
            for (Voucher voucher : new ArrayList<>(this.vouchers.values())) {
                bw.write(voucher.getVoucherType().getName() + ", " + voucher.getVoucherId() + ", " + voucher.getDiscountValue());
                bw.newLine();
            }
        } catch (IOException e) {
            logger.error("Fail to write file when updateVouchersInfoOnCsv");
            throw new FileException(FileExceptionCode.FAIL_TO_UPDATE_DATA_ON_CSV);
        }
    }
}
