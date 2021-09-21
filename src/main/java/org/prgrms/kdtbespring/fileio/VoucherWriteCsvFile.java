package org.prgrms.kdtbespring.fileio;

import org.prgrms.kdtbespring.voucher.Voucher;
import org.prgrms.kdtbespring.voucher.VoucherType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

public class VoucherWriteCsvFile implements WriteFile {

    @Override
    public void writeFile(Voucher voucher) {
        Path directoryPath = Paths.get("localStorage");
        File csv = new File(directoryPath + "\\voucher.csv");

        StringBuilder sb = new StringBuilder();
        String lastData = null;

        // 디렉토리가 경로에 없는 경우 생성
        if(!Files.exists(directoryPath)) {
            createDirectory(directoryPath);
        }

        // 파일이 존재하지 않는 경우 첫번째 라인에 컬럼명 지정
        if (!csv.isFile()) {
            sb.append("Index,VoucherType,VoucherId,DiscountValue\n");
        } else {
            // 현재 파일이 존재하면 현재 파일의 마지막 데이터 값 리턴
            lastData = getCsvFileLastData(csv);
        }


        int index = 1;
        // 마지막 데이터 값 중 인덱스를 가져와서 다음 voucher 값이 들어갈 인덱스 지정
        if (lastData != null) {
            index = parseIndex(lastData) + 1;
        }

        // csv파일의 기존 값에 이어쓰려면 위처럼 true를 지정하고, 기존 값을 덮어쓰려면 true를 삭제한다
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true))) {
            long discountValue = voucher.getDiscountValue();
            UUID voucherId = voucher.getVoucherId();
            String className = voucher.getClass().getSimpleName();
            // System.out.println("className = " + className);
            Optional<VoucherType> voucherType = VoucherType.findByName(className);

            String voucherTypeName = null;
            if (voucherType.isPresent()) {
                voucherTypeName = voucherType.get().toString();
            }

            // 한 줄에 넣을 각 데이터 사이에 ,를 넣는다
            sb.append(index).append(",").append(voucherTypeName).append(",").append(voucherId).append(",").append(discountValue).append("\n");

            // 작성한 데이터를 파일에 넣는다
            bw.write(sb.toString());
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    private boolean createDirectory(Path path) {
        Path directoryPath = Paths.get("localStorage");
        if(!Files.exists(directoryPath)) {
            try {
                Files.createDirectory(directoryPath);
                System.out.println("디렉토리 생성");

            } catch (IOException e) {
                System.out.println("디렉토리 생성 실패");
                e.printStackTrace();
                System.exit(1);
            }
        }
        return true;
    }

    // 현재 바우처 목록 중 마지막 데이터 값을 가져온다.
    private String getCsvFileLastData(File csv) {
        try (RandomAccessFile fileHandler = new RandomAccessFile(csv, "r")) {
            long fileLength = fileHandler.length() - 1;
            StringBuilder sb = new StringBuilder();

            for (long filePointer = fileLength; filePointer != -1; filePointer--) {
                fileHandler.seek(filePointer);
                int readByte = fileHandler.readByte();
                if (readByte == 0xA) {
                    if (filePointer == fileLength) {
                        continue;
                    }
                    break;
                } else if (readByte == 0xD) {
                    if (filePointer == fileLength - 1) {
                        continue;
                    }
                    break;
                }
                sb.append((char) readByte);
            }
            // System.out.println(sb);
            return sb.reverse().toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // String 값을 받아 인덱스에 해당하는 값을 반환한다.
    private int parseIndex(String voucherInfo) {
        String[] vouchers = voucherInfo.split(",");
        return Integer.parseInt(vouchers[0]);
    }
}
