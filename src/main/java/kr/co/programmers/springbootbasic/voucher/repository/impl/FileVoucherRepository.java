package kr.co.programmers.springbootbasic.voucher.repository.impl;

import kr.co.programmers.springbootbasic.customer.domain.Customer;
import kr.co.programmers.springbootbasic.voucher.domain.Voucher;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;
import kr.co.programmers.springbootbasic.voucher.domain.impl.FixedAmountVoucher;
import kr.co.programmers.springbootbasic.voucher.domain.impl.PercentAmountVoucher;
import kr.co.programmers.springbootbasic.voucher.exception.FileVoucherRepositoryFailException;
import kr.co.programmers.springbootbasic.voucher.repository.FileVoucherRepositoryProperties;
import kr.co.programmers.springbootbasic.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Repository
public class FileVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private static final String FILE_WRITE_FORMAT = """
            %s
            %s
            %s
            """;

    public FileVoucherRepository() {
        logger.info("FileVoucherRepository를 초기화합니다.");
        try {
            initFileDirectory();
        } catch (IOException e) {
            logger.error("파일시스템에 오류가 발생했습니다.");
            throw new FileVoucherRepositoryFailException("파일시스템에 오류가 발생했습니다.\n");
        }
    }

    @Override
    public Optional<Voucher> findVoucherById(UUID voucherId) throws RuntimeException {
        logger.info("바우처 하나를 조회합니다...");
        Path path = Paths.get(FileVoucherRepositoryProperties.SAVE_PATH + voucherId);
        if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
            File file = new File(path.toString());
            return makeVoucher(voucherId, file);
        }
        logger.info("바우처가 존재하지 않습니다.");

        return Optional.empty();
    }

    @Override
    public void deleteById(UUID voucherId) {
        Path path = Paths.get(FileVoucherRepositoryProperties.SAVE_PATH + voucherId);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new FileVoucherRepositoryFailException("삭제할 바우처가 존재하지 않습니다.\n");
        }
    }

    @Override
    public List<Voucher> listAll() throws RuntimeException {
        File directory = new File(FileVoucherRepositoryProperties.SAVE_PATH);
        Optional<String[]> fileList = Optional.ofNullable(directory.list());

        return fileList.map(files -> Arrays.stream(files)
                        .map(fileName -> this.findVoucherById(UUID.fromString(fileName)))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList())
                .orElseGet(ArrayList::new);
    }

    @Override
    public Voucher create(Voucher voucher) {
        VoucherType type = voucher.getType();
        UUID id = voucher.getId();
        long amount = voucher.getAmount();

        Path path = Paths.get(FileVoucherRepositoryProperties.SAVE_PATH + id);
        if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
            logger.warn("ID {}가 이미 존재하여 바우처 저장에 실패했습니다.", id);
            throw new FileVoucherRepositoryFailException("기존의 바우처가 존재해서 파일로 저장하는데 실패했습니다.\n");
        }
        writeVoucherOnFile(type, id, amount);

        return voucher;
    }

    private void initFileDirectory() throws IOException {
        logger.info("디렉토리 생성을 시도합니다.");
        Path path = Paths.get(FileVoucherRepositoryProperties.SAVE_PATH);
        if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
            logger.info("기존의 디렉토리가 존재하므로 디렉토리 생성을 종료합니다.");
        } else {
            logger.info("디렉토리가 존재하지 않으므로 새로 생성합니다.");
            Files.createDirectory(path);
        }
    }

    private Optional<Voucher> makeVoucher(UUID voucherId, File file) throws RuntimeException {
        logger.info("ID가 {}인 파일을 Voucher 객체로 만듭니다...", voucherId);
        List<String> voucherData = readVoucherFile(voucherId, file);
        Voucher voucher = makeVoucherByType(voucherData);
        logger.info("ID가 {}인 Voucher 를 읽는데 성공했습니다.", voucherId);

        return Optional.of(voucher);
    }

    private List<String> readVoucherFile(UUID voucherId, File file) {
        List<String> voucherData = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String data;
            while ((data = bufferedReader.readLine()) != null) {
                voucherData.add(data);
            }
        } catch (IOException e) {
            logger.error("ID가 {}인 파일을 Voucher 객체로 만드는데 실패했습니다.", voucherId);
            throw new FileVoucherRepositoryFailException("바우처 목록을 가져오는 도중 오류가 발생했습니다.");
        }

        return voucherData;
    }

    private Voucher makeVoucherByType(List<String> voucherData) {
        VoucherType type = VoucherType.valueOf(voucherData.get(FileVoucherRepositoryProperties.VOUCHER_TYPE_INDEX));
        UUID voucherId = UUID.fromString(voucherData.get(FileVoucherRepositoryProperties.VOUCHER_UUID_INDEX));
        long amount = Long.parseLong(voucherData.get(FileVoucherRepositoryProperties.VOUCHER_AMOUNT_INDEX));

        return switch (type) {
            case PERCENT_AMOUNT -> new PercentAmountVoucher(voucherId, amount);
            case FIXED_AMOUNT -> new FixedAmountVoucher(voucherId, amount);
        };
    }

    private void writeVoucherOnFile(VoucherType type, UUID id, long amount) {
        logger.info("바우처를 파일로 저장합니다...");
        File file = new File(FileVoucherRepositoryProperties.SAVE_PATH + id);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            if (file.isFile() && file.canWrite()) {
                bufferedWriter.write(FILE_WRITE_FORMAT.formatted(type, id, amount));
            }
        } catch (IOException e) {
            logger.warn("바우처를 파일로 저장하는데 실패했습니다.");
            throw new FileVoucherRepositoryFailException("바우처를 파일로 저장하는데 실패했습니다.\n");
        }
        logger.info("바우처를 파일로 저장하는데 성공했습니다.");
    }
}
