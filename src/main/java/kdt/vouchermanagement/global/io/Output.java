package kdt.vouchermanagement.global.io;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.global.response.Response;

import java.util.List;

public interface Output {

    void printMenu();

    void printType();

    void printValue();

    void printError(Response<String> response);

    void printCreateVoucher(Response<Voucher> response);

    void printListVouchers(Response<List> response);

    void printNoneMenu();
}
