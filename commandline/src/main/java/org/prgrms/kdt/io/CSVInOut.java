package org.prgrms.kdt.io;

import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.FileInOutException;
import org.prgrms.kdt.exception.FileNotFoundCustomException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVInOut {
    private final String path;

    public CSVInOut(String path) {
        this.path = path;
        initFile(path);
    }

    public List<Voucher> readCSV() {
        List<Voucher> csvList = new ArrayList<>();
        File csv = new File(path);
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null) {
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundCustomException(ErrorCode.FILE_NOT_FOUND_EXCEPTION.getMessage());
        } catch (IOException e) {
            throw new FileInOutException(ErrorCode.FILE_INOUT_EXCEPTION.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                throw new FileInOutException(ErrorCode.FILE_INOUT_EXCEPTION.getMessage());
            }
        }
        return csvList;
    }

    public void writeCSV(Voucher voucher) {
        File csv = new File(path);
        BufferedWriter bw = null;
        try {
            csv.createNewFile();
            bw = new BufferedWriter(new FileWriter(csv, true));

            String data = voucher.toString();
            bw.write(data);
            bw.newLine();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundCustomException(ErrorCode.FILE_NOT_FOUND_EXCEPTION.getMessage());
        } catch (IOException e) {
            throw new FileInOutException(ErrorCode.FILE_INOUT_EXCEPTION.getMessage());
        } finally {
            try {
                if (bw != null) {
                    bw.flush();
                    bw.close();
                }
            } catch (IOException e) {
                throw new FileInOutException(ErrorCode.FILE_INOUT_EXCEPTION.getMessage());
            }
        }
    }

    private static void initFile(String path) {
        Path filePath = Paths.get(path);
        try {
            Files.deleteIfExists(filePath);

        } catch (IOException e) {
            throw new FileInOutException(ErrorCode.FILE_INOUT_EXCEPTION.getMessage());
        }
    }
}
