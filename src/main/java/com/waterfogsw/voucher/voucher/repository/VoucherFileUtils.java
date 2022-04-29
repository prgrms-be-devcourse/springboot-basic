package com.waterfogsw.voucher.voucher.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waterfogsw.voucher.voucher.domain.Voucher;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class VoucherFileUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String FILE_FORMAT = ".json";
    private static final String FILE_EXIST = "File already exist";
    private static final String DIRECTORY_ERROR = "Error Occurred While Initialize Directory";

    private VoucherFileUtils() {
    }

    public static void save(Voucher voucher, String path) {
        final File voucherFile = new File(path + "/" + voucher.getId() + FILE_FORMAT);
        try {
            if (!voucherFile.createNewFile()) {
                throw new IllegalStateException(FILE_EXIST);
            }
            mapper.writeValue(voucherFile, voucher);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public static List<Voucher> findAll(String path) {
        final File folder = new File(path);

        return Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .map(VoucherFileUtils::wrapperReadValue)
                .toList();
    }

    public static void initFilePath(String path) {
        final File repositoryFolder = new File(path);
        if (repositoryFolder.exists()) {
            deleteDirectory(repositoryFolder);
        }

        if (!repositoryFolder.mkdirs()) {
            throw new IllegalStateException(DIRECTORY_ERROR);
        }
    }

    private static void deleteDirectory(File root) {
        final File[] allFiles = root.listFiles();
        if (allFiles != null) {
            Arrays.stream(allFiles)
                    .forEach(VoucherFileUtils::deleteDirectory);
        }

        if (!root.delete()) {
            throw new IllegalStateException(DIRECTORY_ERROR);
        }
    }

    private static Voucher wrapperReadValue(File file) {
        try {
            return mapper.readValue(file, Voucher.class);
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }
}
