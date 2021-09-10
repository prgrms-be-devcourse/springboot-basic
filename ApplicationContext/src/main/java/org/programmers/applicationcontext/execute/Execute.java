package org.programmers.applicationcontext.execute;

import org.programmers.applicationcontext.OutPutView;
import org.programmers.applicationcontext.voucher.Voucher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public interface Execute {
    boolean execute(List<Voucher> voucherList,
                       AnnotationConfigApplicationContext commandLineContext,
                       BufferedReader br, OutPutView outPutView) throws IOException;
}
