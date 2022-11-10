package org.prgrms.kdt.exception;

public class WrongSalePrice extends RuntimeException {
    public WrongSalePrice(){
        super("할인가가 적절하지 않습니다.");
    }
}