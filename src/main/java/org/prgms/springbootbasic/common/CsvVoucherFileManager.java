package org.prgms.springbootbasic.common;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.prgms.springbootbasic.common.Util.CSV_PATTERN;

@Component
@Slf4j
@Profile({"dev", "prod"})
public class CsvVoucherFileManager {
    private static final String FILE_PATH = "./src/main/resources/voucher.csv";
    private static final int UUID_IDX = 0;
    private static final int TYPE_IDX = 1;
    private static final int DISCOUNT_VALUE_IDX = 2;

    // file: UUID, Type, discountVal(fixed, percentage)

    public List<Voucher> read(){
        log.debug("read started.");

        List<Voucher> vouchers = new ArrayList<>();
        File file = new File(FILE_PATH);

        if(filePathNotExist(file)) return vouchers;

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(FILE_PATH), StandardCharsets.UTF_8))){
            String line = br.readLine();

            while((line = br.readLine()) != null)
                addVoucher(line, vouchers);
        }catch (IOException e){
            log.error("Unable to read the file due to an unexpected error.");
            throw new RuntimeException(e);
        }
        return vouchers;
    }

    public void write(List<Voucher> vouchers){
        log.debug("write started");

        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(FILE_PATH), StandardCharsets.UTF_8))){
            bw.write("UUID,Type,DiscountValue");
            bw.newLine();

            StringBuilder sb = new StringBuilder();

            for (Voucher v : vouchers){
                sb.append(v.getVoucherId());
                sb.append(",");
                sb.append(v.getClass().getSimpleName());
                sb.append(",");
                sb.append(v.getDiscountAmount());
                sb.append(System.lineSeparator());
            }
            log.debug("String value is {}", sb);
            bw.write(sb.toString());
        }catch (FileNotFoundException e){
            log.error("File is not found.");
            throw new RuntimeException("File is not found.");
        }
        catch (IOException e){
            log.error("IOException.");
            throw new RuntimeException("Writing error occurred.");
        }
    }


    private boolean filePathNotExist(File file){
        try {
            if(file.getParentFile().mkdirs())
                return true;

            file.createNewFile();
        }catch (SecurityException e){
            log.error("Security problem occurred.");
            throw e;
        }catch (IOException e){
            log.error("An error occurred during file checking.");
            throw new RuntimeException(e);
        }
        log.info("File directory created.");
        return false;
    }

    private void addVoucher(String line, List<Voucher> vouchers){
        log.debug("line = {}", line);

        List<String> splitLine = Arrays.stream(line.split(CSV_PATTERN))
                .map(s -> s.replaceAll("\"", "")).toList();

        VoucherType[] voucherTypes = VoucherType.values();
        for (int idx = 0; idx < voucherTypes.length; idx++){
            String voucherType = voucherTypes[idx].getDisplayName();
            String curStringType = splitLine.get(TYPE_IDX);
            if(curStringType.equals(voucherType)){
                log.info("This voucher type is {}", voucherType);
                vouchers.add(voucherTypes[idx].create(
                        UUID.fromString(splitLine.get(UUID_IDX)),
                        Long.parseLong(splitLine.get(DISCOUNT_VALUE_IDX))
                ));
                break;
            }
            if (idx == voucherTypes.length - 1) {
                log.error("Invalid voucher type.");
                throw new IllegalArgumentException("Invalid voucher type.");
            }
        }
    }
}
