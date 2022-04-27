package com.prgrms.management.voucher.repository;

import com.prgrms.management.config.exception.NotSavedException;
import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.prgrms.management.config.ErrorMessageType.NOT_SAVED_EXCEPTION;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {
    private static final String VOUCHER_FILE_NAME = "src/main/resources/voucher.csv";
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    @Override
    public Voucher save(Voucher voucher) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(VOUCHER_FILE_NAME, true))) {
            bufferedWriter.write(voucher.serialized());
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new NotSavedException(this.getClass() + NOT_SAVED_EXCEPTION.getMessage());
        }
        return voucher;
    }
    @Override
    public List<Voucher> findAll() {
        /*
        List<Voucher> voucherList = new ArrayList<>();
        //try-with-resource
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(VOUCHER_FILE_NAME))) {
            String reader;
            while ((reader = bufferedReader.readLine()) != null) {
                String[] voucherInfo = reader.split(",");
                VoucherType type = VoucherType.of(voucherInfo[2]);
                if (type.equals(VoucherType.FIXED))
                    voucherList.add(new FixedAmountVoucher(UUID.fromString(voucherInfo[0]), Long.parseLong(voucherInfo[1])));
                else
                    voucherList.add(new PercentAmountVoucher(UUID.fromString(voucherInfo[0]), Long.parseLong(voucherInfo[1])));
            }
        } catch (IOException e) {
            logger.warn("{}:{}", e.getClass(), ErrorMessageType.IO_EXCEPTION.getMessage());
        }
        return voucherList;
        */
        // TODO: vouchers 반환하는 메서드
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Voucher> findAllByVoucherTypeOrCreatedAt(VoucherType voucherType, LocalDate date) {
        // TODO: 조건별 vouchers 반환하는 메서드
        throw new UnsupportedOperationException();
    }
    @Override
    public List<UUID> findCustomerByVoucherType(VoucherType voucherType) {
        // TODO: voucherType 조건으로 vouchers 반환하는 메서드
        throw new UnsupportedOperationException();
    }
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        // TODO: voucherById 반환하는 메서드
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateByCustomerId(UUID voucherId, UUID customerId) {
        // TODO: voucherId 검색 후 customerId 주입하는 메서드
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(UUID customerId) {
        // TODO: customerId로 삭제하는 메서드
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        // TODO: 전부 삭제하는 메서드
        throw new UnsupportedOperationException();
    }

}
