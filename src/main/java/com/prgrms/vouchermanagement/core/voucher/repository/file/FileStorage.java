package com.prgrms.vouchermanagement.core.voucher.repository.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class FileStorage {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper;
    private final File file;
    private final ReentrantReadWriteLock lock;

    @Autowired
    public FileStorage(@Value("${voucher.repository.path}") String filePath) {
        this.file = new File(filePath);
        this.lock = new ReentrantReadWriteLock();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

    /**
     * 파일 읽기
     */
    public List<VoucherVO> readFile() {
        List<VoucherVO> voucherVOList = new ArrayList<>();
        lock.readLock().lock();
        try {
            voucherVOList = objectMapper.readValue(file, new TypeReference<List<VoucherVO>>(){});
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            lock.readLock().unlock();
        }
        return voucherVOList;
    }


    /**
     * 파일에 저장
     */
    public void saveFile(VoucherVO voucherVO) {
        lock.writeLock().lock();
        try {
            List<VoucherVO> voucherVOList = objectMapper.readValue(file, new TypeReference<List<VoucherVO>>(){});
            voucherVOList.add(voucherVO);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, voucherVOList);
        } catch (IOException e) {
            logger.warn(e.getMessage());
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 전체 삭제
     */
    public void deleteAll() {
        lock.writeLock().lock();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, new ArrayList<>());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 단건 삭제
     */
    public void deleteFile(VoucherVO voucherVO) {
        lock.writeLock().lock();
        try {
            List<VoucherVO> voucherVOList = objectMapper.readValue(file, new TypeReference<List<VoucherVO>>(){});
            voucherVOList.remove(voucherVO);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, voucherVOList);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

}
