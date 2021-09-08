package org.prgrms.kdt.assignments;

import org.prgrms.kdt.voucher.Voucher;

/**
* @package : org.prgrms.kdt.assignments
* @name : VoucherFileRepository.java
* @data : 2021/09/05 12:32 오전
* @author : macbook
* @version : 1.0.0
* @modifyed :
**/

public interface FileRepository<E> {

    void saveFile(E e);

    void saveFile(VoucherData voucherData);

    void readFile();


}
