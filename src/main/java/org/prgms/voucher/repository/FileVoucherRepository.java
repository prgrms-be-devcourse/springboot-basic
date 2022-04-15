package org.prgms.voucher.repository;

import org.prgms.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {
    private static final File objectFolder = new File("./objects");
    private static final String objPattern = "*.obj";
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + objPattern);

    @Override
    public void save(Voucher voucher) {
        String filename = String.format("./objects/%s.obj", voucher.getVoucherId().toString());
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(voucher);
        } catch (IOException e) {
            throw new RuntimeException(MessageFormat.format("해당하는 파일이 존재하지 않습니다. msg : {0}", e.getMessage()));
        }
    }

    @Override
    public List<Voucher> findAll() {
        try (Stream<Path> fileStream = Files.list(Paths.get(objectFolder.getPath()))) {
            return fileStream
                    .filter(path -> matcher.matches(path.getFileName()))
                    .map(path -> {
                        try {
                            Object obj = new ObjectInputStream(new FileInputStream(path.toString())).readObject();
                            return (Voucher) obj;
                        } catch (IOException e) {
                            throw new RuntimeException(
                                    MessageFormat.format(
                                            "해당하는 파일이 존재하지 않습니다. msg : {0}", e.getMessage()));
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(MessageFormat.format("deserialization에서 문제가 발생했습니다.. msg : {0}", e.getMessage()));
                        }
                    }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(MessageFormat.format("해당하는 폴더가 존재하지 않습니다. msg : {0}", e.getMessage()));
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try (Stream<Path> fileStream = Files.list(Paths.get(objectFolder.getPath()))) {
            var targetPath = fileStream
                    .filter(path -> matcher.matches(path.getFileName()))
                    .filter(path -> path.startsWith(voucherId.toString())).findFirst();
            if (targetPath.isEmpty())
                return Optional.empty();
            return Optional.of((Voucher) new ObjectInputStream(new FileInputStream(targetPath.get().toString())).readObject());
        } catch (IOException e) {
            throw new RuntimeException(MessageFormat.format("해당하는 폴더가 존재하지 않습니다. msg : {0}", e.getMessage()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(MessageFormat.format("deserialization에서 문제가 발생했습니다.. msg : {0}", e.getMessage()));
        }
    }

    @Override
    public void deleteAll() {
        try (Stream<Path> files = Files.list(Paths.get(objectFolder.getPath()))) {
            files.filter(path -> matcher.matches(path.getFileName()))
                    .forEach(path -> path.toFile().delete());
        } catch (IOException e) {
            throw new RuntimeException(MessageFormat.format("해당하는 폴더가 존재하지 않습니다. msg : {0}", e.getMessage()));
        }
    }
}
