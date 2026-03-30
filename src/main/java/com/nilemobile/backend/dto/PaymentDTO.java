package com.nilemobile.backend.dto;


public abstract class PaymentDTO {

    public static class VNPayResponse {
        public String code;
        public String message;
        public String paymentUrl;

        public VNPayResponse(String code, String message, String paymentUrl) {
            this.code = code;
            this.message = message;
            this.paymentUrl = paymentUrl;
        }

        public VNPayResponse(Builder builder) {
            this.code = builder.code;
            this.message = builder.message;
            this.paymentUrl = builder.paymentUrl;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public String getPaymentUrl() {
            return paymentUrl;
        }

        public static class Builder {
            private String code;
            private String message;
            private String paymentUrl;

            public Builder code(String code) {
                this.code = code;
                return this;
            }

            public Builder message(String message) {
                this.message = message;
                return this;
            }

            public Builder paymentUrl(String paymentUrl) {
                this.paymentUrl = paymentUrl;
                return this;
            }

            public VNPayResponse build() {
                return new VNPayResponse(this);
            }
        }

        public static Builder builder() {
            return new Builder();
        }
    }

}
