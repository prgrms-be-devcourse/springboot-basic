package org.prgrms.orderApp.voucher;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.prgrms.orderApp.util.library.monguDb.DbManagement;
import org.prgrms.orderApp.util.library.monguDb.DbWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("prod")
public class FileVoucherRepository implements VoucherRepository {
    private final static Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    private DbManagement dbManagement;

    public FileVoucherRepository(DbManagement dbManagement) {
        this.dbManagement = dbManagement;
    }


    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        List<Voucher> voucher_list= new ArrayList<>();

        return voucher_list.stream()
                .filter(x ->x.getVoucherId().equals(voucherId))
                .findFirst();
    }

    @Override
    public int save(Voucher voucher) throws IOException {
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
            voucher_list.add(VoucherType.getVoucherTypeByVoucherClassName((String)dataOneParse.get("voucherType"))
                    .get().getVoucher(UUID.fromString((String)dataOneParse.get("voucherId")),
                            (long) dataOneParse.get("voucherAmount")));

        }
        return voucher_list;
    }
}
