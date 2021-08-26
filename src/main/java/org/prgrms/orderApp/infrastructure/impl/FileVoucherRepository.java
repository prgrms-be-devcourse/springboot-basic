package org.prgrms.orderApp.infrastructure.impl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.prgrms.orderApp.OrderAppApplication;
import org.prgrms.orderApp.domain.voucher.repository.VoucherRepository;
import org.prgrms.orderApp.infrastructure.library.monguDb.service.DbManagement;
import org.prgrms.orderApp.infrastructure.library.monguDb.util.DbWriter;
import org.prgrms.orderApp.domain.voucher.model.Voucher;
import org.prgrms.orderApp.domain.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
//@Profile("prod")
public class FileVoucherRepository implements VoucherRepository {

    private final static Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    @Autowired
    DbManagement dbManagement;

    public FileVoucherRepository() {
    }


    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        List<Voucher> voucher_list= new ArrayList<>();
        for(Voucher one_voucher:voucher_list) {
            if (one_voucher.getVoucherId().equals(voucherId))
                return Optional.of(one_voucher);
        }
        return Optional.empty();
    }

    @Override
    public int save(Voucher voucher) throws IOException  {
        Optional<DbWriter> dbWriter = null;

        JSONObject inputData = new JSONObject();
        inputData.put("voucherId",voucher.getVoucherId().toString());
        inputData.put("voucherType",voucher.getClass().getName());
        inputData.put("voucherAmount", voucher.getVoucherAmount());

        try{
            dbWriter = Optional.ofNullable(dbManagement.getConnection().DbConnection().getCollection("vouchers").insertOne(inputData));
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        } finally {
            if(dbWriter.isPresent()){
                dbWriter.get().close();
            }
        }
        return 1;
    }

    @Override
    public List<Voucher> findAll() throws IOException, ParseException {
        List findDataFromMonguDb;
        findDataFromMonguDb = dbManagement.getConnection().DbConnection().getCollection("vouchers").find().stream().toList();

        JSONObject dataOneParse ;
        ArrayList voucher_list = new ArrayList();
        for(Object dataOne : findDataFromMonguDb){
            dataOneParse = (JSONObject) new JSONParser().parse(String.valueOf(dataOne));
            voucher_list.add(VoucherType.getEntityByEntityClassName(dataOneParse));

        }
        return voucher_list;
    }
}
