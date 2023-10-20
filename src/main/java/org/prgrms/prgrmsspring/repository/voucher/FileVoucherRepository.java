package org.prgrms.prgrmsspring.repository.voucher;

import jakarta.annotation.PreDestroy;
import org.prgrms.prgrmsspring.domain.VoucherType;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.prgrms.prgrmsspring.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Profile("dev")
@Repository
public class FileVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> store = new ConcurrentHashMap<>();
    private final String filePath;

    private static final String CSV_SEPARATOR = ",";


    public FileVoucherRepository(@Value("${file.path.voucher}") String filePath) {
        this.filePath = filePath;
        readAndStoreFile();
    }

    @PreDestroy
    private void saveFile() {
        writeFile();
    }

    private void readAndStoreFile() {
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            List<String> fileStrings = readFile(inputStream);
            List<Voucher> vouchers = convertFileStringsToObjectLists(fileStrings);
            vouchers.forEach(v -> store.put(v.getVoucherId(), v));
        } catch (IOException e) {
            throw new NotFoundException(ExceptionMessage.NOT_FOUND_FILE.getMessage());
        }
    }

    private List<String> readFile(InputStream inputStream) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }


    public List<Voucher> convertFileStringsToObjectLists(List<String> fileStrings) {
        List<Voucher> voucherList = new ArrayList<>();
        fileStrings.stream().map(s -> s.split(CSV_SEPARATOR)).forEach(split -> {
            String type = split[0];
            UUID uuid = UUID.fromString(split[1]);
            long value = Long.parseLong(split[2]);
            VoucherType voucherType = VoucherType.from(type);
            Voucher voucher = voucherType.constructVoucher(uuid, value);
            voucherList.add(voucher);
        });
        return voucherList;
    }


    public void writeFile() {
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            List<String> fileStrings = convertObjectListsToFileStrings(store.values());
            for (String str : fileStrings) {
                outputStream.write(str.getBytes());
                outputStream.write(System.lineSeparator().getBytes());
            }
            outputStream.flush();
        } catch (IOException e) {
            throw new NotFoundException(ExceptionMessage.NOT_FOUND_FILE.getMessage());
        }
    }

    public List<String> convertObjectListsToFileStrings(Collection<Voucher> voucherList) {
        return voucherList.stream().map(voucher -> VoucherType.from(voucher).getTitle() + CSV_SEPARATOR + voucher.getVoucherId() + CSV_SEPARATOR + voucher.getAmount())
                .toList();
    }


    @Override
    public Voucher insert(Voucher voucher) {
        store.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return store.values().stream().sorted(Comparator.comparing(Object::toString)).toList();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(store.get(voucherId));
    }
}
