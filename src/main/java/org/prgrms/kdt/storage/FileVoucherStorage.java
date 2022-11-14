package org.prgrms.kdt.storage;

import org.prgrms.kdt.exceptions.AmountException;
import org.prgrms.kdt.exceptions.InvalidITypeInputException;
import org.prgrms.kdt.io.FileIO;
import org.prgrms.kdt.utils.VoucherType;
import org.prgrms.kdt.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.*;

import static org.prgrms.kdt.voucher.VoucherProvider.getVoucher;


@Repository
@Profile("prod")
public class FileVoucherStorage implements VoucherStorage {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherStorage.class);
    private final FileIO fileIO;
    private Map<UUID, Voucher> vouchers;
    private static final String FAIL_PARSE = "숫자 입력값 변환 오류 발생";

    public FileVoucherStorage(FileIO fileIO) {
        this.fileIO = fileIO;
    }

    @Override
    public void save(Voucher voucher) {
        fileIO.write(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        vouchers = new HashMap<>();
        try {
            List<String> readVouchers = fileIO.read();
            readVouchers.forEach(voucher -> {
                String[] info = voucher.split("/");
                insertVoucher(info);
            });
            return vouchers.values()
                    .stream()
                    .toList();
        } catch (RuntimeException e) {
            logger.error("파일에 저장된 바우처 정보가 없습니다. 빈 배열을 반환합니다.");
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        findAll();
        logger.info("생성했던 바우처 목록을 가져오기위해 findAll을 한번 실행했습니다.");
        return Optional.ofNullable(vouchers.get(voucherId));
    }

    private void insertVoucher(String[] info) {
        try {
            UUID voucherId = UUID.fromString(info[0]);
            String className = info[1];
            double amount = parse(info[2]);
            VoucherType voucherType = VoucherType.findVoucherTypeByClassName(className);
            Voucher voucher = getVoucher(voucherType, voucherId, amount);
            if (!vouchers.containsKey(voucherId)) {
                vouchers.put(voucher.getVoucherId(), voucher);
            }
        } catch (InvalidITypeInputException | AmountException e) {
            logger.error(MessageFormat.format("파일에서 읽어와 바우처를 생성하는 곳에서 에러 발생 -> {0}", e.getMessage()));
        }
    }

    private double parse(String amount) {
        try {
            return Double.parseDouble(amount.replace(",", ""));
        } catch (NumberFormatException e) {
            logger.error("숫자가 아닌 값이 입력되어 변환에 실패하였습니다.");
            throw new AmountException(FAIL_PARSE);
        }
    }
}
