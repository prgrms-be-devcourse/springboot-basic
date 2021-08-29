package org.prgrms.dev.voucher.repository;

import org.prgrms.dev.voucher.domain.FixedAmountVoucher;
import org.prgrms.dev.voucher.domain.PercentDiscountVoucher;
import org.prgrms.dev.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
@Qualifier("file")
public class FileVoucherRepository implements VoucherRepository {
    private static final String path = "src/main/resources/voucher.csv";
    private static final String SPLIT_CODE = ":";
    private static final int TYPE_INDEX = 0;
    private static final int UUID_INDEX = 1;
    private static final int VALUE_INDEX = 2;
    private final File file;
    private final Map<UUID, Voucher> STORE;


    public FileVoucherRepository() {
        this.file = new File(System.getProperty("user.dir"), path);
        this.STORE = init();
    }

    private Map<UUID, Voucher> init() {
        Map<UUID, Voucher> vouchers = new HashMap<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] voucherInfo = line.split(SPLIT_CODE);
                if (voucherInfo[TYPE_INDEX].equals("fixed")) {
                    UUID customerId = UUID.fromString(voucherInfo[UUID_INDEX]);
                    long value = Long.valueOf(voucherInfo[VALUE_INDEX]);
                    Voucher voucher = new FixedAmountVoucher(customerId, value);
                    vouchers.put(customerId, voucher);
                } else if (voucherInfo[TYPE_INDEX].equals("percent")) {
                    UUID customerId = UUID.fromString(voucherInfo[UUID_INDEX]);
                    long value = Long.valueOf(voucherInfo[VALUE_INDEX]);
                    Voucher voucher = new PercentDiscountVoucher(customerId, value);
                    vouchers.put(customerId, voucher);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vouchers;
    }

    @Override
    public Voucher create(Voucher voucher) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));

            bufferedWriter.write(voucher.toString());
            bufferedWriter.newLine();

            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(STORE.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(STORE.values());
    }
}
