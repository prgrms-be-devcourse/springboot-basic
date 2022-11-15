package org.prgrms.kdt.storage;

import org.prgrms.kdt.exceptions.AmountException;
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
    private static final String FAIL_PARSE = "숫자 입력값 변환 오류 발생";
    private static final int VOUCHER_ID_INDEX = 0;
    private static final int VOUCHER_CLASS_NAME_INDEX = 1;
    private static final int VOUCHER_AMOUNT_INDEX = 2;
    private final FileIO fileIO;

    public FileVoucherStorage(FileIO fileIO) {
        this.fileIO = fileIO;
    }

    @Override
    public void save(Voucher voucher) {
        String voucherInfo = MessageFormat.format("{0}/{1}/{2}\n", voucher.getVoucherId().toString(), voucher.getClass().getSimpleName(), voucher.getAmount());
        logger.info("voucher 객체를 string으로 변환 -> {}", voucherInfo);
        fileIO.write(voucherInfo);
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = new ArrayList<>();
        try {
            List<String> readVouchers = fileIO.read();
            readVouchers.forEach(voucher ->
                    voucherList.add(
                            createVoucher(getSplitVoucherInfo(voucher))));
            return voucherList;
        } catch (RuntimeException e) {
            logger.error("파일에 저장된 바우처 정보가 없습니다. 빈 배열을 반환합니다.");
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        List<String> readVouchers = fileIO.read();
        Map<UUID, Voucher> voucherMap = new HashMap<>();
        readVouchers.forEach(voucherLine -> {
            Voucher newVoucher = createVoucher(getSplitVoucherInfo(voucherLine));
            voucherMap.put(newVoucher.getVoucherId(), newVoucher);
        });
        return Optional.ofNullable(voucherMap.get(voucherId));
    }

    private Voucher createVoucher(List<String> info) {
        UUID voucherId = UUID.fromString(info.get(VOUCHER_ID_INDEX));
        String className = info.get(VOUCHER_CLASS_NAME_INDEX);
        double amount = parse(info.get(VOUCHER_AMOUNT_INDEX));
        VoucherType voucherType = VoucherType.findVoucherTypeByClassName(className);
        return getVoucher(voucherType, voucherId, amount);
    }

    private List<String> getSplitVoucherInfo(String readVoucher) {
        return Arrays.stream(readVoucher.split("/")).toList();
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
