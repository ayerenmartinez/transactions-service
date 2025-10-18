package com.nttdata.transactions_service.utils;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    SUCCESS("ok"),
    FAILED("Failed"),
    PENDING("Pending");

    private final String status;

    TransactionStatus(String status) {
        this.status = status;
    }

}
