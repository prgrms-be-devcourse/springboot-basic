package org.prgrms.kdt.wallet;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 10:45 오전
 */
public class WalletDto {

    private String customerId;
    private String voucherId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

}
