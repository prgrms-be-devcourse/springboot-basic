package org.prgrms.vouchermanagement.voucher.repository;

import org.prgrms.vouchermanagement.exception.InsertFailException;
import org.prgrms.vouchermanagement.exception.LoadFailException;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.policy.DiscountPolicy;
import org.prgrms.vouchermanagement.voucher.policy.FixedAmountVoucher;
import org.prgrms.vouchermanagement.voucher.policy.PercentDiscountVoucher;
import org.prgrms.vouchermanagement.voucher.policy.PolicyStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class FileRepository implements VoucherRepository{

    private static final String path = "./voucherFile/voucherFile";
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    public FileRepository() {
        if(storage.isEmpty()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");

                    UUID voucherId = UUID.fromString(parts[0]);
                    PolicyStatus policy = PolicyStatus.valueOf(parts[1]);
                    long amountOrPercent = Long.parseLong(parts[2]);

                    DiscountPolicy discountPolicy = null;
                    if (policy == PolicyStatus.FIXED) {
                        discountPolicy = new FixedAmountVoucher(amountOrPercent);
                    } else if (policy == PolicyStatus.PERCENT) {
                        discountPolicy = new PercentDiscountVoucher(amountOrPercent);
                    }

                    storage.put(voucherId, new Voucher(voucherId, discountPolicy));
                }
            } catch (IOException e) {
                throw new LoadFailException("파일로부터 읽어올 수 없습니다.");
            }
        }
    }

    @Override
    public int create(UUID voucherId, DiscountPolicy discountPolicy) {

        long amountOrPercent = discountPolicy.getAmountOrPercent();
        PolicyStatus policy = discountPolicy.getPolicyStatus();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(voucherId + "," + policy + "," + amountOrPercent);
            writer.newLine(); // 새로운 줄 추가
        } catch (IOException e) {
            throw new InsertFailException("데이터 삽입에 실패하였습니다.");
        }

        Voucher voucher = new Voucher(voucherId, discountPolicy);
        storage.put(voucherId, voucher);

        return 0;
    }

    @Override
    public List<Voucher> voucherLists() {
        return storage.values().stream()
                .toList();
    }

    @Override
    public void update(UUID voucherId, long amount) {
    }

    @Override
    public int deleteAll() {

        return 0;
    }

    @Override
    public Voucher getById(UUID voucherId) {
        return storage.get(voucherId);
    }
}
