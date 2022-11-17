//package com.programmers.VoucherManagementApplication.dto;
//
//public class OriginPrice {
//
//    private final long originPrice;
//
//    public OriginPrice(String price) {
//        validate(price);
//        this.originPrice = Long.parseLong(price);
//    }
//
//    private void validate(String price) {
//        validatePriceIsNotNumber(price);
//        validatePriceIsZero(price);
//        validatePriceIsMinus(price);
//    }
//
//    private void validatePriceIsMinus(String price) {
//        if (Long.parseLong(price) < 0) throw new IllegalArgumentException("\nThe price cannot be negative.");
//    }
//
//    private void validatePriceIsZero(String originPrice) {
//        if (Long.parseLong(originPrice) == 0) throw new IllegalArgumentException("\nAmount should not be zero");
//    }
//
//    private void validatePriceIsNotNumber(String originPrice) {
//        if (!isDigit(originPrice)) throw new IllegalArgumentException("\nThe price is an integer.");
//    }
//
//    private boolean isDigit(String originPrice) {
//        for (char x : originPrice.toCharArray()) {
//            if (!Character.isDigit(x)) return false;
//        }
//        return true;
//    }
//
//    public long getOriginPrice() {
//        return originPrice;
//    }
//}
