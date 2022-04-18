package com.waterfogsw.voucher.voucher.controller;

import com.waterfogsw.voucher.voucher.domain.VoucherType;

import java.util.UUID;

public class VoucherDto {

    public static class Request {
        private VoucherType type;
        private Double value;

        public Request(VoucherType type, Double value) {
            this.type = type;
            this.value = value;
        }

        public VoucherType getType() {
            return type;
        }

        public Double getValue() {
            return value;
        }
    }

    public static class Info {
        private UUID id;
        private VoucherType type;
        private Double value;

        public Info(UUID id, VoucherType type, Double value) {
            this.id = id;
            this.type = type;
            this.value = value;
        }

        public UUID getId() {
            return id;
        }

        public VoucherType getType() {
            return type;
        }

        public Double getValue() {
            return value;
        }
    }


    public static class Response {
        private Info info;
        private String returnMessage;

        public Response(Info info, String returnMessage) {
            this.info = info;
            this.returnMessage = returnMessage;
        }

        public Info getInfo() {
            return info;
        }

        public String getReturnMessage() {
            return returnMessage;
        }
    }

}
