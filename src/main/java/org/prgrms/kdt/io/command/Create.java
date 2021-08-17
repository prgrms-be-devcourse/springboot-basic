package org.prgrms.kdt.io.command;

import org.prgrms.kdt.voucher.Voucher;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 2:29 오전
 */
public class Create implements Command {

    private Voucher voucher;

    public Create(Voucher voucher) {
        this.voucher = voucher;
    }

    @Override
    public void execute() {
        //todo : 바우처 저장
    }
}
