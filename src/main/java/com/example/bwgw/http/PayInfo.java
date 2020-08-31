package com.example.bwgw.http;

import lombok.*;

@Data
@AllArgsConstructor
public class PayInfo {
    private String paymentUrl;
    private String txId;
    private String orderNo;
}
