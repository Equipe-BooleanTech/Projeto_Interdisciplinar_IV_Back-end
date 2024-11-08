package com.example.Restaurantto.PDV.exception.financial;

public class NoFinancialRecordsException extends RuntimeException {
    public NoFinancialRecordsException(String message) {
        super(message);
    }
}
