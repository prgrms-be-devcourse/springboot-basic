package org.prgrms.kdt.io;

import org.prgrms.kdt.exception.*;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.util.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVInOut {
    public static final int BRACKET_END = 1;
    private static final int NOT_FOUND_RESULT = -1;
    private static final int EQUAL_NEXT_INDEX = 1;

    private static final Logger logger = LoggerFactory.getLogger(CSVInOut.class);

    private static final String EQUAL = "=";
    private final String path;

    public CSVInOut(String path) {
        this.path = path;
    }

    public Voucher findVoucher(long voucherId) {
        File csv = new File(path);
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null) {
                if (matchId(line, voucherId)) {
                    return stringToVoucher(line);
                }
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
        throw new NotFoundVoucherException(ErrorCode.NOT_FOUND_VOUCHER_EXCEPTION.getMessage());
    }

    public List<Voucher> readAll() {
        List<Voucher> csvList = new ArrayList<>();
        File csv = new File(path);
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null) {
                Voucher voucher = stringToVoucher(line);
                csvList.add(voucher);
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

    public void deleteAllFile() {
        File csv = new File(path);
        BufferedWriter bw = null;
        try {
            csv.createNewFile();
            bw = new BufferedWriter(new FileWriter(csv));
            bw.write("");
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

    private static Voucher stringToVoucher(String input) {
        VoucherType voucherType = VoucherType.selectVoucherTypeFromTypeName(input);

        long voucherId = Long.parseLong(parsingData(input, "voucherId"));
        long discountDegree = Long.parseLong(parsingData(input, voucherType.getDiscountType()));

        return VoucherType.createVoucher(voucherType, voucherId, discountDegree);
    }

    private static String parsingData(String input, String key) {
        if (input.indexOf(key) == NOT_FOUND_RESULT) {
            throw new NotFoundValue(ErrorCode.NOT_FOUND_VALUE_EXCEPTION.getMessage());
        }

        int startIndex = input.indexOf(key);
        input = input.substring(startIndex);
        int endIndex = input.indexOf(",");
        if (endIndex == NOT_FOUND_RESULT) {
            endIndex = input.length() - BRACKET_END;
        }
        String result = input.substring(input.indexOf(EQUAL) + EQUAL_NEXT_INDEX, endIndex);

        return result;
    }

    private boolean matchId(String line, long voucherId) {
        return voucherId == Long.parseLong(parsingData(line, "voucherId"));
    }

    public void voucherUpdate(long voucherId, long newDiscountDegree) {
        File csv = new File(path);
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null) {
                if (matchId(line, voucherId)) {
                    VoucherType voucherType = VoucherType.selectVoucherTypeFromTypeName(line);
                    String oldDiscountDegree = parsingData(line, voucherType.getDiscountType());
                    line.replaceAll(oldDiscountDegree, String.valueOf(newDiscountDegree));
                    return;
                }
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
        throw new NotFoundVoucherException(ErrorCode.NOT_FOUND_VOUCHER_EXCEPTION.getMessage());
    }

}
