package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("production")
@Primary
public class FileVoucherRepository implements VoucherRepository {

    private static final String STORAGE_LINK = "./fileVoucherStorage.txt";
    private File storage = new File(STORAGE_LINK);
    BufferedWriter bufferedWriter;
    //프로그램 시작시 한 번만 파일을 로드하고 사용하기 위해 생성
    Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();
    String line;

    public FileVoucherRepository() throws IOException {
        if (!storage.isFile()) {
            storage.createNewFile();
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(storage));
        while ((line = bufferedReader.readLine()) != null) {
            String[] voucher = line.split(","); //Type, UUID, amount
            VoucherType voucherType = VoucherType.valueOf(voucher[0]);
            UUID uuid = UUID.fromString(voucher[1]);
            long discount = Long.parseLong(voucher[2]);

            Voucher eachVoucher = isFixedAmountVoucher(voucherType) ? new FixedAmountVoucher(uuid, discount) :
                    new PercentDiscountVoucher(uuid, discount);
            vouchers.put(uuid, eachVoucher);
        }
        bufferedWriter = new BufferedWriter(new FileWriter(storage));
    }

    private boolean isFixedAmountVoucher(VoucherType voucherType) {
        return voucherType == VoucherType.FixedAmount;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.of(vouchers.get(voucherId));
    }

    @Override
    public Voucher create(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> list() {
        return vouchers.values().stream().toList();
    }

    @PreDestroy
    public void close() throws Exception {
        for (Voucher voucher: vouchers.values()) {
            if (voucher instanceof FixedAmountVoucher) bufferedWriter.write(VoucherType.FixedAmount.toString() + ","
                    + voucher.getVoucherId() + "," + ((FixedAmountVoucher) voucher).getAmount());
            else bufferedWriter.write(VoucherType.PercentDiscount.toString() + ","
                    + voucher.getVoucherId() + "," + ((PercentDiscountVoucher) voucher).getPercent());
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
