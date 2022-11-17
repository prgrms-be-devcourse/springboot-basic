package org.prgrms.kdt.utils;

import org.prgrms.kdt.exceptions.AmountException;
import org.prgrms.kdt.io.FileIO;
import org.prgrms.kdt.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.prgrms.kdt.voucher.VoucherProvider.getVoucher;

@Component
public class FileParser {

    private static final String FAIL_PARSE = "숫자 입력값 변환 오류 발생";
    private static final String FAIL_CREATE_VOUCHER = "가져온 Split 배열의 길이가 잘못되었습니다.";
    private static final String FIND_ALL_EXCEPTION = "파일에서 바우처 정보를 모두 읽어올 수 없습니다.";
    private static final String VOUCHER_INFO_SPLIT_STANDARD = "/";
    private static final String AMOUNT_BEFORE_REPLACE_STR = ",";
    private static final String AMOUNT_AFTER_REPLACE_STR = "";
    private static final String FILE_TYPE_REPLACE_AFTER = "";
    private static final String FILE_TYPE_REPLACE_STR = ".txt";

    private static final int VOUCHER_ID_INDEX = 0;
    private static final int VOUCHER_CLASS_NAME_INDEX = 1;
    private static final int VOUCHER_AMOUNT_INDEX = 2;
    private static final int VOUCHER_INFO_LIST_SIZE = 3;

    private static final Logger logger = LoggerFactory.getLogger(FileParser.class);

    private final FileIO fileIO;

    public FileParser(FileIO fileIO) {
        this.fileIO = fileIO;
    }

    public void write(Voucher voucher){
        fileIO.write(voucher);
    }

    public static String getVoucherInfo(Voucher voucher) {
        String voucherInfo = MessageFormat.format("{0}/{1}/{2}\n",
                voucher.getVoucherId().toString(), voucher.getClass().getSimpleName(), voucher.getAmount());
        logger.info("voucher 객체를 string으로 변환 -> {}", voucherInfo);
        return voucherInfo;
    }

    public List<String> getVoucherIdList(){
        List<String> rowVoucherList = fileIO.readFileList();
        return rowVoucherList.stream()
                .map(voucher -> voucher
                        .replace(FILE_TYPE_REPLACE_STR, FILE_TYPE_REPLACE_AFTER))
                .toList();
    }

    public List<Voucher> getAllVouchers(){
        List<Voucher> voucherList = new ArrayList<>();
        getAllVoucherInfo().forEach(readVoucherInfo -> {
            try {
                voucherList.add(createVoucher(readVoucherInfo));
            } catch (AmountException amountException) {
                logger.error("[숫자 변환 예외 발생] 예외 발생 바우처 -> {}", readVoucherInfo, amountException);
            }
        });
        if(voucherList.size() != getVoucherIdList().size()){
            throw new RuntimeException(FIND_ALL_EXCEPTION);
        }
        return voucherList;
    }

    public Voucher getVoucherById(String voucherId){
        String voucherInfo = fileIO.read(voucherId);
        return createVoucher(voucherInfo);
    }

    private List<String> getAllVoucherInfo(){
        List<String> voucherIds = getVoucherIdList();
        return voucherIds.stream()
                .map(fileIO::read)
                .toList();
    }

    private Voucher createVoucher(String voucherInfo) {
        List<String> info = Arrays.stream(
                        voucherInfo.split(VOUCHER_INFO_SPLIT_STANDARD))
                .toList();

        if(info.size() != VOUCHER_INFO_LIST_SIZE){
            throw new RuntimeException(FAIL_CREATE_VOUCHER);
        }

        UUID voucherId = UUID.fromString(
                info.get(VOUCHER_ID_INDEX));
        String className = info.get(VOUCHER_CLASS_NAME_INDEX);
        int amount = parseAmount(info.get(VOUCHER_AMOUNT_INDEX));

        VoucherType voucherType = VoucherType.findVoucherTypeByClassName(className);

        return getVoucher(voucherType, voucherId, amount);
    }

    private int parseAmount(String amount) {
        try {
            return Integer.parseInt(
                    amount.replace(AMOUNT_BEFORE_REPLACE_STR, AMOUNT_AFTER_REPLACE_STR));
        } catch (NumberFormatException amountParseException) {
            throw new AmountException(FAIL_PARSE);
        }
    }
}
