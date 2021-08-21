package org.prgrms.kdt.kdtspringorder.common.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.kdtspringorder.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.kdtspringorder.voucher.domain.PercentDiscountVoucher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class TextFileIoTest {

    private FileIo fileIo;
    private List<Object> oList;

    @BeforeEach
    void setUp(){

        fileIo = new FileObjectIo();
        oList = new ArrayList<>();
        oList.add(new FixedAmountVoucher(UUID.randomUUID(), 100));
        oList.add(new FixedAmountVoucher(UUID.randomUUID(), 200));
        oList.add(new PercentDiscountVoucher(UUID.randomUUID(), 30));

    }

    @Test
    void write() {
        fileIo.write(oList);
    }

    @Test
    void readAllLines() {

        List<Object> objects = fileIo.readAllLines();
        objects.forEach(s -> System.out.println(s));

    }

}
