package org.prgrms.kdtspringdemo.domain.voucher;

public abstract class Voucher {
    String voucherId;

    public String getVoucherId(){return voucherId;}
    public void setVoucherId(String id){voucherId = id;}

    public abstract long discount(long beforeDiscount);

}
