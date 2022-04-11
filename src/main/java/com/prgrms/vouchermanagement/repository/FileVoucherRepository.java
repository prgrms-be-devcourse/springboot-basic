package com.prgrms.vouchermanagement.repository;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("file")
public class FileVoucherRepository implements VoucherRepository {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void save(Voucher voucher) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("vouchers.csv", true))){
            pw.println(getCsvStr(voucher));
            log.info("voucher is saved  to vouchers.csv file - {}", voucher);
        } catch (IOException e) {
            log.error("vouchers.csv 파일을 열 수 없습니다.", e);
        }
    }

    private String getCsvStr(Voucher voucher) {
        String className = voucher.getClass().getSimpleName();
        return className + "," + voucher.getId() + "," + voucher.getAmount();
    }

    /**
     * vouchers.csv 파일을 삭제합니다.
     * 테스트 실행시 초기화 용으로 사용
     */
    public void clear() {
        File file = new File("vouchers.csv");
        file.delete();
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("vouchers.csv"))){
            String voucherStr = null;

            while ((voucherStr = br.readLine()) != null) {
                String[] split = voucherStr.split(",");

                String className = split[0];
                UUID id = UUID.fromString(split[1]);
                long amount = Long.parseLong(split[2]);
                VoucherType voucherType = VoucherType.getVoucherType(className);

                vouchers.add(Voucher.createVoucher(id, voucherType, amount));
            }

            log.info("find all vouchers from file. size={}", vouchers.size());
        } catch (IOException e) {
            log.error("vouchers.scv 파일을 읽어오는데 실패하였습니다.", e);
        }

        return vouchers;
    }
}
