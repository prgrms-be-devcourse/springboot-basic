package org.prgrms.kdt.exception;

public class WrongSalePriceException extends RuntimeException {
    public WrongSalePriceException(){
        super("할인가가 적절하지 않습니다.");
    }
}