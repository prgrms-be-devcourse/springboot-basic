package com.org.prgrms.mission1;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.util.*;


public interface VoucherRepository {


    List<Voucher> findAll() throws IOException, CsvException;

    Voucher insert(Voucher voucher) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;


}
