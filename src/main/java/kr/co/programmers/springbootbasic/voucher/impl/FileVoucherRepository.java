package kr.co.programmers.springbootbasic.voucher.impl;

import kr.co.programmers.springbootbasic.exception.FileConvertFailException;
import kr.co.programmers.springbootbasic.exception.FileRepoInitException;
import kr.co.programmers.springbootbasic.exception.FileSaveFailException;
import kr.co.programmers.springbootbasic.voucher.Voucher;
import kr.co.programmers.springbootbasic.voucher.VoucherRepository;
import kr.co.programmers.springbootbasic.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
//@Profile("real")
public class FileVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private static final String SAVE_PATH = "src/main/resources/vouchers/";
    private static final int VOUCHER_TYPE_INDEX = 0;
    private static final int VOUCHER_UUID_INDEX = 1;
    private static final int VOUCHER_AMOUNT_INDEX = 2;

    public FileVoucherRepository() {
        logger.info("FileVoucherRepository를 초기화합니다.");
        try {
            logger.info("디렉토리 생성을 시도합니다.");
            Path path = Paths.get(SAVE_PATH);
            if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
                logger.info("기존의 디렉토리가 존재하므로 디렉토리 생성을 종료합니다.");
            } else {
                logger.info("디렉토리가 존재하지 않으므로 새로 생성합니다.");
                Files.createDirectory(path);
            }
        } catch (IOException e) {
            logger.error("파일시스템에 오류가 발생했습니다.");
            throw new FileRepoInitException("파일시스템에 오류가 발생했습니다.\n");
        }
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        logger.info("바우처 하나를 조회합니다...");
        Path path = Paths.get(SAVE_PATH + voucherId);

        if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
            File file = new File(path.toString());
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                logger.info("ID가 {}인 파일을 Voucher 객체로 만듭니다...", voucherId);

                List<String> voucherData = new ArrayList<>();
                String data;
                while ((data = bufferedReader.readLine()) != null) {
                    voucherData.add(data);
                }

                VoucherType type = VoucherType.valueOf(voucherData.get(VOUCHER_TYPE_INDEX));
                UUID id = UUID.fromString(voucherData.get(VOUCHER_UUID_INDEX));
                long amount = Long.parseLong(voucherData.get(VOUCHER_AMOUNT_INDEX));

                Voucher voucher = switch (type) {
                    case PERCENT_AMOUNT -> new PercentAmountVoucher(id, amount);
                    case FIXED_AMOUNT -> new FixedAmountVoucher(id, amount);
                };

                logger.info("ID가 {}인 Voucher를 읽는데 성공했습니다.", voucherId);
                return Optional.of(voucher);
            } catch (IOException e) {
                logger.error("ID가 {}인 파일을 Voucher 객체로 만드는데 실패했습니다.", voucherId);
                throw new FileConvertFailException("파일을 읽는 도중 오류가 발생했습니다.");
            }
        }

        logger.info("바우처가 존재하지 않습니다.");
        return Optional.empty();
    }

    @Override
    public List<Voucher> listAll() {
        File directory = new File(SAVE_PATH);
        Optional<String[]> fileList = Optional.ofNullable(directory.list());

        return fileList.map(files -> Arrays.stream(files)
                        .map(fileName -> this.findByVoucherId(UUID.fromString(fileName)))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList())
                .orElseGet(ArrayList::new);
    }

    @Override
    public void save(UUID voucherId, Voucher voucher) {
        VoucherType type = voucher.getType();
        UUID id = voucher.getId();
        long amount = voucher.getAmount();

        Path path = Paths.get(SAVE_PATH + voucherId);
        if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
            logger.warn("ID {}가 이미 존재하여 바우처 저장에 실패했습니다.", id);
            throw new FileSaveFailException("기존의 바우처가 존재해서 파일로 저장하는데 실패했습니다.\n");
        }
        File file = new File(SAVE_PATH + voucherId);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            logger.info("바우처를 파일로 저장합니다...");
            if (file.isFile() && file.canWrite()) {
                bufferedWriter.write("""
                        %s
                        %s
                        %s
                        """.formatted(type, voucher.getId(), amount));
            }
            logger.info("바우처를 파일로 저장하는데 성공했습니다.");
        } catch (IOException e) {
            logger.warn("바우처를 파일로 저장하는데 실패했습니다.");
            throw new FileSaveFailException("바우처를 파일로 저장하는데 실패했습니다.\n");
        }
    }
}
