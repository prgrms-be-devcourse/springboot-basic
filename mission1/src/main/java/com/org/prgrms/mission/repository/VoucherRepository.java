package com.org.prgrms.mission.repository;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.org.prgrms.mission.model.Voucher;

import java.io.IOException;
import java.util.*;


public interface VoucherRepository {


    List<Voucher> findAll() throws IOException, CsvException;

    Voucher insert(Voucher voucher) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;


}
