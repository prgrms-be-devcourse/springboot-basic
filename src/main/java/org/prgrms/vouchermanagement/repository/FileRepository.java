package org.prgrms.vouchermanagement.repository;

import org.prgrms.vouchermanagement.exception.InsertFailException;
import org.prgrms.vouchermanagement.exception.InvalidRangeException;
import org.prgrms.vouchermanagement.exception.LoadFailException;
import org.prgrms.vouchermanagement.voucher.*;
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
                        discountPolicy = new FixedAmountVoucher(voucherId, amountOrPercent, policy);
                    } else if (policy == PolicyStatus.PERCENT) {
                        validateAmountOrPercentRange(amountOrPercent);
                        discountPolicy = new PercentDiscountVoucher(voucherId, amountOrPercent, policy);
                    }

                    storage.put(voucherId, new Voucher(voucherId, discountPolicy));
                }
            } catch (IOException e) {
                throw new LoadFailException("파일로부터 읽어올 수 없습니다.");
            }
        }
    }

    @Override
    public void create(UUID voucherId, long amountOrPercent, PolicyStatus policy) {
        DiscountPolicy discountPolicy = null;
        if (policy == PolicyStatus.FIXED) {
            discountPolicy = new FixedAmountVoucher(voucherId, amountOrPercent, policy);
        } else if (policy == PolicyStatus.PERCENT) {
            validateAmountOrPercentRange(amountOrPercent);
            discountPolicy = new PercentDiscountVoucher(voucherId, amountOrPercent, policy);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(voucherId + "," + policy + "," + amountOrPercent);
            writer.newLine(); // 새로운 줄 추가
        } catch (IOException e) {
            throw new InsertFailException("데이터 삽입에 실패하였습니다.");
        }

        Voucher voucher = new Voucher(voucherId, discountPolicy);
        storage.put(voucherId, voucher);
    }

    @Override
    public Voucher getById(UUID voucherId) {
        return storage.get(voucherId);
    }

    @Override
    public List<Voucher> voucherLists() {
        return storage.values().stream()
                .toList();
    }

    private void validateAmountOrPercentRange(long amountOrPercent) {
        if (amountOrPercent < 0 || amountOrPercent > 100) {
            throw new InvalidRangeException("PercentDiscountPolicy는 0~100 사이의 값을 가져야 합니다.");
        }
    }
}
