package org.prgms.kdtspringweek1.voucher.repository;

import jakarta.annotation.PostConstruct;
import org.prgms.kdtspringweek1.voucher.entity.FixedAmountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.PercentDiscountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.Voucher;
import org.prgms.kdtspringweek1.voucher.entity.VoucherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FileVoucherRepository implements VoucherRepository {
    private Map<UUID, Voucher> vouchers;
    private File voucherInfoCsv;
    @Value("${spring.file.voucher.path}")
    private String vouchInfoCsvPath;

    @PostConstruct
    private void init() {
        voucherInfoCsv = new File(vouchInfoCsvPath);
        prepareCsv();
        vouchers = getAllVouchersFromCSV();
    }

    @Override
    public Voucher save(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
        updateVouchersInfoOnCsv();
        return voucher;
    }

    @Override
    public List<Voucher> findAllVouchers() {
        List<Voucher> allVouchers = new ArrayList<>(vouchers.values());
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
        }
    }

    private Map<UUID, Voucher> getAllVouchersFromCSV() {
        Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();

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
            System.out.println("파일 읽기 에러가 발생했습니다.");
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
            System.out.println("파일 쓰기 에러가 발생했습니다.");
        }
    }
}
