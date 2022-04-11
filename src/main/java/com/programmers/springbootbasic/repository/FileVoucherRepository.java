package com.programmers.springbootbasic.repository;

import com.programmers.springbootbasic.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class FileVoucherRepository implements
        VoucherRepository, InitializingBean, DisposableBean {

    private final static Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    private final String filePath = "C:\\Users\\chlwl\\Desktop\\springboot-basic\\src\\main\\resources\\storage\\record.ser";

    private List<Voucher> list;

    private File file;
    private FileOutputStream fileOutputStream;
    private BufferedOutputStream bufferedOutputStream;
    private ObjectOutputStream objectOutputStream;

    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;
    private ObjectInputStream objectInputStream;


    public FileVoucherRepository() {
        try {
            logger.info("로컬 저장소 접근 시도");
            file = new File(filePath);
            logger.info("로컬 저장소 로드 완료");
        }
        catch (NullPointerException e) {
            logger.info("로컬 저장소 로드 실패");
        }
    }

    @Override
    public void afterPropertiesSet() {
        try {
            fileInputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            objectInputStream = new ObjectInputStream(bufferedInputStream);

            logger.info("데이터 베이스 로드 시도");
            list = (ArrayList)objectInputStream.readObject();
            logger.info("데이터 베이스 로드 완료");

            objectInputStream.close();
            bufferedInputStream.close();
            fileInputStream.close();
        }
        catch (EOFException e) {
            logger.info("기존에 저장 내역이 없으므로 새로운 데이터 베이스 생성");
            list = new ArrayList<>();
        }
        catch (IOException e) {
            logger.error("I/O 스트림 에러");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            logger.error("역직렬화 실패(객체 속성의 변경이 의심 됨)");
            e.printStackTrace();
        }
    }

    @Override
    public Voucher insert(Voucher voucher) {
        list.add(voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(String voucherId) {
        return list.stream()
                .filter(voucher -> voucher.getVoucherId().toString().equals(voucherId))
                .findFirst();
    }

    @Override
    public List<Voucher> findAll() {
        return list;
    }

    @Override
    public void destroy() {
        try {
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            objectOutputStream = new ObjectOutputStream(bufferedOutputStream);

            logger.info("데이터 베이스에 저장 시도");
            objectOutputStream.writeObject(list);
            logger.info("데이터 베이스에 저장 완료");

            objectOutputStream.close();
            bufferedOutputStream.close();
            fileOutputStream.close();
        }
        catch (IOException e) {
            logger.error("I/O 스트림 에러");
            e.printStackTrace();
        }
    }

}
