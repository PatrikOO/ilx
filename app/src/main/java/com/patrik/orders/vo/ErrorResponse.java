package com.patrik.orders.vo;

public class ErrorResponse {

    Error error;

    @Override
    public String toString() {
        String result = "";

        if (error != null) {
            if (error.code != null)
                result = result + "Code: " + error.code;

            if (error.message != null)
                result = result + error.message;
        } else {
            result = "unknown error";
        }

        return result;
    }

    public class Error {
        public String message;
        public Integer code;
    }
}