package org.prgrms.kdtspringdemo.voucher.repository;

import org.prgrms.kdtspringdemo.io.file.CSVReader;
import org.prgrms.kdtspringdemo.io.file.CSVWriter;
import org.prgrms.kdtspringdemo.io.file.CsvDto;
import org.prgrms.kdtspringdemo.voucher.VoucherCreator;
import org.prgrms.kdtspringdemo.voucher.model.Voucher;
import org.prgrms.kdtspringdemo.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
@Repository
@Profile("default")
public class FileVoucherRepository implements VoucherRepository {
    private final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private final CSVReader csvReader ;
    private final CSVWriter csvWriter ;

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    private final VoucherCreator voucherCreator;
    @PostConstruct
    public void initStorage() throws IllegalStateException{

        try {
            CsvDto csvDto = csvReader.readCSV();
            List<String[]> csvValues = csvDto.value;
            for (String[] strings : csvValues) {
                UUID uuid = UUID.fromString(strings[0]);
                long value = Long.parseLong(strings[1]);
                VoucherType voucherType = VoucherType.getTypeByName(strings[2]);
                Voucher myVoucher = voucherCreator.createVoucher(voucherType, value);
                storage.put(uuid, myVoucher);
            }
        }catch (IllegalArgumentException e){
            logger.error("[voucher load fail]  파일에 가질 수 없는 값을 가지고 있는 열이 있어 불러오는데 실패했습니다. 잘못된 값이 있는지 확인해 주세요");
//            e.printStackTrace();
//            throw new IllegalStateException("파일에 가질 수 없는 값을 가지고 있는 열이 있습니다.");
        } catch (FileNotFoundException e) {
            logger.error("[voucher load fail] voucher_list.csv 파일을 찾을 수 없습니다. 확인해 주세요.");
//            e.printStackTrace();
//            throw new IllegalStateException("파일을 찾을 수 없습니다.");
        } catch (IOException e) {
            logger.error(e.getMessage());
//            e.printStackTrace();
//            throw new RuntimeException(e);
        }
    }
    public FileVoucherRepository(VoucherCreator voucherCreator,
                                 @Value("${voucher.path}") String path,
                                 @Value("${voucher.append}") boolean append) {
        logger.info("파일 입출력 경로: {}",path);
        logger.info("파일 append 여부 : {}",append);
        this.voucherCreator = voucherCreator;
        csvReader = new CSVReader(path);
        csvWriter = new CSVWriter(path, append);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        CsvDto csvDto = voucher.makeCsvDtoFromVoucher();
        csvWriter.writeCSV(csvDto);
        return storage.put(voucher.getVoucherId(),voucher);
    }

    @Override
    public List<Voucher> findAllVaucher() {
        return new ArrayList<>(storage.values());
    }
}
