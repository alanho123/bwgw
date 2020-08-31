package com.example.bwgw.service;

public enum PaymentStatus {
    OK(1),
    WAIT(2);

    int status;
    PaymentStatus(int status) {
        this.status = status;
    }
}
