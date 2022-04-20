package com.manager.voucher.domain;

public class Item {
    private int price;

    public Item(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void decresePriceByAmount(int amount) {
        this.price -= amount;
    }

    public void decresePriceByPercent(int percent) {
        this.price =  this.price / 100 * (100-percent);
    }
}
